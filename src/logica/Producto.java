package logica;

import java.util.List;

public class Producto {
    private int id;
    private String nombre;
    private Double precio;
    private int cantidad;
    private int cantidadv;

    public Producto(int id, String nombre, Double precio, int cantidad, int cantidadv) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.cantidadv = cantidadv;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidadv() {
        return cantidadv;
    }

    public void setCantidadv(int cantidadv) {
        this.cantidadv = cantidadv;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCantidad() {
        return cantidad;
    }

    public static void agregarProducto(List<Producto> inventario, Producto nuevoProducto) {
        inventario.add(nuevoProducto);
    }

    @Override
    public String toString() {
        return "Producto [ID=" + id + ", nombre=" + nombre + ", cantidad=" + cantidad + ", cantidad vendida="
                + cantidadv + ", precio=" + precio + "]";
    }
}
