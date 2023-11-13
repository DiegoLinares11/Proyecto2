import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Drive{
    public static void main(String[] args) throws Exception {
        
        GestorInventario g = new GestorInventario();
        List<Producto> inventario = g.leerCSV("inventario.csv");
        List<Venta> ventas = g.leerVentas("ventas.csv");
        

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
                        System.out.println("Ingresa el ID del producto que deseas vender (Ingresa 0 para salir del modo venta): ");
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
                            
                            if(vend <= cand){
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
                    Gestorventas gv = new Gestorventas();
                    while (true) {
                        System.out.println("a. Reporte general.");
                        System.out.println("b. Reporte semanal.");
                        System.out.println("c. Reporte mensual.");
                        System.out.println("d. Mes especifico.");
                        System.out.println("s. Salir del reporte de ventas.");
                        
                        Scanner scn = new Scanner(System.in);
                        System.out.println("Ingresa la opcion que deseas: ");   
                        String o = scn.nextLine();  // Usar scn en lugar de sc
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
                                System.out.println("Por favor digita el numero de mes que quieres saber (Ejemplo: Enero -> 1, Febrero -> 2...)");
                                int mes;
                                try {
                                    mes = Integer.parseInt(sc.nextLine());
                                } catch (NumberFormatException e) {
                                    System.out.println("Entrada no válida para el Mes. Asegúrese de ingresar un número entero.");
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
                        if (o.equals("s")){
                            break;
                        }
                    }
                    break;
                 

                case 4:
                    System.out.println("Ingrese el id del nuevo producto: ");
                    int idProducto;
                    try {
                        idProducto = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Entrada no válida para el ID. Asegúrese de ingresar un número entero.");
                        break; // Salir del programa si la entrada no es válida
                    }

                    System.out.println("Ingrese el nombre del nuevo producto: ");
                    String nombreProducto = sc.nextLine();

                    System.out.print("Ingrese la cantidad del nuevo producto: ");
                    int cantidadProducto;
                    try {
                        cantidadProducto = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Entrada no válida para la cantidad. Asegúrese de ingresar un número entero.");
                        break; // Salir del programa si la entrada no es válida
                    }

                    System.out.print("Ingrese el precio del nuevo producto: ");
                    double precioProducto;
                    try {
                        precioProducto = Double.parseDouble(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Entrada no válida para el precio. Asegúrese de ingresar un número decimal.");
                        break; // Salir del programa si la entrada no es válida
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
