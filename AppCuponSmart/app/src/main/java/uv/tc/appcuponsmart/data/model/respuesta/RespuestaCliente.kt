package uv.tc.appcuponsmart.data.model.respuesta

import uv.tc.appcuponsmart.data.model.entidad.Cliente

class RespuestaCliente(
    error: Boolean,
    mensaje: String,
    var clientes: MutableList<Cliente>? = null
): Mensaje(error, mensaje)