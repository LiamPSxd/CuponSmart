package uv.tc.appcuponsmart.data.model.respuesta

import uv.tc.appcuponsmart.data.model.entidad.Direccion

class RespuestaDireccion(
    error: Boolean,
    mensaje: String,
    var direcciones: MutableList<Direccion>? = null
): Mensaje(error, mensaje)