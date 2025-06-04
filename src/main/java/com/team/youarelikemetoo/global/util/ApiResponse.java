package com.team.youarelikemetoo.global.util;

import lombok.Getter;

@Getter
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T body;

    public ApiResponse(T body) {
        this.success = true;
        this.message = "[MSG] 요청이 성공하였습니다.";
        this.body = body;
    }

    public ApiResponse(String message) {
        this.success = false;
        this.message = "[MSG] 요청이 실패하였습니다.";
        this.body = null;
    }


    // 팩토리 메서드
    public static <T> ApiResponse<T> success(T body){
        return new ApiResponse<>(body);
    }

    public static <T> ApiResponse<T> failure(String message){
        return new ApiResponse<>(message);
    }

}
