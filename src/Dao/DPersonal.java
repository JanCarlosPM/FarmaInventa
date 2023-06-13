package Dao;

import modelos.Usuario;


public interface DPersonal {
   
    public void buscarUser(Usuario Usuario);
    
    public void buscarUser1(Usuario Usuario);
    
    public void registrarUser(Usuario Usuario);
    
    public void resgitrarUser1(Usuario Usuario);
    
    public void modificarUser(Usuario Usuario);
    
    public void eliminarUser(Usuario Usuario);
    
    public void recuperarPass(Usuario Usuario);
    
    public void ingresarImagen(Usuario Usuario);
}
