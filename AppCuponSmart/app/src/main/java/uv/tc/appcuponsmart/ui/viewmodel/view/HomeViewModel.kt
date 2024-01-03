package uv.tc.appcuponsmart.ui.viewmodel.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uv.tc.appcuponsmart.data.model.entidad.Categoria
import uv.tc.appcuponsmart.data.model.entidad.Cliente
import uv.tc.appcuponsmart.data.model.entidad.Promocion
import uv.tc.appcuponsmart.domain.CategoriaUseCase
import uv.tc.appcuponsmart.domain.ClienteUseCase
import uv.tc.appcuponsmart.domain.DataStoreUseCase
import uv.tc.appcuponsmart.domain.MediaUseCase
import uv.tc.appcuponsmart.domain.PromocionUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val clienteUseCase: ClienteUseCase,
    private val categoriaUseCase: CategoriaUseCase,
    private val promocionUseCase: PromocionUseCase,
    private val mediaUseCase: MediaUseCase,
    private val dataStoreUseCase: DataStoreUseCase
): ViewModel(){
    val cliente = MutableLiveData<Cliente?>()
    val categorias = MutableLiveData<MutableList<Categoria>?>()
    val promociones = MutableLiveData<MutableList<Promocion>?>()
    val fotoCliente = MutableLiveData<String?>()
    val nombreCliente = MutableLiveData<String?>()
    val carga = MutableLiveData<Boolean>()
    val error = MutableLiveData<String?>()

    fun setError(err: String?) = viewModelScope.launch(Dispatchers.IO){
        error.postValue(err)
    }

    fun getCliente(idCliente: Int) = viewModelScope.launch(Dispatchers.IO){
        cliente.postValue(clienteUseCase.getCliente(idCliente))
        setError(clienteUseCase())
    }

    fun getCategorias() = viewModelScope.launch(Dispatchers.IO){
        categorias.postValue(categoriaUseCase.getCategorias())
        setError(categoriaUseCase())
    }

    fun getCupones() = viewModelScope.launch(Dispatchers.IO){
        carga.postValue(true)

        promocionUseCase.getCupones().also{
            promociones.postValue(it)
            carga.postValue(false)
        }

        setError(promocionUseCase())
    }

    fun getFotoCliente(idCliente: Int) = viewModelScope.launch(Dispatchers.IO){
        fotoCliente.postValue(mediaUseCase.getFotoCliente(idCliente))
        setError(mediaUseCase())
    }

    fun getNombreCliente() = viewModelScope.launch(Dispatchers.IO){
        dataStoreUseCase.getNombreCliente().collect{
            nombreCliente.postValue(it)
        }
    }

    fun putPromocion(promocion: String) = viewModelScope.launch(Dispatchers.IO){
        dataStoreUseCase.putPromocion(promocion)
    }

    fun putNombreCliente(nombre: String) = viewModelScope.launch(Dispatchers.IO){
        dataStoreUseCase.putNombreCliente(nombre)
    }
}