package envioya.transporte;

import envioya.envio.Envio;
import envioya.envio.EnvioBase;

/** Creador del Factory Method. La planificación no conoce la clase concreta del transporte. */
public abstract class LogisticaTransporte {

    protected abstract Transporte crearTransporte();

    public Envio planificarEnvio(double peso, double distancia) {
        Transporte transporte = crearTransporte();
        transporte.cargar();
        return new EnvioBase(transporte, peso, distancia);
    }
}
