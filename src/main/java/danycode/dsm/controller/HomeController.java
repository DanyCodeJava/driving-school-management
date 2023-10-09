package danycode.dsm.controller;

import danycode.dsm.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/api/drivingschool")
public class HomeController {
    private StudentService studentService;



    @GetMapping("/home")
    public String getHomePage(Model model){
     //Long instructorNb = instructorService.getNumberOfInstructorPerMonth();
  //   Long studentsNumber =studentService.getNumberOfStudentsPerMonth();
     //model.addAttribute("numberOfInstructors", instructorNb);
    // model.addAttribute("numberOfStudents", studentsNumber);
        return "index";
    }
}
