package danycode.dsm.service;

import danycode.dsm.dto.*;
import danycode.dsm.entity.SimplePhotoFile;
import danycode.dsm.entity.Student;
import danycode.dsm.entity.StudentTrainingPackage;
import danycode.dsm.entity.StudentTrainingPackageStatus;
import danycode.dsm.mapper.PageMapper;
import danycode.dsm.mapper.StudentMapper;
import danycode.dsm.repository.StudentRepository;
import danycode.dsm.repository.TrainingPackageRepository;
import danycode.dsm.repository.UserPhotoRepository;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private static final int STUDENT_PER_PAGE = 4;
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final TimeProvider timeProvider;
    private final ServiceEventPublisher serviceEventPublisher;
    private final UserPhotoFileProcessor userPhotoFileProcessor;
    private final UserPhotoRepository userPhotoRepository;
    private final TrainingPackageRepository trainingPackageRepository;





    @Override
    public StudentDto getStudentDetails(Long id) throws StudentNotFoundException {
        return studentRepository.findById(id)
                .map(studentMapper::mapToStudentDto)
                .orElseThrow(() -> new StudentNotFoundException("Could not find student with id: " + id));
    }

    @Override
    public PageDto<StudentDto> listByPage(int pageNumber, UserSortField sortingField, SortDirection sortingDir, String keyword) {
        Page<Student> studentPage;
        Sort sort = Sort.by(sortingField.name());
        sort = sortingDir == SortDirection.desc ? sort.descending() : sort.ascending();
        Pageable pageable = PageRequest.of(pageNumber - 1, STUDENT_PER_PAGE, sort);
        if (keyword != null) {
            studentPage = studentRepository.findPageByKeyword(keyword, pageable);
        } else {
            studentPage = studentRepository.findAll(pageable);
        }
        return PageMapper.mapToPageDto(studentPage.map(studentMapper::mapToStudentDto));

    }

    @Override
    @Transactional
    public StudentDto saveStudent(StudentDto studentDto, @Nullable PhotoFileDto studentPhoto) {

        var processUserPhoto = userPhotoFileProcessor.process(studentPhoto);
        Student stud = Optional.ofNullable(studentDto.getId())
                .flatMap(studentRepository::findById)
                .orElseGet(Student::new);
        stud.setEnrollDate(timeProvider.localDate());
        studentMapper.mapToStudentJpa(studentDto,stud);
        //encodePassword(user);

        Student savedStudent = studentRepository.save(stud);
        serviceEventPublisher.publishStudentCreated(savedStudent.getId());

        long id = savedStudent.getId();
        Optional.ofNullable(processUserPhoto).map(PhotoFileDto::getInputStream)
                .map(SimplePhotoFile::new)
                .ifPresent(photoFile -> userPhotoRepository.save(photoFile, id));
        return studentDto;}

    @Override
    @Transactional
    public void createStudentTrainingPackage(Long studentId, StudentTrainingPackageCreationDto creationDto) {
        var student = studentRepository.findById(studentId).orElseThrow(StudentNotFoundException::new);
        var trainingPackage = trainingPackageRepository.findById(creationDto.getTrainingPackageId())
                .orElseThrow(TrainingPackageNotFoundException::new);
        var studentTrainingPackage = new StudentTrainingPackage();
        studentTrainingPackage.setTrainingPackage(trainingPackage);
        studentTrainingPackage.setInstructorName(creationDto.getInstructorName());
        studentTrainingPackage.setStatus(StudentTrainingPackageStatus.INCOMPLETE);
        student.addStudentTrainingPackage(studentTrainingPackage);

    }

    @Override
    @Transactional
    public void updateStudentTrainingPackage(Long studentId, StudentTrainingPackageDto studentTrainingPackageDto) {
        var student =  studentRepository.findById(studentId).orElseThrow(StudentNotFoundException::new);
        student.getStudentTrainingPackages().stream()
                .max(Comparator.comparing(StudentTrainingPackage::getId))
                .ifPresent(studentTrainingPackage -> studentTrainingPackage.setStatus(
                        studentMapper.mapToStudentTrainingPackageStatus(studentTrainingPackageDto.getStatus())));
    }


}
