package ir.androidcoder.hammasir.screen.feature

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun SettingScreen(){

    Column(
        Modifier.fillMaxSize()
    ) {

        HamMasirProfile()

    }

}

@Composable
fun HamMasirProfile(){

    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {

        AsyncImage(
            model = Icons.Outlined.Person,
            contentDescription = null,
            modifier = Modifier.size(70.dp)
            )

        Column(
            Modifier.fillMaxWidth()
        ) {

            Text(text = "کاربر نشان")

            Text(text = "09107806365")

            TextButton(onClick = { /*TODO*/ }) {
                Text(text = "مشاهده پروفایل")
                Icon(imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowLeft, contentDescription = null)
            }
        }

    }

}

@Composable
@Preview(showBackground = true)
fun SettingScreenPreview() {
    SettingScreen()
}