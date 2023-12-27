package uv.tc.appcuponsmart.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uv.tc.appcuponsmart.data.model.entidad.Cliente
import uv.tc.appcuponsmart.domain.ClienteUseCase
import javax.inject.Inject

@HiltViewModel
class ClienteViewModel @Inject constructor(
    private val useCase: ClienteUseCase
): ViewModel(){
    val clientes = MutableLiveData<MutableList<Cliente>?>()
    val cliente = MutableLiveData<Cliente?>()
    val status = MutableLiveData<Boolean>()
    val error = MutableLiveData<String?>()

    private suspend fun getError() = error.postValue(useCase())

    fun getClientes() = viewModelScope.launch(Dispatchers.IO){
        status.postValue(true)

        useCase.getClientes().also{
            clientes.postValue(it)

            status.postValue(false)
        }

        getError()
    }

    fun getCliente(idCliente: Int) = viewModelScope.launch(Dispatchers.IO){
        cliente.postValue(useCase.getCliente(idCliente))
        getError()
    }

    fun getCliente(correo: String) = viewModelScope.launch(Dispatchers.IO){
        cliente.postValue(useCase.getCliente(correo))
        getError()
    }

    fun addCliente(cliente: Cliente) = viewModelScope.launch(Dispatchers.IO){
        status.postValue(useCase.addCliente(cliente))
        getError()
    }

    fun updateCliente(cliente: Cliente) = viewModelScope.launch(Dispatchers.IO){
        status.postValue(useCase.updateCliente(cliente))
        getError()
    }

    fun deleteCliente(idCliente: Int) = viewModelScope.launch(Dispatchers.IO){
        status.postValue(useCase.deleteCliente(idCliente))
        getError()
    }
}