package Ej3;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

public class Ej3App {

	public static void main(String[] args) {

		// Declaración de variables
		Scanner teclado = new Scanner(System.in);
		Hashtable<String, Double> productoPrecio = new Hashtable<String, Double>();
		Hashtable<String, Integer> productoStock = new Hashtable<String, Integer>();
		String nombreProducto = "";
		
		rellenarDatosBase(productoPrecio, productoStock);
		
		do {
			nombreProducto = pedirProducto(teclado, productoPrecio, productoStock);

			if (!nombreProducto.trim().equals("-1")) {
				
				pedirPrecio(teclado, nombreProducto, productoPrecio);
				pedirCantidad(teclado, nombreProducto, productoStock);
				mostrarProductos(productoPrecio, productoStock);
			}
		} while (!nombreProducto.equals("-1"));
		
		mostrarProductos(productoPrecio, productoStock);
		teclado.close();
	}

	private final String asdfg = "";
	// Metodo para pedir un valor válido parta los parametros
	public static String pedirProducto(Scanner teclado, Hashtable<String, Double> productoPrecio, Hashtable<String, Integer> productoStock) {

		// Declaracíon de variables locales del metodo
		String valorIntroducido = "";
		String confirmacion = "";
		boolean valorValido = false;
		boolean confirmacionValida = false;
		boolean existe = false;

		do {
			System.out.print("Introduce el nombre del producto (-1 para salir): ");
			valorIntroducido = teclado.nextLine();

			if (valorIntroducido.trim().length() > 0) {

				valorValido = true;

			} else {
				System.out.println("Introduce algún nombre");
			}

			// Recorrer la lista para comprobar si el producto ya existe
			existe = productoPrecio.containsKey(valorIntroducido);
			
			// Si el producto ya existe preguntar que operacion hacer
			if (existe) {
				System.out.println(
						"El producto ya está registrado en el sistema y en el carrito, que quieres hacer? (P cambiar precio, C cambiar cantidad, O pedir otro producto)");

				// Controlar que se introduzca algo en la confirmación y que sea una valida
				do {
					confirmacion = teclado.nextLine();

					if (confirmacion.trim().toLowerCase().equals("p")) {
						confirmacionValida = true;
						pedirPrecio(teclado, valorIntroducido, productoPrecio);
						mostrarProductos(productoPrecio, productoStock);
					} else if (confirmacion.trim().toLowerCase().equals("c")) {
						confirmacionValida = true;
						pedirCantidad(teclado, valorIntroducido, productoStock);
						mostrarProductos(productoPrecio, productoStock);
					} else if (confirmacion.trim().toLowerCase().equals("o")) {
						confirmacionValida = true;
					} else {
						System.out.println("Valor no válido, (P cambiar precio, C cambiar cantidad, O pedir otro producto)");
					}

					valorValido = false;

				} while (!confirmacionValida);
			}

			existe = false;

		} while (!valorValido);

		return valorIntroducido;
	}

	// Metodo para pedir un valor válido parta los parametros
	public static void pedirPrecio(Scanner teclado, String nombreProducto, Hashtable<String, Double> productoPrecio) {

		// Declaracíon de variables locales del metodo
		String valorIntroducido = "";
		boolean valorValido = false;
		double precio = 0;

		do {
			System.out.print("Introduce el precio del producto: ");
			valorIntroducido = teclado.nextLine();

			try {

				precio = Double.parseDouble(valorIntroducido);

				if (precio >= 0) {
					valorValido = true;
				} else {
					System.out.println("El precio tiene que ser positivo \n\n");
				}
			} catch (Exception e) {
				System.out.println("Valor no válido \n\n");
			}

		} while (!valorValido);
		
		productoPrecio.put(nombreProducto, precio);
		
	}

	// Metodo para pedir un valor válido parta los parametros
	public static void pedirCantidad(Scanner teclado, String nombreProducto, Hashtable<String, Integer> productoStock) {

		// Declaracíon de variables locales del metodo
		String valorIntroducido = "";
		boolean valorValido = false;
		int cantidadIntroducida = 0;
		int nuevaCantidad = 0;
		int cantidadAnterior = 0;
		
		do {
			System.out.print("Introduce la cantidad a añadir o quitar (- delante): ");
			valorIntroducido = teclado.nextLine();

			try {

				valorValido = true;
				cantidadIntroducida = Integer.parseInt(valorIntroducido);

			} catch (Exception e) {
				System.out.println("Valor no válido \n\n");
			}

		} while (!valorValido);

		
		if(productoStock.containsKey(nombreProducto)) {
			cantidadAnterior = productoStock.get(nombreProducto);
		}

		nuevaCantidad = cantidadAnterior + cantidadIntroducida;

		if (nuevaCantidad <= 0) {
			productoStock.remove(nombreProducto);
		} else {
			productoStock.put(nombreProducto, nuevaCantidad);
		}

	}

	public static void mostrarProductos(Hashtable<String, Double> productoPrecio, Hashtable<String, Integer> productoStock) {

		System.out.println("\n\n\n");
		System.out.println(productoPrecio);
		System.out.println(productoStock);
		System.out.println("\n\n\n");
		
	}

	public static void rellenarDatosBase(Hashtable<String, Double> productoPrecio, Hashtable<String, Integer> productoStock) {

		productoPrecio.put("Manzana", 4.5);
		productoStock.put("Manzana", 10);

		productoPrecio.put("Cereza", 1.0);
		productoStock.put("Cereza", 150);

		productoPrecio.put("Melón", 9.0);
		productoStock.put("Melón", 5);

		productoPrecio.put("Sandia", 12.0);
		productoStock.put("Sandia", 5);

		productoPrecio.put("Melocotón", 6.0);
		productoStock.put("Melocotón", 20);

		productoPrecio.put("Plátano", 3.3);
		productoStock.put("Plátano", 33);

		productoPrecio.put("Pera", 4.1);
		productoStock.put("Pera", 14);

		productoPrecio.put("Tomate", 3.8);
		productoStock.put("Tomate", 24);

		productoPrecio.put("Zanahoria", 1.9);
		productoStock.put("Zanahoria", 17);

		productoPrecio.put("Patata", 5.5);
		productoStock.put("Patata", 42);

		productoPrecio.put("Lechuga", 1.6);
		productoStock.put("Lechuga", 31);
	}

}
