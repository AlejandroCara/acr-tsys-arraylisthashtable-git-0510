package Ej1;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

public class Ej1App {

	public static void main(String[] args) {

		// Declaración de variables
		Scanner teclado = new Scanner(System.in);
		String nombre = "";
		double nota = 0.0;
		double sumNotas = 0.0;
		double media = 0.0;
		ArrayList<Double> notas = new ArrayList<Double>();
		Hashtable<String, Double> alumnos = new Hashtable<String, Double>();

		do {
			nombre = pedirNombre(teclado, alumnos);
			
			if (!nombre.trim().equals("-1")) {
				do {
					nota = pedirNota(teclado);
					if (nota != -1) {
						notas.add(nota);
						sumNotas += nota;
					}
				} while (nota != -1);

				media = sumNotas / notas.size();
				alumnos.put(nombre, media);

				notas.clear();
				sumNotas = 0;
			}

		} while (!nombre.equals("-1"));

		teclado.close();
	}

	// Metodo para pedir un valor válido parta los parametros
	public static String pedirNombre(Scanner teclado, Hashtable<String, Double> alumnos) {

		// Declaracíon de variables locales del metodo
		String valorIntroducido = "";
		String confirmacion = "";
		boolean valorValido = false;
		boolean confirmacionValida = false;

		do {
			System.out.print("Introduce el nombre del alumno (-1 para salir, 0 para mostrar alumnos): ");
			valorIntroducido = teclado.nextLine();

			if (valorIntroducido.trim().length() > 0) {
				
				//Mostrar la lista de alumnos introducidos
				if (valorIntroducido.trim().equals("0")) {
					System.out.println(alumnos.toString());
				} else {
					valorValido = true;
				}
			} else {
				System.out.println("Introduce algún nombre");
			}

			//Si el alumno ya se ha introducido pedir si se quiere reintroducir los datos
			if (alumnos.containsKey(valorIntroducido)) {
				System.out.println("Ya se han registrado datos del alumno " + valorIntroducido + ". Quieres reintroducidlos? (Y/N)");
				
				//Controlar que se introduzca algo en la confirmación y que sea una valida
				do {
					confirmacion = teclado.nextLine();

					if (confirmacion.trim().toLowerCase().equals("y")) {
						confirmacionValida = true;
					} else if (confirmacion.trim().toLowerCase().equals("n")) {
						confirmacionValida = true;
						valorValido = false;
					} else {
						System.out.println("Valor no válido, Y/N");
					}

				} while (!confirmacionValida);
			}

		} while (!valorValido);

		return valorIntroducido;
	}

	// Metodo para pedir un valor válido parta los parametros
	public static double pedirNota(Scanner teclado) {

		// Declaracíon de variables locales del metodo
		String valorIntroducido = "";
		boolean valorValido = false;
		double nota = 0;

		do {
			System.out.print("Introduce la nota (-1 para finalizar): ");
			valorIntroducido = teclado.nextLine();

			try {
				nota = Double.parseDouble(valorIntroducido);

				if (nota >= 0 && nota <= 10 || nota == -1) {
					valorValido = true;
				} else {
					System.out.println("Nota no válida tiene que ser de 0 a 10 \n\n");
				}
			} catch (Exception e) {
				System.out.println("Valor no válido \n\n");
			}

		} while (!valorValido);

		return nota;
	}
}
