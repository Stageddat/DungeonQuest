package configuracio;

import core.Dificultats;
import core.Masmorra;
import model.Atributs;
import model.Personatge;
import utils.Colors;
import utils.ConsoleUtils;
import utils.Estils;
import utils.Missatges;

import java.util.Scanner;

public class ConfiguracioPartida {
  private static final double SEGONS_DIFICULTAT = 3.0;
  private static final double SEGONS_ERROR = 1.5;

  private final Scanner teclado;
  private final boolean debug;

  public ConfiguracioPartida(Scanner teclado, boolean debug) {
    this.teclado = teclado;
    this.debug = debug;
  }

  public Personatge crearPersonatge() {
    String nom = debug ? "" : demanarNom();
    Dificultats dificultat = debug ? Dificultats.NORMAL : demanarDificultat();
    Personatge personatge = new Personatge(nom, dificultat);

    if (!debug) {
      repartirPuntsInicials(personatge, dificultat);
    }

    return personatge;
  }

  public Masmorra crearMasmorra(Personatge personatge) {
    int[] mida = debug ? new int[] { 5, 5 } : demanarMidaMasmorra();
    return new Masmorra(mida[0], mida[1], personatge);
  }

  private String demanarNom() {
    ConsoleUtils.saltarPagina(Estils.TITOL + " === Creació de personatge === " + Colors.RESET);
    System.out.println(Estils.PREGUNTA + "Trieu un nom per al vostre jugador: ");
    System.out.print(Estils.RESPOSTA);
    return teclado.nextLine();
  }

  private Dificultats demanarDificultat() {
    Dificultats dificultat = null;

    while (dificultat == null) {
      Missatges.menuDificultats();
      try {
        int opcio = Integer.parseInt(teclado.nextLine());
        dificultat = Dificultats.desdeNumero(opcio);
        if (dificultat == null)
          mostrarError("Opció no vàlida!");
      } catch (NumberFormatException e) {
        mostrarError("Introdueix un número vàlid!");
      }
    }

    return dificultat;
  }

  private int[] demanarMidaMasmorra() {
    int[] mides = new int[2];
    String[] eixos = { "X (Horitzontal)", "Y (Vertical)" };

    for (int i = 0; i < eixos.length; i++) {
      mides[i] = demanarMidaEix(eixos[i], i == 1 ? mides[0] : -1);
    }

    return mides;
  }

  private int demanarMidaEix(String nomEix, int midaXMostrar) {
    while (true) {
      ConsoleUtils.saltarPagina(Estils.TITOL + "=== Mida de la masmorra ===" + Colors.RESET);

      if (midaXMostrar >= 0) {
        System.out.println("X seleccionada: " + Colors.VERD + midaXMostrar + Colors.RESET + "\n");
      }

      try {
        System.out.println(Estils.PREGUNTA + "Introdueix " + nomEix + ": ");
        System.out.print(Estils.RESPOSTA);
        int valor = Integer.parseInt(teclado.nextLine());

        if (valor >= Masmorra.MIN_MIDA_MASMORRA && valor <= Masmorra.MAX_MIDA_MASMORRA) {
          return valor;
        }

        mostrarError("La mida ha de ser entre "
            + Masmorra.MIN_MIDA_MASMORRA + " i " + Masmorra.MAX_MIDA_MASMORRA + ".");
      } catch (NumberFormatException e) {
        mostrarError("Introdueix un número sencer vàlid.");
      }
    }
  }

  private void repartirPuntsInicials(Personatge personatge, Dificultats dificultat) {
    switch (dificultat) {
      case DIFICIL -> mostrarMissatgeDificultat(personatge,
          "Has escollit la dificultat difícil, el teu personatge començarà amb els punts al mínim.");
      case FACIL -> {
        aplicarStatsFacil(personatge);
        mostrarMissatgeDificultat(personatge,
            "Has escollit la dificultat fàcil, el teu personatge començarà amb els punts al màxim.");
      }
      default -> repartirPunts(personatge);
    }
  }

  private void aplicarStatsFacil(Personatge personatge) {
    personatge.setVida(Atributs.VIDA.getMaxim());
    personatge.setAtac(Atributs.ATAC.getMaxim());
    personatge.setAgilitat(Atributs.AGILITAT.getMaxim());
    personatge.setForsa(Atributs.FORSA.getMaxim());
  }

  private void mostrarMissatgeDificultat(Personatge personatge, String missatge) {
    Missatges.mostrarAtributs(personatge);
    System.out.println(Estils.NEGRETA + Colors.VERMELL + missatge + Colors.RESET);
    ConsoleUtils.dormirSegons(SEGONS_DIFICULTAT);
  }

  public void repartirPunts(Personatge personatge) {
    boolean finalitzar = false;

    while (!finalitzar) {
      Missatges.mostrarAtributs(personatge);
      Missatges.menuRepartirPunts(personatge);

      String entrada = teclado.nextLine().toUpperCase();
      if (entrada.isEmpty())
        continue;

      char opcio = entrada.charAt(0);

      if (opcio == 'Q') {
        finalitzar = true;
        continue;
      }

      Atributs stat = Atributs.desdeLletra(opcio);
      if (stat == null) {
        mostrarError("Opció invàlida!");
        continue;
      }

      if (personatge.getPuntsDisponibles() == 0) {
        mostrarAvis("No tens punts disponibles!");
        continue;
      }

      int quantitat = demanarQuantitatPunts(stat, personatge);
      if (quantitat <= 0)
        continue;

      if (!personatge.aplicarPunts(stat, quantitat)) {
        mostrarAvis("No pots afegir " + quantitat + " a " + stat + "!");
      }
    }
  }

  private int demanarQuantitatPunts(Atributs stat, Personatge personatge) {
    System.out.println(Estils.PREGUNTA + "Quants punts vols afegir a " + stat.name().toUpperCase() + "?");
    System.out.print(Estils.RESPOSTA);

    try {
      int quantitat = Integer.parseInt(teclado.nextLine());

      if (quantitat <= 0) {
        mostrarError("Introdueix un número enter positiu!");
        return -1;
      }
      if (quantitat > personatge.getPuntsDisponibles()) {
        mostrarError("No tens tants punts!");
        return -1;
      }

      return quantitat;
    } catch (NumberFormatException e) {
      mostrarError("Entrada invàlida!");
      return -1;
    }
  }

  private void mostrarError(String missatge) {
    System.out.println(Colors.VERMELL + "⚠ " + missatge + Colors.RESET);
    ConsoleUtils.dormirSegons(SEGONS_ERROR);
  }

  private void mostrarAvis(String missatge) {
    System.out.println(Colors.GROC + "⚠ " + missatge + Colors.RESET);
    ConsoleUtils.dormirSegons(SEGONS_ERROR);
  }
}