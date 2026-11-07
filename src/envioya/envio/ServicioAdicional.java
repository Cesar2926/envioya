package envioya.envio;

/** Decorador base: envuelve un Envio y le delega por defecto. */
public abstract class ServicioAdicional implements Envio {
    protected final Envio envio;

    protected ServicioAdicional(Envio envio) { this.envio = envio; }

    @Override public String getDescripcion() { return envio.getDescripcion(); }
    @Override public double getCosto() { return envio.getCosto(); }
}
