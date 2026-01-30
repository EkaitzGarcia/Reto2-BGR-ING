package models;

import java.io.Serializable;

public class Pokemon implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	private String nombre;
	private Tipo tipo;
    private int pokedex;
    
	public Pokemon(String nombre, Tipo tipo, int pokedex) {
		super();
		this.nombre = nombre;
		this.tipo = tipo;
		this.pokedex = pokedex;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	public int getPokedex() {
		return pokedex;
	}
	public void setPokedex(int pokedex) {
		this.pokedex = pokedex;
	}
    
    

	
}