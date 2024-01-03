package uv.tc.appcuponsmart.ui.viewmodel.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uv.tc.appcuponsmart.data.model.entidad.Cliente
import uv.tc.appcuponsmart.domain.AutenticacionUseCase
import uv.tc.appcuponsmart.domain.DataStoreUseCase
import javax.inject.Inject

@HiltViewModel
class IniciarSesionViewModel @Inject constructor(
    private val autenticacionUseCase: AutenticacionUseCase,
    private val dataStoreUseCase: DataStoreUseCase
): ViewModel(){
    val cliente = MutableLiveData<Cliente?>()
    val error = MutableLiveData<String?>()

    private fun setError(err: String?) = viewModelScope.launch(Dispatchers.IO){
        error.postValue(err)
    }

    fun loginMovil(correo: String, contrasenia: String) = viewModelScope.launch(Dispatchers.IO){
        cliente.postValue(autenticacionUseCase.loginMovil(correo, contrasenia))
        setError(autenticacionUseCase())
    }

    fun putIdCliente(idCliente: Int) = viewModelScope.launch(Dispatchers.IO){
        dataStoreUseCase.putIdCliente(idCliente)
    }

    fun putNombreCliente(nombre: String) = viewModelScope.launch(Dispatchers.IO){
        dataStoreUseCase.putNombreCliente(nombre)
    }
}