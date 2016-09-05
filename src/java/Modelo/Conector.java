
package Modelo;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

/** MODELO. */

public class Conector {
    Connection c1 = null;
    String comilla = "'";
    String coma = ",";
    private String IP="localhost";
    private String usuario="root";
    private String password="";
    private String BaseDatos="carritojsp";

        
    protected void conectar(){
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            c1 = DriverManager.getConnection(
            "jdbc:mysql://"+IP+"/"+BaseDatos,usuario,password
            );
            System.out.println("Exito");
        }catch(Exception e){
            System.out.println("Fallo la conexion");
            System.out.println(e.getMessage());
        }
    }
    
    protected void desconectar(){
        try{
            c1.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    
    public boolean consulta_vacia(String SQL){
        boolean flag = false;
        this.conectar();
        try{
            Statement st = c1.createStatement();
//            st.executeUpdate(SQL);
            if(st.executeUpdate(SQL) == 1){
                flag = true;
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        this.desconectar();
        return flag;
    }
    
    public String[][] consulta_registros(String SQL){
        ResultSet registros = null;
        String[][] datos = new String[1][1];
        datos[0][0]="Sin Resultados";
        
        int filas = 0; int columnas = 0;
        ResultSetMetaData metaDatos;
        this.conectar();
        /** Esta parte realiza la conexion y hace la consulta cargando los datosen la variable registros. */
        try{
            Statement st = c1.createStatement();
            registros = st.executeQuery(SQL); //Ejecuta
            metaDatos = registros.getMetaData(); /** Datos sobre registros. */
            columnas = metaDatos.getColumnCount(); /** Cuenta las columnas que tiene la consulta. */
            registros.last(); /** Con esto nos posicionamos en la ultima fila, ya que no hay metodo para contarlas como en
             las columnas "getColumnCount". */
            
            filas = registros.getRow(); /** Obtiene le numero de filas. */
            registros.beforeFirst();/** Se posiciona al inicio. */
            datos = new String [filas][ columnas];
            int j = 0;
            while(registros.next()/*next para pasar al siguiente. */){
                for(int i = 0; i<columnas; i++){
                    datos[j][i]= registros.getString(i+1);
                }
                j++;
            }
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        this.desconectar();
        return datos;
    }
    
    // auto incremental
    public int insertar_AI(String SQL){
        int resultado = 0;
        /**********************/
         this.conectar();
         ResultSet pkAI = null; //Primary Key AUTO INCREMENT
        try{
            Statement st = c1.createStatement();
            st.executeUpdate(SQL,Statement.RETURN_GENERATED_KEYS);
            pkAI = st.getGeneratedKeys();
            
            if(pkAI.next()){
                resultado = pkAI.getInt(1); //UNO PORQUE ES LA PRIMERA COLUMNA
               //LOS RESULTSET EMPIEZAN DESDE 1, LOS ARRAY DESDE 0 
            }          
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        
        this.desconectar();
        return resultado;
    }
    
    // ENCRIPTA BYTE
    public static byte[] encrypt(String x) throws Exception {
        java.security.MessageDigest d = null;
        d = java.security.MessageDigest.getInstance("SHA-1");
        d.reset();
        d.update(x.getBytes());
        return d.digest();
    }

    //**FUNCIONA CON SHA1
    private MessageDigest md;
    private byte[] buffer, digest;
    private String hash = "";

    public String getHash(String message) {
        try {
            buffer = message.getBytes();
            md = MessageDigest.getInstance("SHA1");
            md.update(buffer);
            digest = md.digest();

            for (byte aux : digest) {
                int b = aux & 0xff;
                if (Integer.toHexString(b).length() == 1) {
                    hash += "0";
                }
                hash += Integer.toHexString(b);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return hash;
    }
    
    
}
