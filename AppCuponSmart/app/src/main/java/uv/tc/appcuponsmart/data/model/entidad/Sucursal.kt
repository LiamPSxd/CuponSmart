package uv.tc.appcuponsmart.data.model.entidad

data class Sucursal(
    var id: Int? = null,
    var nombre: String? = null,
    var telefono: String? = null,
    var nombreEncargado: String? = null,
    var idDireccion: Int? = null,
    var direccion: String? = null,
    var idEmpresa: Int? = null
)