package models;

import java.time.LocalDate;
import java.util.Map;

import models.Pokemon;

public class Entrenador extends Persona implements Comparable{

	private String procedencia;
	private Map<String, Pokemon> equipo;

	public Entrenador(String nombre, LocalDate fecha_nac, int id, String procedencia, Map<String, Pokemon> equipo) {
		super(nombre, fecha_nac, id);
		this.procedencia = procedencia;
		this.equipo = equipo;
	}

	public String getProcedencia() {
		return procedencia;
	}

	public void setProcedencia(String procedencia) {
		this.procedencia = procedencia;
	}

	public Map<String, Pokemon> getEquipo() {
		return equipo;
	}

	public void setEquipo(Map<String, Pokemon> equipo) {
		this.equipo = equipo;
	}

	@Override
	public void visualizar() {
		// TODO Auto-generated method stub
		System.out.println(super.toString());
	}

	@Override
	public int sacarEdad() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

}