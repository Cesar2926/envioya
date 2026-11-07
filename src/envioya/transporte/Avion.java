package envioya.transporte;

public class Avion implements Transporte {
    @Override public String getNombre() { return "AVION"; }
    @Override public void cargar() { System.out.println("Cargando paletas en terminal de carga aérea..."); }
    // Tarifa supuesta: el enunciado no la define.
    @Override public double calcularCosto(double peso, double distancia) { return distancia * 0.9 + peso * 5.0; }
}
