import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
public class Drive{
    public static void main(String[] args) {
        
        GestorInventario gestor = new GestorInventario();
        List<Producto> inventario = gestor.leerCSV("inventario.csv");
        List<Venta> ventas = new ArrayList<>();
        Scanner sc = new Scanner(System.in);

        while (true) {
                System.out.println("1. Mostrar productos disponibles");
                System.out.println("2. Hacer una venta");
                System.out.println("3. Generar reporte de ventas");
                System.out.println("4. Agregar nuevo producto al inventario");
                System.out.println("5. Salir");
                System.out.println("Seleccione una opción: ");

                try {
                    int opcion = sc.nextInt();

                    switch (opcion) {
                        case 1:
                            gestor.imprimirInventario(inventario);
                            gestor.guardarCSV(inventario, "inventario.csv");
                            break;

                        case 2:
                            Scanner scn = new Scanner(System.in);
                            while (true) {
                                gestor.imprimirInventario(inventario);
                    
                                System.out.println("¿Qué producto desea comprar? (nombre o 'salir' para terminar)");
                                String nombreProducto = scn.nextLine();
                    
                                if (nombreProducto.equalsIgnoreCase("salir")) {
                                    break;
                                }
                    
                                System.out.println("¿Cuántas unidades de '" + nombreProducto + "' desea comprar?");
                                int cantidadVenta = Integer.parseInt(scn.nextLine());
                    
                                gestor.venderProducto(inventario, ventas, nombreProducto, cantidadVenta);
                            }
                            gestor.guardarCSV(inventario, "inventario.csv");
                            scn.close();
                            break;

                        case 3:
                            double totalVentas = gestor.calcularTotalVentas(inventario);
                            System.out.println("Total de ventas: $" + totalVentas);
                            break;
                        
                        case 4:
                            System.out.print("Ingrese el nombre del nuevo producto: ");
                            String nombreProducto = scanner.nextLine();
                    
                            System.out.print("Ingrese la cantidad del nuevo producto: ");
                            int cantidadProducto;
                            try {
                                cantidadProducto = Integer.parseInt(scanner.nextLine());
                            } catch (NumberFormatException e) {
                                System.out.println("Entrada no válida para la cantidad. Asegúrese de ingresar un número entero.");
                                return; // Salir del programa si la entrada no es válida
                            }
                    
                            System.out.print("Ingrese el precio del nuevo producto: ");
                            double precioProducto;
                            try {
                                precioProducto = Double.parseDouble(scanner.nextLine());
                            } catch (NumberFormatException e) {
                                System.out.println("Entrada no válida para el precio. Asegúrese de ingresar un número decimal.");
                                return; // Salir del programa si la entrada no es válida
                            }
                    
                            // Crear un nuevo objeto Producto con los datos ingresados por el usuario
                            Producto nuevoProducto = new Producto(nombreProducto, cantidadProducto, precioProducto);
                    
                            // Llamar al método para agregar el nuevo producto al inventario
                            agregarProducto(inventario, nuevoProducto);
                    
                            // Verificar que el producto haya sido agregado
                            System.out.println("Nuevo producto agregado al inventario:");
                            System.out.println(nuevoProducto);
                            break;

                        case 5:
                            System.out.println("Gracias por elegirno, hasta luego");
                            gestor.guardarCSV(inventario, "inventario.csv");
                            sc.close();
                            System.exit(0);
                            break;

                        default:
                            System.out.println("Opción inválida. Por favor, elija una opción válida.");
                            break;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Error: Ingrese una opción válida.");
                    sc.nextLine(); // Limpiar el buffer de entrada
                }
        }
    }
}
