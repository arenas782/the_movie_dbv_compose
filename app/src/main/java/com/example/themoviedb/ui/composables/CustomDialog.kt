package com.example.themoviedb.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun CustomDialog(value: String, setShowDialog: (Boolean) -> Unit, onLeaveClicked: () -> Unit) {

    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = Color.White
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = value,
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                    ){
                        TextButton(
                            onClick = {
                                setShowDialog(false)
                                onLeaveClicked()
                            },
                            modifier = Modifier
                                .height(50.dp)
                        ) {
                            Text(text = "LEAVE", style = TextStyle(color = Color.Red))
                        }
                        TextButton(
                            onClick = {
                                setShowDialog(false)
                            },
                            modifier = Modifier
                                .height(50.dp)
                        ) {
                            Text(text = "STAY")
                        }
                    }
                }
            }
        }
    }
}
@Preview
@Composable
fun PreviewDialog(){
    CustomDialog(value = "Text", setShowDialog ={} , onLeaveClicked = {})
}