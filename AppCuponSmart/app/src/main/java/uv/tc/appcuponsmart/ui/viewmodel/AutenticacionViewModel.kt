package uv.tc.appcuponsmart.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uv.tc.appcuponsmart.data.model.entidad.Cliente
import uv.tc.appcuponsmart.domain.AutenticacionUseCase
import javax.inject.Inject

@HiltViewModel
class AutenticacionViewModel @Inject constructor(
    private val useCase: AutenticacionUseCase
): ViewModel(){
    val cliente = MutableLiveData<Cliente?>()
    val error = MutableLiveData<String?>()

    private suspend fun getError() = error.postValue(useCase())

    fun loginMovil(correo: String, contrasenia: String) = viewModelScope.launch(Dispatchers.IO){
        cliente.postValue(useCase.loginMovil(correo, contrasenia))
        getError()
    }
}