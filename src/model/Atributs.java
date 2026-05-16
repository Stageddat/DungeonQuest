package model;

public enum Atributs {
  VIDA("Vida", 'v', 5, 20),
  ATAC("Atac", 'a', 1, 4),
  AGILITAT("Agilitat", 'g', 4, 11),
  FORSA("Força", 'f', 1, 11),
  EXPERIENCIA("Experiencia", 'e', 0, 999);

  private final String nom;
  private final char lletra;
  private final int minimInicial;
  private final int maxim;

  Atributs(String nom, char lletra, int minimInicial, int maxim) {
    this.nom = nom;
    this.lletra = lletra;
    this.minimInicial = minimInicial;
    this.maxim = maxim;
  }

  public static Atributs desdeLletra(char lletra) {
    char lletraLower = Character.toLowerCase(lletra);
    for (Atributs a : Atributs.values()) {
      if (a.lletra == lletraLower)
        return a;
    }
    return null;
  }

  public String getNom() {
    return nom;
  }

  public char getLletra() {
    return lletra;
  }

  public int getMinimInicial() {
    return minimInicial;
  }

  public int getMaxim() {
    return maxim;
  }
}