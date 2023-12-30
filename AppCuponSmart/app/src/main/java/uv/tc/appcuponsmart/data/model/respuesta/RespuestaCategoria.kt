package uv.tc.appcuponsmart.data.model.respuesta

import uv.tc.appcuponsmart.data.model.entidad.Categoria

class RespuestaCategoria(
    error: Boolean,
    mensaje: String,
    var contenido: MutableList<Categoria>? = null
): Mensaje(error, mensaje)