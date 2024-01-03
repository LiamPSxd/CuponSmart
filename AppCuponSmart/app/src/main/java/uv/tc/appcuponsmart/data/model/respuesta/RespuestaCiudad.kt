package uv.tc.appcuponsmart.data.model.respuesta

import uv.tc.appcuponsmart.data.model.entidad.Ciudad

class RespuestaCiudad(
    error: Boolean,
    mensaje: String,
    var contenido: MutableList<Ciudad>? = null
): Mensaje(error, mensaje)