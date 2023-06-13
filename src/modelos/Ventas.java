package modelos;

public class Ventas {

    private String num_factura;
    private String cantidad_productos;
    private String sub_total;
    private String iva;
    private String pago_total;
    private String pago_cliente;
    private String cambio;
    private String fecha_venta;
    private String hora_venta;

    public Ventas() {
    }

    public Ventas(String num_factura, String cantidad_productos, String sub_total, String iva, String pago_total, String pago_cliente, String cambio, String fecha_venta, String hora_venta) {
        this.num_factura = num_factura;
        this.cantidad_productos = cantidad_productos;
        this.sub_total = sub_total;
        this.iva = iva;
        this.pago_total = pago_total;
        this.pago_cliente = pago_cliente;
        this.cambio = cambio;
        this.fecha_venta = fecha_venta;
        this.hora_venta = hora_venta;
    }

    public String getNum_factura() {
        return num_factura;
    }

    public void setNum_factura(String num_factura) {
        this.num_factura = num_factura;
    }

    public String getCantidad_productos() {
        return cantidad_productos;
    }

    public void setCantidad_productos(String cantidad_productos) {
        this.cantidad_productos = cantidad_productos;
    }

    public String getSub_total() {
        return sub_total;
    }

    public void setSub_total(String sub_total) {
        this.sub_total = sub_total;
    }

    public String getIva() {
        return iva;
    }

    public void setIva(String iva) {
        this.iva = iva;
    }

    public String getPago_total() {
        return pago_total;
    }

    public void setPago_total(String pago_total) {
        this.pago_total = pago_total;
    }

    public String getPago_cliente() {
        return pago_cliente;
    }

    public void setPago_cliente(String pago_cliente) {
        this.pago_cliente = pago_cliente;
    }

    public String getCambio() {
        return cambio;
    }

    public void setCambio(String cambio) {
        this.cambio = cambio;
    }

    public String getFecha_venta() {
        return fecha_venta;
    }

    public void setFecha_venta(String fecha_venta) {
        this.fecha_venta = fecha_venta;
    }

    public String getHora_venta() {
        return hora_venta;
    }

    public void setHora_venta(String hora_venta) {
        this.hora_venta = hora_venta;
    }
}
