package com.meta.game.global.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode implements EnumModel{
    BAD_REQUEST(400, "BAD REQUEST", "잘못된 요청입니다."),
    ALREADY_REQUEST(400, "ALREADY REQUEST", "이미 보상을 받았습니다."),
    INVALID(400, "INVALID", "유효하지 않은 데이터입니다."),
    NOT_ENOUGH_MONEY(400, "NOT_ENOUGH_MONEY", "잔액이 부족합니다."),
    INVALID_TOKEN(400 , "INVALID_TOKEN", "유효하지 않은 토큰 정보입니다."),
    CONFLICT(409, "CONFLICT", "중복된 정보가 존재합니다."),
    NOT_FOUND(404, "NOT FOUND", "자원이 유효하지 않습니다."),
    NOT_FOUND_MEMBER(404, "NOT FOUND MEMBER", "존재하지 않는 사용자 입니다."),
    NOT_FOUND_NO1MEMBER(404, "NOT FOUND NO1MEMBER", "1위 맴버가 존재하지 않습니다."),
    FILE_NOT_FOUND(404, "NOT FOUND", "사용자를 찾을 수 없습니다."),
    UNAUTHORIZED(401, "UNAUTHORIZED", "토큰 정보가 유효하지 않습니다.");

    private int status;
    private String code;
    private String message;
    private String detail;

    ErrorCode(int status, String code, String message){
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public void updateDetail(String detail){
        this.detail = detail;
    }

    @Override
    public String getKey() {
        return this.code;
    }

    @Override
    public String getValue() {
        return this.message;
    }


    public int getstatus(){return this.status;}
}
