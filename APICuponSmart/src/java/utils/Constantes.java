package utils;

public class Constantes{
    public static final String MAS_TARDE = "por favor inténtelo más tarde.";
    public static final String VERIFICAR = "favor de verificarlo.";
    
    public class Retornos{
        public static final String SUCCESS = "Success";
        public static final String SIN_DATOS = "No se encontró ninguna información con lo solicitado.";
        public static final String NO_ID = "No hay ningún dato asignado con ese identificador, " + VERIFICAR;
        public static final String REGISTRO = "Información registrada correctamente.";
        public static final String MODIFICACION = "Información modificada correctamente.";
        public static final String ELIMINACION = "Información eliminada correctamente.";
    }
    
    public class Errores{
        public static final String SIN_CONEXION_BD = "Error de conexión con la Base de Datos, " + MAS_TARDE;
        public static final String REGISTRO = "Hubo un error al registrar la información, " + MAS_TARDE;
        public static final String MODIFICACION = "Hubo un error al modificar la información, " + MAS_TARDE;
        public static final String ELIMINACION = "Hubo un error al eliminar la información, " + MAS_TARDE;
    }
    
    public class Excepciones{
        public static final String EXCEPTION = "Ha ocurrido un problema al realizar la operación, " + MAS_TARDE;
        public static final String NO_SUCH_METHOD = "El método en particular no ha podido ser encontrado, " + VERIFICAR;
        public static final String SECURITY = "Se ha producido una violación de seguridad, " + VERIFICAR;
        public static final String JSON_SYNTAX = "Se ha encontrado un error de sintaxis en el JSON proporcionado, " + VERIFICAR;
        public static final String PERSISTENCE = "El correo ya ha sido registrado, " + VERIFICAR;
    }
    
    public class Datos{
        public static final String FECHA_INICIO = "FechaInicio";
        public static final String FECHA_TERMINO = "FechaTermino";
        public static final String NOMBRE = "Nombre";
        public static final String PROMOCION = "Promocion";
        public static final String SUCURSAL = "Sucursal";
        public static final String ID = "Id";
        public static final String ID_DIRECCION = "IdDireccion";
    }
}