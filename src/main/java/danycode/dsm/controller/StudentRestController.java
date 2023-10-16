package danycode.dsm.controller;

import danycode.dsm.dto.StudentTrainingPackageCreationDto;
import danycode.dsm.dto.StudentTrainingPackageDto;
import danycode.dsm.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentRestController {
    private final StudentService studentService;

    @PostMapping("{studentId}/training-packages")
    @ResponseStatus(code = HttpStatus.CREATED)
    void createStudentTrainingPackage(@PathVariable Long studentId,@RequestBody @Valid StudentTrainingPackageCreationDto creationDto){
        studentService.createStudentTrainingPackage(studentId, creationDto);

    }

    @PatchMapping("/{studentId}/training-packages/{trainingPackageId}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void updateStudentTrainingPackageStatusById(@PathVariable("studentId") Long studentId ,
                                                       @PathVariable("trainingPackageId") Long trainingPackageId,
                                                       @RequestBody StudentTrainingPackageDto studentTrainingPackageDto){
         studentService.updateStudentTrainingPackage(studentId,studentTrainingPackageDto);
    }
}
