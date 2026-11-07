package envioya.notificacion;

public enum EstadoEnvio {
    REGISTRADO("Registrado"), EN_CAMINO("En Camino"), ENTREGADO("Entregado");

    private final String etiqueta;
    EstadoEnvio(String etiqueta) { this.etiqueta = etiqueta; }
    @Override public String toString() { return etiqueta; }
}
