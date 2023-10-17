package com.habit.ui.screen.initialsetting.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.habit.ui.common.model.FreeMissionType
import com.habit.ui.theme.HabitPurpleExtraLight
import com.habit.ui.theme.HabitPurpleNormal
import com.habit.ui.theme.HabitTheme

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ChipList(
    chips: List<FreeMissionType>,
    initStartPosition: Int = 0,
    onChipClick: (selectedObject: FreeMissionType) -> Any
) {
    var selected by rememberSaveable { mutableStateOf(initStartPosition) }

    LazyRow(
        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        itemsIndexed(chips) { index: Int, item: FreeMissionType ->
            ElevatedFilterChip(
                shape = RoundedCornerShape(20.dp),
                selected = selected == index,
                onClick = {
                    selected = index
                    onChipClick(item)
                },
                label = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(id = item.icon),
                            contentDescription = item.text
                        )
                        Text(
                            text = item.text,
                            style = HabitTheme.typography.miniBoldBody,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                        )
                    }

                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = HabitPurpleNormal,
                    selectedLabelColor = Color.White,
                    containerColor = Color.White
                ),
                elevation = FilterChipDefaults.filterChipElevation(elevation = 8.dp),
                border = FilterChipDefaults.filterChipBorder(
                    borderColor = HabitPurpleNormal,
                    borderWidth = 0.5.dp
                )
            )
        }
    }

}

@Composable
@Preview
fun PrevChips() {
    val list = listOf<FreeMissionType>(
        FreeMissionType.CLEANING,
        FreeMissionType.BOOK,
        FreeMissionType.VITAMIN
    )
    ChipList(chips = list, onChipClick = {})
}