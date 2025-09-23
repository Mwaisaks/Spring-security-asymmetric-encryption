package com.alibou.app.exceptions;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ErrorResponse {

    private String errorCode;
    private String errorMessage;
    private List<ValidationError> validationErrors;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class ValidationError{
        private String field;
        private String errorCode;
        private String errorMessage;
    }
}
