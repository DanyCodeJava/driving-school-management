package danycode.dsm.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "student_training_package")
public class StudentTrainingPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ToString.Include
    @EqualsAndHashCode.Include
    private long id;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StudentTrainingPackageStatus status;
    private String instructorName;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
    @ManyToOne
    @JoinColumn(name = "training_package_id")
    private TrainingPackage trainingPackage;
}
