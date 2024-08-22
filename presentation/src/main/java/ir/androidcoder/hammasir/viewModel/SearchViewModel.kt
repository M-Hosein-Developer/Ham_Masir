package ir.androidcoder.hammasir.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.androidcoder.domain.entities.HomeEntity
import ir.androidcoder.domain.useCase.search.SearchUsecase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val usecase: SearchUsecase) : ViewModel() {

    private val _homeLocation = MutableLiveData<HomeEntity>()
    val homeLocation: LiveData<HomeEntity> = _homeLocation

    fun insertHomeLocation(homeLocation : HomeEntity) = viewModelScope.launch {
        usecase.insertHomeLocation(homeLocation)
    }

    fun getHomeLocation() = viewModelScope.launch {
        _homeLocation.value = usecase.getHomeLocation()
    }

}