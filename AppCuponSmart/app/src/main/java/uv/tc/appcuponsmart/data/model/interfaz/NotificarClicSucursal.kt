package uv.tc.appcuponsmart.data.model.interfaz

import uv.tc.appcuponsmart.data.model.entidad.Sucursal

interface NotificarClicSucursal{
    fun seleccionarItem(sucursal: Sucursal)
}