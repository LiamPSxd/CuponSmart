package uv.tc.appcuponsmart.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uv.tc.appcuponsmart.data.model.entidad.Sucursal
import uv.tc.appcuponsmart.domain.SucursalUseCase
import javax.inject.Inject

@HiltViewModel
class SucursalViewModel @Inject constructor(
    private val useCase: SucursalUseCase
): ViewModel(){
    val sucursales = MutableLiveData<MutableList<Sucursal>?>()
    val sucursal = MutableLiveData<Sucursal?>()
    val error = MutableLiveData<String?>()

    private suspend fun getError() = error.postValue(useCase())

    fun getSucursales() = viewModelScope.launch(Dispatchers.IO){
        sucursales.postValue(useCase.getSucursales())
        getError()
    }

    fun getSucursal(idSucursal: Int) = viewModelScope.launch(Dispatchers.IO){
        sucursal.postValue(useCase.getSucursal(idSucursal))
        getError()
    }
}