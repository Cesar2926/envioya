package envioya.notificacion;

/** Observador concreto: avisa por correo a un cliente interesado. */
public class NotificadorEmail implements ObservadorEnvio {
    private final String email;
    private final ServicioCorreo correo;

    public NotificadorEmail(String email, ServicioCorreo correo) {
        this.email = email;
        this.correo = correo;
    }

    @Override public void actualizar(String codigoEnvio, EstadoEnvio nuevoEstado) {
        correo.enviar(email, "Su envío " + codigoEnvio + " cambió a \"" + nuevoEstado + "\"");
    }
}
