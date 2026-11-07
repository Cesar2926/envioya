"""Genera docs/diagrama_clases.png. Uso: python docs/diagrama_uml.py"""
import os
import matplotlib
matplotlib.use("Agg")
import matplotlib.pyplot as plt
from matplotlib.patches import Rectangle, Polygon

FS, LH, CW, PAD = 8.5, 1.75, 0.71, 1.4  # tamaño de letra, alto de línea, ancho de carácter, relleno (unidades = 0.1 in)

# nombre: (cx, cy, estereotipo, abstracta, miembros)  —  "--" separa atributos de métodos
C = {
    # Factory Method
    "LogisticaTransporte": (45, 200, "abstract", True, ["--", "#crearTransporte(): Transporte", "+planificarEnvio(peso, distancia): Envio"]),
    "LogisticaTerrestre": (15, 152, None, False, ["--", "#crearTransporte(): Transporte", "  { return new Camion() }"]),
    "LogisticaMaritima": (49, 152, None, False, ["--", "#crearTransporte(): Transporte", "  { return new Barco() }"]),
    "LogisticaAerea": (83, 152, None, False, ["--", "#crearTransporte(): Transporte", "  { return new Avion() }"]),
    "Transporte": (135, 182, "interface", False, ["--", "+getNombre(): String", "+cargar(): void", "+calcularCosto(peso, distancia): double"]),
    "Camion": (110, 135, None, False, ["--", "+cargar()", "+calcularCosto(..)"]),
    "Barco": (135, 135, None, False, ["--", "+cargar()", "+calcularCosto(..)"]),
    "Avion": (160, 135, None, False, ["--", "+cargar()", "+calcularCosto(..)"]),
    # Decorator
    "Envio": (252, 205, "interface", False, ["--", "+getDescripcion(): String", "+getCosto(): double"]),
    "EnvioBase": (210, 168, None, False, ["-transporte: Transporte", "-peso: double", "-distancia: double", "--", "+getDescripcion(): String", "+getCosto(): double"]),
    "ServicioAdicional": (288, 168, "abstract", True, ["#envio: Envio", "--", "+getDescripcion(): String", "+getCosto(): double"]),
    "EmbalajeReforzado": (250, 130, None, False, ["--", "+getDescripcion()", "+getCosto()  // +25"]),
    "SeguroCarga": (280, 130, None, False, ["--", "+getDescripcion()", "+getCosto()  // x1.05"]),
    "EntregaPrioritaria": (308, 130, None, False, ["--", "+getDescripcion()", "+getCosto()  // x1.20"]),
    # Observer + orden de envío
    "OrdenEnvio": (220, 88, None, False, ["-codigo: String", "-envio: Envio", "-emailCliente: String", "-destino: Direccion", "-estado: EstadoEnvio", "-eventos: GestorEventos", "--", "+cambiarEstado(nuevo: EstadoEnvio): void", "+eventos(): GestorEventos"]),
    "GestorEventos": (290, 88, None, False, ["-observadores: List<ObservadorEnvio>", "--", "+suscribir(o: ObservadorEnvio)", "+desuscribir(o: ObservadorEnvio)", "+notificar(codigo, estado)"]),
    "ObservadorEnvio": (290, 48, "interface", False, ["--", "+actualizar(codigo: String,", "    estado: EstadoEnvio): void"]),
    "NotificadorEmail": (290, 14, None, False, ["-email: String", "-correo: ServicioCorreo", "--", "+actualizar(codigo, estado)"]),
    "EstadoEnvio": (196, 45, "enumeration", False, ["REGISTRADO", "EN_CAMINO", "ENTREGADO"]),
    "Direccion": (240, 45, "record", False, ["-calle, ciudad, pais: String", "--", "+formatear(): String"]),
    "ServicioCorreo": (210, 12, "interface", False, ["--", "+enviar(dest, mensaje): void"]),
    # Coordinación (SRP / DIP)
    "Main": (22, 95, None, False, ["--", "+main(args: String[])", "// raíz de composición"]),
    "GestorLogistica": (80, 95, None, False, ["-impuestos: CalculadoraImpuestos", "-impresora: ImpresoraRecibo", "-correo: ServicioCorreo", "--", "+procesarEnvio(orden: OrdenEnvio): double"]),
    "CalculadoraImpuestos": (35, 52, "interface", False, ["--", "+aplicar(subtotal): double"]),
    "ImpuestoFijo": (35, 15, None, False, ["-tasa: double", "--", "+aplicar(subtotal): double"]),
    "ImpresoraRecibo": (95, 52, None, False, ["--", "+imprimir(orden, total): void"]),
    "CorreoSmtp": (150, 12, None, False, ["--", "+enviar(dest, mensaje): void"]),
}

# (origen, destino, tipo, etiqueta, desplazamiento x en origen, desplazamiento x en destino)
R = [
    ("LogisticaTerrestre", "LogisticaTransporte", "gen"), ("LogisticaMaritima", "LogisticaTransporte", "gen"),
    ("LogisticaAerea", "LogisticaTransporte", "gen"),
    ("Camion", "Transporte", "real"), ("Barco", "Transporte", "real"), ("Avion", "Transporte", "real"),
    ("LogisticaTransporte", "Transporte", "dep", "«create»"),
    ("LogisticaTransporte", "Envio", "dep", "«create» EnvioBase"),
    ("EnvioBase", "Envio", "real"), ("ServicioAdicional", "Envio", "real", None, -6, 6),
    ("ServicioAdicional", "Envio", "agg", "envuelve 1", 6, 14),
    ("EnvioBase", "Transporte", "assoc", "1"),
    ("EmbalajeReforzado", "ServicioAdicional", "gen"), ("SeguroCarga", "ServicioAdicional", "gen"),
    ("EntregaPrioritaria", "ServicioAdicional", "gen"),
    ("OrdenEnvio", "Envio", "assoc", "1"), ("OrdenEnvio", "GestorEventos", "comp", "1"),
    ("GestorEventos", "ObservadorEnvio", "agg", "0..*"),
    ("NotificadorEmail", "ObservadorEnvio", "real"), ("NotificadorEmail", "ServicioCorreo", "assoc"),
    ("OrdenEnvio", "EstadoEnvio", "assoc"), ("OrdenEnvio", "Direccion", "assoc"),
    ("CorreoSmtp", "ServicioCorreo", "real"),
    ("Main", "GestorLogistica", "dep", "«create»"),
    ("GestorLogistica", "OrdenEnvio", "dep", "procesa"),
    ("GestorLogistica", "CalculadoraImpuestos", "assoc"), ("GestorLogistica", "ImpresoraRecibo", "assoc"),
    ("GestorLogistica", "ServicioCorreo", "assoc"),
    ("ImpuestoFijo", "CalculadoraImpuestos", "real"),
]

REGIONES = [
    ((0, 118, 172, 100), "Factory Method — creación de transportes", "#e8f0fb"),
    ((178, 118, 142, 100), "Decorator — servicios adicionales", "#eaf6ea"),
    ((178, 0, 142, 112), "Observer — seguimiento del estado", "#fdf1e3"),
    ((0, 0, 172, 112), "Coordinación (SRP / DIP)", "#f3eefa"),
]


def caja(nombre):
    cx, cy, est, abstracta, miembros = C[nombre]
    cab = ([f"«{est}»"] if est else []) + [nombre]
    filas = cab + (["--"] if "--" not in miembros or miembros[0] != "--" else []) + miembros
    ancho = max(len(t) for t in filas if t != "--") * CW + 2 * PAD
    alto = sum(0.6 if t == "--" else LH for t in filas) + PAD
    return cx, cy, ancho / 2, alto / 2, cab, filas, abstracta


def dibujar_caja(ax, nombre):
    cx, cy, hw, hh, cab, filas, abstracta = caja(nombre)
    ax.add_patch(Rectangle((cx - hw, cy - hh), 2 * hw, 2 * hh, fc="white", ec="#333", lw=1.1, zorder=3))
    y = cy + hh - PAD / 2
    for i, t in enumerate(filas):
        if t == "--":
            y -= 0.3
            ax.plot([cx - hw, cx + hw], [y, y], color="#333", lw=0.8, zorder=4)
            y -= 0.3
            continue
        y -= LH / 2
        if i < len(cab):
            es_nombre = i == len(cab) - 1
            ax.text(cx, y, t, ha="center", va="center", fontsize=FS + (1 if es_nombre else 0), family="DejaVu Sans Mono",
                    weight="bold" if es_nombre else "normal", style="italic" if (es_nombre and abstracta) else "normal", zorder=5)
        else:
            ax.text(cx - hw + PAD, y, t, ha="left", va="center", fontsize=FS, family="DejaVu Sans Mono", zorder=5)
        y -= LH / 2


def borde(nombre, hacia, dx=0):
    cx, cy, hw, hh, *_ = caja(nombre)
    cx += dx
    vx, vy = hacia[0] - cx, hacia[1] - cy
    t = min(hw / abs(vx) if vx else 1e9, hh / abs(vy) if vy else 1e9)
    return cx + vx * t, cy + vy * t


def punta(ax, p, q, forma, relleno):
    # forma en el punto q, apuntando de p a q
    import math
    ang = math.atan2(q[1] - p[1], q[0] - p[0])
    L, W = 3.2, 1.6
    if forma == "tri":
        pts = [(0, 0), (-L, W), (-L, -W)]
    elif forma == "rombo":
        pts = [(0, 0), (-L, W * 0.9), (-2 * L, 0), (-L, -W * 0.9)]
    c, s = math.cos(ang), math.sin(ang)
    pts = [(q[0] + x * c - y * s, q[1] + x * s + y * c) for x, y in pts]
    ax.add_patch(Polygon(pts, closed=True, fc=relleno, ec="#333", lw=1, zorder=6))
    return pts


def flecha_abierta(ax, p, q):
    import math
    ang = math.atan2(q[1] - p[1], q[0] - p[0])
    for d in (0.45, -0.45):
        ax.plot([q[0], q[0] - 2.6 * math.cos(ang + d)], [q[1], q[1] - 2.6 * math.sin(ang + d)], color="#333", lw=1, zorder=6)


def dibujar_relacion(ax, r):
    src, dst, tipo = r[:3]
    etiqueta = r[3] if len(r) > 3 else None
    dxs, dxd = (r[4], r[5]) if len(r) > 5 else (0, 0)
    cs, cd = C[src][:2], C[dst][:2]
    p = borde(src, (cd[0] + dxd, cd[1]), dxs)
    q = borde(dst, (cs[0] + dxs, cs[1]), dxd)
    ls = "--" if tipo in ("real", "dep") else "-"
    ax.plot([p[0], q[0]], [p[1], q[1]], ls=ls, color="#333", lw=1, zorder=2)
    if tipo in ("gen", "real"):
        punta(ax, p, q, "tri", "white")
    elif tipo in ("assoc", "dep"):
        flecha_abierta(ax, p, q)
    elif tipo in ("agg", "comp"):
        punta(ax, q, p, "rombo", "white" if tipo == "agg" else "#333")
        flecha_abierta(ax, p, q)
    if etiqueta:
        mx, my = (p[0] + q[0]) / 2, (p[1] + q[1]) / 2
        ax.text(mx + 0.8, my + 0.8, etiqueta, fontsize=FS - 0.5, style="italic", color="#444", zorder=7,
                bbox=dict(fc="white", ec="none", pad=0.5, alpha=0.85))


def main():
    fig = plt.figure(figsize=(32, 22.4))
    ax = fig.add_axes([0, 0, 1, 1])
    ax.set_xlim(-2, 322)
    ax.set_ylim(-4, 220)
    ax.axis("off")
    for (x, y, w, h), titulo, color in REGIONES:
        ax.add_patch(Rectangle((x, y), w, h, fc=color, ec="#999", lw=1, ls=":", zorder=0))
        ax.text(x + 1.5, y + h - 2.5, titulo, fontsize=12, weight="bold", color="#555", zorder=1)
    for r in R:
        dibujar_relacion(ax, r)
    for n in C:
        dibujar_caja(ax, n)
    ax.text(161, -2.5, "EnvíoYa (MRW) — Diagrama de clases de la solución rediseñada", ha="center", fontsize=13, weight="bold")
    salida = os.path.join(os.path.dirname(os.path.abspath(__file__)), "diagrama_clases.png")
    fig.savefig(salida, dpi=110, facecolor="white")
    print(salida)


if __name__ == "__main__":
    main()
