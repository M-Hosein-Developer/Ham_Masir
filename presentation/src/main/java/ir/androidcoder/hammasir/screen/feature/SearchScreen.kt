package ir.androidcoder.hammasir.screen.feature

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ir.androidcoder.hammasir.R
import ir.androidcoder.hammasir.util.Category
import ir.androidcoder.hammasir.util.MyScreen
import ir.androidcoder.hammasir.viewModel.SearchViewModel

@Composable
fun SearchScreen(navController: NavHostController, searchViewModel: SearchViewModel) {

    var searchText by remember { mutableStateOf("") }
    searchViewModel.getHomeLocation()
    searchViewModel.getWorkLocation()

    val categories = listOf(
        Category("پمپ بنزین", R.drawable.gas_station),
        Category("پمپ گاز", R.drawable.gas_station),
        Category("رستوران", R.drawable.food),
        Category("سرویس بهداشتی", R.drawable.wc),
    )

    Column(
        Modifier
            .fillMaxSize()
            .padding(top = 42.dp , bottom = 32.dp)

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
            Modifier
                .fillMaxSize()
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

            Spacer(
                modifier = Modifier
                    .height(4.dp)
                    .fillMaxWidth()
                    .background(Color.LightGray)
            )

            ImportantLocation(
                onHomeClicked = {
                    val result = searchViewModel.homeLocation.value
                    if (result != null)
                    navController.navigate(MyScreen.MapScreen.route + "/" + result.latitude.toString() + "/" +result.longitude.toString() )
                },
                onWorkClicked = {
                    val result = searchViewModel.workLocation.value
                    if (result != null)
                        navController.navigate(MyScreen.MapScreen.route + "/" + result.latitude.toString() + "/" +result.longitude.toString() )
                }
            )

            Spacer(
                modifier = Modifier
                    .height(4.dp)
                    .fillMaxWidth()
                    .background(Color.LightGray)
            )

            SearchResult(searchViewModel)

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
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        textStyle = TextStyle(
            textAlign = TextAlign.End
        ),
        shape = RoundedCornerShape(32.dp)
    )

}

@Composable
fun LocationByCategory(categories: List<Category>, onItemClicked: (String) -> Unit) {

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
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
fun ImportantLocation(onHomeClicked :() -> Unit , onWorkClicked :() -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = CenterVertically,
    ){

        TextButton(
            onClick = { onWorkClicked.invoke() },
            modifier = Modifier
                .padding(horizontal = 4.dp)
        ) {
            Text(text = "محل کار")
            Spacer(modifier = Modifier.width(8.dp))
            Icon(painter = painterResource(id = R.drawable.work), contentDescription = null)
        }

        Spacer(modifier = Modifier
            .background(Color.LightGray)
            .width(2.dp)
            .fillMaxHeight()
            .padding(24.dp))

        TextButton(
            onClick = { onHomeClicked.invoke() },
            modifier = Modifier
                .padding(horizontal = 4.dp),
        ) {
            Text(text = "خانه")
            Spacer(modifier = Modifier.width(8.dp))
            Icon(painter = painterResource(id = R.drawable.home), contentDescription = null)
        }


    }

}

@Composable
fun SearchResult(searchViewModel: SearchViewModel) {

    searchViewModel.searchLocation.value?.forEach {

        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(vertical = 6.dp)
        ) {

            Text(
                text = it.city,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                style = TextStyle(
                    textAlign = TextAlign.End,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            Text(
                text = it.address,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .padding(bottom = 8.dp),
                style = TextStyle(
                    textAlign = TextAlign.End,
                )
            )

            Spacer(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(Color.LightGray)
            )

        }


    }


}

