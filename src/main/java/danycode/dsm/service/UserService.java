package danycode.dsm.service;

import danycode.dsm.dto.*;
import danycode.dsm.entity.User;
import danycode.dsm.util.UserNotFoundException;

import java.util.List;


public interface UserService {



    UserDto saveUser(UserDto userDto, PhotoFileDto userPhoto);

    boolean isEmailUnique(Long id, String email);

    UserDto getUserById(Long id) throws UserNotFoundException;

    void deleteUser(long userId) throws UserNotFoundException;

    byte[] getUserPhoto(long id);

    PageDto<UserDto> listByPage(int pageNumber, UserSortField sortField, SortDirection sortDir, String keyword);


    List<User> listAll();

    byte[] getDefaultUserPhoto();

    void createUserForStudent(long studentId);
}
