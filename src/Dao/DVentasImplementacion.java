package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import modelos.Ventas;

public class DVentasImplementacion implements DVentas {

    Conexion conexion = Conexion.getInstance();

    @Override
    public void registrarVenta(Ventas ventas) {
        try {
            Connection conectar = conexion.getConexion();
            PreparedStatement insertarVenta = conectar.prepareStatement("INSERT INTO Ventas(num_factura, cantidad_productos, sub_total, iva, pago_total, pago_cliente, cambio, fecha_venta, hora_venta) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");

            insertarVenta.setString(1, ventas.getNum_factura());
            insertarVenta.setString(2, ventas.getCantidad_productos());
            insertarVenta.setString(3, ventas.getSub_total());
            insertarVenta.setString(4, ventas.getIva());
            insertarVenta.setString(5, ventas.getPago_total());
            insertarVenta.setString(6, ventas.getPago_cliente());
            insertarVenta.setString(7, ventas.getCambio());
            insertarVenta.setString(8, ventas.getFecha_venta());
            insertarVenta.setString(9, ventas.getHora_venta());
            insertarVenta.executeUpdate();

            conexion.close(conectar);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
