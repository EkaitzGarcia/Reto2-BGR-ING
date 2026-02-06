package principal;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

import excepciones.EquipoException;
import modelo.Entrenador;
import modelo.LiderGimnasio;
import modelo.Persona;
import modelo.Pokemon;
import modelo.Tipo;
import util.Util;

public class Principal {
	public static void main(String[] args) {
		File fichPersona = new File("persona.obj");
		File pokedex = new File("pokedex.obj");

		Tipo Tipo = null;
		ArrayList<Persona> Personas = new ArrayList<>();
		ArrayList<Entrenador> mapaEntrenador = new ArrayList<>();
		ArrayList<LiderGimnasio> mapaLiderGimnasio = new ArrayList<>();
		TreeMap<String, Persona> mapPersona = new TreeMap<>();
		HashMap<Integer, Entrenador> mapEntrenador = new HashMap<>();
		int opcion;
		do {
			opcion = Util.leerInt(
					"Menu:\n1. Añadir Pokemon" + "\n2. Ver Pokemon" + "\n3. Añadir Persona " + "\n4. Ver Persona "
							+ "\n5. Modificar Entranador" + "\n6. Ordenar Persona" + "\n7. Eliminar mas de una Persona"
							+ "\n8. Eliminar varios pokemon" + "\n9. Salir" + "\nElige una opción: ");

			switch (opcion) {
			case 1:
				añadirPokemon(pokedex, Tipo);
				break;
			case 2:
				verPokemon(pokedex);
				break;
			case 3:
				añadirPersona(fichPersona, pokedex, Personas);
				break;
			case 4:
				VerPersona(fichPersona, Personas);
				break;
			case 5:
				ModificarEquipo(fichPersona, pokedex, Personas);
				break;
			case 6:
				OrdenarPersona(fichPersona, Personas, mapaEntrenador, mapaLiderGimnasio, mapPersona, mapEntrenador);
				break;
			case 7:
				eliminarMasDeunaPersona(fichPersona, Personas);
				break;
			case 8:
				eliminarVariosPokemon(pokedex, fichPersona);
				break;
			case 9:
				System.out.println("SALIR");
				break;
			}
		} while (opcion != 10);
	}

	private static void añadirPokemon(File pokedex, Tipo tipo) {
		ArrayList<Pokemon> listaPokemon = new ArrayList<>();
		if (pokedex.exists()) {
			// Leer todos los Pokémon existentes
			try (ObjectInputStream leerfich = new ObjectInputStream(new FileInputStream(pokedex))) {
				while (true) {
					Pokemon p = (Pokemon) leerfich.readObject();
					listaPokemon.add(p);
				}
			} catch (EOFException e) {
				// fin de fichero
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// Usar Iterator para encontrar el máximo pokedex
		int max = 0;
		do {
			Iterator<Pokemon> it = listaPokemon.iterator();
			while (it.hasNext()) {
				Pokemon p = it.next();
				if (p.getPokedex() > max) {
					max = p.getPokedex();
				}

			}
			max += 1;
			String nPokemon = Util.introducirCadena("Dime el nombre de tu Pokemon");

			// Mostrar todas las opciones de Tipo
			System.out.println("Tipos de Pokémon disponibles:");
			for (Tipo t : Tipo.values()) {
				System.out.println("- " + t);
			}
			Tipo tipoEnum;
			String tPokemon = Util.introducirCadena("Introduce el tipo del Pokémon:");

			// Validar el tipo
			
			try {
				tipoEnum = Tipo.valueOf(tPokemon.toUpperCase());
			
			} catch (IllegalArgumentException e) {
				System.out.println("Tipo no válido");
				return;
			}

			// Crear el Pokémon
			Pokemon p = new Pokemon(nPokemon, tipoEnum);
			p.setPokedex(max);
			System.out.println("El Pokémon: " + nPokemon + " con tipo: " + tipoEnum + " fue añadido correctamente");

			// Guardar en el fichero
			try {
				ObjectOutputStream escribirfich;
				if (!pokedex.exists() || pokedex.length() == 0) {
					escribirfich = new ObjectOutputStream(new FileOutputStream(pokedex));
				} else {
					escribirfich = new SinCabeceraObjectOutputStream(new FileOutputStream(pokedex, true));
				}

				escribirfich.writeObject(p);
				escribirfich.close();

			} catch (Exception e) {
				System.out.println("Error al guardar el Pokémon");
				e.printStackTrace();
			}
		} while (Util.introducirCadena("¿Quieres añadir otro Pokémon? (s/n)").equalsIgnoreCase("s"));

	}
	

	private static void verPokemon(File pokedex) {

		try (ObjectInputStream personaIStream = new ObjectInputStream(new FileInputStream(pokedex))) {
			try {
				while (true) {
					Pokemon pokemon = (Pokemon) personaIStream.readObject();
					System.out.println(pokemon.toString());
				}
			} catch (EOFException excepcion) {

			}

		} catch (Exception excepcion) {
			System.out.println();
		}

	}

	private static void añadirPersona(File fichPersona, File pokedex, ArrayList<Persona> personas) {
		int max = 0;

		ArrayList<Pokemon> listaPokemon = new ArrayList<>();
		// Leer Pokémon existentes
		if (pokedex.exists()) {
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(pokedex))) {
				while (true) {
					Pokemon p = (Pokemon) ois.readObject();
					listaPokemon.add(p);
				}
			} catch (EOFException e) {
				// fin fichero
			} catch (Exception e) {
				System.out.println("Error al leer Pokémon");
				return;
			}
		}
		if (listaPokemon.isEmpty()) {
			System.out.println("No hay Pokémon disponibles. Añade Pokémon primero.");
			return;
		}
		// Leer personas existentes para controlar Pokémon en us
		if (fichPersona.exists() && fichPersona.length() > 0) {
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichPersona))) {
				while (true) {
					Persona p = (Persona) ois.readObject();
					personas.add(p);
					p.visualizar();
				}
			} catch (EOFException e) {
				// normal end of file
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println(fichPersona.getName() + " El fichero no existe o esta vacio.");
		}
		Iterator<Persona> it = personas.iterator();
		while (it.hasNext()) {
			Persona p = it.next();
			if (p.getId() > max) {
				max = p.getId();
			}
		}
		max += 1;
		int tipoPersona = Util.leerInt("¿Qué deseas añadir?\n1. Entrenador\n2. Líder de Gimnasio\nOpción: ");
		String nombre = Util.introducirCadena("Introduce nombre: ");
		LocalDate fechaNac;
		do {
			System.out.println("Introduce la fecha de nacimiento: (dd/mm/aaaa)");
			fechaNac = Util.leerFechaDMA();
			if (fechaNac.isAfter(LocalDate.now())) {
				System.out.println("La fecha de nacimiento no puede ser futura. Inténtalo de nuevo.");
			}
		} while (fechaNac.isAfter(LocalDate.now()));
		try {
			if (tipoPersona == 1) { // ENTRENADOR
				String procedencia = Util.introducirCadena("Introduce procedencia: ");
				HashMap<String, Pokemon> equipo = new HashMap<>();
				String seguir;
				do {
					if (equipo.size() > 6) {
						throw new EquipoException();
					}

					System.out.println("Pokémon disponibles:");
					for (Pokemon p : listaPokemon) {
						if (!estaEnUso(p, personas)) {
							System.out.println(p.getPokedex() + " - " + p.getNombre() + " (" + p.getTipo() + ")");
						}
					}
					int pokedex1 = Util.leerInt("Introduce número de Pokédex: ");
					boolean encontrado = false;
					for (Pokemon p : listaPokemon) {
						if (p.getPokedex() == pokedex1) {
							if (estaEnUso(p, personas)) {
								System.out.println("Este Pokémon ya está en otro equipo, elige otro.");
								break;
							}
							equipo.put(String.valueOf(p.getPokedex()), p);
							System.out.println("Pokémon añadido al equipo");
							encontrado = true;
							break;
						}
					}
					if (!encontrado) {
						System.out.println("No se encontró Pokémon con ese número de Pokédex o está en uso.");
					}
					seguir = Util.introducirCadena("¿Añadir otro Pokémon? (s/n)");
				} while (seguir.equalsIgnoreCase("s"));
				Entrenador entrenador = new Entrenador(nombre, fechaNac, max, procedencia, equipo);
				// Guardar en fichero
				try {
					ObjectOutputStream oos;
					if (!fichPersona.exists() || fichPersona.length() == 0) {
						oos = new ObjectOutputStream(new FileOutputStream(fichPersona));
					} else {
						oos = new SinCabeceraObjectOutputStream(new FileOutputStream(fichPersona, true));
					}
					oos.writeObject(entrenador);
					oos.close();
				} catch (Exception e) {
					System.out.println("Error al guardar el Entrenador");
					e.printStackTrace();
				}
				System.out.println("Entrenador añadido correctamente");
			} else if (tipoPersona == 2) { // LÍDER DE GIMNASIO
				System.out.println("Tipos disponibles:");
				for (Tipo t : Tipo.values()) {
					System.out.println("- " + t);
				}
				Tipo tipo = Tipo.valueOf(Util.introducirCadena("Introduce tipo del líder: ").toUpperCase());
				String medalla = Util.introducirCadena("Introduce nombre de la medalla: ");
				HashMap<String, Pokemon> equipoMap = new HashMap<>();
				String seguir;
				do {
					if (equipoMap.size() == 6) {
						System.out.println("El equipo ya tiene 6 Pokémon");
						break;
					}
					System.out.println("Pokémon disponibles del tipo " + tipo + ":");
					for (Pokemon p : listaPokemon) {
						if (!estaEnUso(p, personas) && p.getTipo() == tipo) {
							System.out.println(p.getPokedex() + " - " + p.getNombre());
						}
					}
					int idPoke = Util.leerInt("Introduce número de Pokédex: ");
					boolean encontrado = false;
					for (Pokemon p : listaPokemon) {
						if (p.getPokedex() == idPoke) {
							if (p.getTipo() != tipo) {
								System.out.println("El Pokémon no es del tipo del líder, no se añade.");
								break;
							}
							if (estaEnUso(p, personas)) {
								System.out.println("Este Pokémon ya está en otro equipo, elige otro.");
								break;
							}
							equipoMap.put(String.valueOf(p.getPokedex()), p);
							System.out.println("Pokémon añadido al equipo");
							encontrado = true;
							break;
						}
					}
					if (!encontrado) {
						System.out.println("No se encontró Pokémon con ese número de Pokédex o está en uso.");
					}
					System.out.println("¿Añadir otro Pokémon? (s/n)");
					seguir = Util.introducirCadena();
				} while (seguir.equalsIgnoreCase("s"));
				Persona lider = new LiderGimnasio(nombre, fechaNac, max, tipo, medalla, equipoMap);
				// Guardar en fichero
				try {
					ObjectOutputStream oos;
					if (!fichPersona.exists() || fichPersona.length() == 0) {
						oos = new ObjectOutputStream(new FileOutputStream(fichPersona));
					} else {
						oos = new SinCabeceraObjectOutputStream(new FileOutputStream(fichPersona, true));
					}
					oos.writeObject(lider);
					oos.close();
				} catch (Exception e) {
					System.out.println("Error al guardar el Líder");
					e.printStackTrace();
				}
				System.out.println("Líder de Gimnasio añadido correctamente");
			} else {
				System.out.println("Opción no válida");
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}

	}

	private static boolean estaEnUso(Pokemon p, ArrayList<Persona> personas) {
		if (p == null || personas == null) {
			return false;
		}
		for (Persona pe : personas) {
			if (pe instanceof Entrenador) {
				Entrenador e = (Entrenador) pe;
				if (e.getEquipo() != null) {
					for (Pokemon pk : e.getEquipo().values()) {
						if (pk != null && pk.getPokedex() == p.getPokedex()) {
							return true;
						}
					}
				}
			} else if (pe instanceof LiderGimnasio) {
				LiderGimnasio l = (LiderGimnasio) pe;
				if (l.getEquipoLider() != null && l.getEquipoLider().values() != null) {
					for (Pokemon pk : l.getEquipoLider().values()) {
						if (pk != null && pk.getPokedex() == p.getPokedex()) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	private static void VerPersona(File fichPersona, ArrayList<Persona> personas) {

		try (ObjectInputStream personaIStream = new ObjectInputStream(new FileInputStream(fichPersona))) {
			try {
				while (true) {
					Persona Persona = (Persona) personaIStream.readObject();
					Persona.visualizar();
				}
			} catch (EOFException excepcion) {

			}

		} catch (Exception excepcion) {
			System.out.println();
		}

	}

	private static void ModificarEquipo(File fichPersona, File pokedex, ArrayList<Persona> personas) {
		// 1️ Limpiar lista antes de cargar
		personas.clear();
		// 2️ Leer todos los entrenadores del fichero de forma segura
		if (fichPersona.exists() && fichPersona.length() > 0) {
			try (ObjectInputStream leerfich = new ObjectInputStream(new FileInputStream(fichPersona))) {
				while (true) {
					personas.add((Persona) leerfich.readObject());
				}
			} catch (EOFException e) {
				// fin de fichero correcto
			} catch (Exception e) {
				System.out.println("Error leyendo fichero de entrenadores");
				e.printStackTrace();
				return;
			}
		} else {
			System.out.println("No hay entrenadores guardados aún.");
			return; // salir si no hay entrenadores
		}
		// 3️ Buscar entrenador por ID
		int idBuscar = Util.leerInt("Introduce el ID del entrenador");
		Entrenador entrenador = null;
		for (Persona p : personas) {
			if (p instanceof Entrenador && p.getId() == idBuscar) {
				entrenador = (Entrenador) p;
				break;
			}
		}
		if (entrenador == null) {
			System.out.println("Entrenador no encontrado");
			return;
		}
		// 4️ Submenú añadir/eliminar Pokémon
		int opcion;
		do {
			opcion = Util.leerInt(
					"MODIFICAR PERSONA\n" + "1. Eliminar Pokémon del equipo\n" + "2. Añadir Pokémon al equipo\n"
							+ "3. Modificar Nombre\n" + "4. Modificar Procedencia\n" + "5. Salir");
			switch (opcion) {
			case 1: // ELIMINAR
				if (entrenador.getEquipo().isEmpty()) {
					System.out.println("El equipo está vacío");
				} else {
					System.out.println("Pokémon del equipo:");
					int i = 1;
					for (String key : entrenador.getEquipo().keySet()) {
						System.out.println(i + ". " + key);
						i++;
					}
					String nombreEliminar = Util.introducirCadena("Introduce el nombre del Pokémon a eliminar");
					if (entrenador.getEquipo().containsKey(nombreEliminar)) {
						entrenador.getEquipo().remove(nombreEliminar);
						System.out.println("Pokémon eliminado");
					} else {
						System.out.println("Nombre incorrecto");
					}
				}
				break;
			case 2: // AÑADIR
				if (entrenador.getEquipo().size() >= 6) {
					System.out.println("El equipo ya tiene 6 Pokémon");
				} else {
					ArrayList<Pokemon> lista = new ArrayList<>();
					if (pokedex.exists() && pokedex.length() > 0) {
						try (ObjectInputStream leerfich = new ObjectInputStream(new FileInputStream(pokedex))) {
							while (true) {
								lista.add((Pokemon) leerfich.readObject());
							}
						} catch (EOFException e) {
							// fin de fichero correcto
						} catch (Exception e) {
							System.out.println("Error leyendo la Pokédex");
							break;
						}
					} else {
						System.out.println("No hay Pokémon en la Pokédex");
						break;
					}
					for (int i = 0; i < lista.size(); i++) {
						System.out.println((i + 1) + ". " + lista.get(i).getNombre());
					}
					String nombrePokemon = Util.introducirCadena("Introduce el nombre del Pokémon a añadir");
					Pokemon p = null;
					for (Pokemon pk : lista) {
						if (pk.getNombre().equalsIgnoreCase(nombrePokemon)) {
							p = pk;
							break;
						}
					}
					if (p != null) {
						entrenador.getEquipo().put(p.getNombre(), p);
						System.out.println("Pokémon añadido");
					} else {
						System.out.println("Pokémon no encontrado en la Pokédex");
					}
				}
				break;
			case 3: // MODIFICAR DATOS
				String nuevoNombre = Util.introducirCadena(
						"Introduce el nuevo nombre del entrenador (actual: " + entrenador.getNombre() + "): ");
				entrenador.setNombre(nuevoNombre);
				System.out.println("Nombre actualizado");
				break;
			case 4: // CAMBIAR PROCEDENCIA
				String nuevaProcedencia = Util.introducirCadena(
						"Introduce la nueva procedencia (actual: " + entrenador.getProcedencia() + "): ");
				entrenador.setProcedencia(nuevaProcedencia);
				System.out.println("Procedencia actualizada");
				break;
			case 5: // SALIR
				break;
			default:
				System.out.println("Opción incorrecta");
			}
		} while (opcion != 5);
		// 5️ Guardar cambios sobrescribiendo el fichero
		try (ObjectOutputStream escribirfich = new ObjectOutputStream(new FileOutputStream(fichPersona))) {
			for (Persona p : personas) {
				escribirfich.writeObject(p);
			}
			System.out.println("Cambios guardados correctamente");
		} catch (IOException e) {
			System.out.println("Error guardando fichero");
			e.printStackTrace();
		}
	}

	private static void OrdenarPersona(File fichPersona, ArrayList<Persona> personas,
			ArrayList<Entrenador> mapaEntrenador, ArrayList<LiderGimnasio> mapaLiderGimnasio,
			TreeMap<String, Persona> mapPersona, HashMap<Integer, Entrenador> mapEntrenador) {
		// TODO Auto-generated method stub
		int opc;
		do {
			System.out.println("\n¿Como quieres ordenar las personas?" + "\n1. Por Rol." + "\n2. Por Nombre."
					+ "\n3. Por Código de entrenador." + "\n4. Volver atras.");

			opc = Util.leerInt("");

			switch (opc) {
			case 1:
				ordenarRol(fichPersona, mapaEntrenador, mapaLiderGimnasio);
				break;
			case 2:
				ordenarNombre(fichPersona, mapPersona);
				break;
			case 3:
				ordenarCodEntr(fichPersona, mapEntrenador);
				break;
			case 4:
				System.out.println("\nVolviendo atras...");
				break;
			}
		} while (opc != 4);
	}


	private static void ordenarRol(File fichPersona, ArrayList<Entrenador> mapaEntrenador,
			ArrayList<LiderGimnasio> mapaLiderGimnasio) {
		fichPersona = new File("persona.obj");

		if (!fichPersona.exists()) {
			System.out.println("No existe el fichero persona.obj");
			return;
		}

		String elec = Util.introducirCadena("¿Qué rol quieres ordenar? Entrenador | Lider");

		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichPersona))) {

			while (true) {
				try {
					Object obj = ois.readObject();

					// Guardar según el tipo real
					if (obj instanceof LiderGimnasio) {
						mapaLiderGimnasio.add((LiderGimnasio) obj);
					} else if (obj instanceof Entrenador) {
						mapaEntrenador.add((Entrenador) obj);
					}

				} catch (EOFException eof) {
					break;
				}
			}

		} catch (Exception e) {
			System.out.println("Error de lectura: " + e.getMessage());
			return;
		}

		// ORDENAR Y MOSTRAR SEGÚN ELECCIÓN
		if (elec.equalsIgnoreCase("Entrenador")) {

			if (mapaEntrenador.isEmpty()) {
				System.out.println("No hay entrenadores para ordenar.");
				return;
			}
			mapaEntrenador.sort(Comparator.comparing(Entrenador::getNombre));

			System.out.println("\nEntrenadores ordenados:");
			for (Entrenador e : mapaEntrenador) {
				System.out.println(e);
			}

		} else if (elec.equalsIgnoreCase("Lider")) {

			if (mapaLiderGimnasio.isEmpty()) {
				System.out.println("No hay líderes de gimnasio para ordenar.");
				return;
			}
			mapaLiderGimnasio.sort(Comparator.comparing(LiderGimnasio::getNombre));

			System.out.println("\nLíderes ordenados:");
			for (LiderGimnasio l : mapaLiderGimnasio) {
				System.out.println(l);
			}
		}
	}


	private static void ordenarNombre(File fichPersona, TreeMap<String, Persona> mapPersona) {
		// Intentar hacerlo con TreeMap.
		fichPersona = new File("persona.obj");

		if (!fichPersona.exists()) {
			System.out.println("\nNo existe el fichero 'persona.obj'");
		} else {
			try (FileInputStream fichIStream = new FileInputStream(fichPersona);
					ObjectInputStream personaIStream = new ObjectInputStream(fichIStream)) {

				while (true) {
					try {
						Persona per = (Persona) personaIStream.readObject();
						mapPersona.put(per.getNombre(), per);
					} catch (EOFException exc) {
						break;
					}
				}

				System.out.println("\nPersonas ordenadas por Nombre:");
				for (Persona p : mapPersona.values()) {
					System.out.println(p);
				}

			} catch (Exception e) {
				System.out.println("\nError de lectura: " + e.getMessage());
			}
		}
	}

	private static void ordenarCodEntr(File fichPersona, HashMap<Integer, Entrenador> mapEntrenador) {
		// Intentar hacerlo con HashMap.
		fichPersona = new File("persona.obj");

		if (!fichPersona.exists()) {
			System.out.println("\nNo existe el fichero 'persona.obj'");
		} else {
			try (FileInputStream fichIStream = new FileInputStream(fichPersona);
					ObjectInputStream entrenadorIStream = new ObjectInputStream(fichIStream)) {

				while (true) {
					try {
						Entrenador entr = (Entrenador) entrenadorIStream.readObject();
						mapEntrenador.put(entr.getId(), entr);
					} catch (EOFException eof) {
						break;
					}
				}

				// Ordenar el HashMap
				mapEntrenador.values().stream().sorted(Comparator.comparing(Entrenador::getNombre))
						.forEach(System.out::println);

				// Mostrar el HashMap ordenado
				System.out.println("\nPersonas ordenadas por");
				for (Entrenador e : mapEntrenador.values()) {
					System.out.println(e);
				}
			} catch (Exception e) {
				System.out.println("\nError de lectura: " + e.getMessage());
			}
		}

	}

	private static void eliminarMasDeunaPersona(File fichPersona, ArrayList<Persona> personas) {

		if (!fichPersona.exists()) {
			System.out.println("No hay Personas registrados en la Base de datos.");
			return;
		}
		ArrayList<String> listaNombresBorrar = new ArrayList<>();
		String continuar;
		do {
			String nombre = Util.introducirCadena("Introduce el nombre de la persona que quieres eliminar:").trim()
					.toUpperCase();
			if (!listaNombresBorrar.contains(nombre)) {
				listaNombresBorrar.add(nombre);
				System.out.println(nombre + " añadido a la lista de eliminación.");
			} else {
				System.out.println("Ese nombre ya estaba en la lista.");
			}
			continuar = Util.introducirCadena("¿Quieres añadir otra Persona para eliminar? (S/N)");
		} while (continuar.equalsIgnoreCase("s"));
		// 2️⃣ Mostrar lista
		System.out.println("\n--- LISTA DE PERSONAS A ELIMINAR ---");
		for (String n : listaNombresBorrar) {
			System.out.println("- " + n);
		}
		// 3️⃣ Confirmación
		String confirmar;
		confirmar = Util.introducirCadena(
				"¿Estás seguro de que quieres eliminar estas " + listaNombresBorrar.size() + " Personas? (S/N)");

		if (confirmar.equalsIgnoreCase("N")) {
			System.out.println("Operación cancelada. No se ha borrado nada.");
			return;
		}
		// Leer Pokémon que se quedan
		ArrayList<Persona> PersonaRestantes = new ArrayList<>();
		boolean algunBorrado = false;
		try (ObjectInputStream leerfich = new ObjectInputStream(new FileInputStream(fichPersona))) {
			while (true) {
				Persona p = (Persona) leerfich.readObject();
				if (!listaNombresBorrar.contains(p.getNombre().toUpperCase())) {
					PersonaRestantes.add(p);
				} else {
					System.out.println("Eliminando: " + p.getNombre());
					algunBorrado = true;
				}
			}
		} catch (EOFException e) {
			// fin correcto
		} catch (Exception e) {
			System.out.println("Error leyendo la Base de datos.");
			e.printStackTrace();
			return;
		}
		if (!algunBorrado) {
			System.out.println("No se encontró ninguna persona con ese nombre.");
			return;
		}
		// REORDENAR La base de datos (1,2,3...)
		int contador = 1;
		for (Persona p : PersonaRestantes) {
			p.setId(contador);
			contador++;
		}
		// Sobrescribir fichero
		try (ObjectOutputStream escribirfich = new ObjectOutputStream(new FileOutputStream(fichPersona))) {
			for (Persona p : PersonaRestantes) {
				escribirfich.writeObject(p);
			}
			System.out.println("Base de datos actualizada correctamente.");
		} catch (IOException e) {
			System.out.println("Error escribiendo la Base de datos.");
			e.printStackTrace();
		}

	}

	private static void eliminarVariosPokemon(File pokedex, File fichPersona) {
		if (!pokedex.exists() || pokedex.length() == 0) {
			System.out.println("No hay Pokémon registrados en la Pokedex.");
			return;
		}
		
		//  Recoger nombres de Pokémon a eliminar
		ArrayList<String> listaNombresBorrar = new ArrayList<>();
		String continuar;
		do {
			String nombre = Util.introducirCadena("Introduce el nombre del Pokémon que quieres eliminar:").trim()
					.toUpperCase();
			if (!listaNombresBorrar.contains(nombre)) {
				listaNombresBorrar.add(nombre);
				System.out.println(nombre + " añadido a la lista de eliminación.");
			} else {
				System.out.println("Ese nombre ya estaba en la lista.");
			}
			continuar = Util.introducirCadena("¿Quieres añadir otro Pokémon para eliminar? (S/N)");
		} while (continuar.equalsIgnoreCase("s"));
		
		//  Mostrar lista
		System.out.println("\n--- LISTA DE POKÉMON A ELIMINAR ---");
		for (String n : listaNombresBorrar) {
			System.out.println("- " + n);
		}
		
		//  Confirmación
		String confirmar = Util.introducirCadena(
				"¿Estás seguro de que quieres eliminar estos " + listaNombresBorrar.size() + " Pokémon? (S/N)");

		if (confirmar.equalsIgnoreCase("N")) {
			System.out.println("Operación cancelada. No se ha borrado nada.");
			return;
		}
		
		//  Leer Pokémon que se quedan en la Pokédex
		ArrayList<Pokemon> pokemonRestantes = new ArrayList<>();
		ArrayList<Integer> pokedexEliminados = new ArrayList<>(); // IDs de Pokémon eliminados
		boolean algunBorrado = false;
		
		try (ObjectInputStream leerfich = new ObjectInputStream(new FileInputStream(pokedex))) {
			while (true) {
				Pokemon p = (Pokemon) leerfich.readObject();
				if (!listaNombresBorrar.contains(p.getNombre().toUpperCase())) {
					pokemonRestantes.add(p);
				} else {
					System.out.println("Eliminando de Pokédex: " + p.getNombre());
					pokedexEliminados.add(p.getPokedex()); // Guardar ID para eliminar de equipos
					algunBorrado = true;
				}
			}
		} catch (EOFException e) {
			// fin correcto
		} catch (Exception e) {
			System.out.println("Error leyendo la Pokedex.");
			e.printStackTrace();
			return;
		}
		
		if (!algunBorrado) {
			System.out.println("No se encontró ningún Pokémon con esos nombres.");
			return;
		}
		
		//  ACTUALIZAR EQUIPOS DE ENTRENADORES Y LÍDERES
		ArrayList<Persona> personasActualizadas = new ArrayList<>();
		
		if (fichPersona.exists() && fichPersona.length() > 0) {
			try (ObjectInputStream leerfich = new ObjectInputStream(new FileInputStream(fichPersona))) {
				while (true) {
					Persona persona = (Persona) leerfich.readObject();
					
					// Procesar según tipo de persona
					if (persona instanceof Entrenador) {
						Entrenador entrenador = (Entrenador) persona;
						HashMap<String, Pokemon> equipoActualizado = new HashMap<>();
						
						// Filtrar Pokémon eliminados del equipo
						for (String key : entrenador.getEquipo().keySet()) {
							Pokemon pokemon = entrenador.getEquipo().get(key);
							if (!pokedexEliminados.contains(pokemon.getPokedex())) {
								equipoActualizado.put(key, pokemon);
							} else {
								System.out.println("Eliminando " + pokemon.getNombre() + 
										" del equipo de " + entrenador.getNombre());
							}
						}
						entrenador.setEquipo(equipoActualizado);
						
					} else if (persona instanceof LiderGimnasio) {
						LiderGimnasio lider = (LiderGimnasio) persona;
						HashMap<String, Pokemon> equipoActualizado = new HashMap<>();
						
						// Filtrar Pokémon eliminados del equipo
						for (String key : lider.getEquipoLider().keySet()) {
							Pokemon pokemon = lider.getEquipoLider().get(key);
							if (!pokedexEliminados.contains(pokemon.getPokedex())) {
								equipoActualizado.put(key, pokemon);
							} else {
								System.out.println("Eliminando " + pokemon.getNombre() + 
										" del equipo del líder " + lider.getNombre());
							}
						}
						lider.setEquipoLider(equipoActualizado);
					}
					
					personasActualizadas.add(persona);
				}
			} catch (EOFException e) {
				// fin correcto
			} catch (Exception e) {
				System.out.println("Error leyendo fichero de personas.");
				e.printStackTrace();
			}
			
			// Guardar fichero de personas actualizado
			try (ObjectOutputStream escribirfich = new ObjectOutputStream(new FileOutputStream(fichPersona))) {
				for (Persona p : personasActualizadas) {
					escribirfich.writeObject(p);
				}
				System.out.println("Equipos actualizados correctamente.");
			} catch (IOException e) {
				System.out.println("Error escribiendo fichero de personas.");
				e.printStackTrace();
			}
		}
		
		//  REORDENAR POKÉDEX (1,2,3...)
		int contador = 1;
		for (Pokemon p : pokemonRestantes) {
			p.setPokedex(contador);
			contador++;
		}
		
		// Sobrescribir fichero de Pokédex
		try (ObjectOutputStream escribirfich = new ObjectOutputStream(new FileOutputStream(pokedex))) {
			for (Pokemon p : pokemonRestantes) {
				escribirfich.writeObject(p);
			}
			System.out.println("Pokedex actualizada correctamente.");
			System.out.println("\n Operación completada: Pokémon eliminados de la Pokédex y de todos los equipos.");
		} catch (IOException e) {
			System.out.println("Error escribiendo la Pokedex.");
			e.printStackTrace();
		}
	}
}
