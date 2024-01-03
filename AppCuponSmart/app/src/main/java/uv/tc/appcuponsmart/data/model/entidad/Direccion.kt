package uv.tc.appcuponsmart.data.model.entidad

data class Direccion(
    var id: Int? = null,
    var calle: String? = null,
    var numero: String? = null,
    var codigoPostal: String? = null,
    var colonia: String? = null,
    var latitud: String? = "",
    var longitud: String? = "",
    var idCiudad: Int? = null,
)