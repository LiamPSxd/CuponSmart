package uv.tc.appcuponsmart.data.model.respuesta

import uv.tc.appcuponsmart.data.model.entidad.Estatus

class RespuestaEstatus(
    error: Boolean,
    mensaje: String,
    var contenido: MutableList<Estatus>? = null
): Mensaje(error, mensaje)