**Sistema de Gestión Pokémon (Java)**
El objetivo de la práctica es desarrollar una aplicación de consola en Java que permita gestionar un registro de personajes (Entrenadores y Líderes) y sus Pokémon, utilizando Programación Orientada a Objetos (POO) y persistencia en ficheros.
1. Estructura de Datos (Clases)
	Deberás implementar el siguiente modelo de clases:
	- Clase Pokémon: Representa a las criaturas.
		- Atributos: nombre, tipo (uso de Enum) y pokedex (identificador vinculado a un fichero de 	datos).
	- Clase Persona (Abstracta): Base para los personajes del mundo.
		- Atributos comunes: nombre, fecha_nac, id, Visualizar(), Sacaredad(), agregarPokemon().
	- Clase Entrenador (Hereda de Persona):
		- Atributos específicos: equipo (Lista de Pokémon) y procedencia, maximo 6 exception.
	- Clase Líder de Gimnasio (Hereda de Persona):
		- Atributos específicos: tipo (Enum), medalla (nombre de la medalla que entrega) y equipoLider (Lista de Pokémon) solo pueden tener pokemon del mismo tipo, maximo 6 exception.

2. Persistencia
	El sistema debe ser capaz de volcar y leer la información de objetos en ficheros (recomendado Personas.txt y Pokemon.txt) para que los datos no se pierdan al cerrar el programa.

3. Menú de Usuario:
   
El programa presentará un menú interactivo con las siguientes funcionalidades:
- Gestión de Personas:
	- Añadir Pokemon Solo fichero
		Tendrá que pedirle al usuario, el nombre del pokemon y el tipo del pokemon
	- Visualizar Pokemon solo con fichero
	- Añadir Persona Estructura de datos:
   		Registro de nuevos Entrenadores o Líderes y enlazar con los pokemon.
		Al usuario le pedirás que quiere añadir si un entrenador o un líder de gym
		- El entrenador le pedimos que añade el id, el nombre, la fecha nac, Procedencia y el equipo que tiene máximo de 6 pokemon pero puede dejar huecos libres
		- El líder le pedimos que añada el id, el nombre, la fecha nac , tipo , medalla que da y quipo que tiene máximo de 6 pokémon pero puede dejar huecos libres y solo puede ser del tipo del lider.	
4. Visualizar Personas Estructura de datos: Listado completo de todos los registros.

5. Modificar Personas (Solo el equipo) SUBMENUS: Editar datos de un registro existente mediante su ID, y visualización esto implica poder eliminar los pokemon del equipo o añadir alguno más (si tiene 6 no puede añadir más) repetir hasta darle a la opción de salir.
   
6. Ordenar Personas: Submenú de ordenación:
	6.1 Por Rol (Entrenador y Líder).
	6.2 Por Nombre (Alfabético).
	6.3 Por Código de Entrenador.
7. Eliminar Persona por id.
8. Eliminar mas de una Personas por id
9. Eliminar Pokemon por código de pokedex
