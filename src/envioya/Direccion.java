package envioya;

public record Direccion(String calle, String ciudad, String pais) {
    public String formatear() { return calle + ", " + ciudad + " (" + pais.toUpperCase() + ")"; }
}
