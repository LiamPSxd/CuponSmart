package uv.tc.appcuponsmart.data.model.respuesta

import uv.tc.appcuponsmart.data.model.entidad.TipoPromocion

class RespuestaTipoPromocion(
    error: Boolean,
    mensaje: String,
    var tiposPromocion: MutableList<TipoPromocion>? = null
): Mensaje(error, mensaje)