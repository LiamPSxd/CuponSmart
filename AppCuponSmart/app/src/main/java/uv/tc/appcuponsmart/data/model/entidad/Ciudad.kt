package uv.tc.appcuponsmart.data.model.entidad

data class Ciudad(
    var id: Int? = null,
    var nombre: String? = null,
    var idMunicipio: Int? = null
){
    override fun toString(): String =
        nombre.toString()
}