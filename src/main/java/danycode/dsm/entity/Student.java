package danycode.dsm.entity;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "students")

public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    @ToString.Include
    @EqualsAndHashCode.Include
    private Long id;
    @Column(name = "first_name", nullable = false, length = 45)
    private String firstName;
    @Column(name = "last_name", nullable = false, length = 45)
    private String lastName;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(name = "phone_number", nullable = false, length = 15)
    private String phoneNumber;
    @Column(name = "birth_date", nullable = false, length = 15)
    private LocalDate birthDate;
    @Column(name = "enroll_date", nullable = false, length = 15)
    private LocalDate enrollDate;
    @Column(name = "nin", nullable = false, length = 15)
    private String nin;
    @Embedded
    private Address address;
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter(AccessLevel.NONE)
    private List<StudentTrainingPackage> studentTrainingPackages = new ArrayList<>();

    public List<StudentTrainingPackage> getStudentTrainingPackages(){
        return Collections.unmodifiableList(studentTrainingPackages);
    }
    public void addStudentTrainingPackage(StudentTrainingPackage studentTrainingPackage){
        studentTrainingPackage.setStudent(this);
        studentTrainingPackages.add(studentTrainingPackage);
    }
    @CanIgnoreReturnValue
    public List<StudentTrainingPackage> removeStudentTrainingPackages(Predicate<StudentTrainingPackage> predicate){
        var remStp = studentTrainingPackages.stream().filter(predicate).toList();
        studentTrainingPackages.removeAll(remStp);
        return remStp;
    }

}
