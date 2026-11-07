package envioya;

import envioya.envio.EmbalajeReforzado;
import envioya.envio.EntregaPrioritaria;
import envioya.envio.Envio;
import envioya.envio.SeguroCarga;
import envioya.facturacion.ImpresoraRecibo;
import envioya.facturacion.ImpuestoFijo;
import envioya.notificacion.CorreoSmtp;
import envioya.notificacion.EstadoEnvio;
import envioya.notificacion.NotificadorEmail;
import envioya.notificacion.ServicioCorreo;
import envioya.transporte.LogisticaAerea;
import envioya.transporte.LogisticaMaritima;
import envioya.transporte.LogisticaTerrestre;
import envioya.transporte.LogisticaTransporte;

public class Main {
    public static void main(String[] args) {
        // Raíz de composición: único lugar donde se eligen las clases concretas.
        ServicioCorreo correo = new CorreoSmtp();
        GestorLogistica gestor = new GestorLogistica(new ImpuestoFijo(0.15), new ImpresoraRecibo(), correo);
        Direccion destino = new Direccion("Av. Guayana, Edif. Torre Loreto", "Puerto Ordaz", "Venezuela");

        System.out.println("=== Caso 1: equivalente al código original (CAMION, 100 kg, 500 km) ===");
        LogisticaTransporte terrestre = new LogisticaTerrestre();
        gestor.procesarEnvio(new OrdenEnvio("ENV-001", terrestre.planificarEnvio(100, 500), "cliente@ejemplo.com", destino));

        System.out.println("\n=== Caso 2: BARCO con Seguro de Carga ===");
        Envio maritimo = new SeguroCarga(new LogisticaMaritima().planificarEnvio(800, 4000));
        gestor.procesarEnvio(new OrdenEnvio("ENV-002", maritimo, "importadora@ejemplo.com", destino));

        System.out.println("\n=== Caso 3: AVION con tres servicios y seguimiento de estado ===");
        Envio aereo = new EntregaPrioritaria(new SeguroCarga(new EmbalajeReforzado(
                new LogisticaAerea().planificarEnvio(20, 3000))));
        OrdenEnvio orden = new OrdenEnvio("ENV-003", aereo, "ana@ejemplo.com", destino);
        orden.eventos().suscribir(new NotificadorEmail("ana@ejemplo.com", correo));
        orden.eventos().suscribir(new NotificadorEmail("almacen@ejemplo.com", correo));
        gestor.procesarEnvio(orden);
        orden.cambiarEstado(EstadoEnvio.EN_CAMINO);
        orden.cambiarEstado(EstadoEnvio.ENTREGADO);
    }
}
