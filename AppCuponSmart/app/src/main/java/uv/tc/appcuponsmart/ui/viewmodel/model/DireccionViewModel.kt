package uv.tc.appcuponsmart.ui.viewmodel.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uv.tc.appcuponsmart.data.model.entidad.Direccion
import uv.tc.appcuponsmart.domain.DireccionUseCase
import javax.inject.Inject

@HiltViewModel
class DireccionViewModel @Inject constructor(
    private val useCase: DireccionUseCase
): ViewModel(){
    val direcciones = MutableLiveData<MutableList<Direccion>?>()
    val direccion = MutableLiveData<Direccion?>()
    val status = MutableLiveData<Boolean>()
    val error = MutableLiveData<String?>()

    private suspend fun getError() = error.postValue(useCase())

    fun getDirecciones() = viewModelScope.launch(Dispatchers.IO){
        direcciones.postValue(useCase.getDirecciones())
        getError()
    }

    fun getDireccion(idDireccion: Int) = viewModelScope.launch(Dispatchers.IO){
        direccion.postValue(useCase.getDireccion(idDireccion))
        getError()
    }

    fun addDireccion(direccion: Direccion) = viewModelScope.launch(Dispatchers.IO){
        status.postValue(useCase.addDireccion(direccion))
        getError()
    }

    fun updateDireccion(direccion: Direccion) = viewModelScope.launch(Dispatchers.IO){
        status.postValue(useCase.updateDireccion(direccion))
        getError()
    }

    fun deleteDireccion(idDireccion: Int) = viewModelScope.launch(Dispatchers.IO){
        status.postValue(useCase.deleteDireccion(idDireccion))
        getError()
    }
}