package modelo;

import java.time.LocalDate;
import java.util.Map;

public class Entrenador extends Persona  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String procedencia;
	private Map<String, Pokemon> equipo;
	
	public Entrenador(String nombre, LocalDate fecha_nac, int id, String procedencia,
			Map<String, Pokemon> equipo) {
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
			System.out.println(super.toString());
			System.out.println("Edad: " + sacarEdad());
			System.out.println("Procedencia: " +procedencia);
			System.out.println("Equipo: " + equipo.values());
		}



	@Override
	public int sacarEdad() {
		// TODO Auto-generated method stub
		return LocalDate.now().getYear() - fecha_nac.getYear();
	}

	@Override
	public int compareTo(Persona o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
