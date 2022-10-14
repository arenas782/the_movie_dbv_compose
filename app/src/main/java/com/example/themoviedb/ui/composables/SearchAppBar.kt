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
import androidx.compose.ui.text.TextStyle


@OptIn(ExperimentalTextApi::class)
@Composable
fun SearchAppBar(
    text : String,
    onTextChanged : (String) -> Unit,
    onCloseClicked : () -> Unit,
    onSearchClicked : (String) -> Unit
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
            .padding( end = 16.dp),) {
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
                onValueChange = { onTextChanged(it) },
                decorationBox = { innerTextField ->
                    Row(
                        Modifier
                            .background(Color.White, RoundedCornerShape(percent = 20))
                            .fillMaxWidth()


                    ) {

                        if (text.isEmpty()) {
                            Text("Search",
                            style = TextStyle(color = Color.Gray)
                            )
                        }
                        innerTextField()  //<-- Add this
                    }
                },
            )

//            TextField(
//                modifier =  Modifier.background(Color.White).height(40.dp).padding(0.dp),
//                shape = MaterialTheme.shapes.small.copy(bottomEnd = ZeroCornerSize, bottomStart = ZeroCornerSize),
//                textStyle = TextStyle(fontSize = 4.sp, platformStyle = PlatformTextStyle(
//                    includeFontPadding = false,
//                )),
//                onValueChange ={ onTextChanged(it) },
//                value = text,
//                singleLine = true,
//                placeholder = {
//                    Text(
//                        modifier = Modifier
//                            .alpha(ContentAlpha.medium),
//                        style = TextStyle(fontSize = 4.sp, platformStyle = PlatformTextStyle(
//                            includeFontPadding = false,
//                        )
//                        ),
//                        text = "Search for a TV Show",
//                        color = Color.Black
//                    )
//                },
//
//                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
//                keyboardActions = KeyboardActions(
//                    onSearch =  {
//                        onSearchClicked(text)
//                    }
//                )
//            )

        }
//        TextField(
//            modifier = Modifier
//                .fillMaxWidth()
//                ,
//            onValueChange ={ onTextChanged(it) },
//            value = text,
//            singleLine = true,
//            placeholder = {
//                Text(
//                    modifier = Modifier
//                        .alpha(ContentAlpha.medium),
//                    text = "Search for a TV Show",
//                    color = Color.White
//                )
//            },
//            leadingIcon = {
//
//            },
//            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
//            keyboardActions = KeyboardActions(
//                onSearch =  {
//                    onSearchClicked(text)
//                }
//            )
//        )
    }
}

@Composable
@Preview
fun SearchAppBarPreview(){
    SearchAppBar(text = "", onTextChanged = {}, onCloseClicked = {  }, onSearchClicked ={} )
}