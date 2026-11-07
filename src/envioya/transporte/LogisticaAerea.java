package envioya.transporte;

public class LogisticaAerea extends LogisticaTransporte {
    @Override protected Transporte crearTransporte() { return new Avion(); }
}
