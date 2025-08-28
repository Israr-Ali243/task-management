package com.task_management.task_management.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {
    private boolean success;   // true if request succeeded
    private String message;    // message (success/failure details)
    private T data;
    private Object errors;
    int code;

    public static <T> BaseResponse<T> success(T data, String message, int code) {
        return BaseResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .code(code)
                .build();
    }


    public static <T> BaseResponse<T> failure(String message, Object errors, int code) {
        return BaseResponse.<T>builder()
                .success(false)
                .message(message)
                .errors(errors)
                .code(code)
                .build();
    }
}
