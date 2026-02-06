	package modelo;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class Persona implements Serializable, Comparable<Persona> {

	@Override
	public String toString() {
		return "Persona [id=" + id + ",Nombre=" + nombre + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String nombre;
	protected LocalDate fecha_nac;
	protected int id;

	public Persona(String nombre, LocalDate fecha_nac, int id) {
		super();
		this.nombre = nombre;
		this.fecha_nac = fecha_nac;
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public LocalDate getFecha_nac() {
		return fecha_nac;
	}

	public void setFecha_nac(LocalDate fecha_nac) {
		this.fecha_nac = fecha_nac;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public abstract void visualizar();

	public abstract int sacarEdad();

}
