package sebastian.company.min3rapp.ui.discuss.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sebastian.company.min3rapp.R
import sebastian.company.min3rapp.ui.theme.Typography

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun WriteComment(focusRequest: FocusRequester,
sendComment: (String) -> Unit, isLoading: Boolean){
    val keyboardController = LocalSoftwareKeyboardController.current
    val maxChar = 200
    var text by remember {mutableStateOf("")}
    var visible by remember { mutableStateOf(false)}
    Column(modifier = Modifier
        .background(
            colorResource(id = R.color.brand_darkblue_variant),
            RoundedCornerShape(topEnd = 4.dp, topStart = 4.dp)

        )
        .fillMaxWidth()
    ) {


        AnimatedVisibility(visible = visible) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(
                    16.dp
                ),
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(modifier = Modifier.align(Alignment.CenterVertically),
                    text = "Comment",
                    style = Typography.h4)
                Icon(modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        sendComment(text)
                    },
                    painter = painterResource(id = R.drawable.ic_send),
                    contentDescription = "sendComment",
                    tint = colorResource(id = R.color.brand_pink))
            }
        }


        TextField(value = text,
            onValueChange = {
                if(text.length <= maxChar) text = it },
            modifier = Modifier
                .fillMaxWidth()
                .focusable(true)
                .focusRequester(focusRequest)
                .onFocusChanged {
                    if (it.isFocused) {
                        visible = true
                        keyboardController?.show()
                    } else {
                        visible = false
                        keyboardController?.hide()
                    }
                },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = colorResource(id = R.color.brand_darkblue_variant)
            ),
            enabled = !isLoading,
            textStyle = Typography.body1,
            placeholder = { Text(text = "Write something...",
            style = Typography.body2, modifier = Modifier.fillMaxWidth())}

        )

    }

}


@Preview
@Composable
fun TestWriteComment(){
    val focusRequest = FocusRequester()
    WriteComment(focusRequest, sendComment = {comment ->
        println(comment)
    }, false)
}