import java.time.LocalDate;

public class Venta {
    private String nombreProducto;
    private int cantidadVendida;
    private double total;
    private LocalDate fecha;

    public Venta(String nombreProducto, int cantidadVendida, double total, LocalDate fecha) {
        this.nombreProducto = nombreProducto;
        this.cantidadVendida = cantidadVendida;
        this.total = total;
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public int getCantidadVendida() {
        return cantidadVendida;
    }

    @Override
    public String toString() {
        return "Venta [Producto: " + nombreProducto + ", Cantidad vendida: " + cantidadVendida + ", Fecha de la venta: "
                + fecha + "]";
    }
}