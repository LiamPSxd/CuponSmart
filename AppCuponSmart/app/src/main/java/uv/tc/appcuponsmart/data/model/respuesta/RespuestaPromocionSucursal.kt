package uv.tc.appcuponsmart.data.model.respuesta

import uv.tc.appcuponsmart.data.model.entidad.PromocionSucursal

class RespuestaPromocionSucursal(
    error: Boolean,
    mensaje: String,
    var contenido: MutableList<PromocionSucursal>? = null
): Mensaje(error, mensaje)