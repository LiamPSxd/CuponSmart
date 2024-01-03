package uv.tc.appcuponsmart.ui.viewmodel.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uv.tc.appcuponsmart.domain.DataStoreUseCase
import javax.inject.Inject

@HiltViewModel
class BienvenidaViewModel @Inject constructor(
    private val dataStoreUseCase: DataStoreUseCase
): ViewModel(){
    val idCliente = MutableLiveData<Int>()

    fun getIdCliente() = viewModelScope.launch(Dispatchers.IO){
        dataStoreUseCase.getIdCliente().collect{
            idCliente.postValue(it)
        }
    }
}