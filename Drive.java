import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Drive {
    public static void main(String[] args) {

        GestorInventario g = new GestorInventario();
        List<Venta> ventas = new GestorInventario().leerCSV2("ventas.csv");
        List<Producto> inventario = g.leerCSV("inventario.csv");

        while (true) {
            System.out.println("1. Mostrar productos disponibles");
            System.out.println("2. Hacer una venta");
            System.out.println("3. Generar reporte de ventas");
            System.out.println("4. Agregar nuevo producto al inventario");
            System.out.println("5. Salir");

            Scanner sc = new Scanner(System.in);
            System.out.println("Ingresa la opcion que deseas: ");
            int op = sc.nextInt();
            sc.nextLine();

            switch (op) {
                case 1:
                    g.imprimirInventario(inventario);
                    break;

                case 2:
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
                    break;

                case 3:
                    while (true) {
                        System.out.println("1. Reporte general.");
                        System.out.println("2. Reporte semanal.");
                        System.out.println("3. Reporte mensual.");
                        System.out.println("4. Mes especifico.");

                        Scanner scn = new Scanner(System.in);
                        System.out.println("Ingresa la opcion que deseas: ");
                        int opt = sc.nextInt();
                        sc.nextLine();
                        switch (opt) {
                            case 1:

                                break;

                            default:
                                break;
                        }

                    }

                case 4:
                    System.out.println("Ingrese el id del nuevo producto: ");
                    int idProducto;
                    try {
                        idProducto = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Entrada no válida para el ID. Asegúrese de ingresar un número entero.");
                        return; // Salir del programa si la entrada no es válida
                    }

                    System.out.println("Ingrese el nombre del nuevo producto: ");
                    String nombreProducto = sc.nextLine();

                    System.out.print("Ingrese la cantidad del nuevo producto: ");
                    int cantidadProducto;
                    try {
                        cantidadProducto = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out
                                .println("Entrada no válida para la cantidad. Asegúrese de ingresar un número entero.");
                        return; // Salir del programa si la entrada no es válida
                    }

                    System.out.print("Ingrese el precio del nuevo producto: ");
                    double precioProducto;
                    try {
                        precioProducto = Double.parseDouble(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out
                                .println("Entrada no válida para el precio. Asegúrese de ingresar un número decimal.");
                        return; // Salir del programa si la entrada no es válida
                    }

                    Producto producto = new Producto(idProducto, nombreProducto, precioProducto, cantidadProducto, 0);
                    inventario.add(producto);
                    g.guardarCSV(inventario, "inventario.csv");
                    break;

                case 5:
                    System.out.println("Gracias por elegirnos, hasta luego");
                    g.guardarCSV(inventario, "inventario.csv");
                    sc.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Opción inválida. Por favor, elija una opción válida.");
                    break;
            }
        }
    }
}
