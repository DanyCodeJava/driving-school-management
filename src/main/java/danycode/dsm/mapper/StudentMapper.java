package danycode.dsm.mapper;

import danycode.dsm.dto.*;
import danycode.dsm.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    @Mapping(target = "lastTrainingPackage",source = "studentTrainingPackages")
    StudentDto mapToStudentDto(Student student);
    @Mapping(target = "enrollDate",ignore = true)
    @Mapping(target = "studentTrainingPackages", ignore = true)
     void mapToStudentJpa(StudentDto studentDto, @MappingTarget Student studentEntity);
    AddressDto mapToAddressDto(Address address);
    Address mapToAddressJpa(AddressDto addressDto);
    default StudentTrainingPackageDto mapToLastStudentTrainingPackage(List<StudentTrainingPackage> studentTrainingPackages){
        return Optional.ofNullable(studentTrainingPackages)
                .stream()
                .flatMap(Collection::stream)
                .sorted(Comparator.comparing(StudentTrainingPackage::getId).reversed())
                .map(this::mapToStudentTrainingPackageDto)
                .findFirst()
                .orElse(null);
    }
    @Mapping(target = "cost", source = "trainingPackage.cost")
    @Mapping(target = "duration", source = "trainingPackage.duration")
    @Mapping(target = "carType", source = "trainingPackage.carType")
    StudentTrainingPackageDto mapToStudentTrainingPackageDto(StudentTrainingPackage studentTrainingPackage);

    StudentTrainingPackageStatusDto mapToStudentTrainingPackageStatusDto(StudentTrainingPackageStatus studentTrainingPackageStatus);
    List<TrainingPackageDto> mapToTrainingPackageDto(List<TrainingPackage> trainingPackage);

    StudentTrainingPackageStatus mapToStudentTrainingPackageStatus(StudentTrainingPackageStatusDto status);
}
