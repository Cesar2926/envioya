package envioya.notificacion;

/** Observador: cualquiera interesado en los cambios de estado de un envío. */
public interface ObservadorEnvio {
    void actualizar(String codigoEnvio, EstadoEnvio nuevoEstado);
}
