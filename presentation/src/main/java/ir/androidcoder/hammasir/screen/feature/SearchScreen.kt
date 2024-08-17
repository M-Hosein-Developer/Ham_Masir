package ir.androidcoder.hammasir.screen.feature

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun SearchScreen(){

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        SearchTextField(
            "در نقشه جسجتو کن",
            {

            },
            {

            }
        )

        LocationByCategory()
        ImportantLocation()

    }

}

@Composable
fun SearchTextField(value: String, onValueChange: (String) -> Unit, onBackClick: () -> Unit) {

    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange.invoke(it) },
        trailingIcon = {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.ArrowForward,
                contentDescription = null,
                modifier = Modifier.clickable {
                    onBackClick.invoke()
                }
            )
        },
        modifier = Modifier.fillMaxWidth(),
        textStyle = TextStyle(
            textAlign = TextAlign.End
        ),
        shape = RoundedCornerShape(32.dp)
    )

}

@Composable
fun LocationByCategory() {


}

@Composable
fun ImportantLocation() {


}

