package danycode.dsm.service;


import danycode.dsm.dto.AddressDto;
import danycode.dsm.dto.PhotoFileDto;
import danycode.dsm.dto.StudentDto;
import danycode.dsm.entity.Student;
import danycode.dsm.mapper.StudentMapper;
import danycode.dsm.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {
    @InjectMocks
    private StudentServiceImpl subject;
    @Mock
    private StudentRepository studentRepository;
    @SuppressWarnings("WrongUsageOfMappersFactory")
    @Spy
    private StudentMapper studentMapper = Mappers.getMapper(StudentMapper.class);
    @Mock
    private  UserPhotoFileProcessor userPhotoFileProcessor;
    @Mock
    private TimeProvider timeProvider;
    @Mock
    private ServiceEventPublisher serviceEventPublisher;
    @Test
    void saveStudent(){
        var studentDto = new StudentDto();
        studentDto.setFirstName("anas");
        studentDto.setLastName("selenium");
        studentDto.setEmail("anas@gmail.com");
        studentDto.setAddress(AddressDto.builder()
                        .line1("strada sperantei")
                        .line2("la parter")
                        .city("cluj")
                        .country("romania")
                        .postalCode("40002")
                .build());
        var students = new Student[1];
        doAnswer(invocation -> {
           invocation.callRealMethod();
           var student = invocation.getArgument(1,Student.class);
            students[0] = student;
            return student;
        }).when(studentMapper).mapToStudentJpa(any(),any());
        doAnswer(invocation -> {
           var student =  invocation.getArgument(0, Student.class);
           student.setId(11L);
           return student;
        }).when(studentRepository).save(any());
        var photoFileDto = new PhotoFileDto(new ByteArrayInputStream(new byte[0]));
        assertThatCode(()->subject.saveStudent(studentDto,photoFileDto)).doesNotThrowAnyException();
        verify(userPhotoFileProcessor).process(photoFileDto);
        verify(timeProvider).localDate();
        verify(studentMapper).mapToStudentJpa(eq(studentDto),any());
        verify(studentRepository).save(students[0]);
        verify(serviceEventPublisher).publishStudentCreated(11L);
        assertThat(students[0]).satisfies(student -> {
           assertThat(student.getLastName()).isEqualTo(studentDto.getLastName());
            assertThat(student.getFirstName()).isEqualTo(studentDto.getFirstName());
            assertThat(student.getEmail()).isEqualTo(studentDto.getEmail());
            assertThat(student.getAddress()).satisfies(
                    address -> {
                        var addressDto = studentDto.getAddress();
                        assertThat(address.getLine1()).isEqualTo(addressDto.getLine1());
                        assertThat(address.getLine2()).isEqualTo(addressDto.getLine2());
                        assertThat(address.getCity()).isEqualTo(addressDto.getCity());
                        assertThat(address.getCountry()).isEqualTo(addressDto.getCountry());
                        assertThat(address.getPostalCode()).isEqualTo(addressDto.getPostalCode());
                    });
        });
    }
}