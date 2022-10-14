package com.example.themoviedb.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchAppBar(
    text : String,
    onTextChanged : (String) -> Unit,
    onCloseClicked : () -> Unit,
){
    Surface (
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = MaterialTheme.colors.primary
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(end = 16.dp),) {
            IconButton(modifier = Modifier
                .alpha(ContentAlpha.medium),
                onClick = {
                    onCloseClicked()
                }
            ) {
                Icon(imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = Color.White
                )
            }

            BasicTextField(
                value = text,
                maxLines = 1,
                onValueChange = { onTextChanged(it) },
                decorationBox = { innerTextField ->
                    Row(
                        Modifier
                            .background(MaterialTheme.colors.surface, RoundedCornerShape(percent = 20))
                            .fillMaxWidth().padding(4.dp)
                    ) {

                        if (text.isEmpty()) {
                            val focusRequester = FocusRequester()
                            val keyboardController = LocalSoftwareKeyboardController.current
                            DisposableEffect(Unit) {
                                focusRequester.requestFocus()
                                onDispose { }
                            }

                            Text("Search",
                            style = TextStyle(color = Color.Gray),
                                modifier = Modifier.focusRequester(focusRequester)
                                    .onFocusChanged {
                                        if (it.isFocused) {
                                            keyboardController?.show()
                                        }
                                    }

                            )
                        }
                        innerTextField()  //<-- Add this
                    }
                },

            )
        }
    }

}


@Composable
@Preview
fun SearchAppBarPreview(){
    SearchAppBar(text = "", onTextChanged = {}, onCloseClicked = {  })
}