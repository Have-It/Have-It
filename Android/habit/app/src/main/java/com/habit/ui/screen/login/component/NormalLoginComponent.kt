package com.habit.ui.screen.login.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.habit.R
import com.habit.ui.common.component.PurpleNormalUnderLine
import com.habit.ui.theme.HabitTheme

@Composable
fun NormalLoginComponent(
    emailList: MutableList<String>,
    idText: String,
    domainText: String,
    onIdTextChanged: (String) -> Unit,
    onDropDownMenuSelected: (String) -> Unit,
    directInputExist: Boolean,
    directInputText: String = "",
    directInputTextChanged: (String) -> Unit = {},
    passWordText: String,
    onPassWordTextChanged: (String) -> Unit
) {

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(id = R.string.login_email),
            style = HabitTheme.typography.blackBodyPurpleNormal
        )
        EmailTextField(
            emailList = emailList,
            idText = idText,
            domainText = domainText,
            onValueChanged = onIdTextChanged,
            onDropDownMenuSelected = onDropDownMenuSelected,
            directInputExist = directInputExist,
            directInputText = directInputText,
            directInputTextChanged = directInputTextChanged
        )
        PurpleNormalUnderLine()
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = stringResource(id = R.string.login_password),
            style = HabitTheme.typography.blackBodyPurpleNormal
        )
        Spacer(modifier = Modifier.height(20.dp))
        Box(modifier = Modifier.fillMaxWidth()) {
            BasicTextField(
                value = passWordText,
                onValueChange = { changedString ->
                    onPassWordTextChanged(changedString)
                },
                textStyle = HabitTheme.typography.boldBodyGray,
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
            )
        }
        PurpleNormalUnderLine()
    }

}
