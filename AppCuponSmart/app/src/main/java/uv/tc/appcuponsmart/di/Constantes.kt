package uv.tc.appcuponsmart.di

object Constantes{
    object Servicios{
        const val URL_WS = "http://192.168.1.68:8084/cuponSmart/api/"

        const val GET = "GET"
        const val POST = "POST"
        const val PUT = "PUT"
        const val DELETE = "DELETE"

        const val CONTENT_TYPE = "Content-Type"
        const val APPLICATION_WWW_FORM = "application/x-www-form-urlencoded"
        const val APPLICATION_JSON = "application/json"
    }

    object Mensajes{
        const val EXITO = "Éxito"
        const val ADVERTENCIA = "Advertencia"
        const val ERROR = "Error"

        fun bienvenida(nombre: String): String =
            "Bienvenido(a) $nombre a CuponSmart"
    }

    object Excepciones{
        const val CONEXION_BD = "Error de conexión con la Base de Datos, por favor inténtelo más tarde"
        const val DATOS_CLIENTE = "No se encontraron los datos del usuario, por favor cierre sesión"
        const val DATOS_PROMOCION = "No se encontraron los datos de la cupón, por favor inténte más tarde"
        const val DATOS_SUCURSAL = "No se encontraron los datos de la sucursal, por favor inténte más tarde"
    }

    object Utileria{
        const val FORMATO_FECHA = "dd-MM-yyyy"
        const val CLIENTE = "cliente"
        const val FOTO = "foto"
        const val DIRECCION = "direccion"
    }
}