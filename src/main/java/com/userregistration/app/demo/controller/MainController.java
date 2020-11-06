package com.userregistration.app.demo.controller;

import com.userregistration.app.demo.dto.UserAccountDto;
import com.userregistration.app.demo.mapper.UserAccountMapper;
import com.userregistration.app.demo.model.UserAccount;
import com.userregistration.app.demo.service.UserAccountService;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MainController {

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private UserAccountMapper userAccountMapper;

    @RequestMapping("/")
    public String viewHome(Model model) {
        return "welcomePage";
    }

    @GetMapping("/user")
    public String viewMembers(Model model) {
        List<UserAccountDto> usersAccountDtos = userAccountService.getUserAccounts().stream()
                .map(userAccountMapper::userAccountToDto)
                .collect(Collectors.toList());
        model.addAttribute("users", usersAccountDtos);
        return "userList";
    }

    @GetMapping("/user/{id}/edit")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        UserAccountDto user = userAccountService.findById(id);
        model.addAttribute("user", user);
        return "editUser";
    }

    @PostMapping("/user/{id}/edit")
    public String updateUserAccount(@PathVariable Long id, @Valid UserAccountDto user,
                                    BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setId(id);
            return "editUser";
        }
        UserAccount newUserAccount = userAccountService.add(user);
        model.addAttribute("users", newUserAccount);
        return "redirect:/user";
    }

    @RequestMapping("user/{id}/delete")
    public String deleteUserAccount(@PathVariable Long id) {
        userAccountService.deleteUserAccount(id);
        return "redirect:/user";
    }

    @GetMapping(value = "/user/new")
    public String viewUserAccountForm(Model model) {
        model.addAttribute("userAccountDto", new UserAccountDto());
        return "userAccountDto";
    }

    @PostMapping(value = "/user/new")
    public String saveUserAccount(@Valid UserAccountDto userAccountDto,
                                  BindingResult result,
                                  final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "userAccountDto";
        }
        UserAccount newUserAccount = userAccountService.add(userAccountDto);
        redirectAttributes.addFlashAttribute("flashUser", newUserAccount);
        return "redirect:/success";
    }

    @GetMapping("/success")
    public String successPage(Model model, @ModelAttribute("userAccountDto")
            UserAccountDto userAccountDto) {
        model.addAttribute("userAccountDto", userAccountDto);
        return "success";
    }
}
