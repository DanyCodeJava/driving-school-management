package danycode.dsm.service;

import danycode.dsm.dto.*;
import danycode.dsm.entity.SimplePhotoFile;
import danycode.dsm.entity.User;
import danycode.dsm.entity.UserType;
import danycode.dsm.mapper.PageMapper;
import danycode.dsm.mapper.UserMapper;
import danycode.dsm.repository.StudentRepository;
import danycode.dsm.repository.UserPhotoRepository;
import danycode.dsm.repository.UserRepository;
import danycode.dsm.util.UserNotFoundException;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Predicate;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    public static final int USER_PER_PAGE = 4;

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserPhotoFileProcessor userPhotoFileProcessor;
    //fixme inject password encoder

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserPhotoRepository userPhotoRepository;
    private final StudentRepository studentRepository;


    @Override
    public UserDto saveUser(UserDto userDto, @Nullable PhotoFileDto userPhoto) {
        var roles = Optional.ofNullable(userDto.getRoles())
                .filter(Predicate.not(Collection::isEmpty))
                .orElseThrow(() -> new UserRolesConstraintsException("Roles are mandatory"));
        if (roles.contains(UserRoleDto.STUDENT) && roles.size() > 1) {
            throw new UserRolesConstraintsException("Role Student and all other roles are self-exclusive.");
        }
        var processUserPhoto = userPhotoFileProcessor.process(userPhoto);
        final UserType userType = roles.contains(UserRoleDto.STUDENT) ? UserType.STUDENT : UserType.EMPLOYEE;
        User user = Optional.ofNullable(userDto.getId())
                .flatMap(userRepository::findById)
                .orElseGet(User::new);

        userMapper.mapToUserJpa(userDto, userType, user);
        encodePassword(user);

        User savedUser = userRepository.save(user);

        long id = savedUser.getId();
        Optional.ofNullable(processUserPhoto).map(PhotoFileDto::getInputStream)
                .map(SimplePhotoFile::new)
                .ifPresent(photoFile -> userPhotoRepository.save(photoFile, id));
        return userDto;
    }

    public void encodePassword(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

    public boolean isEmailUnique(Long id, String email) {
        // Se obține un utilizator din baza de date căutând după adresa de email.
        User userByEmail = userRepository.getUserByEmail(email);
        // Se verifică dacă s-a găsit un utilizator cu adresa de email specificată.
        // Dacă s-a găsit, se returnează false pentru că email-ul nu este unic.
        if (userByEmail == null)
            return true;
        return Objects.equals(userByEmail.getId(), id);

    }

    @Override
    public UserDto getUserById(Long id) throws UserNotFoundException {

        try {
            User user = userRepository.findById(id).get();
            UserDto userDto = userMapper.mapToUserDto(user);
            return userDto;
        } catch (NoSuchElementException ex) {
            throw new UserNotFoundException("Could not find any user with id: " + id);
        }

    }

    @Override
    public void deleteUser(long userId) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new UserNotFoundException("Could not find user with id: " + userId);
        }
        userRepository.delete(user.get());
    }

    @Override
    public byte[] getUserPhoto(long id) {

        return Optional.ofNullable(userPhotoRepository.getPhotoById(id)).orElseGet(userPhotoRepository::getDefaultPhoto);
    }

    @Override
    public PageDto<UserDto> listByPage(int pageNumber, UserSortField sortField, SortDirection sortDir, String keyword) {
        Page<User> userPage;
        Sort sort = Sort.by(sortField.name());
        sort = sortDir == SortDirection.desc ? sort.descending() : sort.ascending();
        Pageable pageable = PageRequest.of(pageNumber - 1, USER_PER_PAGE, sort);
        if (keyword != null) {
            userPage = userRepository.findPageByKeyword(keyword, pageable);
        } else {
            userPage = userRepository.findAll(pageable);
        }
        return PageMapper.mapToPageDto(userPage.map(userMapper::mapToUserDto));
    }

    @Override
    public List<User> listAll() {
        return userRepository.findAll(Sort.by("firstName").ascending());
    }

    @Override
    public byte[] getDefaultUserPhoto() {
        return userPhotoRepository.getDefaultPhoto();
    }

    @Override
    public void createUserForStudent(long studentId) {
        var student = studentRepository.findById(studentId).orElseThrow(StudentNotFoundException::new);

        var user = new User();
        user.setUserType(UserType.STUDENT);
        user.setFirstName(student.getFirstName());
        user.setLastName(student.getLastName());
        user.setEmail(student.getEmail());
        user.setEnabled(true);
        user.setPassword("123455"); //todo generator
        user.setStudent(student);
        userRepository.save(user);
    }
}
