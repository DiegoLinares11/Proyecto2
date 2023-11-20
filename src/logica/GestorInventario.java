package logica;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GestorInventario {
    /**
     * @param nombreArchivo
     * @return
     */
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

    /**
     * @param rutaArchivo
     * @return
     * @throws IOException
     */
    public List<Venta> leerVentas(String rutaArchivo) throws IOException {
        List<Venta> ventas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                // Separamos la línea por comas
                String[] datos = linea.split(",");

                // Creamos una nueva venta
                Venta venta = new Venta(datos[0], Integer.parseInt(datos[1]), Double.parseDouble(datos[2]),
                        LocalDate.parse(datos[3]));

                // Agregamos la venta al List
                ventas.add(venta);
            }
        }

        return ventas;
    }

    /**
     * @param inventario
     */
    public void imprimirInventario(List<Producto> inventario) {
        System.out.println("Inventario actual:");
        for (Producto producto : inventario) {
            System.out.println(producto);
        }
    }

    /**
     * @param ventas
     */
    public void imprimirVentas(List<Venta> ventas) {
        System.out.println("Ventas realizadas:");
        for (Venta venta : ventas) {
            System.out.println(venta);
        }
    }

    /**
     * @param inventario
     * @param nuevoProducto
     */
    public void agregarProducto(List<Producto> inventario, Producto nuevoProducto) {
        inventario.add(nuevoProducto);
    }

    /**
     * @param inventario
     * @param nombreArchivo
     */
    public void guardarCSV(List<Producto> inventario, String nombreArchivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            for (Producto producto : inventario) {
                String linea = producto.getId() + "," + producto.getNombre() + "," + producto.getPrecio() + ","
                        + producto.getCantidad() + "," + producto.getCantidadv();
                writer.write(linea);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param ventas
     * @param nombreArchivo
     */
    public void guardarCSV2(List<Venta> ventas, String nombreArchivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            for (Venta venta : ventas) {
                String linea = venta.getNombreProducto() + "," + venta.getCantidadVendida() + "," + venta.getTotal()
                        + "," + venta.getFecha();
                writer.write(linea);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
