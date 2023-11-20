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
import java.time.Clock;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
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
		setBounds(100, 100, 450, 323);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("BIENVENIDOS AL PROGRAMA QUE TE AYUDARA CON TU INVENTARIO.");
		lblNewLabel.setBounds(10, 11, 501, 22);
		contentPane.add(lblNewLabel);

		JButton btnNewButton = new JButton("Mostrar productos disponibles");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearConsole();
				showTitle(_title, ANSI_PURPLE);
				g.imprimirInventario(inventario);
			}
		});
		btnNewButton.setBounds(44, 44, 275, 23);
		contentPane.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Hacer una venta");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearConsole();
				showTitle(_title, ANSI_PURPLE);
				g.imprimirInventario(inventario);
				while (true) {
					// int index = formLabel("Ingresa el ID del producto que deseas vender (Ingresa
					// 0 para salir del modo venta): ", ANSI_BLACK);

					var answer = formLabel(
							"Ingresa el ID del producto que deseas vender (Ingresa 0 para salir del modo venta): ",
							ANSI_CYAN);
					var index = Integer.parseInt(answer);
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
							consoleWriteLine("Venta realizada!");
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
				clearConsole();
				showTitle(_title, ANSI_PURPLE);
				Gestorventas gv = new Gestorventas();
				while (true) {
					printSpaceSeparated("a)", "Reporte general.", ANSI_YELLOW);
					printSpaceSeparated("b)", "Reporte semanal.", ANSI_YELLOW);
					printSpaceSeparated("c)", "Reporte mensual.", ANSI_YELLOW);
					printSpaceSeparated("d", "Mes especifico.", ANSI_YELLOW);
					printSpaceSeparated("s", "Salir del reporte de ventas.", ANSI_YELLOW);

					// Scanner scn = new Scanner(System.in);
					var o = formLabel("Ingresa la opcion que deseas: ", ANSI_CYAN);

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
							consoleWriteLine("Saliendo del reporte de ventas.");
							break;

						default:
							writeErrorMessage("Opción inválida. Por favor, elija una opción válida.");
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
				clearConsole();
				showTitle(_title, ANSI_PURPLE);
				int idProducto = 0; // Inicializa las variables fuera de los bloques try-catch
				int cantidadProducto = 0;
				double precioProducto = 0.0;

				System.out.println("Ingrese el id del nuevo producto: ");
				try {
					idProducto = Integer.parseInt(sc.nextLine());
				} catch (NumberFormatException ex) {
					writeErrorMessage("Entrada no válida para el ID. Asegúrese de ingresar un número entero.");
					return; // Sale del actionPerformed sin agregar el producto
				}

				var nombreProducto = formLabel("Ingrese el nombre del nuevo producto: ", ANSI_CYAN);

				System.out.print("Ingrese la cantidad del nuevo producto: ");
				try {
					cantidadProducto = Integer.parseInt(sc.nextLine());
				} catch (NumberFormatException ex) {
					writeErrorMessage("Entrada no válida para la cantidad. Asegúrese de ingresar un número entero.");
					return; // Sale del actionPerformed sin agregar el producto
				}

				System.out.print("Ingrese el precio del nuevo producto: ");
				try {
					precioProducto = Double.parseDouble(sc.nextLine());
				} catch (NumberFormatException ex) {
					writeErrorMessage("Entrada no válida para el precio. Asegúrese de ingresar un número decimal.");
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
				writeSuccessMessage("Gracias por elegirnos, hasta luego");
				g.guardarCSV(inventario, "inventario.csv");
				sc.close();
				System.exit(0);
			}
		});
		btnNewButton_2.setBounds(139, 197, 89, 23);
		contentPane.add(btnNewButton_2);
	}

	public static ArrayList<Object> _title = new ArrayList<>() {
		{

			add(" ______    # ______       # ______      # __  __    # ______      # ______      # _________  # ______      # _____       #");
			add("/_____/\\   #/_____/\\      #/_____/\\     #/_/\\/_/\\   #/_____/\\     #/_____/\\     #/________/\\ #/_____/\\     #/_____/\\     #");
			add("\\:::_ \\ \\  #\\:::_ \\ \\     #\\:::_ \\ \\    #\\ \\ \\ \\ \\  #\\::::_\\/_    #\\:::__\\/     #\\__.::.__\\/ #\\:::_ \\ \\    #\\:::_:\\ \\    #.");
			add(" \\:(_) \\ \\ # \\:(_) ) )_   # \\:\\ \\ \\ \\   # \\:\\_\\ \\ \\ # \\:\\/___/\\   # \\:\\ \\  __   #   \\::\\ \\   # \\:\\ \\ \\ \\   #    _\\:\\|    #");
			add("  \\: ___\\/ #  \\: __ `\\ \\  #  \\:\\ \\ \\ \\  #  \\::::_\\/ #  \\::___\\/_  #  \\:\\ \\/_/\\  #    \\::\\ \\  #  \\:\\ \\ \\ \\  #   /::_/__   #");
			add("   \\ \\ \\   #   \\ \\ `\\ \\ \\ #   \\:\\_\\ \\ \\ #    \\::\\ \\ #   \\:\\____/\\ #   \\:\\_\\ \\ \\ #     \\::\\ \\ #   \\:\\_\\ \\ \\ #   \\:\\____/\\ #");
			add("    \\_\\/   #    \\_\\/ \\_\\/ #    \\_____\\/ #     \\__\\/ #    \\_____\\/ #    \\_____\\/ #      \\__\\/ #    \\_____\\/ #    \\_____\\/ #");
			add("           ##              ##             ##           ##             ##             ##            ##             ##             ##");
		}
	};

	public static void writeWarningMessage(Object message) {
		consoleWriteLine(message, ANSI_YELLOW);
	}

	public static <T> void printList(Object title, Iterable<T> list) {
		consoleWriteLine(title, ANSI_CYAN);
		for (T itemT : list) {
			System.out.println(itemT.toString() + " ");
		}
	}

	public static <T> void printTable(Object title, Iterable<T> list, int columnWidth, String separator) {
		consoleWriteLine(title, ANSI_CYAN);
		int i = 0;
		for (T itemT : list) {
			if (i % columnWidth == 0) {
				System.out.println();
			}
			System.out.print(itemT.toString() + separator);
			i++;
		}
		System.out.println();
	}

	public static void writeErrorMessage(Object message) {
		consoleWriteLine(message, ANSI_WHITE, ANSI_RED_BACKGROUND);
	}

	public static void writeSuccessMessage(Object message) {
		consoleWriteLine(message, ANSI_WHITE, ANSI_GREEN_BACKGROUND);
	}

	public static void showTitle(ArrayList<Object> titulo, String foreground) {
		for (Object line : titulo) {
			consoleWriteLine(line, foreground);
		}
	}

	public static <T> void progressBar(Object prefix, Iterable<Object> frames) {
		progressBar(prefix, frames, 3000, ANSI_YELLOW);
	}

	public static <T> void progressBar(Object prefix, Iterable<Object> frames, long durationInMs) {
		progressBar(prefix, frames, durationInMs, ANSI_YELLOW);
	}

	public static <T> void progressBar(Object prefix, Iterable<Object> frames, long durationInMs, Object color) {
		final long START_MS = Clock.systemUTC().instant().toEpochMilli();
		boolean startAgain = true;

		System.out.print(String.format("%s%s", color, prefix));
		while (startAgain) {
			for (Object frame : frames) {
				final String stringFrame = frame.toString();
				System.out.print(stringFrame);

				final long CURRENT_MS = Clock.systemUTC().instant().toEpochMilli();
				if (CURRENT_MS - START_MS > durationInMs) {
					System.out.println(ANSI_RESET);
					startAgain = false;
					break;
				}

				try {
					Thread.sleep(200, 0);
				} catch (Exception e) {
				}

				for (int i = 0; i < stringFrame.length(); i++) {
					System.out.print("\b \b"); // \b is a not destructive backspace, that's why we need the space
				}
			}
		}

	}

	public static <T> void progressBar(Object prefix, Iterable<Object> frames, BooleanSupplier endCondition,
			Object color) {
		System.out.print(String.format("%s%s", color, prefix));
		while (endCondition.getAsBoolean()) {
			for (Object frame : frames) {
				final String stringFrame = frame.toString();
				System.out.print(stringFrame);

				try {
					Thread.sleep(200, 0);
				} catch (Exception e) {
				}

				for (int i = 0; i < stringFrame.length(); i++) {
					System.out.print("\b \b"); // \b is a not destructive backspace, that's why we need the space
				}
			}
		}
		System.out.println(ANSI_RESET);
	}

	public static <T> void showInTable(T item, Function<T, LinkedHashMap<Object, Object>> convertToMap) {
		Integer maxLength = null;
		var rows = convertToMap.apply(item);
		if (maxLength == null) {
			maxLength = rows.keySet().stream()
					.map(o -> o.toString().length())
					.max(Integer::compare)
					.orElse(0) + 1;
		}

		for (Object rowName : rows.keySet()) {
			int spacing = maxLength - rowName.toString().length() + rows.get(rowName).toString().length();
			var format = "%" + spacing + "s";
			consoleWrite(rowName + ":", ANSI_CYAN);
			consoleWriteLine(String.format(format, rows.get(rowName)));
		}
	}

	public static <T> void showInTable(Iterable<T> list,
			Function<T, LinkedHashMap<Object, Object>> convertToMap) {
		Integer maxLength = null;
		for (T itemT : list) {
			var rows = convertToMap.apply(itemT);
			if (maxLength == null) {
				maxLength = rows.keySet().stream()
						.map(o -> o.toString().length())
						.max(Integer::compare)
						.orElse(0) + 1;
			}
			for (Object rowName : rows.keySet()) {
				int spacing = maxLength - rowName.toString().length() + rows.get(rowName).toString().length();
				var format = "%" + spacing + "s";
				consoleWrite(rowName + ":", ANSI_CYAN);
				consoleWriteLine(String.format(format, rows.get(rowName)));
			}
			consoleWriteLine(SUB_DIVIDER);
		}
	}

	static final String ANSI_RESET = "\u001B[0m";
	static final String ANSI_BLACK = "\u001B[30m";
	static final String ANSI_RED = "\u001B[31m";
	static final String ANSI_GREEN = "\u001B[32m";
	static final String ANSI_YELLOW = "\u001B[33m";
	static final String ANSI_BLUE = "\u001B[34m";
	static final String ANSI_PURPLE = "\u001B[35m";
	static final String ANSI_CYAN = "\u001B[36m";
	static final String ANSI_WHITE = "\u001B[37m";
	static final Scanner sc = new Scanner(System.in);

	static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

	static final int DIVIDER_LENGTH = 32;
	static final int DIVIDER_HALF_LENGTH = DIVIDER_LENGTH / 2;
	static final String DIVIDER = new String(new char[DIVIDER_LENGTH]).replace("\0", "=");
	static final String SUB_DIVIDER = new String(new char[DIVIDER_LENGTH]).replace("\0", "-");
	static final Iterable<Object> FRAMES = new ArrayList<>() {
		{
			add("-");
			add("\\");
			add("|");
			add("/");
		}
	};

	public static String formLabel(Object label, String color) {
		consoleWrite(label + ": ");
		return waitForInputColored(color);
	}

	public static String formLabel(Object label, String color, Function<String, Boolean> check) {
		String input = "";
		do {
			input = formLabel(label, color);
		} while (!check.apply(input));

		return input;
	}

	public static <T> T formLabel(Object label, String color, Function<String, Boolean> check,
			Function<String, T> conv) {
		String input = formLabel(label, color, check);
		return conv.apply(input);
	}

	public static String waitForInputColored(String color) {
		System.out.print(color);
		String s = readLine();
		System.out.print(ANSI_RESET);
		return s;
	}

	public static void printSpaceSeparated(Object identifier, Object option, String color) {
		consoleWrite(identifier + " ", color);
		consoleWriteLine(option.toString());
	}

	public static void printSpaceSeparatedFor(Object identifier, Object option, String color) {
		consoleWrite(identifier + " ");
		consoleWriteLine(option.toString(), color);
	}

	public static void clearConsole() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	public static void consoleWriteLine(Object message) {
		consoleWriteLine(message, "", "");
	}

	public static void consoleWriteLine(Object message, String foreground) {
		consoleWriteLine(message, foreground, "");
	}

	public static void consoleWriteLine(Object message, String foreground, String background) {
		System.out.println(background + foreground + message + ANSI_RESET);
	}

	public static void consoleWrite(Object message) {
		consoleWrite(message, "", "");
	}

	public static void consoleWrite(Object message, String foreground) {
		consoleWrite(message, foreground, "");
	}

	public static void consoleWrite(Object message, String foreground, String background) {
		System.out.print(background + foreground + message + ANSI_RESET);
	}

	public static String readLine() {
		return sc.nextLine();
	}

}
