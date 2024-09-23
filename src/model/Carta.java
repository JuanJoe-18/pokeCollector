package model;

public class Carta{
    //atributos
    private String id;
    private String nombre;
    private int puntosVida;  //hacen referencia al this                             
    private String tipo;
    private String rareza;

    //Comportamiento - Metodos         
                      //A12
    public Carta(String id, String nombre, int puntosVida, String tipo, String rareza){
        this.id = id;
        this.nombre = nombre;
        this.puntosVida = puntosVida;
        this.tipo = tipo;
        this.rareza = rareza;
    }
    
    //Setters

    public void setId(String id){
        this.id = id;   
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void setPuntosVida(int puntosVida){
        this.puntosVida = puntosVida;
    }

    public void setTipo(String tipo){
        this.tipo = tipo;
    }

    public void setRareza(String rareza){
        this.rareza = rareza;
    }

    //Getters

    public String getId(){
        return id;
    }

    public String getNombre(){
        return nombre;
    }

    public int getPuntosVida(){
        return puntosVida;
    }

    public String getTipo(){
        return tipo;
    }

    public String getRareza(){
        return rareza;
    }

}