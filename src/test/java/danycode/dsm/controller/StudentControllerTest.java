package danycode.dsm.controller;

import danycode.dsm.dto.StudentDto;
import danycode.dsm.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentControllerTest {
    @InjectMocks
    private StudentController subject; //subiectul testului
    @Mock
    private StudentService studentService;
    @Mock
    private RedirectAttributes redirectAttributes;

    @Test
    void saveStudent() throws IOException {
        var studentDto = new StudentDto();
        assertThat(subject.studentSave(studentDto,redirectAttributes,new MockMultipartFile("photo.png",new byte[0])))
                .isEqualTo("redirect:/students");
        verify(studentService).saveStudent(eq(studentDto),any());
    }

}