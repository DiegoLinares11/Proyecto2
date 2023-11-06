import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GestorInventario {
    public List<Producto> leerCSV(String nombreArchivo) {
        List<Producto> inventario = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 5) {
                    int id = (Integer.parseInt(partes[0]));
                    String nombre = (partes[1]);
                    double precio = (Double.parseDouble(partes[2]));
                    int cantidad = (Integer.parseInt(partes[3]));
                    int cantidadv = (Integer.parseInt(partes[4]));
                    Producto producto = new Producto(id, nombre, precio, cantidad, cantidadv);
                    inventario.add(producto);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        return inventario;
    }

    public List<Venta> leerCSV2(String nombreArchivo) {
        List<Venta> ventas = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 3) {
                    String nombre = (partes[0]);
                    int cantv = (Integer.parseInt(partes[1]));
                    double total = (Double.parseDouble(partes[2]));
                    LocalDate fecha = LocalDate.parse(partes[3]);
                    Venta venta = new Venta(nombre, cantv, total, fecha);
                    ventas.add(venta);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        return ventas;
    }

    public static void imprimirInventario(List<Producto> inventario) {
        System.out.println("Inventario actual:");
        for (Producto producto : inventario) {
            System.out.println(producto);
        }
    }

    public static void imprimirVentas(List<Venta> ventas) {
        System.out.println("Ventas realizadas:");
        for (Venta venta : ventas) {
            System.out.println(venta);
        }
    }


    public void agregarProducto(List<Producto> inventario, Producto nuevoProducto) {
        inventario.add(nuevoProducto);
    }


    public void guardarCSV(List<Producto> inventario, String nombreArchivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            for (Producto producto : inventario) {
                String linea = producto.getId() + "," + producto.getNombre() + "," + producto.getPrecio() + "," + producto.getCantidad() + "," + producto.getCantidadv();
                writer.write(linea);
                writer.newLine(); 
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void guardarCSV2(List<Venta> ventas, String nombreArchivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            for (Venta venta : ventas) {
                String linea = venta.getNombreProducto() + "," + venta.getCantidadVendida() + "," + venta.getTotal() + "," + venta.getFecha();
                writer.write(linea);
                writer.newLine(); 
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

