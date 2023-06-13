package Dao;

import modelos.Productos;


public interface DProductos {
    public void registrarProducto(Productos productos);
    
    public void nuevosProductos(Productos productos);

    public void modificarProducto(Productos productos);

    public void eliminarProducto(Productos productos);

    public void buscarProducto(Productos productos);
    
    public void actualizarStock(Productos productos);
    
    public void buscarStock(Productos productos);
    
    public void actualizarStockTwo(Productos productos);
}
