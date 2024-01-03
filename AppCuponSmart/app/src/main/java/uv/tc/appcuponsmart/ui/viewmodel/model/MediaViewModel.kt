package uv.tc.appcuponsmart.ui.viewmodel.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uv.tc.appcuponsmart.domain.MediaUseCase
import javax.inject.Inject

@HiltViewModel
class MediaViewModel @Inject constructor(
    private val useCase: MediaUseCase
): ViewModel(){
    val imagenPromocion = MutableLiveData<String?>()
    val fotoCliente = MutableLiveData<String?>()
    val status = MutableLiveData<Boolean>()
    val error = MutableLiveData<String?>()

    private suspend fun getError() = error.postValue(useCase())

    fun getImagenPromocion(idPromocion: Int) = viewModelScope.launch(Dispatchers.IO){
        imagenPromocion.postValue(useCase.getImagenPromocion(idPromocion))
        getError()
    }

    fun getFotoCliente(idCliente: Int) = viewModelScope.launch(Dispatchers.IO){
        fotoCliente.postValue(useCase.getFotoCliente(idCliente))
        getError()
    }

    fun addFotoCliente(idCliente: Int, foto: ByteArray) = viewModelScope.launch(Dispatchers.IO){
        status.postValue(useCase.addFotoCliente(idCliente, foto))
        getError()
    }
}