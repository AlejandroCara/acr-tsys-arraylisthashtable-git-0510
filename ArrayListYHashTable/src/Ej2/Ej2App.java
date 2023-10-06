package Ej2;

import java.util.ArrayList;
import java.util.Scanner;

public class Ej2App {
	
	public static final double IVA = 0.21;

	public static void main(String[] args) {

		// Declaración de variables
		Scanner teclado = new Scanner(System.in);
		ArrayList<ArrayList<String>> carrito = new ArrayList<ArrayList<String>>();
		ArrayList<String> producto = new ArrayList<String>();
		String nombreProducto = "";
		double precioPreIVA = 0;
		int totalProductos = 0;
		double cambio = 0;

		do {
			nombreProducto = pedirProducto(teclado, carrito);

			if (!nombreProducto.trim().equals("-1")) {
				
				producto.add(nombreProducto);
				carrito.add((ArrayList<String>) producto.clone());
				pedirPrecio(teclado, nombreProducto, carrito);
				pedirCantidad(teclado, nombreProducto, carrito);
			}
			producto.clear();
		} while (!nombreProducto.equals("-1"));
		
		
		precioPreIVA = calcularPrecioTotal(carrito);
		totalProductos = calcularCantidadProductosTotal(carrito);
		cambio = calcularCambio(teclado, precioPreIVA);
		
		mostrarResultado(precioPreIVA, totalProductos, cambio);
	}

	// Metodo para pedir un valor válido parta los parametros
	public static String pedirProducto(Scanner teclado, ArrayList<ArrayList<String>> carrito) {

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
			
			//Recorrer la lista para comprobar si el producto ya existe
			for (ArrayList<String> producto : carrito) {
				if(!existe){
					existe = producto.get(0).equals(valorIntroducido);
				}
			}
			
			// Si el producto ya existe preguntar que operacion hacer
			if (existe) {
				System.out.println("El producto ya está registrado en el sistema y en el carrito, que quieres hacer? (P cambiar precio, C cambiar cantidad, O pedir otro producto)");

				// Controlar que se introduzca algo en la confirmación y que sea una valida
				do {
					confirmacion = teclado.nextLine();

					if (confirmacion.trim().toLowerCase().equals("p")) {
						confirmacionValida = true;
						pedirPrecio(teclado, valorIntroducido, carrito);
						mostrarProductos(carrito);
					} else if (confirmacion.trim().toLowerCase().equals("c")) {
						confirmacionValida = true;
						pedirCantidad(teclado, valorIntroducido, carrito);
						mostrarProductos(carrito);
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
	public static void pedirPrecio(Scanner teclado, String nombreProducto, ArrayList<ArrayList<String>> carrito) {

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

		for (ArrayList<String> producto : carrito) {
			if(producto.get(0).equals(nombreProducto)) {
				if(producto.size() == 3) {
					producto.set(1, valorIntroducido);
				} else {
					producto.add(valorIntroducido);
				}
			}
		}
	}

	// Metodo para pedir un valor válido parta los parametros
	public static void pedirCantidad(Scanner teclado, String nombreProducto, ArrayList<ArrayList<String>> carrito) {

		// Declaracíon de variables locales del metodo
		String valorIntroducido = "";
		boolean valorValido = false;
		int cantidadIntroducida = 0;
		int nuevaCantidad = 0;
		int cantidadAnterior = 0;
		int indiceProducto = 0;
		boolean existe = false;

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
		
		for (ArrayList<String> producto : carrito) {
			if(producto.get(0).equals(nombreProducto)) {
				if(producto.size() == 3) {
					cantidadAnterior = Integer.parseInt(producto.get(2));
				}
				existe = true;
			} else if(!existe){
				indiceProducto++;
			}
		}
		
		nuevaCantidad = cantidadAnterior + cantidadIntroducida;
		
		if(nuevaCantidad <= 0) {
			carrito.remove(indiceProducto);
		} else {
			if(carrito.get(indiceProducto).size() == 3) {
				carrito.get(indiceProducto).set(2, String.valueOf(nuevaCantidad));
			} else {
				carrito.get(indiceProducto).add(String.valueOf(nuevaCantidad));
			}
		}
		
	}
	
	public static void mostrarProductos(ArrayList<ArrayList<String>> carrito) {
		System.out.println("\n\n\n");
		for (ArrayList<String> productoCarrito : carrito) {
			System.out.println("Nombre: " + productoCarrito.get(0));
			System.out.println("Precio: " + productoCarrito.get(1));
			System.out.println("Cantidad: " + productoCarrito.get(2) + "\n\n\n");		
		}
	}
	
	public static double calcularPrecioTotal(ArrayList<ArrayList<String>> carrito) {
		
		//Declaración de variables locales del metodo
		double total = 0;
		
		for (ArrayList<String> producto : carrito) {
			total += (Double.parseDouble(producto.get(1)) * Integer.parseInt(producto.get(2)));
		}
		
		return total;
	}
	
	public static int calcularCantidadProductosTotal(ArrayList<ArrayList<String>> carrito) {
		//Declaración de variables locales del metodo
		int total = 0;
		
		for (ArrayList<String> producto : carrito) {
			total += Integer.parseInt(producto.get(2));
		}
		
		return total;
	}
	
	// Metodo para pedir un valor válido parta los parametros
	public static double calcularCambio(Scanner teclado, double precioPreIVA) {

		// Declaracíon de variables locales del metodo
		String valorIntroducido = "";
		boolean valorValido = false;
		int pago = 0;
		double precioConIVA = (precioPreIVA + (precioPreIVA * IVA));

		do {
			System.out.print("Cuanto ha pagado el cliente: ");
			valorIntroducido = teclado.nextLine();

			try {

				pago = Integer.parseInt(valorIntroducido);

				if (pago >= precioConIVA) {
					valorValido = true;
				} else {
					System.out.println("El precio tiene que ser positivo y mayor que el precio total" +precioConIVA+ " \n\n");
				}
			} catch (Exception e) {
				System.out.println("Valor no válido \n\n");
			}

		} while (!valorValido);

		return  pago - precioConIVA;
	}
	
	public static void mostrarResultado(double precioPreIVA, int totalProductos, double cambio) {
		
		double precioConIVA = (precioPreIVA + (precioPreIVA * IVA));
		
		System.out.println("IVA aplicado: " + (IVA*100) + "%");
		System.out.println("Precio total bruto: " + precioPreIVA + "€");
		System.out.println("Precio total + IVA: " + precioConIVA + "€");
		System.out.println("Numero de articulos comprados: " + totalProductos + "uds");
		System.out.println("Cantidad pagada: " + (cambio + precioConIVA) + "€");
		System.out.println("Cambio: " + cambio + "€");
	}
}
