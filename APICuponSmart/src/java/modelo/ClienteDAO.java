package modelo;

import java.util.List;
import modelo.pojo.entidad.Cliente;
import modelo.pojo.respuesta.RespuestaCliente;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import utils.Constantes;
import utils.Verificaciones;

public class ClienteDAO {
    public static RespuestaCliente obtenerClientes(){
        RespuestaCliente respuesta = new RespuestaCliente();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        if(conexionBD != null){
            try {
                List<Cliente> clientes = conexionBD.selectList("clientes.ObtenerClientes");
                if(Verificaciones.Datos.listaNoVacia(clientes)){
                    respuesta.setError(false);
                    respuesta.mensajeSuccess();
                    respuesta.setContenido(clientes);
                }else{
                    respuesta.mensajeSinDatos();
                }
            } catch (Exception e) {                
                respuesta.setMensaje(e.getMessage());
            }finally{
                conexionBD.close();
            }
        }else{
            respuesta.mensajeSinConexionBD();
        }        
        return respuesta;
    }
    
    public static RespuestaCliente registrarCliente(Cliente cliente){
        RespuestaCliente respuesta = new RespuestaCliente();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        if(conexionBD != null){
            try {
                int numFilasAfectadas = conexionBD.insert("clientes.registrarCliente", cliente);
                conexionBD.commit();
                if(numFilasAfectadas > 0){
                    respuesta.setError(false);
                    respuesta.mensajeSuccess();
                    respuesta.setMensaje(Constantes.Retornos.REGISTRO);
                }else{
                    respuesta.setMensaje(Constantes.Errores.REGISTRO);
                }
            } catch (Exception e) {                
                respuesta.setMensaje(e.getMessage());
            }finally{
                conexionBD.close();
            }
        }else{
            respuesta.mensajeSinConexionBD();
        }        
        return respuesta;
    }
}
