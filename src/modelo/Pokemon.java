	package modelo;

import java.io.Serializable;

public class Pokemon implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nombre;
	
	private Tipo tipo;
    private int pokedex;
    
	public Pokemon(String nombre, Tipo tipo) {
		this.nombre = nombre;
		this.tipo = tipo;
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
    
	@Override
	public String toString() {
		return "Pokemon [nombre=" + nombre + ", tipo=" + tipo + ", pokedex=" + pokedex + "]";
	}

	public int getPokedex() {
		// TODO Auto-generated method stub
		return pokedex;
	}

	public void setPokedex(int pokedex) {
		this.pokedex = pokedex;
		
		
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
