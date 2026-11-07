package envioya;

import envioya.envio.Envio;
import envioya.notificacion.EstadoEnvio;
import envioya.notificacion.GestorEventos;

/** Sujeto del Observer: cambia de estado y delega la difusión en GestorEventos. */
public class OrdenEnvio {
    private final String codigo;
    private final Envio envio;
    private final String emailCliente;
    private final Direccion destino;
    private final GestorEventos eventos = new GestorEventos();
    private EstadoEnvio estado = EstadoEnvio.REGISTRADO;

    public OrdenEnvio(String codigo, Envio envio, String emailCliente, Direccion destino) {
        this.codigo = codigo;
        this.envio = envio;
        this.emailCliente = emailCliente;
        this.destino = destino;
    }

    public void cambiarEstado(EstadoEnvio nuevo) {
        if (nuevo == estado) return;
        estado = nuevo;
        eventos.notificar(codigo, nuevo);
    }

    public GestorEventos eventos() { return eventos; }
    public String getCodigo() { return codigo; }
    public Envio getEnvio() { return envio; }
    public String getEmailCliente() { return emailCliente; }
    public Direccion getDestino() { return destino; }
    public EstadoEnvio getEstado() { return estado; }
}
