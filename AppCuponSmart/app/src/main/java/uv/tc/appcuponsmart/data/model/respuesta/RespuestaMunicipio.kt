package uv.tc.appcuponsmart.data.model.respuesta

import uv.tc.appcuponsmart.data.model.entidad.Municipio

class RespuestaMunicipio(
    error: Boolean,
    mensaje: String,
    var contenido: MutableList<Municipio>? = null
): Mensaje(error, mensaje)