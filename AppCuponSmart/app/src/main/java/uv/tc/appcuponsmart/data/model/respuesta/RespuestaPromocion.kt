package uv.tc.appcuponsmart.data.model.respuesta

import uv.tc.appcuponsmart.data.model.entidad.Promocion

class RespuestaPromocion(
    error: Boolean,
    mensaje: String,
    var promociones: MutableList<Promocion>? = null
): Mensaje(error, mensaje)