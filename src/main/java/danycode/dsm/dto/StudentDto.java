package danycode.dsm.dto;

import danycode.dsm.entity.StudentTrainingPackage;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StudentDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate birthDate;
    private LocalDate enrollDate;
    private String nin;
    private AddressDto address;
    private StudentTrainingPackageDto lastTrainingPackage;



}
