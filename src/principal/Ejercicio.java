package principal;

import java.io.*;
import java.nio.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;
import models.*;
import java.time.LocalDate;

public class Ejercicio {
	public static void main(String[] args) {
		
		File fichPersona = new File("persona.obj");
		File pokedex = new File("pokedex.obj");
		ArrayList<Persona> Personas = new ArrayList<>();
		ArrayList<Entrenador> mapaEntrenador = new ArrayList<>();
		ArrayList< LiderGimnasio> mapaLiderGimnasio = new ArrayList<>();
		int opcion;
		
		do {
			opcion = Util.leerInt(
					"Menu:\n1. Añadir Pokemon"
					+ "\n2. Ver Pokemon"
					+ "\n3. Añadir Persona "
					+ "\n4. Ver Persona "
					+ "\n5. Modificar equipo de Persona"
					+ "\n6. Ordenar Persona"
					+ "\n7. Eliminar Persona"
					+ "\n8. Eliminar mas de una Persona"
					+ "\n9. Salir"
					+ "\nElige una opción: ");
			
			switch (opcion) {
			case 1:
				añadirPokemon(pokedex);
				break;
			case 2:
				verPokemon(pokedex);
				break;
			case 3:
				añadirPersona(fichPersona,pokedex,Personas);
				break;
			case 4:
				VerPersona(fichPersona,Personas);
				break;
			case 5:
				ModificarEquipo(fichPersona,pokedex,Personas);
				break;
			case 6:
				OrdenarPersona(fichPersona,Personas, mapaEntrenador, mapaLiderGimnasio);
				break;
			case 7:
				eliminarPersona(fichPersona,Personas);
				break;
			case 8:
				eliminarMasDeunaPersona(fichPersona,Personas);
				break;
			case 9:
				System.out.println("\nSaliendo...");
				break;
			}
		} while (opcion != 9);
	}
	
	//Case 1.
	private static void añadirPokemon(File pokedex) {
		

	}
	
	//Case 2
	private static void verPokemon(File pokedex) {		
		try (ObjectInputStream personaIStream = new ObjectInputStream(new FileInputStream(pokedex))) {
			try {
				while (true) {
					Pokemon pokemon = (Pokemon) personaIStream.readObject();
					pokemon.toString();
				}
			} catch (EOFException excepcion) {

			}

		} catch (Exception excepcion) {
			System.out.println();
		}
	}
	
	//Case 3
	private static void añadirPersona(File fichPersona, File pokedex, ArrayList<Persona> personas) {
		// TODO Auto-generated method stub
		
	}
	
	//Case 4
	private static void VerPersona(File fichPersona, ArrayList<Persona> personas) {
		if (!fichPersona.exists()) {
	        System.out.println("No hay personas registradas.");
	        return;
	    }

	    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichPersona))) {

	        while (true) {
	            Persona p = (Persona) ois.readObject();
	            personas.add(p); 
	        }

	    } catch (EOFException e) {
	       
	    } catch (Exception e) {
	        System.out.println("Error al leer personas.");
	    }

	    
	    for (Persona p : personas) {
	        p.visualizar(); 
	    }
	}
	
	//Case 5
	private static void ModificarEquipo(File fichPersona, File pokedex, ArrayList<Persona> personas) {
		// TODO Auto-generated method stub
		
	}
	
	// ------------------- O -------------------
	//Case 6
	private static void OrdenarPersona(File fichPersona, ArrayList<Persona> personas, 
			ArrayList<Entrenador> mapaEntrenador, ArrayList<LiderGimnasio> mapaLiderGimnasio) {
		// TODO Auto-generated method stub
		int opc;
		do {
			System.out.println("\n¿Como quieres ordenar las personas?"
					+ "\n1. Por Rol."
					+ "\n2. Por Nombre."
					+ "\n3. Por Código de entrenador."
					+ "\n4. Volver atras.");
			
			opc = Util.leerInt("");
			
			switch(opc) {
				case 1:
					ordenarRol(fichPersona, mapaEntrenador, mapaLiderGimnasio);
					break;
				case 2:
					ordenarNombre(fichPersona, mapaEntrenador, mapaLiderGimnasio);
					break;
				case 3:
					ordenarCodEntr(fichPersona, mapaEntrenador, mapaLiderGimnasio);
					break;
				case 4:
					System.out.println("\nVolviendo atras...");
					break;
			}
		}while(true);
	}
	
	//Case 1 del submenú.
	private static void ordenarRol(File fichPersona, ArrayList<Entrenador> mapaEntrenador, 
			ArrayList<LiderGimnasio> mapaLiderGimnasio) {
		//String elec = Util.introducirCadena("\n¿Que rol quieres ordenar? Entrenador | Lider.");
		
		fichPersona = new File("persona.obj");
		
		if(!fichPersona.exists()) {
			System.out.println("\nNo exixte el fichero 'persona.obj'");
		}else {
			try(FileInputStream fichIStream = new FileInputStream(fichPersona);
					ObjectInputStream personaIStream = new ObjectInputStream(fichIStream)){
				
				String elec = Util.introducirCadena("\n¿Que rol quieres ordenar? Entrenador | Lider.");
				if(elec.equalsIgnoreCase("Entrenador")) {
					
					while(true) {
						try {
							Entrenador entr = (Entrenador) personaIStream.readObject();
							mapaEntrenador.add(entr);
						}catch(EOFException exc) {
							break;
						}
					}
					
					//Ordenar entrenadores.
					mapaEntrenador.sort(Comparator.comparing(Entrenador::getNombre));
					
					//Mostrar los entrenadores.
					System.out.println("\nEntrenadores ordenados:");
					for(Entrenador e : mapaEntrenador) {
						System.out.println(e);
					}
				} else if(elec.equalsIgnoreCase("Lider")) {
					while(true) {
						try {
							LiderGimnasio lid = (LiderGimnasio) personaIStream.readObject();
							mapaLiderGimnasio.add(lid);
						}catch(EOFException ex) {
							break;
						}
					}
					
					//Ordenar los líderes.
					mapaLiderGimnasio.sort(Comparator.comparing(LiderGimnasio::getNombre));
					
					//Mostrar los líderes ya ordenados.
					System.out.println("\nLideres de gimnasio ordenados:");
					for(LiderGimnasio l : mapaLiderGimnasio) {
						System.out.println(l);
					}
				}
				
			}catch(Exception e) {
				System.out.println("\nError de lectura: " +e.getMessage());
			}
		}
	}
	
	//Case 2 del submenú.
	private static void ordenarNombre(File fichPersona, ArrayList<Entrenador> mapaEntrenador, 
			ArrayList<LiderGimnasio> mapaLiderGimnasio) {
		
	}
	
	//Case 3 del submenú.
	private static void ordenarCodEntr(File fichPersona, ArrayList<Entrenador> mapaEntrenador, 
			ArrayList<LiderGimnasio> mapaLiderGimnasio) {
		
	}
	
	// ------------------- O -------------------
	
	//Case 7
	private static void eliminarPersona(File fichPersona, ArrayList<Persona> personas) {
		// TODO Auto-generated method stub
		
	}
	
	//Case 8
	private static void eliminarMasDeunaPersona(File fichPersona, ArrayList<Persona> personas) {
		// TODO Auto-generated method stub
		
	}
}