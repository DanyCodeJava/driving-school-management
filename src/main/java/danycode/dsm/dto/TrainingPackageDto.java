package danycode.dsm.dto;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class TrainingPackageDto {
    private long id;
    private String carType;
    private BigDecimal cost;
    private int duration;

}
