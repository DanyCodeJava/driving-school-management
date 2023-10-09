package danycode.dsm.entity;

import jakarta.persistence.*;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Data
@Builder
public class Address {
    @Column(name = "line1", nullable = false, length = 45)
    private String line1;
    @Column(name = "line2", length = 45)
    private String line2;
    @Column(name = "postalCode", length = 15)
    private String postalCode;
    @Column(name = "city", length = 15)
    private String city;
    @Column(name = "country", length = 15)
    private String country;


}
