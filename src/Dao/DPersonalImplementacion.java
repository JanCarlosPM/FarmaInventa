package Dao;

import Interfaces.FrmAdministrador;
import Interfaces.FrmLogin;
import Interfaces.FrmPrincipal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import modelos.Usuario;

public class DPersonalImplementacion implements DPersonal {

    Conexion conexion = Conexion.getInstance();

    @Override
    public void buscarUser(Usuario Usuario) {
        try {
            Connection conectar = conexion.getConexion();
            PreparedStatement buscarU = conectar.prepareStatement("SELECT Usuario, Contraseña FROM Login Where Usuario = ? and Contraseña = ?");

            buscarU.setString(1, Usuario.getUsuario());
            buscarU.setString(2, Usuario.getContraseña());
            ResultSet consulta = buscarU.executeQuery();

            if (consulta.next()) {
                Usuario.setUsuario(consulta.getString("Usuario"));
                Usuario.setContraseña(consulta.getString("Contraseña"));
                FrmPrincipal l = new FrmPrincipal();
                l.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Usuario y/o Contraseña Incorrectos", "Farmacia", 2);

                FrmLogin a = new FrmLogin();
                a.setVisible(true);
            }
            conexion.close(conectar);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void registrarUser(Usuario Usuario) {
        try {
            Connection conectar = conexion.getConexion();
            PreparedStatement buscar = conectar.prepareStatement("SELECT * FROM Login Where Usuario = ?");

            buscar.setString(1, Usuario.getUsuario());
            ResultSet consulta = buscar.executeQuery();

            if (consulta.next()) {
                Usuario.setUsuario(consulta.getString("Usuario"));
                JOptionPane.showMessageDialog(null, "Usuario ya registrado, intente con uno nuevo", "Farmacia", 2);
            } else {
                PreparedStatement insertar = conectar.prepareStatement("INSERT INTO Login(Usuario, Contraseña, Correo) VALUES(?, ?, ?)");
                insertar.setString(1, Usuario.getUsuario());
                insertar.setString(2, Usuario.getContraseña());
                insertar.setString(3, Usuario.getCorreo());
                insertar.executeUpdate();
                JOptionPane.showMessageDialog(null, "Usuario Registrado, inicie sesión", "Farmacia", 2);
            }
            conexion.close(conectar);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void resgitrarUser1(Usuario Usuario) {
        try {
            Connection conectar = conexion.getConexion();
            PreparedStatement buscar = conectar.prepareStatement("SELECT Usuario FROM Login Where Usuario = ?");

            buscar.setString(1, Usuario.getUsuario());
            ResultSet consulta = buscar.executeQuery();

            if (consulta.next()) {
                Usuario.setUsuario(consulta.getString("Usuario"));
                JOptionPane.showMessageDialog(null, "Usuario ya registrado, intente con uno nuevo", "Farmacia", 2);
            } else {
                PreparedStatement insertar = conectar.prepareStatement("INSERT INTO Login(Usuario, Contraseña) VALUES(?, ?)");
                insertar.setString(1, Usuario.getUsuario());
                insertar.setString(2, Usuario.getContraseña());
                insertar.executeUpdate();
                JOptionPane.showMessageDialog(null, "Usuario Registrado", "Farmacia", 2);
            }
            conexion.close(conectar);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void modificarUser(Usuario Usuario) {
        try {
            Connection conectar = conexion.getConexion();
            PreparedStatement buscarUser = conectar.prepareStatement("SELECT usuario, contraseña FROM Login WHERE usuario = ? and contraseña = ?");

            buscarUser.setString(1, Usuario.getUsuario());
            buscarUser.setString(2, Usuario.getContraseña());
            ResultSet consultaUser = buscarUser.executeQuery();
            if (consultaUser.next()) {
                Usuario.setUsuario(consultaUser.getString("Usuario"));
                Usuario.setContraseña(consultaUser.getString("Contraseña"));
                JOptionPane.showMessageDialog(null, "Debe modificar al menos un dato del Usuario", "Farmacia", 2);
            } else {
                PreparedStatement modificarUser = conectar.prepareStatement("UPDATE Login SET Contraseña = ? WHERE Usuario = ?");
                modificarUser.setString(1, Usuario.getContraseña());
                modificarUser.setString(2, Usuario.getUsuario());
                modificarUser.executeUpdate();
                JOptionPane.showMessageDialog(null, "Contraseña Modificada con Éxito", "Farmacia", 2);
            }

            conexion.close(conectar);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void eliminarUser(Usuario Usuario) {
        try {
            Connection conectar = conexion.getConexion();
            PreparedStatement buscar = conectar.prepareStatement("SELECT * FROM Login WHERE usuario = ?");

            buscar.setString(1, Usuario.getUsuario());
            ResultSet consulta = buscar.executeQuery();

            if (consulta.next()) {
                Usuario.setUsuario(consulta.getString("Usuario"));
                PreparedStatement eliminar = conectar.prepareStatement("DELETE FROM Login WHERE usuario = ?");
                eliminar.setString(1, Usuario.getUsuario());
                int confirmado = JOptionPane.showConfirmDialog(null,
                        "¿Está seguro de eliminar el usuario: " + Usuario.getUsuario(), "Farmacia", 2);

                if (JOptionPane.OK_OPTION == confirmado) {
                    eliminar.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Usuario Eliminado con Éxito", "Farmacia", 2);
                    FrmLogin a = new FrmLogin();
                    a.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Usuario no Eliminado", "Farmacia", 2);
                    FrmAdministrador b = new FrmAdministrador();
                    b.setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Usuario no Existente", "Farmacia", 2);
                FrmAdministrador b = new FrmAdministrador();
                b.setVisible(true);
            }

            conexion.close(conectar);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void buscarUser1(Usuario Usuario) {
        try {
            Connection conectar = conexion.getConexion();
            PreparedStatement buscarUser1 = conectar.prepareStatement("SELECT * FROM Login Where Usuario = ?");

            buscarUser1.setString(1, Usuario.getUsuario());
            ResultSet consulta = buscarUser1.executeQuery();

            if (consulta.next()) {
                Usuario.setUsuario(consulta.getString("Usuario"));
                Usuario.setContraseña(consulta.getString("contraseña"));
                JOptionPane.showMessageDialog(null, "Usuario Registrado", "Farmacia", 2);
            } else {
                JOptionPane.showMessageDialog(null, "Usuario no Registrado", "Farmacia", 2);
            }
            conexion.close(conectar);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    static String generarConsena() {
        char[] mayusculas = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        char[] minusculas = {'a', 'b', 'c', 'd', 'e', 'f', 'g'};
        char[] numeros = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};
        char[] simbolos = {'#', '$', '%', '/', '?', '¿', '.', ',', '*'};

        StringBuilder caracteres = new StringBuilder();
        caracteres.append(mayusculas);
        caracteres.append(minusculas);
        caracteres.append(numeros);
        caracteres.append(simbolos);

        StringBuilder contrasena = new StringBuilder();

        for (int i = 0; i <= 8; i++) {
            int cantidadCaracteres = caracteres.length();
            int numeroRandom = (int) (Math.random() * cantidadCaracteres);

            contrasena.append((caracteres.toString()).charAt(numeroRandom));
        }
        return contrasena.toString();
    }

    @Override
    public void recuperarPass(Usuario Usuario) {
        try {
            Connection conectar = conexion.getConexion();
            PreparedStatement buscarPass = conectar.prepareStatement("SELECT usuario, correo FROM Login Where usuario = ? and correo = ?");

            buscarPass.setString(1, Usuario.getUsuario());
            buscarPass.setString(2, Usuario.getCorreo());
            ResultSet consulta = buscarPass.executeQuery();

            if (consulta.next()) {
                Usuario.setUsuario(consulta.getString("Usuario"));
                Usuario.setCorreo(consulta.getString("Correo"));
                JOptionPane.showMessageDialog(null, "Usuario y Correo Coinciden", "Farmacia", 2);
                PreparedStatement modificarUser = conectar.prepareStatement("UPDATE Login SET Contraseña = ? WHERE Usuario = ? and Correo = ?");
                modificarUser.setString(1, generarConsena());
                modificarUser.setString(2, Usuario.getUsuario());
                modificarUser.setString(3, Usuario.getCorreo());
                modificarUser.executeUpdate();
                JOptionPane.showMessageDialog(null, "Contraseña generada y guardada en la base de datos", "Farmacia", 2);

                PreparedStatement mostrarPass = conectar.prepareStatement("SELECT contraseña FROM Login WHERE usuario = ? and correo = ?");
                mostrarPass.setString(1, Usuario.getUsuario());
                mostrarPass.setString(2, Usuario.getCorreo());
                ResultSet consultaPass = mostrarPass.executeQuery();

                if (consultaPass.next()) {
                    Usuario.setContraseña(consultaPass.getString("Contraseña"));
                }

            } else {
                JOptionPane.showMessageDialog(null, "Usuario y/o Correo no Coinciden", "Farmacia", 2);
            }

            conexion.close(conectar);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void ingresarImagen(Usuario Usuario) {
        try {
            Connection conectar = conexion.getConexion();
            PreparedStatement insertar = conectar.prepareStatement("INSERT INTO prueba(imagen) VALUES(?)");
            insertar.setBytes(1, Usuario.getImagen());
            insertar.executeUpdate();

            JOptionPane.showMessageDialog(null, "Imagen guardada", "Farmacia", 2);
            conexion.close(conectar);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
