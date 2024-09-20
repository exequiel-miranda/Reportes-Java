package Controlador;

import Modelo.Peliculas;
import Vista.frmCatalogoPeliculas;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

//3-Importar las acciones/eventos de la clase MouseListener
public class ctrlCatalogoPeliculas implements MouseListener {
    
    //1-Importar las otras capas
    Peliculas modelo;
    frmCatalogoPeliculas vista;
    
    //2-Crear el constructor de la clase
    public ctrlCatalogoPeliculas(Peliculas Modelo, frmCatalogoPeliculas Vista){
        this.modelo = Modelo;
        this.vista = Vista;    
        
        vista.btnAgregar.addMouseListener(this);
        vista.btnEliminar.addMouseListener(this);
        vista.btnActualizar.addMouseListener(this);
        vista.btnLimpiar.addMouseListener(this);
        vista.jtbPeliculas.addMouseListener(this);
        
        modelo.Mostrar(vista.jtbPeliculas);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
       
        if(e.getSource() == vista.btnAgregar){
            modelo.setNombre_Pelicula(vista.txtNombre.getText());
            modelo.setAño_Lanzamiento(Integer.parseInt(vista.txtAñoLanzamiento.getText()));
            modelo.setGenero_Pelicula(vista.txtGenero.getText());
            
            modelo.Guardar();
            modelo.Mostrar(vista.jtbPeliculas);
            modelo.Limpiar(vista);
        }
        
        if(e.getSource() == vista.btnEliminar){
            modelo.Eliminar(vista.jtbPeliculas);
            modelo.Mostrar(vista.jtbPeliculas);
            modelo.Limpiar(vista);
        }
        
        if(e.getSource() == vista.jtbPeliculas){
            modelo.cargarDatosTabla(vista);
        }
        
        if(e.getSource() == vista.btnActualizar){
            modelo.setNombre_Pelicula(vista.txtNombre.getText());
            modelo.setAño_Lanzamiento(Integer.parseInt(vista.txtAñoLanzamiento.getText()));
            modelo.setGenero_Pelicula(vista.txtGenero.getText());
            
            modelo.Actualizar(vista.jtbPeliculas);
            modelo.Mostrar(vista.jtbPeliculas);
            modelo.Limpiar(vista);
        }
        
        if(e.getSource() == vista.btnLimpiar){
            modelo.Limpiar(vista);
        }
        
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
}
