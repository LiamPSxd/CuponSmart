package uv.tc.appcuponsmart.data.model.entidad

data class Empresa(
    var id: Int? = null,
    var nombre: String? = null,
    var nombreComercial: String? = null,
    var nombreRepresentanteLegal: String? = null,
    var correo: String? = null,
    var telefono: String? = null,
    var paginaWeb: String? = null,
    var rfc: String? = null,
    var idEstatus: Int? = null,
    var idDireccion: Int? = null,
)