package danycode.dsm.repository;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

public class PasswordEncoderTest {

    @Test
    public void testEncodePassword(){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String rawPassword = "david123";
        String encodePass = passwordEncoder.encode(rawPassword);
        System.out.println(encodePass);
        boolean matches = passwordEncoder.matches(rawPassword,encodePass);
        assertThat(matches).isTrue();
    }
}
