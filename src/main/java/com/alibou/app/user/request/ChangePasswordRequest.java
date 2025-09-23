package com.alibou.app.user.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangePasswordRequest {

    @NotBlank(message = "VALIDATION.CHANGEPASSWORD.CURRENT.NOT_BLANK")
    @Size(min = 8, max = 72, message = "VALIDATION.CHANGEPASSWORD.CURRENT.SIZE")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*\\W).*$", message = "VALIDATION.CHANGEPASSWORD.CURRENT.WEAK")
    @Schema(example = "pAssword1_")
    private String currentPassword;

    @NotBlank(message = "VALIDATION.CHANGEPASSWORD.NEW.NOT_BLANK")
    @Size(min = 8, max = 72, message = "VALIDATION.CHANGEPASSWORD.NEW.SIZE")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*\\W).*$", message = "VALIDATION.CHANGEPASSWORD.NEW.WEAK")
    @Schema(example = "pAssword1_")
    private String newPassword;

    @NotBlank(message = "VALIDATION.CHANGEPASSWORD.CONFIRM.NOT_BLANK")
    @Size(min = 8, max = 72, message = "VALIDATION.CHANGEPASSWORD.CONFIRM.SIZE")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*\\W).*$", message = "VALIDATION.CHANGEPASSWORD.CONFIRM.WEAK")
    @Schema(example = "pAssword1_")
    private String confirmNewPassword;
}
