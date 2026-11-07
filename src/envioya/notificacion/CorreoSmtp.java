package envioya.notificacion;

public class CorreoSmtp implements ServicioCorreo {
    @Override public void enviar(String destinatario, String mensaje) {
        System.out.println("Enviando correo SMTP a " + destinatario + ": " + mensaje);
    }
}
