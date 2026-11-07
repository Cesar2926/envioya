package envioya.envio;

public class SeguroCarga extends ServicioAdicional {
    public SeguroCarga(Envio envio) { super(envio); }

    @Override public String getDescripcion() { return envio.getDescripcion() + " + Seguro de Carga"; }
    // 5 % sobre lo acumulado hasta este decorador: el orden de envoltura importa.
    @Override public double getCosto() { return envio.getCosto() * 1.05; }
}
