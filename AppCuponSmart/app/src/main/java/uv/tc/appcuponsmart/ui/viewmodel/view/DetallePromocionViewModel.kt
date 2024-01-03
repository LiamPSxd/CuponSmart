package uv.tc.appcuponsmart.ui.viewmodel.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uv.tc.appcuponsmart.data.model.entidad.Promocion
import uv.tc.appcuponsmart.data.model.entidad.Sucursal
import uv.tc.appcuponsmart.domain.DataStoreUseCase
import javax.inject.Inject

@HiltViewModel
class DetallePromocionViewModel @Inject constructor(
    private val dataStoreUseCase: DataStoreUseCase
): ViewModel(){
    val promocionJSON = MutableLiveData<String>()
    val promocion = MutableLiveData<Promocion?>()
    val sucursales = MutableLiveData<MutableList<Sucursal>?>()
    val carga = MutableLiveData<Boolean>()
    val error = MutableLiveData<String?>()

    fun setError(err: String?) = viewModelScope.launch(Dispatchers.IO){
        error.postValue(err)
    }

    fun putSucursal(sucursal: String) = viewModelScope.launch(Dispatchers.IO){
        dataStoreUseCase.putSucursal(sucursal)
    }

    fun getPromocion() = viewModelScope.launch(Dispatchers.IO){
        dataStoreUseCase.getPromocion().collect{
            promocionJSON.postValue(it)
        }
    }

    fun setPromocion(promocion: Promocion) = viewModelScope.launch(Dispatchers.IO){
        this@DetallePromocionViewModel.promocion.postValue(promocion)
    }

    fun setSucursales(sucursales: MutableList<Sucursal>) = viewModelScope.launch(Dispatchers.IO){
        carga.postValue(true)

        sucursales.also{
            this@DetallePromocionViewModel.sucursales.postValue(it)
            carga.postValue(false)
        }
    }
}