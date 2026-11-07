package envioya.transporte;

public class LogisticaMaritima extends LogisticaTransporte {
    @Override protected Transporte crearTransporte() { return new Barco(); }
}
