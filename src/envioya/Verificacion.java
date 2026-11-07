package envioya;

import envioya.envio.EmbalajeReforzado;
import envioya.envio.EntregaPrioritaria;
import envioya.envio.SeguroCarga;
import envioya.facturacion.ImpuestoFijo;
import envioya.notificacion.EstadoEnvio;
import envioya.transporte.LogisticaAerea;
import envioya.transporte.LogisticaMaritima;
import envioya.transporte.LogisticaTerrestre;
import java.util.ArrayList;
import java.util.List;

/** Autoverificación sin dependencias externas: java -cp out envioya.Verificacion */
public class Verificacion {
    public static void main(String[] args) {
        ImpuestoFijo iva = new ImpuestoFijo(0.15);
        // Mismos resultados que el GestorLogistica original.
        igual(425.5, iva.aplicar(new LogisticaTerrestre().planificarEnvio(100, 500).getCosto()));
        igual(1322.5, iva.aplicar(new LogisticaMaritima().planificarEnvio(100, 3000).getCosto()));
        // Decoradores: (2800 + 25) * 1.05 * 1.20
        igual(3559.5, new EntregaPrioritaria(new SeguroCarga(new EmbalajeReforzado(
                new LogisticaAerea().planificarEnvio(20, 3000)))).getCosto());
        // Observer: dos suscriptores, dos cambios reales (el repetido no notifica).
        List<String> recibidos = new ArrayList<>();
        OrdenEnvio orden = new OrdenEnvio("T", new LogisticaTerrestre().planificarEnvio(1, 1), "x@x", null);
        orden.eventos().suscribir((c, e) -> recibidos.add("a:" + e));
        orden.eventos().suscribir((c, e) -> recibidos.add("b:" + e));
        orden.cambiarEstado(EstadoEnvio.EN_CAMINO);
        orden.cambiarEstado(EstadoEnvio.EN_CAMINO);
        orden.cambiarEstado(EstadoEnvio.ENTREGADO);
        if (!recibidos.equals(List.of("a:En Camino", "b:En Camino", "a:Entregado", "b:Entregado")))
            throw new AssertionError(recibidos.toString());
        System.out.println("OK: todas las verificaciones pasaron");
    }

    private static void igual(double esperado, double real) {
        if (Math.abs(esperado - real) > 1e-6) throw new AssertionError("esperado " + esperado + " pero fue " + real);
    }
}
