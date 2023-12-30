package uv.tc.appcuponsmart.data.model.entidad

data class Municipio(
    var id: Int? = null,
    var nombre: String? = null,
    var idEstado: Int? = null
){
    override fun toString(): String =
        nombre.toString()
}