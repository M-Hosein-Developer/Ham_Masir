package ir.androidcoder.hammasir.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.androidcoder.domain.entities.HomeEntity
import ir.androidcoder.domain.entities.SearchEntity
import ir.androidcoder.domain.entities.WorkEntity
import ir.androidcoder.domain.useCase.search.SearchUsecase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val usecase: SearchUsecase) : ViewModel() {

    //home location
    private val _homeLocation = MutableLiveData<HomeEntity>()
    val homeLocation: LiveData<HomeEntity> = _homeLocation

    //work location
    private val _workLocation = MutableLiveData<WorkEntity>()
    val workLocation : LiveData<WorkEntity> = _workLocation

    //---home location------------------------------------------------------------------------------
    fun insertHomeLocation(homeLocation : HomeEntity) = viewModelScope.launch {
        usecase.insertHomeLocation(homeLocation)
    }

    fun getHomeLocation() = viewModelScope.launch {
        _homeLocation.value = usecase.getHomeLocation()
    }

    //---work location------------------------------------------------------------------------------
    fun insertWorkLocation(workLocation : WorkEntity) = viewModelScope.launch {
        usecase.insertWorkLocation(workLocation)
    }

    fun getWorkLocation() = viewModelScope.launch {
        _workLocation.value = usecase.getWorkLocation()
    }

    //---search location----------------------------------------------------------------------------
    fun getSearchLocation(search : String , onSearchLocation :(SearchEntity) -> Unit) = viewModelScope.launch {
        onSearchLocation.invoke(usecase.getSearchLocation(search , "pe"))
    }


}