package danycode.dsm.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id;
    @Column(length = 128, nullable = false, unique = true)
    private String email;
    @Column(length = 64, nullable = false)
    private String password;
    @Column(name="first_name", length = 45, nullable = false)
    private String firstName;
    @Column(name="last_name", length = 45, nullable = false)
    private String lastName;
    @Enumerated(EnumType.STRING)
    @Column(name="user_type", length = 45, nullable = false)
    private UserType userType;
    private boolean enabled;
    @ElementCollection
    @CollectionTable(
            name="users_roles", //tabelul de join
            joinColumns = @JoinColumn (name="user_id")//cheie straina a entitatii user
    )
    @Setter(AccessLevel.NONE)
    @Enumerated(EnumType.STRING)
    private Set<UserRole> roles = new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    public User(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void addRole(UserRole role){
        this.roles.add(role);
    }
    public void addRoles(UserRole...roles){
        this.roles.addAll(Arrays.asList(roles));
    }
    public void replaceRoles(Set<UserRole> roles){
        this.roles.retainAll(roles);
        this.roles.addAll(roles);
    }
    public Set<UserRole> getRoles() {
       return Collections.unmodifiableSet(this.roles);
    }


}
