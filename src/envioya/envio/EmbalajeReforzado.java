package envioya.envio;

public class EmbalajeReforzado extends ServicioAdicional {
    public EmbalajeReforzado(Envio envio) { super(envio); }

    @Override public String getDescripcion() { return envio.getDescripcion() + " + Embalaje Reforzado"; }
    @Override public double getCosto() { return envio.getCosto() + 25.0; }
}
