package envioya.envio;

public class EntregaPrioritaria extends ServicioAdicional {
    public EntregaPrioritaria(Envio envio) { super(envio); }

    @Override public String getDescripcion() { return envio.getDescripcion() + " + Entrega Prioritaria"; }
    @Override public double getCosto() { return envio.getCosto() * 1.20; }
}
