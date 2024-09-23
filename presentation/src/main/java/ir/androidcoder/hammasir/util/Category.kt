package ir.androidcoder.hammasir.util

import androidx.compose.ui.graphics.vector.ImageVector
import ir.androidcoder.hammasir.R

data class Category(

    val categoryName: String,
    val icon: Int,

) {
    constructor(categoryName: String, icon: ImageVector) : this("" , R.drawable.ic_launcher_background)
}
