package utils;

public class Constantes{
    public static final String MAS_TARDE = "por favor inténtelo más tarde.";
    public static final String VERIFICAR = "favor de verificarlo.";
    
    public class MetodosHTTP{
        public static final String GET = "GET";
        public static final String POST = "POST";
        public static final String PUT = "PUT";
        public static final String DELETE = "DELETE";
    }
    
    public class Servicios{
        public static final String URL_WS = "http://localhost:8084/cuponsmart/api/";
        public static final String CONTENT_TYPE = "Content-Type";
        public static final String APPLICATION_JSON = "application/json";
        public static final String APPLICATION_WWW_FORM = "application/x-www-form-urlencoded";
        
        public static final String AUTENTICACION = URL_WS + "autenticacion/";
        public static final String CATALOGO = URL_WS + "catalogo/";
        public static final String CATEGORIA = URL_WS + "categorias/";
        public static final String CIUDAD = URL_WS + "ciudades/";
        public static final String CLIENTE = URL_WS + "clientes/";
        public static final String DIRECCION = URL_WS + "direcciones/";
        public static final String EMPRESA = URL_WS + "empresas/";
        public static final String MEDIA = URL_WS + "media/";
        public static final String PROMOCION_SUCURSAL = URL_WS + "promocionesSucursales/";
        public static final String PROMOCION = URL_WS + "promociones/";
        public static final String SUCURSAL = URL_WS + "sucursales/";
        public static final String USUARIO = URL_WS + "usuarios/";
    }
    
    public static class Retornos{
        public static final String ADMIN_GENERAL = "Administrador General";
        public static final String ADMIN_COMERCIAL = "Administrador Comercial";
        public static final String ESTATUS_ACTIVO = "Activo";
        public static final String ESTATUS_INACTIVO = "Inactivo";
        public static final String CUPON_EXITO = "Cupón cajeado exitosamente";
        public static final String CUPON_FALLO = "Cupón no válido";
        public static final String CUPON_INACTIVO = "El cupón ya no está activo";
        public static final String CUPON_LONG = "El código debe de ser de 8 caracteres, favor de verificarlo";
        public static final String CUPON_FALTANTE = "Para canjear un cupón primero debe ingresar el código";
        public static final String SUCCESS = "Success";
        public static final String BUSQUEDA = "Debe ingresar algún dato para realizar la búsqueda";
        public static final String FILTRO = "Debe seleccionar un filtro para realizar la búsqueda";
        public static final String BUSQUEDA_FILTRO = "Debe ingresar algún dato y seleccionar un filtro para realizar la búsqueda";
        public static final String SELECCION = "No coincide ninguna opción, " + MAS_TARDE;
        
        public static String codigoRespuestaHTTP(Integer codigo){
            return "Código de respuesta HTTP: " + codigo;
        }
    }
    
    public static class Errores{
        public static final Integer ERROR_URL = 101;
        public static final Integer ERROR_PETICION = 102;
        
        public static final String MEDIA = "Lo sentimos, por el momento no se puede registrar la imagen, " + MAS_TARDE;
        public static final String SOLICITUD = "Hubo un error al procesar la solicitud, " + MAS_TARDE;
        public static final String REGISTRO = "Lo sentimos, por el momento no se puede registrar la información, " + MAS_TARDE;
        public static final String MODIFICACION = "Lo sentimos, por el momento no se puede modificar la información, " + MAS_TARDE;
        public static final String ELIMINACION = "Lo sentimos, por el momento no se puede eliminar la información, " + MAS_TARDE;
        public static final String CAMPOS_VACIOS = "Hay uno o más campos vacios, " + VERIFICAR;
        public static final String COMBO_ESTATUS = "Lo sentimos, no se pudieron cargar los estatus, " + MAS_TARDE;
        public static final String COMBO_EMPRESA = "Lo sentimos, no se pudieron cargar las empresas, " + MAS_TARDE;
        public static final String COMBO_ESTADO = "Lo sentimos, no se pudieron cargar los estados, " + MAS_TARDE;
        public static final String COMBO_MUNICIPIO = "Lo sentimos, no se pudieron cargar los municipios, " + MAS_TARDE;
        public static final String COMBO_CIUDAD = "Lo sentimos, no se pudieron cargar las ciudades, " + MAS_TARDE;
        public static final String COMBO_TIPO = "Lo sentimos, no se pudieron cargar los tipos de promoción, " + MAS_TARDE;
        public static final String COMBO_CATEGORIA = "Lo sentimos, no se pudieron cargar las categorías, " + MAS_TARDE;
        public static final String COMBO_ROL = "Lo sentimos, no se pudieron cargar los roles, " + MAS_TARDE;
        public static final String CAMPOS_NUMERICOS = "Los campos deben ser numéricos, " + VERIFICAR;
    }
    
    public class Excepciones{
        public static final String EXCEPTION = "Ha ocurrido un problema al realizar la operación, " + MAS_TARDE;
        public static final String NO_SUCH_METHOD = "El método en particular no ha podido ser encontrado, " + VERIFICAR;
        public static final String SECURITY = "Se ha producido una violación de seguridad, " + VERIFICAR;
        public static final String JSON_SYNTAX = "Se ha encontrado un error de sintaxis en el JSON proporcionado, " + VERIFICAR;
        public static final String MALFORMED_URL = "Se ha encontrado un error en la dirección de conexión, " + VERIFICAR;
        public static final String PERSISTENCE = "El correo ya ha sido registrado, " + VERIFICAR;
        public static final String IO = "Error de conexión con la Base de Datos, por favor inténtelo más tarde";
    }
    
    public class Pantallas{
        public static final String URL_VISTA = "/cuponsmart/vista/";
        public static final String EXITO = "Éxito";
        public static final String ALERTA = "Alerta";
        public static final String ERROR = "Error";
        public static final String CAMPO_VACIO = "Campo vacío";
        public static final String CAMPOS_VACIOS = "Campos vacíos";
        public static final String SIN_SELECCION = "Selección requerida";
        public static final String CONFIRMAR_ELIMINACION = "Confirmar eliminación";
        public static final String SELECCION_IMAGEN = "Selecciona una imagen";
        public static final String ARCHIVOS_IMAGEN = "Archivos de imagen (*.png, *.jpg, *.jpeg)";
        
        public static final String INICIO_SESION = "Inicio de Sesión";
        public static final String ADMIN_GENERAL = "Home Administrador General";
        public static final String ADMIN_COMERCIAL = "Home Administrador Comercial";
        public static final String GESTION_EMPRESA = "Gestión de Empresas";
        public static final String GESTION_SUCURSAL = "Gestión de Sucursales";
        public static final String GESTION_PROMOCION = "Gestión de Promociones";
        public static final String GESTION_USUARIO = "Gestión de Usuarios";
        public static final String FORM_EMPRESA = "Formulario Empresa";
        public static final String FORM_SUCURSAL = "Formulario Sucursal";
        public static final String FORM_PROMOCION = "Formulario Promoción";
        public static final String FORM_USUARIO = "Formulario Usuario";
    }
}