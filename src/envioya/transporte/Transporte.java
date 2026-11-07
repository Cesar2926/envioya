package envioya.transporte;

/** Producto del Factory Method: lo que todo medio de transporte sabe hacer. */
public interface Transporte {
    String getNombre();
    void cargar();
    double calcularCosto(double peso, double distancia);
}
