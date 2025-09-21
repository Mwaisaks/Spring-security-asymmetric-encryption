package com.alibou.app.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
public enum ErrorCode {

    USER_NOT_FOUND("USER_NOT_FOUND", "User not found", NOT_FOUND ),
    CHANGE_PASSWORD_MISMATCH("CHANGE_PASSWORD_MISMATCH", "Confirm the new password" , BAD_REQUEST ),
    INVALID_CURRENT_PASSWORD(" INVALID_CURRENT_PASSWORD", "Current password is invalid" , BAD_REQUEST ),
    ACCOUNT_ALREADY_DEACTIVATED("ACCOUNT_ALREADY_DEACTIVATED", "Account is already deactivated", BAD_REQUEST ),
    ACCOUNT_ALREADY_ACTIVATED(" ACCOUNT_ALREADY_ACTIVATED","Account is already activated", BAD_REQUEST),
    EMAIL_ALREADY_EXISTS("EMAIL_ALREADY_EXISTS", "Email Already Exists", BAD_REQUEST),
    PHONE_ALREADY_EXISTS("PHONE_ALREADY_EXISTS", "Phone Number Already Exists", BAD_REQUEST),
    PASSWORD_MISMATCH("PASSWORD_MISMATCH", "Password do not Match", BAD_REQUEST);

    private final String code;
    private final String default_message;
    private final HttpStatus status;

    ErrorCode(final String code,
              final String default_message,
              final HttpStatus status) {
        this.code = code;
        this.default_message = default_message;
        this.status = status;
    }
}
