package com.meta.character.global.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private String code;
    private String message;
    private int status;
    private String detail;

    public ErrorResponse(final String message) {
        this.message = message;
    }

//    public ErrorResponse(ErrorCode code) {
//        this.code = code.getCode();
//        this.status = code.getStatus();
//        this.message = code.getMessage();
//        this.detail = code.getDetail();
//    }

    public static ErrorResponse of(int status, String code, String message) {
        return ErrorResponse.builder()
                .status(status)
                .code(code)
                .message(message)
                .build();
    }


    private static String createErrorMessage(BindingResult bindingResult) {
        StringBuilder sb = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()){
            sb.append("[");
            sb.append(fieldError.getField());
            sb.append("](은)는 ");
            sb.append(fieldError.getDefaultMessage());
            sb.append(". ");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
