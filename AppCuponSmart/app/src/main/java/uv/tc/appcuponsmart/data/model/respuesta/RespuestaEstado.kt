package uv.tc.appcuponsmart.data.model.respuesta

import uv.tc.appcuponsmart.data.model.entidad.Estado

class RespuestaEstado(
    error: Boolean,
    mensaje: String,
    var contenido: MutableList<Estado>? = null
): Mensaje(error, mensaje)