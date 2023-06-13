package modelos;

public class Productos {

    private String producto;
    private String descripcion;
    private float precio;
    private String stock;
    private String fecha_vencimiento;
    private String fecha_registro;

    public Productos() {
    }

    public Productos(String producto, String descripcion, float precio, String stock, String fecha_vencimiento, String fecha_registro) {
        this.producto = producto;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.fecha_vencimiento = fecha_vencimiento;
        this.fecha_registro = fecha_registro;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getFecha_vencimiento() {
        return fecha_vencimiento;
    }

    public void setFecha_vencimiento(String fecha_vencimiento) {
        this.fecha_vencimiento = fecha_vencimiento;
    }

    public String getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(String fecha_registro) {
        this.fecha_registro = fecha_registro;
    }
}
