package com.habit.ui.theme

import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.graphics.Color

//val Purple80 = Color(0xFFD0BCFF)
//val PurpleGrey80 = Color(0xFFCCC2DC)
//val Pink80 = Color(0xFFEFB8C8)
//
//val Purple40 = Color(0xFF6650a4)
//val PurpleGrey40 = Color(0xFF625b71)
//val Pink40 = Color(0xFF7D5260)

val HabitBlack = Color(0xFF000000)
val HabitPurpleNormal = Color(0xFF5D54C7)
val HabitPurpleLight = Color(0xFFA3AEFC)
val HabitPurpleExtraLight = Color(0xFFD9DFFF)
val HabitPurpleExtraLight2 = Color(0xFFE6EAFF)
val HabitPurpleExtraLight3 = Color(0xFFF1F3FF)
val HabitPurpleExtraLight4 = Color(0xFFF7F8FF)
val HabitPink = Color(0xFFFFB0AC)
val HabitPinkLight = Color(0xFFFFE5E3)
val HabitYellow = Color(0xFFFFEEB5)
val HabitGreen = Color(0xFF77DD77)
val HabitGreenLight = Color(0xFFC1FFC1)
val HabitBlue = Color(0xFF98CEFF)
val HabitBlueLight = Color(0xFFE1F1FF)
val Gray900 = Color(0xFF1C1C1C)
val Gray800 = Color(0xFF3C3C3C)
val Gray700 = Color(0xFF5B5B5B)
val Gray600 = Color(0xFF6E6E6E)
val Gray500 = Color(0xFF979797)
val Gray400 = Color(0xFFB7B7B7)
val Gray300 = Color(0xFFDADADA)
val Gray200 = Color(0xFFEAEAEA)
val Gray100 = Color(0xFFF3F3F3)
val Gray50 = Color(0xFFF9F9F9)

val Black = Color(0xFF000000)
val HabitWhite = Color(0xFFFFFFFF)
val HabitError = Color(0xFFFF4747)
val HabitBlueGray = Color(0xFFBEBCDC)

val KakaoYellow = Color(0xFFFEE500)
val KakoLabel = Color(0xD9000000)


val MaterialColors = lightColorScheme(
    primary = HabitPurpleLight,
    onPrimary = Gray900,
    secondary = HabitPurpleNormal,
    onSecondary = HabitWhite,
    tertiary = HabitGreen,
    onTertiary = Gray900,
    error = HabitError,
    onError = HabitError,
    background = HabitWhite,
    surface = HabitWhite
)

@Stable
class HabitColors(
    habitBlack: Color,
    habitWhite: Color,
    habitPurpleNormal: Color,
    habitPurpleLight: Color,
    habitPurpleExtralight: Color,
    habitPurpleExtralight2: Color,
    habitPink: Color,
    habitPinkLight: Color,
    habitYellow: Color,
    habitGreen: Color,
    habitGreenLight: Color,
    habitBlue: Color,
    habitBlueLight: Color,
    habitError : Color,
    gray900: Color,
    gray800: Color,
    gray700: Color,
    gray600: Color,
    gray500: Color,
    gray400: Color,
    gray300: Color,
    gray200: Color,
    gray100: Color,
    gray50: Color
) {
    var habitBlack: Color by mutableStateOf(habitBlack, structuralEqualityPolicy())
        private set
    var habitPurpleNormal: Color by mutableStateOf(habitPurpleNormal, structuralEqualityPolicy())
        private set
    var habitPurpleLight: Color by mutableStateOf(habitPurpleLight, structuralEqualityPolicy())
        private set
    var habitPurpleExtralight: Color by mutableStateOf(
        habitPurpleExtralight,
        structuralEqualityPolicy()
    )
        private set

    var habitPurpleExtralight2: Color by mutableStateOf(
        habitPurpleExtralight2,
        structuralEqualityPolicy()
    )
        private set

    var habitGreen: Color by mutableStateOf(habitGreen, structuralEqualityPolicy())
        private set

    var habitGreenLight: Color by mutableStateOf(habitGreenLight, structuralEqualityPolicy())
        private set

    var habitYellow: Color by mutableStateOf(habitYellow, structuralEqualityPolicy())
        private set

    var habitWhite: Color by mutableStateOf(habitWhite, structuralEqualityPolicy())
        private set

    var habitBlue: Color by mutableStateOf(habitBlue, structuralEqualityPolicy())
        private set

    var habitBlueLight: Color by mutableStateOf(habitBlueLight, structuralEqualityPolicy())
        private set

    var habitPink: Color by mutableStateOf(habitPink, structuralEqualityPolicy())
        private set

    var habitPinkLight: Color by mutableStateOf(habitPinkLight, structuralEqualityPolicy())
        private set

    var habitError: Color by mutableStateOf(habitError, structuralEqualityPolicy())
        private set

    var gray900: Color by mutableStateOf(gray900, structuralEqualityPolicy())
        private set
    var gray800: Color by mutableStateOf(gray800, structuralEqualityPolicy())
        private set
    var gray700: Color by mutableStateOf(gray700, structuralEqualityPolicy())
        private set
    var gray600: Color by mutableStateOf(gray600, structuralEqualityPolicy())
        private set
    var gray500: Color by mutableStateOf(gray500, structuralEqualityPolicy())
        private set
    var gray400: Color by mutableStateOf(gray400, structuralEqualityPolicy())
        private set
    var gray300: Color by mutableStateOf(gray300, structuralEqualityPolicy())
        private set
    var gray200: Color by mutableStateOf(gray200, structuralEqualityPolicy())
        private set
    var gray100: Color by mutableStateOf(gray100, structuralEqualityPolicy())
        private set
    var gray50: Color by mutableStateOf(gray50, structuralEqualityPolicy())
        private set

    fun copy(
        habitWhite: Color = this.habitWhite,
        habitBlack: Color = this.habitBlack,
        habitPurpleNormal: Color = this.habitPurpleNormal,
        habitPurpleLight: Color = this.habitPurpleLight,
        habitPurpleExtralight: Color = this.habitPurpleExtralight,
        habitPurpleExtralight2: Color = this.habitPurpleExtralight2,
        habitGreen: Color = this.habitGreen,
        habitGreenLight: Color = this.habitGreenLight,
        habitBlue: Color = this.habitBlue,
        habitBlueLight: Color = this.habitBlueLight,
        habitYellow: Color = this.habitYellow,
        habitPink: Color = this.habitPink,
        habitPinkLight: Color = this.habitPinkLight,
        habitError:Color = this.habitError,
        gray900: Color = this.gray900,
        gray800: Color = this.gray800,
        gray700: Color = this.gray700,
        gray600: Color = this.gray600,
        gray500: Color = this.gray500,
        gray400: Color = this.gray400,
        gray300: Color = this.gray300,
        gray200: Color = this.gray200,
        gray100: Color = this.gray100,
        gray50: Color = this.gray50
    ) = HabitColors(
        habitWhite = habitWhite,
        habitBlack = habitBlack,
        habitBlue = habitBlue,
        habitBlueLight = habitBlueLight,
        habitPinkLight = habitPinkLight,
        habitPink = habitPink,
        habitYellow = habitYellow,
        habitGreen = habitGreen,
        habitGreenLight = habitGreenLight,
        habitPurpleNormal = habitPurpleNormal,
        habitPurpleLight = habitPurpleLight,
        habitPurpleExtralight = habitPurpleExtralight,
        habitPurpleExtralight2 = habitPurpleExtralight2,
        habitError = habitError,
        gray900 = gray900,
        gray800 = gray800,
        gray700 = gray700,
        gray600 = gray600,
        gray500 = gray500,
        gray400 = gray400,
        gray300 = gray300,
        gray200 = gray200,
        gray100 = gray100,
        gray50 = gray50
    )
}

fun lightColors(
    habitWhite: Color = HabitWhite,
    habitBlack: Color = HabitBlack,
    habitBlue: Color = HabitBlue,
    habitBlueLight: Color = HabitBlueLight,
    habitPinkLight: Color = HabitPinkLight,
    habitPink: Color = HabitPink,
    habitYellow: Color = HabitYellow,
    habitGreen: Color = HabitGreen,
    habitGreenLight: Color = HabitGreenLight,
    habitPurpleNormal: Color = HabitPurpleNormal,
    habitPurpleLight: Color = HabitPurpleLight,
    habitPurpleExtralight: Color = HabitPurpleExtraLight,
    habitPurpleExtralight2: Color = HabitPurpleExtraLight4,
    habitError: Color = HabitError,
    gray900: Color = Gray900,
    gray800: Color = Gray800,
    gray700: Color = Gray700,
    gray600: Color = Gray600,
    gray500: Color = Gray500,
    gray400: Color = Gray400,
    gray300: Color = Gray300,
    gray200: Color = Gray200,
    gray100: Color = Gray100,
    gray50: Color = Gray50
) = HabitColors(
    habitWhite = habitWhite,
    habitBlack = habitBlack,
    habitBlue = habitBlue,
    habitBlueLight = habitBlueLight,
    habitPinkLight = habitPinkLight,
    habitPink = habitPink,
    habitYellow = habitYellow,
    habitGreen = habitGreen,
    habitGreenLight = habitGreenLight,
    habitPurpleNormal = habitPurpleNormal,
    habitPurpleLight = habitPurpleLight,
    habitPurpleExtralight = habitPurpleExtralight,
    habitPurpleExtralight2 = habitPurpleExtralight2,
    habitError = habitError,
    gray900 = gray900,
    gray800 = gray800,
    gray700 = gray700,
    gray600 = gray600,
    gray500 = gray500,
    gray400 = gray400,
    gray300 = gray300,
    gray200 = gray200,
    gray100 = gray100,
    gray50 = gray50
)

internal val LocalColors = staticCompositionLocalOf { lightColors() }