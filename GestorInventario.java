import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestorInventario {
    public List<Producto> leerCSV(String nombreArchivo) {
        List<Producto> inventario = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 3) {
                    Producto producto = new Producto(linea, 0, 0);
                    producto.setNombre(partes[0]);
                    producto.setCantidad(Integer.parseInt(partes[1]));
                    producto.setPrecio(Double.parseDouble(partes[2]));
                    inventario.add(producto);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        return inventario;
    }

    public static void imprimirInventario(List<Producto> inventario) {
        System.out.println("Inventario actual:");
        for (Producto producto : inventario) {
            System.out.println(producto);
        }
    }

    public void agregarProducto(List<Producto> inventario, Producto nuevoProducto) {
        inventario.add(nuevoProducto);
    }

    public void modificarProducto(List<Producto> inventario, String nombre, int nuevaCantidad, double nuevoPrecio) {
        for (Producto producto : inventario) {
            if (producto.getNombre().equals(nombre)) {
                producto.setCantidad(nuevaCantidad);
                producto.setPrecio(nuevoPrecio);
                break;
            }
        }
    }
    
    public static void venderProducto(List<Producto> inventario, List<Venta> ventas, String nombreProducto, int cantidadVendida) {
        for (Producto producto : inventario) {
            if (producto.getNombre().equalsIgnoreCase(nombreProducto)) {
                int cantidadDisponible = producto.getCantidad();
                if (cantidadDisponible >= cantidadVendida) {
                    producto.setCantidad(cantidadDisponible - cantidadVendida);
                    ventas.add(new Venta(nombreProducto, cantidadVendida));
                    System.out.println("Venta registrada: " + cantidadVendida + " unidades de " + nombreProducto);
                } else {
                    System.out.println("No hay suficientes unidades disponibles para la venta.");
                }
                return; // Terminamos la búsqueda después de encontrar el producto.
            }
        }
        System.out.println("Producto no encontrado en el inventario.");
    }

    public static double calcularTotalVentas(List<Producto> inventario) {
        double totalVentas = 0.0;
        for (Producto producto : inventario) {
            double precioProducto = producto.getPrecio();
            int cantidadVendida = producto.getCantidad();
            double ventasProducto = precioProducto * cantidadVendida;
            totalVentas += ventasProducto;
        }
        return totalVentas;
    }

    public void guardarCSV(List<Producto> inventario, String nombreArchivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            for (Producto producto : inventario) {
                String linea = producto.getNombre() + "," + producto.getCantidad() + "," + producto.getPrecio();
                writer.write(linea);
                writer.newLine(); // Add a new line separator
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void modificarProducto(Producto productoExistente, int nuevaCantidad, double nuevoPrecio) {
    }
}

