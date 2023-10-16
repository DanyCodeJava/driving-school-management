package danycode.dsm.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class StudentTrainingPackageCreationDto {
    @NotNull
    private Long trainingPackageId;
    @NotNull
    @Size(min = 1)
    private String instructorName;

}
