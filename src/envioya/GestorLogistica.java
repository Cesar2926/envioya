package envioya;

import envioya.facturacion.CalculadoraImpuestos;
import envioya.facturacion.ImpresoraRecibo;
import envioya.notificacion.ServicioCorreo;
import java.util.Locale;

/** Orquestador: ya no elige transportes ni calcula, imprime o envía por sí mismo; solo coordina. */
public class GestorLogistica {
    private final CalculadoraImpuestos impuestos;
    private final ImpresoraRecibo impresora;
    private final ServicioCorreo correo;

    public GestorLogistica(CalculadoraImpuestos impuestos, ImpresoraRecibo impresora, ServicioCorreo correo) {
        this.impuestos = impuestos;
        this.impresora = impresora;
        this.correo = correo;
    }

    public double procesarEnvio(OrdenEnvio orden) {
        double total = impuestos.aplicar(orden.getEnvio().getCosto());
        impresora.imprimir(orden, total);
        correo.enviar(orden.getEmailCliente(),
                String.format(Locale.US, "Confirmación de envío %s por un monto de $%.2f", orden.getCodigo(), total));
        return total;
    }
}
