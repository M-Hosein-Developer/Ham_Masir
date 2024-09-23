package ir.androidcoder.hammasir.screen.feature

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import ir.androidcoder.hammasir.util.Category

@Composable
fun SettingScreen(){

    val item = listOf(
        Category("تنظیمات" , R.drawable.setting),
        Category("مکان های شخصی" , R.drawable.star),
    )
    
    Column(
        Modifier
            .fillMaxSize()
    ) {

        HamMasirProfile()
        SettingItem(item){

            when(it){

                "تنظیمات" -> {  }
                "مکان های شخصی" -> {  }

            }

        }

    }

}

@Composable
fun HamMasirProfile(){

    Row(
        Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .padding(top = 42.dp),
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

@Composable
fun SettingItem(item: List<Category> , onItemClicked :(String) -> Unit) {
    
    LazyColumn(
        Modifier.fillMaxSize()
    ) {
        items(item.size){
            Item(item[it]){
                onItemClicked.invoke(it)
            }
        }
    }
    
}

@Composable
fun Item(item: Category , onItemClicked :(String) -> Unit) {
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
            .clickable { onItemClicked.invoke(item.categoryName) },
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = item.categoryName,
            modifier = Modifier.padding(end = 16.dp)
            )

        Icon(
            painter = painterResource(item.icon),
            contentDescription = null ,
            modifier = Modifier.size(35.dp)
        )
    }
    
}