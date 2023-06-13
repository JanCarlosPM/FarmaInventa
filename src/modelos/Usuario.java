package modelos;

public class Usuario {

    private String usuario;
    private String contraseña;
    private String correo;
    private byte [] imagen;

    public Usuario() {
    }

    public Usuario(String usuario, String contraseña, String correo, byte[] imagen) {
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.correo = correo;
        this.imagen = imagen;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }
}