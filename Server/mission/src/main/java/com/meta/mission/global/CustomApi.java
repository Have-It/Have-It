package com.meta.mission.global;


import com.meta.mission.global.error.ErrorResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "#### 성공"),
        @ApiResponse(responseCode = "에러", description = "#### 에러 이유를 확인 하십시오",
                content =@Content(schema = @Schema(implementation = ErrorResponse.class),
                        examples = {
                                @ExampleObject( name = "400", value = "클라이언트 에러 응답 : 잘못된 문법으로 인하여 서버가 요청을 이해할 수 없음을 의미합니다."),
                                @ExampleObject( name = "400_ALREADY REQUEST", value = "이미 달성완료 버튼을 눌러서 보상을 받았습니다. 오늘은 더이상 이 버튼을 누를 수 없습니다."),
                                @ExampleObject( name = "401", value = "클라이언트 에러 응답 : 미승인(unauthorized) 또는 비인증(unauthenticated)"),
                                @ExampleObject( name = "404_NOT FOUND MEMBER", value = "일치하는 사용자를 찾을 수 없습니다."),
                                @ExampleObject( name = "404_NOT FOUND NO1MEMBER", value = "1위 맴버가 존재하지 않습니다."),
                                @ExampleObject( name = "404_NOT FOUND TODAY", value = "오늘 날짜에 맴버가 존재하지 않습니다."),
                                @ExampleObject( name = "404", value = "클라이언트 에러 응답 : 요청받은 리소스를 찾을 수 없습니다. "),
                                @ExampleObject( name = "409", value = "클라이언트 에러 응답 : 요청이 현재 서버의 상태와 충돌될 때 보냅니다. 중복을 혀용하지 않은 상황에서 중복된 값이 존재합니다."),
                                @ExampleObject( name = "500", value = "서버 에러 응답")}))})

public @interface CustomApi {
    String value() default "";
}