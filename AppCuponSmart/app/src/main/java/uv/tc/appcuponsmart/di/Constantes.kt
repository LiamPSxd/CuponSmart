package uv.tc.appcuponsmart.di

object Constantes{
    object Servicios{
        const val URL_WS = "http://192.168.1.101:8084/cuponsmart/api/"

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
        const val PETICION = "No se pudo realizar la operación, por favor inténtelo más tarde"
        const val DATOS_CLIENTE = "No se encontraron los datos del usuario, por favor cierre sesión"
        const val DATOS_PROMOCION = "No se encontraron los datos de la cupón, por favor inténte más tarde"
        const val DATOS_SUCURSAL = "No se encontraron los datos de la sucursal, por favor inténte más tarde"
    }

    object Utileria{
        const val FORMATO_FECHA = "yyyy-MM-dd"
        const val FORMATO_VIGENCIA = "dd/MM/yyyy"
        const val TIME_ZONE = "UTC"

        const val CLIENTE = "cliente"
        const val FOTO = "foto"
        const val DIRECCION = "direccion"

        const val LATITUD = "latitud"
        const val LONGITUD = "longitud"

        const val VALOR_LATITUD = 19.54157686626711
        const val VALOR_LONGITUD = -96.92723351113182
    }

    object DataStore{
        const val ALMACENAMIENTO = "almacenamiento"

        const val ID_CLIENTE = "idCliente"
        const val NOMBRE_CLIENTE = "nombre"

        const val PROMOCION = "promocion"
        const val SUCURSAL = "sucursal"

        const val CLIENTE = "cliente"
        const val FOTO_BASE_64 = "fotoBase64"
        const val DIRECCION = "direccion"
    }
}