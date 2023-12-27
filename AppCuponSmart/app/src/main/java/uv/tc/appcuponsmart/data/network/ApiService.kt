package uv.tc.appcuponsmart.data.network

import com.google.gson.Gson
import com.koushikdutta.ion.builder.Builders
import com.koushikdutta.ion.builder.LoadBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uv.tc.appcuponsmart.di.Constantes
import java.util.concurrent.ExecutionException
import javax.inject.Inject

class ApiService @Inject constructor(
    private val ion: LoadBuilder<Builders.Any.B>,
    private val json: Gson
){
    suspend fun get(url: String): String? = withContext(Dispatchers.IO){
        try{
            ion.load(Constantes.Servicios.GET, url)
                .asString()
                .withResponse()
                .get()
                .result
        }catch(e: ExecutionException){
            Constantes.Excepciones.CONEXION_BD
        }
    }.let{
        return it.ifEmpty{ null }
    }

    suspend fun postWWWForm(url: String, correo: String, contrasenia: String): String? = withContext(Dispatchers.IO){
        try{
            ion.load(Constantes.Servicios.POST, url)
                .setHeader(
                    Constantes.Servicios.CONTENT_TYPE,
                    Constantes.Servicios.APPLICATION_WWW_FORM
                )
                .setBodyParameter("correo", correo)
                .setBodyParameter("contrasenia", contrasenia)
                .asString()
                .withResponse()
                .get()
                .result
        }catch(e: ExecutionException){
            Constantes.Excepciones.CONEXION_BD
        }
    }.let{
        return it.ifEmpty{ null }
    }

    suspend fun post(url: String, objeto: Any): String? = withContext(Dispatchers.IO){
        try{
            ion.load(Constantes.Servicios.POST, url)
                .setHeader(Constantes.Servicios.CONTENT_TYPE, Constantes.Servicios.APPLICATION_JSON)
                .setStringBody(json.toJson(objeto))
                .asString()
                .withResponse()
                .get()
                .result
        }catch(e: ExecutionException){
            Constantes.Excepciones.CONEXION_BD
        }
    }.let{
        return it.ifEmpty{ null }
    }

    suspend fun put(url: String, objeto: Any): String? = withContext(Dispatchers.IO){
        try{
            ion.load(Constantes.Servicios.PUT, url)
                .setHeader(Constantes.Servicios.CONTENT_TYPE, Constantes.Servicios.APPLICATION_JSON)
                .setStringBody(json.toJson(objeto))
                .asString()
                .withResponse()
                .get()
                .result
        }catch(e: ExecutionException){
            Constantes.Excepciones.CONEXION_BD
        }
    }.let{
        return it.ifEmpty{ null }
    }

    suspend fun putMedia(url: String, media: ByteArray): String? = withContext(Dispatchers.IO){
        try{
            ion.load(Constantes.Servicios.PUT, url)
                .setByteArrayBody(media)
                .asString()
                .withResponse()
                .get()
                .result
        }catch(e: ExecutionException){
            Constantes.Excepciones.CONEXION_BD
        }
    }.let{
        return it.ifEmpty{ null }
    }

    suspend fun delete(url: String): String? = withContext(Dispatchers.IO){
        try{
            ion.load(Constantes.Servicios.DELETE, url)
                .asString()
                .withResponse()
                .get()
                .result
        }catch(e: ExecutionException){
            Constantes.Excepciones.CONEXION_BD
        }
    }.let{
        return it.ifEmpty{ null }
    }
}