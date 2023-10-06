package Ej4;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

public class Ej4App {
	
	public final static double IVA = 0.21;
		
	public static void main(String[] args) {

		// Declaración de variables
		Scanner teclado = new Scanner(System.in);
		Hashtable<String, Double> productoPrecio = new Hashtable<String, Double>();
		Hashtable<String, Integer> productoStock = new Hashtable<String, Integer>();
		ArrayList<ArrayList<String>> carrito = new ArrayList<ArrayList<String>>();
		String nombreProducto = "";
		String opcion = "";
		double precioPreIVA = 0;
		int totalProductos = 0;
		double cambio = 0;
		boolean masProductos = true;

		rellenarDatosBase(productoPrecio, productoStock);

		do {
			
			//Pedir si hacer operación de caja o de stock
			opcion = pedirOpcion(teclado);
			
			if (opcion.trim().toLowerCase().equals("caja")) {
				//Pedir productos que añadir o quitar del carrito hasta que se introduzca -1
				do {
					mostrarProductos(productoPrecio, productoStock);
					masProductos = añadirProductoAlCarrito(teclado, carrito, productoStock);
				} while(masProductos);
				
				precioPreIVA = calcularPrecioTotal(carrito, productoPrecio);
				totalProductos = calcularCantidadProductosTotal(carrito);
				cambio = calcularCambio(teclado, precioPreIVA);
				
				mostrarResultado(precioPreIVA, totalProductos, cambio);
				
			} else {

				mostrarProductos(productoPrecio, productoStock);
				nombreProducto = pedirProducto(teclado, productoPrecio, productoStock);

				if (!nombreProducto.trim().equals("-1")) {

					pedirPrecio(teclado, nombreProducto, productoPrecio);
					pedirCantidadStock(teclado, nombreProducto, productoStock);
					mostrarProductos(productoPrecio, productoStock);
				}

			}

		} while (!nombreProducto.equals("-1"));

		teclado.close();
	}

	// Metodo para pedir un valor válido parta los parametros
	public static String pedirProducto(Scanner teclado, Hashtable<String, Double> productoPrecio,
			Hashtable<String, Integer> productoStock) {

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
						pedirCantidadStock(teclado, valorIntroducido, productoStock);
						mostrarProductos(productoPrecio, productoStock);
					} else if (confirmacion.trim().toLowerCase().equals("o")) {
						confirmacionValida = true;
					} else {
						System.out.println(
								"Valor no válido, (P cambiar precio, C cambiar cantidad, O pedir otro producto)");
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
	public static void pedirCantidadStock(Scanner teclado, String nombreProducto, Hashtable<String, Integer> productoStock) {

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

		if (productoStock.containsKey(nombreProducto)) {
			cantidadAnterior = productoStock.get(nombreProducto);
		}

		nuevaCantidad = cantidadAnterior + cantidadIntroducida;

		if (nuevaCantidad <= 0) {
			productoStock.remove(nombreProducto);
		} else {
			productoStock.put(nombreProducto, nuevaCantidad);
		}

	}
	
	
	
	// Metodo para pedir un valor válido parta los parametros
	public static String pedirOpcion(Scanner teclado) {

		// Declaracíon de variables locales del metodo
		String valorIntroducido = "";
		boolean valorValido = false;

		do {
			System.out.print("Hacer operación de caja o stock: ");
			valorIntroducido = teclado.nextLine();

			if (valorIntroducido.trim().toLowerCase().equals("caja") ||  valorIntroducido.trim().toLowerCase().equals("stock")) {
				valorValido = true;
			} else {
				System.out.println("Opción no válida.");
			}

		} while (!valorValido);

		return valorIntroducido;
	}

	public static void mostrarProductos(Hashtable<String, Double> productoPrecio, Hashtable<String, Integer> productoStock) {

		System.out.println("\n\n\n");
		System.out.println(productoPrecio);
		System.out.println(productoStock);
		System.out.println("\n\n\n");

	}
	
	public static void mostrarCarrito(ArrayList<ArrayList<String>> carrito) {
		System.out.println("Carrito");
		System.out.println(carrito);
	}
	
	public static double calcularPrecioTotal(ArrayList<ArrayList<String>> carrito, Hashtable<String, Double> productoPrecio) {

		// Declaración de variables locales del metodo
		double total = 0;

		for (ArrayList<String> producto : carrito) {
			total += productoPrecio.get(producto.get(0)) * Integer.parseInt(producto.get(1));
		}

		return total;
	}

	public static int calcularCantidadProductosTotal(ArrayList<ArrayList<String>> carrito) {
		// Declaración de variables locales del metodo
		int total = 0;

		for (ArrayList<String> producto : carrito) {
			total += Integer.parseInt(producto.get(1));
		}

		return total;
	}

	// Metodo para pedir un valor válido parta los parametros
	public static double calcularCambio(Scanner teclado, double precioPreIVA) {

		// Declaracíon de variables locales del metodo
		String valorIntroducido = "";
		boolean valorValido = false;
		double pago = 0;
		double precioConIVA = (precioPreIVA + (precioPreIVA * IVA));

		do {
			System.out.print("El precio total es "+precioConIVA+", cuanto ha pagado el cliente: ");
			valorIntroducido = teclado.nextLine();

			try {

				pago = Double.parseDouble(valorIntroducido);

				if (pago >= precioConIVA) {
					valorValido = true;
				} else {
					System.out.println(
							"El precio tiene que ser positivo y mayor que el precio total " + precioConIVA + "€ \n\n");
				}
			} catch (Exception e) {
				System.out.println("Valor no válido \n\n");
			}

		} while (!valorValido);

		return pago - precioConIVA;
	}

	public static boolean añadirProductoAlCarrito(Scanner teclado, ArrayList<ArrayList<String>> carrito, Hashtable<String, Integer> productoStock) {

		// Declaracíon de variables locales del metodo
		String valorIntroducido = "";
		boolean valorValido = false;
		boolean cantidadValida = false;
		boolean masProductos = true;;
		boolean existe = false;
		boolean eliminado = false;
		int cantidadIntroducida = 0;
		int stockProducto = 0;
		int cantidadAnterior = 0;
		int indiceExiste = 0;
		int i = 0;
		ArrayList<String> producto = new ArrayList<String>();

		do {
			System.out.print("Introduce el nombre del producto a añadir al carrito (-1 dejar de introdudcir productos): ");
			valorIntroducido = teclado.nextLine();
			
			//Comprobar si existe el producto
			if (productoStock.containsKey(valorIntroducido.toLowerCase().trim())) {
				
				for (ArrayList<String> productoInCarrito : carrito) {
					if(productoInCarrito.get(0).equals(valorIntroducido)) {
						existe = true;
						indiceExiste = i;
					} else {
						i++;
					}
				}
				
				if(existe) {
					producto = carrito.get(indiceExiste);
				} else {
					//Añadir el nombre al arraylist de datos del producto en el carrito
					producto.add(valorIntroducido);
					producto.add("0");
				}
				
				stockProducto = productoStock.get(producto.get(0));
				
				//Pedir cantidad y controlar que se introduzca un valor válido
				do {
															//Stock del producto
					System.out.print("Cantidad a añadir ("+stockProducto+" disponible, - delante para sacar del carrito y devolver stock): ");
					valorIntroducido = teclado.nextLine();
					
					try {
						
						cantidadIntroducida = Integer.parseInt(valorIntroducido);
						
						//Si el valor introducido es mayor al stock, añadir el total del stock, si no, añadir el valor introducido
						 if(stockProducto == 0 && cantidadIntroducida >= 0){
								System.out.println("\n\nNo hay mas stock del producto.\n\n");
						} else if(cantidadIntroducida > stockProducto) {
							producto.set(1, String.valueOf(stockProducto));
							productoStock.replace(producto.get(0), 0);
						} else if(cantidadIntroducida < 0){
							
							//Si se intentan retirar mas elementos de los qu ehay en el carrito los pone a 0
							//Y pone toda la cantidad del carrito al stock
							if(Math.abs(cantidadIntroducida) > Integer.parseInt(producto.get(1)) && existe) {
								cantidadAnterior = Integer.parseInt(producto.get(1));
								productoStock.replace(producto.get(0), stockProducto + cantidadAnterior);
								carrito.remove(indiceExiste);
								eliminado = true;
							} else {
								//Si no hay ninguna cantidad en el carrito muestra mensaje y cuenta como eliminado
								System.out.println("No hay productos que quitar del carrito");
								eliminado = true;
							}
							
						} else {
							cantidadAnterior = Integer.parseInt(producto.get(1));
							producto.set(1, String.valueOf(cantidadAnterior + cantidadIntroducida));
							productoStock.replace(producto.get(0), stockProducto - cantidadIntroducida);
						}
						
						cantidadValida = true;

					} catch (Exception e) {
						System.out.println("Valor no tiene que ser un numero entero \n\n");
					}
					
				} while(!cantidadValida);
				
				
				valorValido = true;
			} else if(valorIntroducido.toLowerCase().trim().equals("-1")){
				valorValido = true;
				masProductos = false;
			} else {
				System.out.println("No existe el producto "+valorIntroducido+".");
			}

		} while (!valorValido);
		
		if(existe && !eliminado) {
			carrito.set(indiceExiste, producto);
			mostrarCarrito(carrito);
		} else if(!eliminado && !valorIntroducido.toLowerCase().trim().equals("-1")){
			carrito.add(producto);
			mostrarCarrito(carrito);
		}
		
		return masProductos;
	}
	
	public static void mostrarResultado(double precioPreIVA, int totalProductos, double cambio) {
		
		double precioConIVA = (precioPreIVA + (precioPreIVA * IVA));
		
		System.out.println("IVA aplicado: " + (IVA*100) + "%");
		System.out.println("Precio total bruto: " + precioPreIVA + "€");
		System.out.println("Precio total + IVA: " + precioConIVA + "€");
		System.out.println("Numero de articulos comprados: " + totalProductos + "uds");
		System.out.println("Cantidad pagada: " + (cambio + precioConIVA) + "€");
		System.out.println("Cambio: " + cambio + "€");
		System.out.println("\n\n");
	}

	public static void rellenarDatosBase(Hashtable<String, Double> productoPrecio,
			Hashtable<String, Integer> productoStock) {

		productoPrecio.put("manzana", 4.5);
		productoStock.put("manzana", 10);

		productoPrecio.put("cereza", 1.0);
		productoStock.put("cereza", 150);

		productoPrecio.put("melón", 9.0);
		productoStock.put("melón", 5);

		productoPrecio.put("sandia", 12.0);
		productoStock.put("sandia", 5);

		productoPrecio.put("melocotón", 6.0);
		productoStock.put("melocotón", 20);

		productoPrecio.put("plátano", 3.3);
		productoStock.put("plátano", 33);

		productoPrecio.put("pera", 4.1);
		productoStock.put("pera", 14);

		productoPrecio.put("tomate", 3.8);
		productoStock.put("tomate", 24);

		productoPrecio.put("zanahoria", 1.9);
		productoStock.put("zanahoria", 17);

		productoPrecio.put("patata", 5.5);
		productoStock.put("patata", 42);

		productoPrecio.put("lechuga", 1.6);
		productoStock.put("lechuga", 31);
	}

}
