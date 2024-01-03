package uv.tc.appcuponsmart.ui.viewmodel.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uv.tc.appcuponsmart.data.model.entidad.Sucursal
import uv.tc.appcuponsmart.domain.DataStoreUseCase
import javax.inject.Inject

@HiltViewModel
class DetalleSucursalViewModel @Inject constructor(
    private val dataStoreUseCase: DataStoreUseCase
): ViewModel(){
    val sucursalJSON = MutableLiveData<String>()
    val sucursal = MutableLiveData<Sucursal?>()
    val error = MutableLiveData<String?>()

    fun setError(err: String?) = viewModelScope.launch(Dispatchers.IO){
        error.postValue(err)
    }

    fun getSucursal() = viewModelScope.launch(Dispatchers.IO){
        dataStoreUseCase.getSucursal().collect{
            sucursalJSON.postValue(it)
        }
    }

    fun setSucursal(sucursal: Sucursal) = viewModelScope.launch(Dispatchers.IO){
        this@DetalleSucursalViewModel.sucursal.postValue(sucursal)
    }
}