package Modelo;

import Vista.frmCatalogoPeliculas;
import java.sql.*;
import java.util.UUID;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Peliculas {
    //1- Parametros
    private String UUID_Pelicula;
    private String Nombre_Pelicula;
    private int Año_Lanzamiento;
    private String Genero_Pelicula;
    
    //2- Getters y Setters

    public String getUUID_Pelicula() {
        return UUID_Pelicula;
    }

    public void setUUID_Pelicula(String UUID_Pelicula) {
        this.UUID_Pelicula = UUID_Pelicula;
    }

    public String getNombre_Pelicula() {
        return Nombre_Pelicula;
    }

    public void setNombre_Pelicula(String Nombre_Pelicula) {
        this.Nombre_Pelicula = Nombre_Pelicula;
    }

    public int getAño_Lanzamiento() {
        return Año_Lanzamiento;
    }

    public void setAño_Lanzamiento(int Año_Lanzamiento) {
        this.Año_Lanzamiento = Año_Lanzamiento;
    }

    public String getGenero_Pelicula() {
        return Genero_Pelicula;
    }

    public void setGenero_Pelicula(String Genero_Pelicula) {
        this.Genero_Pelicula = Genero_Pelicula;
    }
    
    //3-Métodos (Insert, select, update, delete)
    
    ///////////////// Guardar //////////////////
    public void Guardar() {
        //Creamos una variable igual a ejecutar el método de la clase de conexión
        Connection conexion = ClaseConexion.getConexion();
        try {
            //Creamos el PreparedStatement que ejecutará la Query
            PreparedStatement addPelicula = conexion.prepareStatement("INSERT INTO tbPeliculas(UUID_Pelicula, Nombre_Pelicula, Año_Lanzamiento, Genero_Pelicula) VALUES (?, ?, ?, ?)");
            //Establecer valores de la consulta SQL
            addPelicula.setString(1, UUID.randomUUID().toString());
            addPelicula.setString(2, getNombre_Pelicula());
            addPelicula.setDouble(3, getAño_Lanzamiento());
            addPelicula.setString(4, getGenero_Pelicula());
 
            //Ejecutar la consulta
            addPelicula.executeUpdate();
 
        } catch (SQLException ex) {
            System.out.println("este es el error en el modelo:metodo guardar " + ex);
        }
    }
    
    
    //////////////// Mostrar ////////////////////////
    public void Mostrar(JTable tabla) {
        //Creamos una variable de la clase de conexion
        Connection conexion = ClaseConexion.getConexion();
        //Definimos el modelo de la tabla
        DefaultTableModel modeloDatos = new DefaultTableModel();
        modeloDatos.setColumnIdentifiers(new Object[]{"UUID_Pelicula", "Nombre_Pelicula", "Año_Lanzamiento", "Genero_Pelicula"});
        try {
            //Creamos un Statement
            Statement statement = conexion.createStatement();
            //Ejecutamos el Statement con la consulta y lo asignamos a una variable de tipo ResultSet
            ResultSet rs = statement.executeQuery("SELECT * FROM tbPeliculas");
            //Recorremos el ResultSet
            while (rs.next()) {
                //Llenamos el modelo por cada vez que recorremos el resultSet
                modeloDatos.addRow(new Object[]{rs.getString("UUID_Pelicula"), 
                    rs.getString("Nombre_Pelicula"), 
                    rs.getInt("Año_Lanzamiento"), 
                    rs.getString("Genero_Pelicula")});
            }
            
            //Asignamos el nuevo modelo lleno a la tabla
            tabla.setModel(modeloDatos);
        } catch (Exception e) {
            System.out.println("Este es el error en el modelo, metodo mostrar " + e);
        }
    }
  
     ///////////////////// Eliminar //////////////////////////
    public void Eliminar(JTable tabla) {
        //Creamos una variable igual a ejecutar el método de la clase de conexión
        Connection conexion = ClaseConexion.getConexion();
 
        //obtenemos que fila seleccionó el usuario
        int filaSeleccionada = tabla.getSelectedRow();
        //Obtenemos el id de la fila seleccionada
        String miId = tabla.getValueAt(filaSeleccionada, 0).toString();
        //borramos 
        try {
            PreparedStatement deletePelicula = conexion.prepareStatement("delete from tbPeliculas where UUID_Pelicula = ?");
            deletePelicula.setString(1, miId);
            deletePelicula.executeUpdate();
        } catch (Exception e) {
            System.out.println("este es el error metodo de eliminar" + e);
        }
    }
    
     ////////// Cargar Datos de la fila seleccionada en los TextField /////////////////
       public void cargarDatosTabla(frmCatalogoPeliculas vista) {
        // Obtén la fila seleccionada 
        int filaSeleccionada = vista.jtbPeliculas.getSelectedRow();
        // Debemos asegurarnos que haya una fila seleccionada antes de acceder a sus valores
        if (filaSeleccionada != -1) {
            String UUIDDeTb = vista.jtbPeliculas.getValueAt(filaSeleccionada, 0).toString();
            String NombreDeTB = vista.jtbPeliculas.getValueAt(filaSeleccionada, 1).toString();
            String AñoLanzamientoTB = vista.jtbPeliculas.getValueAt(filaSeleccionada, 2).toString();
            String GeneroTB = vista.jtbPeliculas.getValueAt(filaSeleccionada, 3).toString();
            // Establece los valores en los campos de texto
            vista.txtNombre.setText(NombreDeTB);
            vista.txtAñoLanzamiento.setText(AñoLanzamientoTB);
            vista.txtGenero.setText(GeneroTB);
        }
    }
       
            ///////////////////////// Actualizar ///////////////////////////////
     public void Actualizar(JTable tabla) {
        //Creamos una variable igual a ejecutar el método de la clase de conexión
        Connection conexion = ClaseConexion.getConexion();
        //obtenemos que fila seleccionó el usuario
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada != -1) {
            //Obtenemos el id de la fila seleccionada
            String miUUId = tabla.getValueAt(filaSeleccionada, 0).toString();
            try { 
                //Ejecutamos la Query
                PreparedStatement updatePelicula = conexion.prepareStatement("update tbPeliculas set Nombre_Pelicula= ?, Año_Lanzamiento = ?, Genero_Pelicula = ? where UUID_Pelicula = ?");
                updatePelicula.setString(1, getNombre_Pelicula());
                updatePelicula.setInt(2, getAño_Lanzamiento());
                updatePelicula.setString(3, getGenero_Pelicula());
                updatePelicula.setString(4, miUUId);
                updatePelicula.executeUpdate();
            } catch (Exception e) {
                System.out.println("este es el error en el metodo de actualizar" + e);
            }
        } else {
            System.out.println("no funciona actualizar");
        }
    }
     
    /////////////// Limpiar ///////////////////////
     public void Limpiar(frmCatalogoPeliculas vista){
         vista.txtNombre.setText("");
         vista.txtAñoLanzamiento.setText("");
         vista.txtGenero.setText("");
     }

}
