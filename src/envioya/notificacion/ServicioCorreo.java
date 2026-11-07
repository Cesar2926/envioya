package envioya.notificacion;

/** Abstracción del canal de correo (DIP): SMTP, API externa, doble de pruebas... */
public interface ServicioCorreo {
    void enviar(String destinatario, String mensaje);
}
