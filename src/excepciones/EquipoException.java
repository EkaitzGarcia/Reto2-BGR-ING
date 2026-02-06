package excepciones;

public class EquipoException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EquipoException(String mensaje) {
		super(mensaje);
	}

	public EquipoException() {
		super("El equipo ya tiene 6 Pokémon");
	}
}
