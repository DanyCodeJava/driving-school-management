package danycode.dsm.controller;

import danycode.dsm.dto.UserCheckEmailResponseDto;
import danycode.dsm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {

    @Autowired
    private UserService userService;

    @PostMapping("/users/check_email")
    public UserCheckEmailResponseDto checkDuplicateEmail(@Param("id") Long id, @Param("email") String email) {
        return new UserCheckEmailResponseDto(userService.isEmailUnique(id, email) ? "Ok" : "Duplicated");
    }
}
