import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

public class Gestorventas {
    public void Diario(List<Venta> ventas){
        Double total = 0.0;

        LocalDate today = LocalDate.now();

        for(Venta venta : ventas){
            if(today.equals(venta.getFecha())){
                total = total  + venta.getTotal(); 
            }
        }

        System.out.println("El total de ventas realizadas en el dia es: " + total);
    }

    public void Semanal(List<Venta> ventas){
        Double total = 0.0;
        LocalDate today = LocalDate.now();

        LocalDate firstDayOfWeek = today.with(DayOfWeek.MONDAY);

        LocalDate lastDayOfWeek = firstDayOfWeek.plusDays(7);

        for (LocalDate date = firstDayOfWeek; date.isBefore(lastDayOfWeek); date = date.plusDays(1)) {
            for(Venta venta : ventas){
                if(date.equals(venta.getFecha())){
                    total = total  + venta.getTotal(); 
                }   
            }
        }

        System.out.println("Total de ingresos de la semana: " + total);
    }

    public void Mensual(List<Venta> ventas){
        Double total = 0.0;

        LocalDate today = LocalDate.now();

        LocalDate firstDayOfMonth = today.withDayOfMonth(1);

        LocalDate lastDayOfMonth = today.with(TemporalAdjusters.lastDayOfMonth());

        for (LocalDate date = firstDayOfMonth; date.isBefore(lastDayOfMonth.plusDays(1)); date = date.plusDays(1)) {
            for(Venta venta : ventas){
                if(date.equals(venta.getFecha())){
                    total = total  + venta.getTotal(); 
                }   
            }     
        }

        System.out.println("Total de ingresos del mes: " + total);
    }

    public void MensuaF(List<Venta> ventas, int mes){
        Double total = 0.0;

        LocalDate firstDayOfMonth = LocalDate.of(2023, mes, 1);

        LocalDate lastDayOfMonth = firstDayOfMonth.with(TemporalAdjusters.lastDayOfMonth());

        for (LocalDate date = firstDayOfMonth; date.isBefore(lastDayOfMonth.plusDays(1)); date = date.plusDays(1)) {
            for(Venta venta : ventas){
                if(date.equals(venta.getFecha())){
                    total = total  + venta.getTotal(); 
                }   
            }
        }

        System.out.println("Total de ingresos del mes: " + total);
    }

    public void Anual(List<Venta> ventas){
        LocalDate today = LocalDate.now();

        Month month = today.getMonth();

        int monthNumber = month.getValue();

        for(int i = 1; i < monthNumber ; i++){

            LocalDate day = LocalDate.of(today.getYear(), i, 1);

            Month mt = day.getMonth();

            String mtname = mt.name();

            System.out.println(mtname);
            MensuaF(ventas, i);
            System.out.println("-------");

        }
    }
}
