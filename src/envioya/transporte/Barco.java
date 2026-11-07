package envioya.transporte;

public class Barco implements Transporte {
    @Override public String getNombre() { return "BARCO"; }
    @Override public void cargar() { System.out.println("Cargando contenedor en puerto marítimo..."); }
    @Override public double calcularCosto(double peso, double distancia) { return distancia * 0.3 + peso * 2.5; }
}
