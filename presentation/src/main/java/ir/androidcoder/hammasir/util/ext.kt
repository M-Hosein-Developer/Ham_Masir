package ir.androidcoder.hammasir.util

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler

val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
    Log.v("errorCo" , "Error -> " + throwable.message)
}