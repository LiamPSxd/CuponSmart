package uv.tc.appcuponsmart.data.model.entidad

data class Cliente(
    var id: Int? = null,
    var nombre: String? = null,
    var apellidoPaterno: String? = null,
    var apellidoMaterno: String? = null,
    var telefono: String? = null,
    var correo: String? = null,
    var fechaNacimiento: String? = null,
    var contrasenia: String? = null,
    var fotoBase64: String? = null,
    var idDireccion: Int? = null
)