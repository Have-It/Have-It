package com.habit.ui.screen.login.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.habit.ui.common.component.CustomDropDownMenu
import com.habit.ui.theme.HabitTheme

@Composable
fun EmailTextField(
    emailList: MutableList<String>,
    idText: String,
    domainText: String,
    onValueChanged: (String) -> Unit,
    onDropDownMenuSelected: (String) -> Unit,
    directInputExist: Boolean,
    directInputText: String = "",
    directInputTextChanged: (String) -> Unit = {}
) {
    Column() {
        Row(modifier = Modifier.align(Alignment.End)) {
            Spacer(modifier = Modifier.weight(5f))
            Box(modifier = Modifier.weight(5f)) {
                CustomDropDownMenu(emailList, domainText, onDropDownMenuSelected)
            }
        }
        Spacer(modifier = Modifier.size(20.dp))
        Row {
            BasicTextField(
                value = idText,
                textStyle = HabitTheme.typography.boldBodyGray,
                onValueChange = { changedString ->
                    onValueChanged(changedString)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(5f)
            )
            Text(
                text = "@",
                modifier = Modifier.align(Alignment.CenterVertically),
                style = HabitTheme.typography.boldBodyGray
            )
            Spacer(modifier = Modifier.width(10.dp))
            Box(
                modifier = Modifier
                    .weight(5f)
                    .align(Alignment.CenterVertically)
            ) {
                if (directInputExist && domainText == emailList[emailList.size - 1]) {
                    BasicTextField(
                        value = directInputText,
                        textStyle = HabitTheme.typography.boldBodyGray,
                        onValueChange = directInputTextChanged,
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    Text(text = domainText, style = HabitTheme.typography.boldBodyGray)

                }
            }

        }
    }
}

@Composable
@Preview
fun EmailTextFieldPreview() {
    var list = mutableListOf<String>()
    list.apply {
        add("gmail.com")
        add("naver.com")
        add("kakao.com")
        add("daum.net")
        add("hanmail.net")
        add("직접 입력")
    }
    var idText by remember { mutableStateOf("") }
    var directInputText by remember { mutableStateOf("") }
    var domainText by remember { mutableStateOf("") }
    EmailTextField(
        emailList = list,
        idText = idText,
        domainText = domainText,
        onValueChanged = { changedString ->
            idText = changedString
        },
        onDropDownMenuSelected = { selectedMenu ->
            domainText = selectedMenu
        },
        directInputExist = true,
        directInputText = directInputText,
        directInputTextChanged = { changedText ->
            directInputText = changedText
        }
    )
}