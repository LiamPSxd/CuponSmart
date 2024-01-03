package uv.tc.appcuponsmart.data.model.respuesta

import uv.tc.appcuponsmart.data.model.entidad.Empresa

class RespuestaEmpresa(
    error: Boolean,
    mensaje: String,
    var contenido: MutableList<Empresa>? = null
): Mensaje(error, mensaje)