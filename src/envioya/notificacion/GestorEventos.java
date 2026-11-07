package envioya.notificacion;

import java.util.ArrayList;
import java.util.List;

/** Mantiene la lista de suscriptores para que la orden de envío no tenga que hacerlo. */
public class GestorEventos {
    private final List<ObservadorEnvio> observadores = new ArrayList<>();

    public void suscribir(ObservadorEnvio o) { observadores.add(o); }
    public void desuscribir(ObservadorEnvio o) { observadores.remove(o); }

    public void notificar(String codigoEnvio, EstadoEnvio estado) {
        for (ObservadorEnvio o : List.copyOf(observadores)) o.actualizar(codigoEnvio, estado);
    }
}
