package danycode.dsm.repository;

import danycode.dsm.entity.User;
import danycode.dsm.entity.UserType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static danycode.dsm.entity.UserRole.*;
import static danycode.dsm.entity.UserRole.INSTRUCTOR;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest()
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager; //gestioneaza ciclul de viata al entitatilor din baza de date

    @Test
    public void testAddUser(){
        User userStudent = new User("gabrielaMatei" +UUID.randomUUID() +"@gmail.com","admin123","gabriela","matei");
        userStudent.addRole(STUDENT);
        userStudent.setUserType(UserType.STUDENT);
        User savedUser = userRepository.save(userStudent);
        assertThat(savedUser.getId()).isGreaterThan(0);
        System.out.println(savedUser);
    }
    @Test
    public void createUserWithTwoRoles(){
        User userMarc = new User("marc" + UUID.randomUUID() +"@yahoo.com","marc1234", "laurentiu","marc");
        userMarc.addRoles(INSTRUCTOR, MANAGER);
        userMarc.setUserType(UserType.EMPLOYEE);
        User savedUser = userRepository.save(userMarc);
        assertThat(savedUser.getId()).isGreaterThan(0);
        System.out.println(savedUser);
    }
    //obtinem toti utilizatorii din baza de date
    @Test
    public void getAllUsers(){
       List<User> users = userRepository.findAll();
        users.forEach(user -> System.out.println(user));
        assertThat(users.size()).isGreaterThan(0);
    }
    //obtinem un utilizator dupa id
    //todo insert user before querying
//    @Test
//    public void getUserById(){
//        User user  = userRepository.findById(1L).get();
//        System.out.println(user);
//        assertThat(user).isNotNull();
//    }
    //update pe un utilizator
    //todo insert user before querying
//    @Test
//    public void testUpdateUser(){
//        User user  = userRepository.findById(1L).get();
//        user.setPassword("admin4321");
//        user.setEnabled(true);
//        userRepository.save(user);
//    }

    //testam stergerea unui utilizator
    @Test
    public void delete(){
        int userId = 2;
       userRepository.deleteById((long) userId);
    }
    //todo insert user before querying
//    @Test
//    public void testGetUserByEmail(){
//        String email = "maria@gmail.com";
//        User userGetByEmail = userRepository.getUserByEmail(email);
//        assertThat(userGetByEmail).isNotNull();
//    }


    @Test
    public void testListFirstPage(){
        int pageNumber = 0;
        int pageSize = 4;
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        Page<User> page = userRepository.findAll(pageable);
        List<User>  listUser = page.getContent();
        listUser.forEach(System.out::println);
        assertThat(listUser.size()).isEqualTo(pageSize);
    }
    @Test
    public void testSearchUsers(){
        String keyword = "gabriela";
        int pageNumber = 0;
        int pageSize = 4;
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        Page<User> page = userRepository.findPageByKeyword(keyword,pageable);
        List<User>  listUser = page.getContent();
        listUser.forEach(System.out::println);
        assertThat(listUser.size()).isGreaterThan(0);
    }
}
