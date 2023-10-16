package danycode.dsm.student;

import danycode.dsm.entity.*;
import danycode.dsm.repository.StudentRepository;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class StudentRepositoryTest {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void testAddStudent(){
        Student userStudent = createStudent();
        Student savedUser = studentRepository.save(userStudent);
        assertThat(savedUser.getId()).isGreaterThan(0);
        System.out.println(savedUser);
    }


    @Test
    public void addStudentTrainingPackage(){
        var trainingPackage = new TrainingPackage();
        trainingPackage.setCost(new BigDecimal("4000.33"));
        trainingPackage.setDuration(50);
        var trainingPackageId = testEntityManager.persist(trainingPackage).getId();
        var student = createStudent();
        var studentId = testEntityManager.persist(student).getId();
        testEntityManager.flush();
        testEntityManager.clear();
        student = testEntityManager.find(Student.class,studentId);
        trainingPackage = testEntityManager.find(TrainingPackage.class, trainingPackageId);
        var studentTrainingPackage = new StudentTrainingPackage();
        studentTrainingPackage.setTrainingPackage(trainingPackage);
        studentTrainingPackage.setStatus(StudentTrainingPackageStatus.COMPLETED);
        student.addStudentTrainingPackage(studentTrainingPackage);
        testEntityManager.flush();
        testEntityManager.clear();
        student = testEntityManager.find(Student.class,studentId);
        trainingPackage = testEntityManager.find(TrainingPackage.class, trainingPackageId);
        assertThat(student.getStudentTrainingPackages()).hasSize(1)
                .element(0)
                .satisfies(stp->{
                    assertThat(stp.getStudent()).hasFieldOrPropertyWithValue("id", studentId);
                    assertThat(stp.getTrainingPackage()).hasFieldOrPropertyWithValue("id",trainingPackageId);
                    assertThat(stp.getStatus()).isEqualTo(StudentTrainingPackageStatus.COMPLETED);
                });
        assertThat(trainingPackage.getStudentTrainingPackages()).hasSize(1);
        assertThat(student.getStudentTrainingPackages().get(0)).isSameAs(trainingPackage.getStudentTrainingPackages().get(0));
        testEntityManager.remove(student);
        testEntityManager.flush();
        testEntityManager.clear();
        assertThat(testEntityManager.find(Student.class,studentId)).isNull();
        assertThat(trainingPackage = testEntityManager.find(TrainingPackage.class,trainingPackageId)).isNotNull();
        testEntityManager.remove(trainingPackage);

    }
    @NotNull
    private static Student createStudent() {
        var birhDate =  LocalDate.of(1993,10,9);
        var enrollDate = LocalDate.now();

        Student userStudent = new Student();
        userStudent.setFirstName("marck");
        userStudent.setLastName("mand");
        userStudent.setEmail(UUID.randomUUID() + "mark@gmail.com");
        userStudent.setPhoneNumber("465112");
        userStudent.setBirthDate(birhDate);
        userStudent.setEnrollDate(enrollDate);
        userStudent.setNin("955412131");
        userStudent.setAddress(new Address("alba iulia",null,"6522","alba iulia","romania"));
        return userStudent;
    }

}
