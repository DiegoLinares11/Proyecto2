import java.util.List;

public class Producto {
    private String nombre;
    private Double precio;
    private int cantidad;

    public Producto(String nombre, double precio, int cantidad){
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public double getPrecio(){
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCantidad(){
        return cantidad;
    }
    
    public static void agregarProducto(List<Producto> inventario, Producto nuevoProducto) {
        inventario.add(nuevoProducto);
    }
    
    @Override
    public String toString() {
        return "Producto [nombre=" + nombre + ", cantidad=" + cantidad + ", precio=" + precio + "]";
    }
}
