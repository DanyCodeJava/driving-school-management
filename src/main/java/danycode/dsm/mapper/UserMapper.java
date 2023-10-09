package danycode.dsm.mapper;

import danycode.dsm.dto.UserDto;
import danycode.dsm.dto.UserRoleDto;
import danycode.dsm.entity.User;
import danycode.dsm.entity.UserRole;
import danycode.dsm.entity.UserType;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto mapToUserDto(User user);

    @Mapping(target = "roles", ignore = true)
    void mapToUserJpa(UserDto userDto, UserType userType, @MappingTarget User user);
    @AfterMapping
    default void afterMapToUserJpa(UserDto userDto, @MappingTarget User user){
        user.replaceRoles(userDto.getRoles().stream().map(this::mapToUserRoleJpa).collect(Collectors.toSet()));
    }
    UserRole mapToUserRoleJpa(UserRoleDto userRoleDto);
}
