package appcpanama.logicstudio.net.appcpanama.model;

/**
 * Created by LogicStudio on 23/10/2015.
 */
public class Animal {


    private String nombre;
    private String nombreCientifico;
    private int imageAnimal;
    private String estadoAnimal;
    private String ciudadanoReporte;
    private String ubicacionReporte;

    public String getEstadoAnimal() {
        return estadoAnimal;
    }

    public void setEstadoAnimal(String estadoAnimal) {
        this.estadoAnimal = estadoAnimal;
    }

    public String getUbicacionReporte() {
        return ubicacionReporte;
    }

    public void setUbicacionReporte(String ubicacionReporte) {
        this.ubicacionReporte = ubicacionReporte;
    }

    public String getCiudadanoReporte() {
        return ciudadanoReporte;
    }

    public void setCiudadanoReporte(String ciudadanoReporte) {
        this.ciudadanoReporte = ciudadanoReporte;
    }

    public Animal()
    {

    }

    public Animal(String nombre,String nombreCientifico,int imageAnimal)
    {
        this.nombre = nombre;
        this.nombreCientifico = nombreCientifico;
        this.imageAnimal = imageAnimal;
    }



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreCientifico() {
        return nombreCientifico;
    }

    public void setNombreCientifico(String nombreCientifico) {
        this.nombreCientifico = nombreCientifico;
    }

    public int getImageAnimal() {
        return imageAnimal;
    }

    public void setImageAnimal(int imageAnimal) {
        this.imageAnimal = imageAnimal;
    }
}
