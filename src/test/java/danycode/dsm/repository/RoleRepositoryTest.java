//package danycode.dsm.user;
//
//import danycode.dsm.entity.Role;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.annotation.Rollback;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Rollback(value = false)
//public class RoleRepositoryTest {
//    @Autowired
//   private RoleRepository roleRepository;
//
//    @Test
//    public void testCreateFirstRole(){
//        Role role = new Role("Admin", "manage everything");
//        Role saveRole = roleRepository.save(role);
//        //verificam daca operatia de salvare a avut succes, deci entitatea persistata a primit un id valid.
//        assertThat(saveRole.getId()).isGreaterThan(0);
//        System.out.println(role);
//    }
//
//    @Test
//    public void testCreateRestRoles() {
//        Role manager = new Role("Manager", "Manage students, pays, instructors, schedules");
//        Role student = new Role("Student", "See schedule, pay fee, see test schedule, get documentation");
//        Role instructor = new Role("Instructor", "Manages meetings with students ");
//
//
//
//        roleRepository.saveAll(List.of(manager, student,instructor));
//    }
//}
