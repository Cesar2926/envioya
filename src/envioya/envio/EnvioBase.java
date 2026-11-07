package envioya.envio;

import envioya.transporte.Transporte;

/** Componente concreto: el envío sin servicios adicionales. */
public class EnvioBase implements Envio {
    private final Transporte transporte;
    private final double peso;
    private final double distancia;

    public EnvioBase(Transporte transporte, double peso, double distancia) {
        if (peso <= 0 || distancia <= 0) throw new IllegalArgumentException("Peso y distancia deben ser positivos");
        this.transporte = transporte;
        this.peso = peso;
        this.distancia = distancia;
    }

    @Override public String getDescripcion() { return "Transporte: " + transporte.getNombre(); }
    @Override public double getCosto() { return transporte.calcularCosto(peso, distancia); }
}
