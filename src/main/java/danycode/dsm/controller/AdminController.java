package danycode.dsm.controller;

import danycode.dsm.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    //private InstructorService instructorService;
    private StudentService studentService;

    public AdminController( StudentService studentService) {
        //this.instructorService = instructorService;
        this.studentService = studentService;
    }

    @GetMapping("/home")
    public String getHomePage(Model model){
       // Long instructorNb = instructorService.getNumberOfInstructorPerMonth();
       // Long studentsNumber =studentService.getNumberOfStudentsPerMonth();
        //model.addAttribute("numberOfInstructors", instructorNb);
       // model.addAttribute("numberOfStudents", studentsNumber);
        return "admin/indexAdmin";
    }
}
