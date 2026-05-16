package core;

public enum Dificultats {
  FACIL("Fàcil", 1, 32),
  NORMAL("Normal", 2, 12),
  DIFICIL("Difícil", 3, 0);

  private final String nom;
  private final int numero;
  private final int puntsPerDificultat;

  Dificultats(String nom, int numero, int puntsPerDificultat) {
    this.nom = nom;
    this.numero = numero;
    this.puntsPerDificultat = puntsPerDificultat;
  }

  public static Dificultats desdeNumero(int numero) {
    for (Dificultats dificultat : Dificultats.values()) {
      if (dificultat.numero == numero)
        return dificultat;
    }
    return null;
  }

  public String getNom() {
    return nom;
  }

  public int getPuntsPerDificultat() {
    return puntsPerDificultat;
  }
}
