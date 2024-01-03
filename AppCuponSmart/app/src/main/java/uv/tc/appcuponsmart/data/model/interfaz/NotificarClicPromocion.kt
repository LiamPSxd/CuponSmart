package uv.tc.appcuponsmart.data.model.interfaz

import uv.tc.appcuponsmart.data.model.entidad.Promocion

interface NotificarClicPromocion{
    fun seleccionarItem(promocion: Promocion)
}