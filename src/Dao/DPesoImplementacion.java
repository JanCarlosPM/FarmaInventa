package Dao;

import modelos.Peso;
import java.sql.*;

/**
 *
 * @author Jan Carlos
 */
public class DPesoImplementacion implements DPeso{
    
    Conexion conexion = Conexion.getInstance();
   
    @Override
    public void ingresar(Peso Peso) {
        try {
            Connection conectar = conexion.getConexion();

            PreparedStatement modificar = conectar.prepareStatement("UPDATE EXAMEN SET peso = ? WHERE peso = ?");
            modificar.setDouble(1, Peso.getPeso1());
            modificar.setDouble(2, Peso.getPeso2());

            modificar.executeUpdate();

            conexion.close(conectar);

        } catch (SQLException e) {
            //System.out.println(e);
        }
    }

}
