package uv.tc.appcuponsmart.data.model.entidad

data class Estado(
    var id: Int? = null,
    var nombre: String? = null
){
    override fun toString(): String =
        nombre.toString()
}