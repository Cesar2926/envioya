package envioya.facturacion;

import envioya.OrdenEnvio;
import java.util.Locale;

public class ImpresoraRecibo {
    public void imprimir(OrdenEnvio orden, double total) {
        System.out.println("--- RECIBO DE ENVÍO " + orden.getCodigo() + " ---");
        System.out.println(orden.getEnvio().getDescripcion());
        System.out.println("Destino: " + orden.getDestino().formatear());
        System.out.println(String.format(Locale.US, "Costo Final: $%.2f", total));
    }
}
