package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static Conexion instancia;
    private static final String SERVIDOR = "localhost";
    private static final String USUARIO = "sa";
    private static final String PW = "Usuario123.";
    private static final String NOMBREBD = "Registro_Ventas_Farmacia";
    private static final String PUERTO = "1434";
    private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

    public Connection getConexion() {
        try {
            String conexionUrl = "jdbc:sqlserver://" + SERVIDOR + ": " + PUERTO
                    + "; Databasename= " + NOMBREBD + "; user= " + USUARIO
                    + "; password = " + PW + ";";
            Class.forName(DRIVER);
            return (DriverManager.getConnection(conexionUrl));
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex);
        }
        return null;
    }

    public void close(Connection conn) {
        try {
            conn.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public static Conexion getInstance() {
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }
}
