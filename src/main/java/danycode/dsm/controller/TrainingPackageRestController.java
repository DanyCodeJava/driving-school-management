package danycode.dsm.controller;

import danycode.dsm.dto.TrainingPackageListDto;
import danycode.dsm.service.TrainingPackageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/training-packages")
public class TrainingPackageRestController {

    private final TrainingPackageService trainingPackageService;
    @GetMapping
    public TrainingPackageListDto getTrainingPackagesList(){
        var trainingPackageList = new TrainingPackageListDto();
        var trainingPackage  = trainingPackageService.getListTrainingPackages();
        trainingPackageList.setItems( trainingPackage);
        return trainingPackageList;
    }

}
