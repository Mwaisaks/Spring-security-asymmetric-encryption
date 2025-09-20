package com.alibou.app.controller;

import com.alibou.app.user.User;
import com.alibou.app.user.UserService;
import com.alibou.app.user.request.ChangePasswordRequest;
import com.alibou.app.user.request.ProfileUpdateRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "User API")
public class UserController {

    private final UserService userService;

    @PatchMapping("/me")
    @ResponseStatus(code = HttpStatus.NO_CONTENT) //What does this line do?
    public void updateProfileInfo(
            @RequestBody @Valid
            final ProfileUpdateRequest request,
            final Authentication principal //Why are we not using AuthenticationPrincipal
            ){
        this.userService.updateProfileInfo(request, getUserId(principal));
    }

    @PostMapping("/me/password")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void changePassword(
            @RequestBody @Valid
            final ChangePasswordRequest request,
            final Authentication principal
            ){
        this.userService.changePassword(request, getUserId(principal));
    }

    @PatchMapping("/me/deactivate")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deactivateAccount(final Authentication principal){
        this.userService.deactivateAccount(getUserId(principal));
    }

    @PatchMapping("/me/reactivate")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void reactivateAccount(final Authentication principal){
        this.userService.reactivateAccount(getUserId(principal));
    }

    @DeleteMapping("/me/delete")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteAccount(
            final Authentication principal
    ){
        this.userService.deleteAccount(getUserId(principal));
    }

    private String getUserId(final Authentication principal) {
        return ((User) principal.getPrincipal()).getId();
    }
}
