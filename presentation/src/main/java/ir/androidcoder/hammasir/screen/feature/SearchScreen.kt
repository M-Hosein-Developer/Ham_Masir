package ir.androidcoder.hammasir.screen.feature

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ir.androidcoder.hammasir.R
import ir.androidcoder.hammasir.util.Category

@Composable
fun SearchScreen(navController: NavHostController) {

    var searchText by remember { mutableStateOf("") }

    val categories = listOf(
        Category("پمپ بنزین", R.drawable.gas_station),
        Category("پمپ گاز", R.drawable.gas_station),
        Category("رستوران", R.drawable.food),
        Category("سرویس بهداشتی", R.drawable.wc),
    )

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        SearchTextField(
            searchText,
            {
                searchText = it
            },
            {
                navController.popBackStack()
            }
        )


        Column(
            Modifier.fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            LocationByCategory(categories) {

                when(it){

                    categories[0].categoryName -> {}
                    categories[1].categoryName -> {}
                    categories[2].categoryName -> {}
                    categories[3].categoryName -> {}

                }

            }

            ImportantLocation()

        }


    }

}

@Composable
fun SearchTextField(value: String, onValueChange: (String) -> Unit, onBackClick: () -> Unit) {

    OutlinedTextField(
        label = {
            Text(
                "در نقشه جستجو کن",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
        },
        value = value,
        onValueChange = { onValueChange.invoke(it) },
        trailingIcon = {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.ArrowForward,
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        onBackClick.invoke()
                    }
                    .padding(end = 8.dp)
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
fun LocationByCategory(categories: List<Category>, onItemClicked: (String) -> Unit) {

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = CenterVertically,
        reverseLayout = true
    ) {
        items(categories.size) {
            LocationByCategoryItem(categories[it]) { onItemClicked.invoke(it) }
        }
    }

}

@Composable
fun LocationByCategoryItem(category: Category, onItemClicked: (String) -> Unit) {

    TextButton(
        onClick = { onItemClicked.invoke(category.categoryName) },
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .padding(vertical = 14.dp)
            .border(1.dp, Color.Gray, RoundedCornerShape(32.dp)),
    ) {
        Text(text = category.categoryName)
        Spacer(modifier = Modifier.width(8.dp))
        Icon(painter = painterResource(id = category.icon), contentDescription = null)
    }

}

@Composable
fun ImportantLocation() {


}

