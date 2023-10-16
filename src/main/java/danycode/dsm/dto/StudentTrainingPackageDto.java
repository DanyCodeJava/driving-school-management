package danycode.dsm.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StudentTrainingPackageDto {
    private Long id;
    private BigDecimal cost;
    private String instructorName;
    private int duration;
    private String carType;
    private StudentTrainingPackageStatusDto status;
}
