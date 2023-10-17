package com.habit.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp
import com.habit.R

val NanumSquareRound = FontFamily(
    Font(R.font.nanum_square_round_otf_extrabold, FontWeight.ExtraBold),
    Font(R.font.nanum_square_round_otf_bold, FontWeight.Bold),
    Font(R.font.nanum_square_round_otf_regular, FontWeight.Medium),
    Font(R.font.nanum_square_round_otf_light, FontWeight.Light),
)

// Set of Material typography styles to start with
val Typography = HabitTypography(
//    bodyLarge = TextStyle(
//        fontFamily = NanumSquareRound,
//        fontWeight = FontWeight.Normal,
//        fontSize = 16.sp,
//        lineHeight = 24.sp,
//        letterSpacing = 0.5.sp
//    ),

    headline3 = TextStyle(
        fontFamily = NanumSquareRound,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    headline1 = TextStyle(
        fontFamily = NanumSquareRound,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 28.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    headline2 = TextStyle(
        fontFamily = NanumSquareRound,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    headTextPurpleNormal = TextStyle(
        fontFamily = NanumSquareRound,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 24.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
        color = HabitPurpleNormal
    ),
    headTextPurpleLight = TextStyle(
        fontFamily = NanumSquareRound,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 24.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
        color = HabitPurpleLight
    ),
    headTextWhite = TextStyle(
        fontFamily = NanumSquareRound,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 24.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        ),
        color = HabitWhite
    ),
    body1 = TextStyle(
        fontFamily = NanumSquareRound,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 27.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    body2 = TextStyle(
        fontFamily = NanumSquareRound,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    body3 = TextStyle(
        fontFamily = NanumSquareRound,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 21.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    body4 = TextStyle(
        fontFamily = NanumSquareRound,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 18.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    blackBodyPurpleNormal = TextStyle(
        fontFamily = NanumSquareRound,
        fontWeight = FontWeight.Black,
        fontSize = 20.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
        color = HabitPurpleNormal
    ),
    boldBodyGray = TextStyle(
        fontFamily = NanumSquareRound,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
        color = Gray800
    ),
    boldBodyBlack = TextStyle(
        fontFamily = NanumSquareRound,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
        color = Color.Black
    ),
    miniBoldBody= TextStyle(
        fontFamily = NanumSquareRound,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    ),
    boldBodyPurpleNormal = TextStyle(
        fontFamily = NanumSquareRound,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
        color = HabitPurpleNormal
    ),
    mediumBodyPurpleNormal = TextStyle(
        fontFamily = NanumSquareRound,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
        color = HabitPurpleNormal
    ),
    caption1 = TextStyle(
        fontFamily = NanumSquareRound,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    caption2 = TextStyle(
        fontFamily = NanumSquareRound,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 21.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    caption3 = TextStyle(
        fontFamily = NanumSquareRound,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 18.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    mediumBoldTextBlack = TextStyle(
        fontFamily = NanumSquareRound,
        fontWeight = FontWeight.Black,
        fontSize = 20.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        ),
        color = Color.Black
    ),
    mediumNormalTextGray700 = TextStyle(
        fontFamily = NanumSquareRound,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        ),
        color = Gray700
    ),
    mediumBoldTextWhite = TextStyle(
        fontFamily = NanumSquareRound,
        fontWeight = FontWeight.Black,
        fontSize = 20.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        ),
        color = Color.White
    ),
    mediumSquareButtonTextKakaoLabel = TextStyle(
        fontFamily = NanumSquareRound,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        ),
        color = KakoLabel
    ),
    cardTextBlack = TextStyle(
        fontFamily = NanumSquareRound,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 20.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    ),
    cardTextGray900 = TextStyle(
        fontFamily = NanumSquareRound,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 20.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        ),
        color = Gray900
    ),
    largeCardTextGray900 = TextStyle(
        fontFamily = NanumSquareRound,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 26.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        ),
        color = Gray900
    ),
    extraLargeBoldTextPurpleNormal = TextStyle(
        fontFamily = NanumSquareRound,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 40.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        ),
        color = HabitPurpleNormal
    )




    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

@Immutable
data class HabitTypography(
    val headline1: TextStyle,
    val headline2: TextStyle,
    val headline3: TextStyle,
    val headTextPurpleNormal: TextStyle,
    val headTextPurpleLight: TextStyle,
    val headTextWhite: TextStyle,
    val body1: TextStyle,
    val body2: TextStyle,
    val body3: TextStyle,
    val body4: TextStyle,
    val blackBodyPurpleNormal: TextStyle,
    val boldBodyGray: TextStyle,
    val boldBodyBlack: TextStyle,
    val miniBoldBody: TextStyle,
    val boldBodyPurpleNormal: TextStyle,
    val mediumBodyPurpleNormal: TextStyle,
    val caption1: TextStyle,
    val caption2: TextStyle,
    val caption3: TextStyle,
    val mediumBoldTextWhite: TextStyle,
    val mediumBoldTextBlack: TextStyle,
    val mediumNormalTextGray700: TextStyle,
    val mediumSquareButtonTextKakaoLabel: TextStyle,
    val cardTextBlack: TextStyle,
    val cardTextGray900: TextStyle,
    val largeCardTextGray900: TextStyle,
    val extraLargeBoldTextPurpleNormal: TextStyle
)

internal val LocalTypography = staticCompositionLocalOf { Typography }