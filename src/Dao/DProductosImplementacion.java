package Dao;

import java.sql.*;
import javax.swing.JOptionPane;
import modelos.Productos;

public class DProductosImplementacion implements DProductos {

    Conexion conexion = Conexion.getInstance();

    @Override
    public void registrarProducto(Productos productos) {
        try {
            Connection conectar = conexion.getConexion();
            PreparedStatement buscar = conectar.prepareStatement("SELECT * FROM Productos Where producto = ?");
            buscar.setQueryTimeout(60); 
            buscar.setString(1, productos.getProducto());
            ResultSet consulta = buscar.executeQuery();

            if (consulta.next()) {
                productos.setProducto(consulta.getString("producto"));
                JOptionPane.showMessageDialog(null, "Producto ya registrado, intente con otro", "Farmacia", 2);
            } else {
                PreparedStatement insertar = conectar.prepareStatement("INSERT INTO Productos(producto, descripcion, precio, stock, fecha_vencimiento, fecha_registro) VALUES(?, ?, ?, ?, ?, ?)");
                insertar.setQueryTimeout(60); 
                insertar.setString(1, productos.getProducto());
                insertar.setString(2, productos.getDescripcion());
                insertar.setFloat(3, productos.getPrecio());
                insertar.setString(4, productos.getStock());
                insertar.setString(5, productos.getFecha_vencimiento());
                insertar.setString(6, productos.getFecha_registro());

                insertar.executeUpdate();
                JOptionPane.showMessageDialog(null, "Producto Registrado", "Farmacia", 2);
            }
            conexion.close(conectar);

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void modificarProducto(Productos productos) {
        try {
            Connection conectar = conexion.getConexion();
            PreparedStatement buscar = conectar.prepareStatement("SELECT producto, descripcion, precio, stock, fecha_vencimiento, fecha_registro FROM Productos WHERE producto = ? and descripcion = ? and precio = ? and stock = ? and fecha_vencimiento = ? and fecha_registro = ?");

            buscar.setString(1, productos.getProducto());
            buscar.setString(2, productos.getDescripcion());
            buscar.setFloat(3, productos.getPrecio());
            buscar.setString(4, productos.getStock());
            buscar.setString(5, productos.getFecha_vencimiento());
            buscar.setString(6, productos.getFecha_registro());
            ResultSet consulta = buscar.executeQuery();

            if (consulta.next()) {
                productos.setProducto(consulta.getString("producto"));
                productos.setDescripcion(consulta.getString("descripcion"));
                productos.setPrecio(consulta.getFloat("precio"));
                productos.setStock(consulta.getString("stock"));
                productos.setFecha_vencimiento(consulta.getString("fecha_vencimiento"));
                productos.setFecha_registro(consulta.getString("fecha_registro"));
                //DEBE MODIFICAR AL MENOS 1 DATO//
                JOptionPane.showMessageDialog(null, "Debe modificar al menos un dato del producto", "Farmacia", 2);
            } else {
                PreparedStatement modificar = conectar.prepareStatement("UPDATE Productos SET descripcion = ?, precio = ?, stock = ?, fecha_vencimiento = ?, fecha_registro = ? WHERE producto = ?");
                modificar.setString(1, productos.getDescripcion());
                modificar.setFloat(2, productos.getPrecio());
                modificar.setString(3, productos.getStock());
                modificar.setString(4, productos.getFecha_vencimiento());
                modificar.setString(5, productos.getFecha_registro());
                modificar.setString(6, productos.getProducto());
                modificar.executeUpdate();
                JOptionPane.showMessageDialog(null, "Producto Modificado con Éxito", "Farmacia", 2);
            }
            conexion.close(conectar);

        } catch (SQLException e) {
            //System.out.println(e);
        }
    }

    @Override
    public void eliminarProducto(Productos productos) {
        try {
            Connection conectar = conexion.getConexion();
            PreparedStatement buscar = conectar.prepareStatement("SELECT * FROM Productos Where producto = ?");

            buscar.setString(1, productos.getProducto());
            ResultSet consulta = buscar.executeQuery();

            if (consulta.next()) {
                productos.setProducto(consulta.getString("producto"));
                PreparedStatement eliminar = conectar.prepareStatement("DELETE FROM Productos WHERE producto = ?");
                eliminar.setString(1, productos.getProducto());
                int confirmado = JOptionPane.showConfirmDialog(null,
                        "¿Está seguro de eliminar el producto: " + productos.getProducto(), "Farmacia", 2);

                if (JOptionPane.OK_OPTION == confirmado) {
                    eliminar.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Producto Eliminado con Éxito", "Farmacia", 2);
                } else {
                    JOptionPane.showMessageDialog(null, "Producto no Eliminado", "Farmacia", 2);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Producto no Existente", "Farmacia", 2);
            }
            conexion.close(conectar);

        } catch (Exception e) {
            //System.out.println(e);
        }
    }

    @Override
    public void buscarProducto(Productos productos) {
        try {
            Connection conectar = conexion.getConexion();
            PreparedStatement buscar = conectar.prepareStatement("SELECT * FROM Productos WHERE producto = ?");

            buscar.setString(1, productos.getProducto());
            ResultSet consulta = buscar.executeQuery();

            if (consulta.next()) {
                JOptionPane.showMessageDialog(null, "Producto Encontrado", "Farmacia", 2);
                productos.setProducto(consulta.getString("producto"));
                productos.setDescripcion(consulta.getString("descripcion"));
                productos.setPrecio(consulta.getFloat("precio"));
                productos.setStock(consulta.getString("stock"));
                productos.setFecha_vencimiento(consulta.getString("fecha_vencimiento"));
                productos.setFecha_registro(consulta.getString("fecha_registro"));
            } else {
                JOptionPane.showMessageDialog(null, "Producto no Encontrado", "Farmacia", 2);
            }
            conexion.close(conectar);

        } catch (Exception e) {
            //System.out.println(e);
        }
    }

    @Override
    public void actualizarStock(Productos productos) {
        try {
            Connection conectar = conexion.getConexion();

            PreparedStatement modificar = conectar.prepareStatement("UPDATE Productos SET stock = ? WHERE producto = ?");
            modificar.setString(1, productos.getStock());
            modificar.setString(2, productos.getProducto());
            modificar.executeUpdate();
            JOptionPane.showMessageDialog(null, "Producto Listado con Éxito", "Farmacia", 2);
            conexion.close(conectar);

        } catch (SQLException e) {
            //System.out.println(e);
        }
    }

    @Override
    public void nuevosProductos(Productos productos) {
        try {
            Connection conectar = conexion.getConexion();
            PreparedStatement buscar = conectar.prepareStatement("SELECT * FROM Productos Where producto = ?");

            buscar.setString(1, productos.getProducto());
            ResultSet consulta = buscar.executeQuery();

            if (consulta.next()) {
                productos.setProducto(consulta.getString("producto"));
                JOptionPane.showMessageDialog(null, "Producto ya registrado, intente con otro", "Farmacia", 2);
            } else {
                PreparedStatement insertar = conectar.prepareStatement("INSERT INTO Productos(producto, descripcion, precio, stock, fecha_vencimiento, fecha_registro) VALUES(?, ?, ?, ?, ?, ?)");
                insertar.setString(1, productos.getProducto());
                insertar.setString(2, productos.getDescripcion());
                insertar.setFloat(3, productos.getPrecio());
                insertar.setString(4, productos.getStock());
                insertar.setString(5, productos.getFecha_vencimiento());
                insertar.setString(6, productos.getFecha_registro());

                insertar.executeUpdate();
            }
            conexion.close(conectar);

        } catch (SQLException e) {
            //System.out.println(e);
        }
    }

    @Override
    public void buscarStock(Productos productos) {
        try {
            Connection conectar = conexion.getConexion();
            PreparedStatement buscar = conectar.prepareStatement("SELECT stock FROM Productos WHERE producto = ?");

            buscar.setString(1, productos.getProducto());
            ResultSet consulta = buscar.executeQuery();

            if (consulta.next()) {
                productos.setStock(consulta.getString("stock"));
            }

            conexion.close(conectar);

        } catch (SQLException e) {
            //System.out.println(e);
        }
    }

    @Override
    public void actualizarStockTwo(Productos productos) {
        try {
            Connection conectar = conexion.getConexion();
            PreparedStatement modificar = conectar.prepareStatement("UPDATE Productos SET stock = ? WHERE producto = ?");

            modificar.setString(1, productos.getStock());
            modificar.setString(2, productos.getProducto());
            modificar.executeUpdate();
            conexion.close(conectar);

        } catch (SQLException e) {
            //System.out.println(e);
        }
    }
}
