package danycode.dsm.service;

import danycode.dsm.dto.TrainingPackageDto;
import danycode.dsm.mapper.StudentMapper;
import danycode.dsm.repository.TrainingPackageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class TrainingPackageServiceImpl implements TrainingPackageService{
    private final TrainingPackageRepository trainingPackageRepository;
    private final StudentMapper studentMapper;
    @Override
    public List<TrainingPackageDto> getListTrainingPackages() {
        var trainingPackagesList = trainingPackageRepository.findAll();
        return studentMapper.mapToTrainingPackageDto(trainingPackagesList);
    }
}
