package danycode.dsm.controller;

import danycode.dsm.dto.*;
import danycode.dsm.service.StudentNotFoundException;
import danycode.dsm.service.StudentService;
import danycode.dsm.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller

public class StudentController {
    private final StudentService studentService;
    private final UsersPresentationProps usersPresentationProps;


    @GetMapping("/students/home/{id}")
    public String studentDetails(@PathVariable(name = "id") Long id, Model model) throws StudentNotFoundException {
        StudentDto studentDto = studentService.getStudentDetails(id);
        model.addAttribute("student", studentDto);

        return "student/student_details";
    }


    @GetMapping(value = "/student/new")
    public String createNewUser(Model model) {
        StudentDto studentDto = new StudentDto();
        model.addAttribute("student", studentDto);
        return "student/student_form.html";
    }

    @PostMapping("/student/save")
    public String studentSave(@ModelAttribute("student") StudentDto studentDto,
                              RedirectAttributes redirectAttributes,
                              @RequestParam("image") MultipartFile multipartFile) throws IOException {

        if (!multipartFile.isEmpty()) {
            studentService.saveStudent(studentDto, new PhotoFileDto(multipartFile.getInputStream()));
        } else {

            studentService.saveStudent(studentDto, null);
        }
        redirectAttributes.addFlashAttribute("message", "The user has been saved successfully!");
        return "redirect:/students";
    }

    @GetMapping("/students")
    public String listAllUsers(Model model) {
        return listByPage(1, model, UserSortField.firstName, SortDirection.asc, null);
    }

    @GetMapping(value = "/students/page/{pageNumber}")
    public String listByPage(@PathVariable("pageNumber") int pageNumber, Model model,
                             @RequestParam("sortField") UserSortField sortingField,
                             @RequestParam("sortDir") SortDirection sortingDir,
                             @RequestParam(value = "keyword", required = false) String keyword) {
        log.info("sort field: {}, sort dir: {} ", sortingField, sortingDir);
        PageDto<StudentDto> pageStudent = studentService.listByPage(pageNumber, sortingField, sortingDir, keyword);
        List<StudentDto> userDtoList = pageStudent.getContent();
        long startCount = (long) (pageNumber - 1) * usersPresentationProps.getPageSize() + 1;
        long endCount = startCount + UserServiceImpl.USER_PER_PAGE - 1;
        if (endCount > pageStudent.getTotalElements()) {
            endCount = pageStudent.getTotalElements();
        }
        String reverseSortDir = sortingDir == SortDirection.asc ? "desc" : "asc";
        model.addAttribute("totalPages", pageStudent.getTotalPages());
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("lastPage", pageStudent.getTotalPages());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", pageStudent.getTotalElements());
        model.addAttribute("students", userDtoList);
        model.addAttribute("sortField", sortingField.name());
        model.addAttribute("sortDir", sortingDir.name());
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("keyword", keyword);
        return "student/students";
    }

    @GetMapping("/student/trainingPackage/{id}")
    public String getStudentTrainingPackage(@PathVariable(name = "id") Long id, Model model) {
        StudentDto studentDto = studentService.getStudentDetails(id);
        model.addAttribute("student", studentDto);
        return "student/student_training";
    }


}
