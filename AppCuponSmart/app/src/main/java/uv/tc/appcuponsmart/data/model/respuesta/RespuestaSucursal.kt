package uv.tc.appcuponsmart.data.model.respuesta

import uv.tc.appcuponsmart.data.model.entidad.Sucursal

class RespuestaSucursal(
    error: Boolean,
    mensaje: String,
    var contenido: MutableList<Sucursal>? = null
): Mensaje(error, mensaje)