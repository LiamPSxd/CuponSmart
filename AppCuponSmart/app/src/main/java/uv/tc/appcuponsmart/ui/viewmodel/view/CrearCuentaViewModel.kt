package uv.tc.appcuponsmart.ui.viewmodel.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uv.tc.appcuponsmart.data.model.entidad.Ciudad
import uv.tc.appcuponsmart.data.model.entidad.Cliente
import uv.tc.appcuponsmart.data.model.entidad.Direccion
import uv.tc.appcuponsmart.data.model.entidad.Estado
import uv.tc.appcuponsmart.data.model.entidad.Municipio
import uv.tc.appcuponsmart.domain.CatalogoUseCase
import uv.tc.appcuponsmart.domain.CiudadUseCase
import uv.tc.appcuponsmart.domain.ClienteUseCase
import uv.tc.appcuponsmart.domain.DataStoreUseCase
import uv.tc.appcuponsmart.domain.DireccionUseCase
import uv.tc.appcuponsmart.domain.MediaUseCase
import javax.inject.Inject

@HiltViewModel
class CrearCuentaViewModel @Inject constructor(
    private val dataStoreUseCase: DataStoreUseCase,
    private val clienteUseCase: ClienteUseCase,
    private val direccionUseCase: DireccionUseCase,
    private val catalogoUseCase: CatalogoUseCase,
    private val ciudadUseCase: CiudadUseCase,
    private val mediaUseCase: MediaUseCase
): ViewModel(){
    val cliente = MutableLiveData<Cliente?>()
    val idCliente = MutableLiveData<Int>()
    val direcciones = MutableLiveData<MutableList<Direccion>?>()
    val direccion = MutableLiveData<Direccion?>()
    val estados = MutableLiveData<MutableList<Estado>?>()
    val municipios = MutableLiveData<MutableList<Municipio>?>()
    val ciudades = MutableLiveData<MutableList<Ciudad>?>()
    val statusCliente = MutableLiveData<Boolean>()
    val statusDireccion = MutableLiveData<Boolean>()
    val statusMedia = MutableLiveData<Boolean>()
    val error = MutableLiveData<String?>()

    fun setError(err: String?) = viewModelScope.launch(Dispatchers.IO){
        error.postValue(err)
    }

    fun putIdCliente(idCliente: Int) = viewModelScope.launch(Dispatchers.IO){
        dataStoreUseCase.putIdCliente(idCliente)
    }

    fun putNombreCliente(nombre: String) = viewModelScope.launch(Dispatchers.IO){
        dataStoreUseCase.putNombreCliente(nombre)
    }

    fun getIdCliente(correo: String) = viewModelScope.launch(Dispatchers.IO){
        idCliente.postValue(clienteUseCase.getCliente(correo)?.id ?: 0)
        setError(clienteUseCase())
    }

    fun addCliente(cliente: Cliente) = viewModelScope.launch(Dispatchers.IO){
        statusCliente.postValue(clienteUseCase.addCliente(cliente))
        setError(clienteUseCase())
    }

    fun setCliente(cliente: Cliente) = viewModelScope.launch(Dispatchers.IO){
        this@CrearCuentaViewModel.cliente.postValue(cliente)
    }

    fun getDirecciones() = viewModelScope.launch(Dispatchers.IO){
        direcciones.postValue(direccionUseCase.getDirecciones())
        setError(direccionUseCase())
    }

    fun addDireccion(direccion: Direccion) = viewModelScope.launch(Dispatchers.IO){
        statusDireccion.postValue(direccionUseCase.addDireccion(direccion))
        setError(direccionUseCase())
    }

    fun setDireccion(direccion: Direccion) = viewModelScope.launch(Dispatchers.IO){
        this@CrearCuentaViewModel.direccion.postValue(direccion)
    }

    fun getEstados() = viewModelScope.launch(Dispatchers.IO){
        estados.postValue(catalogoUseCase.getEstados())
        setError(catalogoUseCase())
    }

    fun getMunicipiosPorEstado(idEstado: Int) = viewModelScope.launch(Dispatchers.IO){
        municipios.postValue(catalogoUseCase.getMunicipiosPorEstado(idEstado))
        setError(catalogoUseCase())
    }

    fun getCiudadesPorMunicipio(idMunicipio: Int) = viewModelScope.launch(Dispatchers.IO){
        ciudades.postValue(ciudadUseCase.getCiudadesPorMunicipio(idMunicipio))
        setError(ciudadUseCase())
    }

    fun addFotoCliente(idCliente: Int, foto: ByteArray) = viewModelScope.launch(Dispatchers.IO){
        statusMedia.postValue(mediaUseCase.addFotoCliente(idCliente, foto))
        setError(mediaUseCase())
    }
}