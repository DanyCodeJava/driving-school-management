package danycode.dsm.service;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import danycode.dsm.dto.*;

public interface StudentService {

   // void saveStudent(StudentDto studentDto);

    StudentDto getStudentDetails(Long id) throws StudentNotFoundException;

    PageDto<StudentDto> listByPage(int pageNumber, UserSortField sortingField, SortDirection sortingDir, String keyword);

    @CanIgnoreReturnValue
    StudentDto saveStudent(StudentDto studentDto, PhotoFileDto studentPhoto);

    void createStudentTrainingPackage(Long studentId, StudentTrainingPackageCreationDto creationDto);


    void updateStudentTrainingPackage(Long studentId, StudentTrainingPackageDto studentTrainingPackageDto);
}
