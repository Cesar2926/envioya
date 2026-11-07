package envioya.transporte;

public class Camion implements Transporte {
    @Override public String getNombre() { return "CAMION"; }
    @Override public void cargar() { System.out.println("Cargando camión en almacén terrestre..."); }
    @Override public double calcularCosto(double peso, double distancia) { return distancia * 0.5 + peso * 1.2; }
}
