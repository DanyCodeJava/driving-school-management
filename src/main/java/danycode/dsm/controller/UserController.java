package danycode.dsm.controller;

import danycode.dsm.dto.*;
import danycode.dsm.entity.User;
import danycode.dsm.service.UserService;
import danycode.dsm.service.UserServiceImpl;
import danycode.dsm.util.UserCsvExporter;
import danycode.dsm.util.UserNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class UserController {


    private final UserService userService;
    private final UsersPresentationProps usersPresentationProps;

    @GetMapping("/users")
    public String listAllUsers(Model model) {
        return listByPage(1, model, UserSortField.firstName, SortDirection.asc, null);
    }

    @GetMapping(value = "/users/page/{pageNumber}")
    public String listByPage(@PathVariable("pageNumber") int pageNumber, Model model,
                             @RequestParam("sortField") UserSortField sortingField,
                             @RequestParam("sortDir") SortDirection sortingDir,
                             @RequestParam(value = "keyword", required = false) String keyword) {
        log.info("sort field: {}, sort dir: {} ", sortingField, sortingDir);
        PageDto<UserDto> pageUser = userService.listByPage(pageNumber, sortingField, sortingDir, keyword);
        List<UserDto> userDtoList = pageUser.getContent();
        long startCount = (long) (pageNumber - 1) * usersPresentationProps.getPageSize() + 1;
        long endCount = startCount + UserServiceImpl.USER_PER_PAGE - 1;
        if (endCount > pageUser.getTotalElements()) {
            endCount = pageUser.getTotalElements();
        }
        String reverseSortDir = sortingDir == SortDirection.asc ? "desc" : "asc";
        model.addAttribute("totalPages", pageUser.getTotalPages());
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("lastPage", pageUser.getTotalPages());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", pageUser.getTotalElements());
        model.addAttribute("users", userDtoList);
        model.addAttribute("sortField", sortingField.name());
        model.addAttribute("sortDir", sortingDir.name());
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("keyword", keyword);
        return "user/users";
    }

    @GetMapping(value = "/users/new", produces = {"text/javascript", "text/css"})
    public String createNewUser(Model model) {
        UserDto userDto = new UserDto();
        userDto.setEnabled(true);
        model.addAttribute("user", userDto);
        model.addAttribute("roles", Arrays.asList(UserRoleDto.values()));
        model.addAttribute("pageTitle", "Create New User");
        return "user/user_form.html";
    }

    //todo confirm password
    @PostMapping("/users/save")
    public String userSave(@ModelAttribute("user") UserDto userDto,
                           RedirectAttributes redirectAttributes,
                           @RequestParam("image") MultipartFile multipartFile) throws IOException {

        if (!multipartFile.isEmpty()) {
            UserDto savedUser = userService.saveUser(userDto, new PhotoFileDto(multipartFile.getInputStream()));
        } else {

            userService.saveUser(userDto, null);
        }
        redirectAttributes.addFlashAttribute("message", "The user has been saved successfully!");
        return "redirect:/users";
    }

    @GetMapping("/users/{id}/photo")
    @ResponseBody
    public byte[] getUserPhoto(@PathVariable Long id) {
        return userService.getUserPhoto(id);
    }

    @GetMapping("/users/default/photo")
    @ResponseBody
    public byte[] getUserPhotoDefault() {
        return userService.getDefaultUserPhoto();
    }

    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable(name = "id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            //obtinem utilizatorul dupa id
            UserDto userDto = userService.getUserById(id);
            List<UserRoleDto> roleDtoList = Arrays.asList(UserRoleDto.values());
            model.addAttribute("user", userDto);
            model.addAttribute("pageTitle", "Edit User (ID: " + id + ")");
            model.addAttribute("roles", roleDtoList);
            return "user/user_form";
        } catch (UserNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());

        }
        return "redirect:/users";
    }


    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Long id,
                             RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUser(id);
            redirectAttributes.addFlashAttribute("message", "The user ID: " + id + " has been deleted successfully");
        } catch (UserNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());

        }
        return "redirect:/users";

    }

    @GetMapping("/users/exports/csv")
    public void exportCsv(HttpServletResponse response) throws IOException {
        List<User> listUsers = userService.listAll();
        UserCsvExporter exporter = new UserCsvExporter();
        exporter.export(listUsers, response);
    }

}
