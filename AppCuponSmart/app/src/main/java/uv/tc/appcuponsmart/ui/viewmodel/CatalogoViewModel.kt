package uv.tc.appcuponsmart.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uv.tc.appcuponsmart.data.model.entidad.Estado
import uv.tc.appcuponsmart.data.model.entidad.Estatus
import uv.tc.appcuponsmart.data.model.entidad.Municipio
import uv.tc.appcuponsmart.data.model.entidad.TipoPromocion
import uv.tc.appcuponsmart.domain.CatalogoUseCase
import javax.inject.Inject

@HiltViewModel
class CatalogoViewModel @Inject constructor(
    private val useCase: CatalogoUseCase
): ViewModel(){
    val estados = MutableLiveData<MutableList<Estado>?>()
    val estado = MutableLiveData<Estado?>()
    val estatus = MutableLiveData<MutableList<Estatus>?>()
    val estatu = MutableLiveData<Estatus?>()
    val municipios = MutableLiveData<MutableList<Municipio>?>()
    val municipio = MutableLiveData<Municipio?>()
    val tiposPromocion = MutableLiveData<MutableList<TipoPromocion>?>()
    val tipoPromocion = MutableLiveData<TipoPromocion?>()
    val error = MutableLiveData<String?>()

    private suspend fun getError() = error.postValue(useCase())

    fun getEstados() = viewModelScope.launch(Dispatchers.IO){
        estados.postValue(useCase.getEstados())
        getError()
    }

    fun getEstado(idEstado: Int) = viewModelScope.launch(Dispatchers.IO){
        estado.postValue(useCase.getEstado(idEstado))
        getError()
    }

    fun getEstatus() = viewModelScope.launch(Dispatchers.IO){
        estatus.postValue(useCase.getEstatus())
        getError()
    }

    fun getEstatus(idEstatus: Int) = viewModelScope.launch(Dispatchers.IO){
        estatu.postValue(useCase.getEstatus(idEstatus))
        getError()
    }

    fun getMunicipios() = viewModelScope.launch(Dispatchers.IO){
        municipios.postValue(useCase.getMunicipios())
        getError()
    }

    fun getMunicipiosPorEstado(idEstado: Int) = viewModelScope.launch(Dispatchers.IO){
        municipios.postValue(useCase.getMunicipiosPorEstado(idEstado))
        getError()
    }

    fun getMunicipio(idMunicipio: Int) = viewModelScope.launch(Dispatchers.IO){
        municipio.postValue(useCase.getMunicipio(idMunicipio))
        getError()
    }

    fun getTiposPromocion() = viewModelScope.launch(Dispatchers.IO){
        tiposPromocion.postValue(useCase.getTiposPromocion())
        getError()
    }

    fun getTipoPromocion(idTipoPromocion: Int) = viewModelScope.launch(Dispatchers.IO){
        tipoPromocion.postValue(useCase.getTipoPromocion(idTipoPromocion))
        getError()
    }
}