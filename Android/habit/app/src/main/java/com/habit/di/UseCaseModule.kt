package com.habit.di

import com.habit.domain.repository.HealthConnectRepository
import com.habit.domain.repository.MainRepository
import com.habit.domain.repository.MissionRepository
import com.habit.domain.repository.UserRepository
import com.habit.domain.useCase.example.ExampleUseCases
import com.habit.domain.useCase.example.GetAllPostsUseCase
import com.habit.domain.useCase.example.GetFirstPostUseCase
import com.habit.domain.useCase.home.GetAllExerciseDataUseCase
import com.habit.domain.useCase.home.GetHomeDataUseCase
import com.habit.domain.useCase.home.GetMemberHomeInfoUseCase
import com.habit.domain.useCase.home.GetMissionUseCase
import com.habit.domain.useCase.home.GetSleepDataUseCase
import com.habit.domain.useCase.home.GetStepDataUseCase
import com.habit.domain.useCase.home.GetTodaysCalorieUseCase
import com.habit.domain.useCase.home.GetTodaysExerciseUseCase
import com.habit.domain.useCase.home.HomeUseCases
import com.habit.domain.useCase.home.PostExerciseMissionSuccessUseCase
import com.habit.domain.useCase.home.PostFreeMissionSuccessUseCase
import com.habit.domain.useCase.home.PostSleepMissionSuccessUseCase
import com.habit.domain.useCase.login.GetAccessTokenUseCase
import com.habit.domain.useCase.login.GetKakaoTokenUseCase
import com.habit.domain.useCase.login.GetNormalLoginInfoUseCase
import com.habit.domain.useCase.login.KakaoLoginUseCase
import com.habit.domain.useCase.login.LoginUseCases
import com.habit.domain.useCase.login.NormalLoginUseCase
import com.habit.domain.useCase.login.PutAccessTokenUseCase
import com.habit.domain.useCase.login.PutKakaoTokenUseCase
import com.habit.domain.useCase.login.PutMemberInfoUseCase
import com.habit.domain.useCase.login.PutNormalLoginInfoUseCase
import com.habit.domain.useCase.main.GetMemberInfoUseCase
import com.habit.domain.useCase.main.MainUseCases
import com.habit.domain.useCase.mission.MissionUseCases
import com.habit.domain.useCase.mission.PutMissionUseCase
import com.habit.domain.useCase.setting.DeleteAccessTokenUseCase
import com.habit.domain.useCase.setting.DeleteKakaoTokenUseCase
import com.habit.domain.useCase.setting.DeleteNormalLoginInfoUseCase
import com.habit.domain.useCase.setting.GetMemberSettingPageInfoUseCase
import com.habit.domain.useCase.setting.GetStatUseCase
import com.habit.domain.useCase.setting.LogoutUseCase
import com.habit.domain.useCase.setting.PutStepDataUseCase
import com.habit.domain.useCase.setting.SettingUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideMovieUseCases(mainRepository: MainRepository) = ExampleUseCases(
        getFirstPostUseCase = GetFirstPostUseCase(mainRepository = mainRepository),
        getAllPostsUseCase = GetAllPostsUseCase(mainRepository = mainRepository)
    )

    @Provides
    fun provideMainUseCases(userRepository: UserRepository) = MainUseCases(
        getMemberInfoUseCase = GetMemberInfoUseCase(userRepository = userRepository),
        getMissionUseCase = GetMissionUseCase(userRepository = userRepository)
    )

    @Provides
    fun provideLoginUseCases(userRepository: UserRepository) = LoginUseCases(
        kakaoLoginUseCase = KakaoLoginUseCase(userRepository = userRepository),
        normalLoginUseCase = NormalLoginUseCase(userRepository = userRepository),
        putAccessTokenUseCase = PutAccessTokenUseCase(userRepository = userRepository),
        getAccessTokenUseCase = GetAccessTokenUseCase(userRepository = userRepository),
        putMemberInfoUseCase = PutMemberInfoUseCase(userRepository = userRepository),
        putKakaoTokenUseCase = PutKakaoTokenUseCase(userRepository = userRepository),
        putNormalLoginInfoUseCase = PutNormalLoginInfoUseCase(userRepository = userRepository),
        getKakaoTokenUseCase = GetKakaoTokenUseCase(userRepository = userRepository),
        getNormalLoginInfoUseCase = GetNormalLoginInfoUseCase(userRepository = userRepository)
    )

    @Provides
    fun provideMissionUseCases(userRepository: UserRepository) = MissionUseCases(
        putMissionUseCase = PutMissionUseCase(userRepository = userRepository)
    )

    @Provides
    fun provideSettingUseCase(
        userRepository: UserRepository,
        healthConnectRepository: HealthConnectRepository
    ) = SettingUseCases(
        deleteKakaoTokenUseCase = DeleteKakaoTokenUseCase(userRepository = userRepository),
        deleteNormalLoginInfoUseCase = DeleteNormalLoginInfoUseCase(userRepository = userRepository),
        logoutUseCase = LogoutUseCase(userRepository = userRepository),
        getMemberSettingPageInfoUseCase = GetMemberSettingPageInfoUseCase(userRepository = userRepository),
        getStatUseCase = GetStatUseCase(userRepository = userRepository),
        deleteAccessTokenUseCase = DeleteAccessTokenUseCase(userRepository = userRepository),
        getStepDataUseCase = GetStepDataUseCase(healthConnectRepository = healthConnectRepository),
        putStepDataUseCase = PutStepDataUseCase(userRepository = userRepository)
    )

    @Provides
    fun provideHomeUseCase(
        healthConnectRepository: HealthConnectRepository,
        userRepository: UserRepository,
        missionRepository: MissionRepository
    ) = HomeUseCases(
        getSleepDataUseCase = GetSleepDataUseCase(healthConnectRepository = healthConnectRepository),
        getMissionUseCase = GetMissionUseCase(userRepository = userRepository),
        getHomeDataUseCase = GetHomeDataUseCase(userRepository = userRepository),
        postExerciseMissionSuccessUseCase = PostExerciseMissionSuccessUseCase(missionRepository = missionRepository),
        postSleepMissionSuccessUseCase = PostSleepMissionSuccessUseCase(missionRepository = missionRepository),
        postFreeMissionSuccessUseCase = PostFreeMissionSuccessUseCase(missionRepository = missionRepository),
        getAllExerciseDataUseCase = GetAllExerciseDataUseCase(missionRepository = missionRepository),
        getTodaysCalorieUseCase = GetTodaysCalorieUseCase(missionRepository = missionRepository),
        getTodaysExerciseUseCase = GetTodaysExerciseUseCase(missionRepository = missionRepository),
        getMemberHomeInfoUseCase = GetMemberHomeInfoUseCase(userRepository = userRepository),
        deleteAccessTokenUseCase = DeleteAccessTokenUseCase(userRepository = userRepository),
        getStepDataUseCase = GetStepDataUseCase(healthConnectRepository = healthConnectRepository)
    )

//    @Provides
//    fun provideMovieUseCases(mainRepository: MainRepository) = HomeUseCases(
//        getFirstPostUseCase = GetFirstPostUseCase(mainRepository = mainRepository),
//        getAllPostsUseCase = GetAllPostsUseCase(mainRepository = mainRepository)
//    )
}