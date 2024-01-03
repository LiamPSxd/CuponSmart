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
class PerfilViewModel @Inject constructor(
    private val clienteUseCase: ClienteUseCase,
    private val direccionUseCase: DireccionUseCase,
    private val ciudadUseCase: CiudadUseCase,
    private val catalogoUseCase: CatalogoUseCase,
    private val mediaUseCase: MediaUseCase,
    private val dataStoreUseCase: DataStoreUseCase
): ViewModel(){
    val cliente = MutableLiveData<Cliente?>()
    val direccion = MutableLiveData<Direccion?>()
    val ciudades = MutableLiveData<MutableList<Ciudad>?>()
    val ciudad = MutableLiveData<Ciudad?>()
    val municipios = MutableLiveData<MutableList<Municipio>?>()
    val municipio = MutableLiveData<Municipio?>()
    val estados = MutableLiveData<MutableList<Estado>?>()
    val estado = MutableLiveData<Estado?>()
    val fotoCliente = MutableLiveData<String?>()
    val statusCliente = MutableLiveData<Boolean>()
    val statusDireccion = MutableLiveData<Boolean>()
    val statusMedia = MutableLiveData<Boolean>()
    val error = MutableLiveData<String?>()

    fun setError(err: String?) = viewModelScope.launch(Dispatchers.IO){
        error.postValue(err)
    }

    fun getCliente(idCliente: Int) = viewModelScope.launch(Dispatchers.IO){
        cliente.postValue(clienteUseCase.getCliente(idCliente))
        setError(clienteUseCase())
    }

    fun updateCliente(cliente: Cliente) = viewModelScope.launch(Dispatchers.IO){
        statusCliente.postValue(clienteUseCase.updateCliente(cliente))
        setError(clienteUseCase())
    }

    fun getDireccion(idDireccion: Int) = viewModelScope.launch(Dispatchers.IO){
        direccion.postValue(direccionUseCase.getDireccion(idDireccion))
        setError(direccionUseCase())
    }

    fun updateDireccion(direccion: Direccion) = viewModelScope.launch(Dispatchers.IO){
        statusDireccion.postValue(direccionUseCase.updateDireccion(direccion))
        setError(direccionUseCase())
    }

    fun getCiudadesPorMunicipio(idMunicipio: Int) = viewModelScope.launch(Dispatchers.IO){
        ciudades.postValue(ciudadUseCase.getCiudadesPorMunicipio(idMunicipio))
        setError(ciudadUseCase())
    }

    fun getCiudad(idCiudad: Int) = viewModelScope.launch(Dispatchers.IO){
        ciudad.postValue(ciudadUseCase.getCiudad(idCiudad))
        setError(ciudadUseCase())
    }

    fun getMunicipiosPorEstado(idEstado: Int) = viewModelScope.launch(Dispatchers.IO){
        municipios.postValue(catalogoUseCase.getMunicipiosPorEstado(idEstado))
        setError(catalogoUseCase())
    }

    fun getMunicipio(idMunicipio: Int) = viewModelScope.launch(Dispatchers.IO){
        municipio.postValue(catalogoUseCase.getMunicipio(idMunicipio))
        setError(catalogoUseCase())
    }

    fun getEstados() = viewModelScope.launch(Dispatchers.IO){
        estados.postValue(catalogoUseCase.getEstados())
        setError(catalogoUseCase())
    }

    fun getEstado(idEstado: Int) = viewModelScope.launch(Dispatchers.IO){
        estado.postValue(catalogoUseCase.getEstado(idEstado))
        setError(catalogoUseCase())
    }

    fun getFotoCliente(idCliente: Int) = viewModelScope.launch(Dispatchers.IO){
        fotoCliente.postValue(mediaUseCase.getFotoCliente(idCliente))
        setError(mediaUseCase())
    }

    fun addFotoCliente(idCliente: Int, foto: ByteArray) = viewModelScope.launch(Dispatchers.IO){
        statusMedia.postValue(mediaUseCase.addFotoCliente(idCliente, foto))
        setError(mediaUseCase())
    }

    fun deleteDataStore() = viewModelScope.launch(Dispatchers.IO){
        dataStoreUseCase.putIdCliente(0)
        dataStoreUseCase.putNombreCliente("")
        dataStoreUseCase.putPromocion("")
        dataStoreUseCase.putSucursal("")
    }
}