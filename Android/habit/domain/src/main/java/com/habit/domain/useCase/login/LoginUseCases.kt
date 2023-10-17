package com.habit.domain.useCase.login

data class LoginUseCases(
    val kakaoLoginUseCase: KakaoLoginUseCase,
    val normalLoginUseCase: NormalLoginUseCase,
    val putAccessTokenUseCase: PutAccessTokenUseCase,
    val getAccessTokenUseCase: GetAccessTokenUseCase,
    val putMemberInfoUseCase: PutMemberInfoUseCase,
    val putKakaoTokenUseCase: PutKakaoTokenUseCase,
    val putNormalLoginInfoUseCase: PutNormalLoginInfoUseCase,
    val getKakaoTokenUseCase: GetKakaoTokenUseCase,
    val getNormalLoginInfoUseCase: GetNormalLoginInfoUseCase
)