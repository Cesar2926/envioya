package envioya.facturacion;

public class ImpuestoFijo implements CalculadoraImpuestos {
    private final double tasa;

    public ImpuestoFijo(double tasa) { this.tasa = tasa; }

    @Override public double aplicar(double subtotal) { return subtotal + subtotal * tasa; }
}
