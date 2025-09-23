package com.alibou.app.user.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileUpdateRequest {

    @NotBlank(message = "VALIDATION.PROFILEUPDATE.FIRSTNAME.NOT_BLANK")
    @Size(min = 5, max = 50, message = "VALIDATION.PROFILEUPDATE.FIRSTNAME.SIZE")
    @Pattern(regexp = "^[\\p{L} '-]+$", message = "VALIDATION.PROFILEUPDATE.FIRSTNAME.PATTERN")
    @Schema(example = "Ali")
    private String firstName;

    @NotBlank(message = "VALIDATION.PROFILEUPDATE.LASTNAME.NOT_BLANK")
    @Size(min = 5, max = 50, message = "VALIDATION.PROFILEUPDATE.LASTNAME.SIZE")
    @Pattern(regexp = "^[\\p{L} '-]+$", message = "VALIDATION.PROFILEUPDATE.LASTNAME.PATTERN")
    @Schema(example = "Ali")
    private String lastName;

    @NotNull(message = "VALIDATION.PROFILEUPDATE.DATEOFBIRTH.NOT_NULL")
    @Past(message = "VALIDATION.PROFILEUPDATE.DATEOFBIRTH.PAST")
    @Schema(example = "1995-06-15")
    private LocalDate dateOfBirth;
}
