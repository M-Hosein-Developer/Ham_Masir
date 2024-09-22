package ir.androidcoder.hammasir.screen.feature

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ir.androidcoder.hammasir.R

@Composable
fun SettingScreen(){

    Column(
        Modifier
            .fillMaxSize()
    ) {

        HamMasirProfile()

    }

}

@Composable
fun HamMasirProfile(){

    Row(
        Modifier.fillMaxWidth().background(Color.Gray).padding(top = 42.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {

        Column(
            Modifier
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.End
        ) {

            Text(text = "کاربر نشان" , Modifier.padding(top = 8.dp))

            Text(text = "09107806365" , Modifier.padding(top = 6.dp))

            TextButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowLeft, contentDescription = null)
                Text(text = "مشاهده پروفایل" , style = TextStyle(textAlign = TextAlign.End))
            }
        }

        AsyncImage(
            model = Icon(painter = painterResource(R.drawable.user), contentDescription = null),
            contentDescription = null,
            modifier = Modifier
                .size(20.dp)
        )

    }

}