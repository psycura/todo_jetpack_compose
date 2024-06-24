@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.to_docompose.ui.screens.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.example.to_docompose.R
import com.example.to_docompose.ui.theme.TOOL_BAR_HEIGHT
import com.example.to_docompose.ui.theme.TOP_APP_BAR_HEIGHT
import com.example.to_docompose.ui.theme.topAppBarBackgroundColor
import com.example.to_docompose.ui.theme.topAppBarContentColor

@Composable
fun SearchListAppBar(
    text: String,
    onTextChanged: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(TOP_APP_BAR_HEIGHT + TOOL_BAR_HEIGHT),
        color = MaterialTheme.colorScheme.topAppBarBackgroundColor,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(TOOL_BAR_HEIGHT)
            )
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    cursorColor = MaterialTheme.colorScheme.topAppBarContentColor
                ),
                onValueChange = { onTextChanged(it) },
                placeholder = {
                    Text(
                        modifier = Modifier.alpha(0.8f),
                        text = "Search",
                        color = MaterialTheme.colorScheme.topAppBarContentColor
                    )
                },
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.topAppBarContentColor,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                ),
                singleLine = true,
                leadingIcon = {
                    IconButton(
                        modifier = Modifier.alpha(0.38f),
                        onClick = { onSearchClicked(text) }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = stringResource(R.string.search_tasks),
                            tint = MaterialTheme.colorScheme.topAppBarContentColor
                        )
                    }
                },
                trailingIcon = {
                    IconButton(
                        modifier = Modifier.alpha(0.38f),
                        onClick = { onCloseClicked() }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = stringResource(R.string.close_search),
                            tint = MaterialTheme.colorScheme.topAppBarContentColor
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = { onSearchClicked(text) }
                ),

                )
        }

    }
}

@Composable
@Preview
private fun SearchListAppBarPreview() {
    SearchListAppBar(
        text = "",
        onSearchClicked = {},
        onCloseClicked = {},
        onTextChanged = {}
    )
}