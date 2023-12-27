package uv.tc.appcuponsmart.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uv.tc.appcuponsmart.data.model.entidad.Empresa
import uv.tc.appcuponsmart.domain.EmpresaUseCase
import javax.inject.Inject

@HiltViewModel
class EmpresaViewModel @Inject constructor(
    private val useCase: EmpresaUseCase
): ViewModel(){
    val empresas = MutableLiveData<MutableList<Empresa>?>()
    val empresa = MutableLiveData<Empresa?>()
    val error = MutableLiveData<String?>()

    private suspend fun getError() = error.postValue(useCase())

    fun getEmpresas() = viewModelScope.launch(Dispatchers.IO){
        empresas.postValue(useCase.getEmpresas())
        getError()
    }

    fun getEmpresa(idEmpresa: Int) = viewModelScope.launch(Dispatchers.IO){
        empresa.postValue(useCase.getEmpresa(idEmpresa))
        getError()
    }
}