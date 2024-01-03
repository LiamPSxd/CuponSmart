package uv.tc.appcuponsmart.ui.viewmodel.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uv.tc.appcuponsmart.data.model.entidad.Ciudad
import uv.tc.appcuponsmart.domain.CiudadUseCase
import javax.inject.Inject

@HiltViewModel
class CiudadViewModel @Inject constructor(
    private val useCase: CiudadUseCase
): ViewModel(){
    val ciudades = MutableLiveData<MutableList<Ciudad>?>()
    val ciudad = MutableLiveData<Ciudad?>()
    val error = MutableLiveData<String?>()

    private suspend fun getError() = error.postValue(useCase())

    fun getCiudades() = viewModelScope.launch(Dispatchers.IO){
        ciudades.postValue(useCase.getCiudades())
        getError()
    }

    fun getCiudadesPorMunicipio(idMunicipio: Int) = viewModelScope.launch(Dispatchers.IO){
        ciudades.postValue(useCase.getCiudadesPorMunicipio(idMunicipio))
        getError()
    }

    fun getCiudad(idCiudad: Int) = viewModelScope.launch(Dispatchers.IO){
        ciudad.postValue(useCase.getCiudad(idCiudad))
        getError()
    }
}