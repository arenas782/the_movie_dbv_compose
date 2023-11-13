package com.example.themoviedb.ui.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
@Composable
fun ChipFilter(
    filterName : String,
    isSelected : Boolean = false,
    onSelectedCategoryChanged : (String) -> Unit,
    onExecuteSearch : () -> Unit
){
    Surface(
        modifier = Modifier.padding(end = 8.dp),
        elevation = 8.dp,
        shape = RoundedCornerShape(50), // = 50% percent
        color = if (!isSelected) Color.LightGray else MaterialTheme.colors.primary
    ){
        Row(
            modifier = Modifier.toggleable(
                value = isSelected,
                onValueChange = {
                    onSelectedCategoryChanged(filterName)
                    onExecuteSearch()
                }
            )
        ){
            Text(
                text = filterName,
                color = Color.White,
                modifier = Modifier.padding(vertical = 6.dp, horizontal = 12.dp)
            )
        }
    }
}