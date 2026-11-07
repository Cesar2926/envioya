package envioya.transporte;

public class LogisticaTerrestre extends LogisticaTransporte {
    @Override protected Transporte crearTransporte() { return new Camion(); }
}
