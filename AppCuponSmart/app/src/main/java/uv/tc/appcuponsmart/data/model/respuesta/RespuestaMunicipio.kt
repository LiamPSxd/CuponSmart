package uv.tc.appcuponsmart.data.model.respuesta

import uv.tc.appcuponsmart.data.model.entidad.Municipio

class RespuestaMunicipio(
    error: Boolean,
    mensaje: String,
    var municipios: MutableList<Municipio>? = null
): Mensaje(error, mensaje)