package ir.androidcoder.hammasir.screen.feature

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ir.androidcoder.domain.entities.SearchEntity
import ir.androidcoder.hammasir.R
import ir.androidcoder.hammasir.util.Category
import ir.androidcoder.hammasir.util.MyScreen
import ir.androidcoder.hammasir.util.fakeSearchEntity
import ir.androidcoder.hammasir.viewModel.SearchViewModel

@Composable
fun SearchScreen(navController: NavHostController, searchViewModel: SearchViewModel) {

    var searchText by remember { mutableStateOf("") }
    val searchData = remember { mutableStateOf(fakeSearchEntity) }
    searchViewModel.getHomeLocation()
    searchViewModel.getWorkLocation()

    val categories = listOf(
        Category("پمپ بنزین", R.drawable.gas_station),
        Category("پمپ گاز", R.drawable.gas_station),
        Category("رستوران", R.drawable.food),
        Category("سرویس بهداشتی", R.drawable.wc),
    )

    searchViewModel.getSearchLocation(searchText) {
        searchData.value = it
    }

    Box(Modifier.fillMaxSize()) {


    Column(
        Modifier
            .fillMaxSize()
            .padding(top = 42.dp, bottom = 32.dp)
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
                .fillMaxWidth()
        ) {

            LocationByCategory(categories) {

                when (it) {

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
                        navController.navigate(MyScreen.MapScreen.route + "/" + result.latitude.toString() + "/" + result.longitude.toString() + "/" + result.name)
                },
                onWorkClicked = {
                    val result = searchViewModel.workLocation.value
                    if (result != null)
                        navController.navigate(MyScreen.MapScreen.route + "/" + result.latitude.toString() + "/" + result.longitude.toString() + "/" + result.name)
                }
            )

            Spacer(
                modifier = Modifier
                    .height(4.dp)
                    .fillMaxWidth()
                    .background(Color.LightGray)
            )

            if (searchData.value.hits!![0].name != "Tehran")
                SearchResult(searchData.value.hits!!){ lat , lng , name ->
                    navController.navigate(MyScreen.MapScreen.route + "/" + lat.toString() + "/" + lng.toString() + "/" + name)
                }


        }

    }

        FloatingActionButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .padding(start = 16.dp, bottom = 72.dp)
                .align(Alignment.BottomStart),

            ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "انتخاب از روی نقشه", Modifier.padding(start = 12.dp))
                Icon(
                    painter = painterResource(R.drawable.finding_map),
                    contentDescription = null,
                    Modifier.padding(horizontal = 12.dp)
                )
            }
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
        shape = RoundedCornerShape(32.dp),
        maxLines = 1
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
            .wrapContentHeight()
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
fun SearchResult(data: List<SearchEntity.Hit> , onLocationClicked :(Double , Double , String) -> Unit) {

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        items(data.size) {
            SearchResultItem(data[it]){ lat , lng , name ->
                onLocationClicked.invoke(lat , lng , name)
            }

        }
    }

}

@Composable
    fun SearchResultItem(data: SearchEntity.Hit , onLocationClicked :(Double , Double , String) -> Unit) {

    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(vertical = 6.dp)
            .clickable { onLocationClicked.invoke(data.point!!.lat!! , data.point!!.lng!! , data.name ?: "نام") }
        ) {

            Text(
                text = data.name ?: "نام",
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
                text =
                (if (data.country != null)data.country + "," else "")
                        + (if(data.state != null) data.state + "," else "")
                        +(if (data.city != null) data.city + "," else "")
                        +(if (data.name != null) data.name + "," else "")

                ,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .padding(bottom = 8.dp),
                style = TextStyle(
                    textDirection = TextDirection.Rtl
                ),
                maxLines = 1
            )

            Spacer(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(Color.LightGray)
            )

    }


}

