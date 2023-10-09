package danycode.dsm.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="training_package")
public class TrainingPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "training_package_id")
    @ToString.Include
    @EqualsAndHashCode.Include
    private Long id;
    @Column(name = "cost")
    private BigDecimal cost;
    private String carType;
    @Column(name = "duration")
    private int duration;
    @OneToMany(mappedBy = "trainingPackage")
    private List<StudentTrainingPackage> studentTrainingPackages = new ArrayList<>();
}
