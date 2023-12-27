package uv.tc.appcuponsmart.data.model.respuesta

import uv.tc.appcuponsmart.data.model.entidad.Estatus

class RespuestaEstatus(
    error: Boolean,
    mensaje: String,
    var estatus: MutableList<Estatus>? = null
): Mensaje(error, mensaje)