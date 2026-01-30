package models;

import java.time.LocalDate;
import java.util.Map;

public class LiderGimnasio extends Persona implements Comparable{
	
	private Tipo tipo;
	private String medalla;
	Map<String, Pokemon> equipoLider;
	
	public LiderGimnasio(String nombre, LocalDate fecha_nac, int id, Tipo tipo, String medalla,
			Map<String, Pokemon> equipoLider) {
		super(nombre, fecha_nac, id);
		this.tipo = tipo;
		this.medalla = medalla;
		this.equipoLider = equipoLider;
		
	}
	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	public String getMedalla() {
		return medalla;
	}
	public void setMedalla(String medalla) {
		this.medalla = medalla;
	}
	public Map<String, Pokemon> getEquipoLider() {
		return equipoLider;
	}
	public void setEquipoLider(Map<String, Pokemon> equipoLider) {
		this.equipoLider = equipoLider;
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