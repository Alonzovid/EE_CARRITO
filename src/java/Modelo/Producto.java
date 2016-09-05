/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Controlador.BeanProducto;
import java.util.ArrayList;

/**
 *
 * @author dajua
 */
public class Producto extends Conector {
    
    public ArrayList<BeanProducto> producto_x_todos(){
        ArrayList<BeanProducto> productos = new ArrayList<>();
        String[][] datosSQL = null;
        String SQL = "CALL selectProductos();";
        datosSQL = this.consulta_registros(SQL);
        for(int i = 0; i < datosSQL.length; i++){
            productos.add(new BeanProducto(Integer.parseInt(datosSQL[i][0]), datosSQL[i][1], 
                    datosSQL[i][2], Integer.parseInt(datosSQL[i][3]), Double.parseDouble(datosSQL[i][4]), 
                    Integer.parseInt(datosSQL[i][5])));
        }
        
        return productos;
    }
    
    public BeanProducto producto_x_id(int idProducto){
        BeanProducto producto = null;
        String[][] datosSQL = null;
        String[] dato = null;
        String SQL = "CALL selectProducto("+idProducto+");";
        datosSQL = this.consulta_registros(SQL);
        if(datosSQL.length != 0){
            dato = datosSQL[0];
            producto = new BeanProducto(Integer.parseInt(dato[0]), dato[1], dato[2], Integer.parseInt(dato[3]), 
                    Double.parseDouble(dato[4]), Integer.parseInt(dato[5]));
        }
        
        return producto;
    }
    
    /*
    public static void main(String[] args) {
        Producto producto = new Producto();
        for(BeanProducto p : producto.producto_x_todos()){
            System.out.println(p.getNombre());
        }
        System.out.println("-------");
        
        BeanProducto bp = producto.producto_x_id(1);
        System.out.println(bp.getNombre());
    }
    */
}
