package uv.tc.appcuponsmart.data.model.entidad

data class Promocion(
    var id: Int? = null,
    var nombre: String? = null,
    var descripcion: String? = null,
    var imagenBase64: String? = null,
    var fechaInicio: String? = null,
    var fechaTermino: String? = null,
    var vigencia: String? = null,
    var restricciones: String? = null,
    var numeroCupones: Int? = null,
    var codigo: String? = null,
    var valor: Float? = null,
    var valorTipo: String? = null,
    var idEstatus: Int? = null,
    var idCategoria: Int? = null,
    var idEmpresa: Int? = null,
    var empresa: String? = null,
    var idTipoPromocion: Int? = null,
    var tipo: String? = null
)