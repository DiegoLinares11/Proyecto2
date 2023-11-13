package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logica.GestorInventario;
import logica.Gestorventas;
import logica.Producto;
import logica.Venta;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.util.Scanner;

public class Pantalla extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Pantalla frame = new Pantalla();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws IOException
	 */
	public Pantalla() throws IOException {
		GestorInventario g = new GestorInventario();
		List<Producto> inventario = g.leerCSV("inventario.csv");
		List<Venta> ventas = g.leerVentas("ventas.csv");
		Scanner sc = new Scanner(System.in);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("BIENVENIDOS AL PROGRAMA QUE TE AYUDARA CON TU INVENTARIO.");
		lblNewLabel.setBounds(34, 11, 366, 22);
		contentPane.add(lblNewLabel);

		JButton btnNewButton = new JButton("Mostrar productos disponibles");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				g.imprimirInventario(inventario);
			}
		});
		btnNewButton.setBounds(44, 44, 275, 23);
		contentPane.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Hacer una venta");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				g.imprimirInventario(inventario);
				while (true) {
					System.out.println(
							"Ingresa el ID del producto que deseas vender (Ingresa 0 para salir del modo venta): ");
					int index = sc.nextInt();

					if (index == 0) {
						break;
					}

					inventario.stream()
							.filter(p -> p.getId() == index)
							.findAny();

					Producto producto = inventario.stream()
							.filter(p -> p.getId() == index)
							.findAny()
							.orElse(null);

					if (producto != null) {
						System.out.println("Ingresa la cantidad a vender: ");
						String name = producto.getNombre();
						int vend = sc.nextInt();
						int cand = producto.getCantidad();
						double tot = vend * producto.getPrecio();

						if (vend <= cand) {
							producto.setCantidad(cand - vend);
							producto.setCantidadv(vend);

							Venta venta = new Venta(name, vend, tot, LocalDate.now());
							ventas.add(venta);
						} else {
							System.out.println("Cantidad no disponible, lo sentimos");
						}

					} else {
						System.out.println("Lo sentimos el ID que proporcionaste no existe");
					}

				}
				g.guardarCSV2(ventas, "ventas.csv");
				g.guardarCSV(inventario, "inventario.csv");
			}
		});
		btnNewButton_1.setBounds(44, 78, 275, 23);
		contentPane.add(btnNewButton_1);

		JButton btnNewButton_1_1 = new JButton("Generar reporte de ventas");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Gestorventas gv = new Gestorventas();
				while (true) {
					System.out.println("a. Reporte general.");
					System.out.println("b. Reporte semanal.");
					System.out.println("c. Reporte mensual.");
					System.out.println("d. Mes especifico.");
					System.out.println("s. Salir del reporte de ventas.");

					Scanner scn = new Scanner(System.in);
					System.out.println("Ingresa la opcion que deseas: ");
					String o = scn.nextLine(); // Usar scn en lugar de sc
					switch (o) {
						case "a":
							System.out.println("----------------------Reporte General------------------");
							System.out.println("VENTAS DEL DIA:");
							gv.Diario(ventas);
							System.out.println("-------");
							System.out.println("VENTAS DE LA SEMANA:");
							gv.Semanal(ventas);
							System.out.println("-------");
							System.out.println("VENTAS DEL MES:");
							gv.Mensual(ventas);
							System.out.println("-------");
							gv.Anual(ventas);
							System.out.println("--------------------------------------------------------");
							break;

						case "b":
							gv.Semanal(ventas);
							break;

						case "c":
							gv.Mensual(ventas);
							break;

						case "d":
							System.out.println(
									"Por favor digita el numero de mes que quieres saber (Ejemplo: Enero -> 1, Febrero -> 2...)");
							int mes;
							try {
								mes = Integer.parseInt(sc.nextLine());
							} catch (NumberFormatException ex) {
								System.out.println(
										"Entrada no válida para el Mes. Asegúrese de ingresar un número entero.");
								break;
							}

							gv.MensuaF(ventas, mes);
							break;

						case "s":
							System.out.println("Saliendo del reporte de ventas.");
							break;

						default:
							System.out.println("Opción inválida. Por favor, elija una opción válida.");
							break;
					}
					if (o.equals("s")) {
						break;
					}
				}
			}
		});
		btnNewButton_1_1.setBounds(44, 112, 275, 23);
		contentPane.add(btnNewButton_1_1);

		JButton btnNewButton_1_2 = new JButton("Agregar nuevo producto al inventario");
		btnNewButton_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int idProducto = 0; // Inicializa las variables fuera de los bloques try-catch
				int cantidadProducto = 0;
				double precioProducto = 0.0;

				System.out.println("Ingrese el id del nuevo producto: ");
				try {
					idProducto = Integer.parseInt(sc.nextLine());
				} catch (NumberFormatException ex) {
					System.out.println("Entrada no válida para el ID. Asegúrese de ingresar un número entero.");
					return; // Sale del actionPerformed sin agregar el producto
				}

				System.out.println("Ingrese el nombre del nuevo producto: ");
				String nombreProducto = sc.nextLine();

				System.out.print("Ingrese la cantidad del nuevo producto: ");
				try {
					cantidadProducto = Integer.parseInt(sc.nextLine());
				} catch (NumberFormatException ex) {
					System.out.println("Entrada no válida para la cantidad. Asegúrese de ingresar un número entero.");
					return; // Sale del actionPerformed sin agregar el producto
				}

				System.out.print("Ingrese el precio del nuevo producto: ");
				try {
					precioProducto = Double.parseDouble(sc.nextLine());
				} catch (NumberFormatException ex) {
					System.out.println("Entrada no válida para el precio. Asegúrese de ingresar un número decimal.");
					return; // Sale del actionPerformed sin agregar el producto
				}

				Producto producto = new Producto(idProducto, nombreProducto, precioProducto, cantidadProducto, 0);
				inventario.add(producto);
				g.guardarCSV(inventario, "inventario.csv");
			}
		});
		btnNewButton_1_2.setBounds(44, 146, 275, 23);
		contentPane.add(btnNewButton_1_2);

		JButton btnNewButton_2 = new JButton("Salir");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Gracias por elegirnos, hasta luego");
				g.guardarCSV(inventario, "inventario.csv");
				sc.close();
				System.exit(0);
			}
		});
		btnNewButton_2.setBounds(139, 197, 89, 23);
		contentPane.add(btnNewButton_2);
	}
}
