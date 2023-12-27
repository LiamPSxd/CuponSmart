package uv.tc.appcuponsmart.data.model.respuesta

import uv.tc.appcuponsmart.data.model.entidad.Ciudad

class RespuestaCiudad(
    error: Boolean,
    mensaje: String,
    var ciudades: MutableList<Ciudad>? = null
): Mensaje(error, mensaje)