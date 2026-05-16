package vista;

import core.Masmorra;
import model.Personatge;
import sala.Sala;
import utils.Colors;
import utils.ConsoleUtils;
import utils.Estils;
import utils.Missatges;

import java.util.ArrayList;
import model.Tresor;

public class Vistes {

  private static final int PASSOS_BARRA = 10;
  private static final int AMPLADA_BARRA = 30;
  private static final double SEGONS_PAS = 0.1;
  private static final double SEGONS_CURT = 1.0;
  private static final double SEGONS_LLARG = 3.0;
  private static final double SEGONS_FINAL = 5.0;

  private static final int EXPERIENCIA_TRESOR = 5;
  private static final int EXPERIENCIA_BUIDA = 1;

  public static void mostrarMapaAmbStats(Personatge personatge, Masmorra masmorra) {
    String[] mapa = masmorra.generarLiniesMapa(personatge);
    String[] stats = Missatges.generarLiniesStats(personatge);
    int maxLinies = Math.max(mapa.length, stats.length);

    for (int i = 0; i < maxLinies; i++) {
      String columnaMapa = (i < mapa.length) ? mapa[i] : " ".repeat(masmorra.getY() * 5);
      String columnaStats = (i < stats.length) ? stats[i] : "";
      System.out.println(columnaMapa + "       " + columnaStats);
    }
  }

  public static void mostrarInventari(Personatge personatge) {
    ConsoleUtils.saltarPagina(Estils.TITOL + "=== Inventari ===" + Colors.RESET);
    ArrayList<Tresor> equipament = personatge.getEquipament();

    if (equipament == null || equipament.isEmpty()) {
      System.out.println(Colors.GRIS + "  No tens cap tresor." + Colors.RESET);
    } else {
      mostrarLlistaTresors(equipament);
    }

    System.out.println();
    ConsoleUtils.dormirSegons(SEGONS_LLARG);
    ConsoleUtils.saltarPagina();
  }

  private static void mostrarLlistaTresors(ArrayList<Tresor> equipament) {
    System.out.println(Colors.GRIS + "  Tresors:" + Colors.RESET);

    int i = 1;
    for (Tresor tresor : equipament) {
      String index = String.format("%s[%d]%s", Colors.GRIS, i, Colors.RESET);
      String nom = Colors.BLANC + tresor.getNom() + Colors.RESET;
      String pes = String.format("%s%.1f g%s", Colors.GROC, tresor.getPes(), Colors.RESET);
      String valor = String.format("%s%d or%s", Colors.TARONJA, tresor.getValor(), Colors.RESET);
      System.out.println("  " + index + " " + nom + " - " + pes + " - " + valor);
      i++;
    }
  }

  public static void mostrarBarraExploracio() {
    for (int pas = 0; pas < PASSOS_BARRA; pas++) {
      ConsoleUtils.saltarPagina(Estils.TITOL + "=== Explorar la sala ===" + Colors.RESET);
      Missatges.mostrarBarra("Explorant la sala...", Colors.TARONJA, pas, PASSOS_BARRA, AMPLADA_BARRA, false);
      ConsoleUtils.dormirSegons(SEGONS_PAS);
    }
  }

  public static void mostrarResultatExploracio(Personatge personatge, Sala sala) {
    ConsoleUtils.saltarPagina(Estils.TITOL + "=== Sala explorada ===" + Colors.RESET);
    if (sala.getTresor() != null) {
      System.out.println(Colors.GROC + "Has trobat un tresor: "
          + sala.getTresor().getNom() + "!" + Colors.RESET);
      personatge.sumarExperiencia(EXPERIENCIA_TRESOR);
    } else {
      System.out.println(Colors.GRIS + "No hi ha res d'interessant aquí." + Colors.RESET);
      personatge.sumarExperiencia(EXPERIENCIA_BUIDA);
    }
    ConsoleUtils.dormirSegons(SEGONS_CURT);
  }

  public static void mostrarSalaJaExplorada() {
    ConsoleUtils.saltarPagina(Estils.TITOL + "=== Sala explorada ===" + Colors.RESET);
    System.out.println(Colors.GRIS + "Ja has explorat aquesta sala." + Colors.RESET);
    ConsoleUtils.dormirSegons(SEGONS_CURT);
  }

  public static void mostrarGameOver(Personatge personatge, Masmorra masmorra) {
    ConsoleUtils.saltarPagina();
    System.out.println(Colors.VERMELL + Estils.NEGRETA);
    System.out.println("  ╔═════════════════════════════════╗");
    System.out.println("  ║          GAME  OVER             ║");
    System.out.println("  ╚═════════════════════════════════╝");
    System.out.println(Colors.RESET);
    mostrarResum(personatge, masmorra);
  }

  public static void mostrarVictoriaFinal(Personatge personatge, Masmorra masmorra) {
    ConsoleUtils.saltarPagina();
    System.out.println(Colors.GROC + Estils.NEGRETA);
    System.out.println("  ╔═════════════════════════════════╗");
    System.out.println("  ║           HAS GUANYAT!          ║");
    System.out.println("  ╚═════════════════════════════════╝");
    System.out.println(Colors.RESET);
    mostrarResum(personatge, masmorra);
    ConsoleUtils.dormirSegons(SEGONS_FINAL);
  }

  private static void mostrarResum(Personatge personatge, Masmorra masmorra) {
    System.out.println(Colors.GRIS + "  Heroi: " + Colors.BLANC + personatge.getNom());
    System.out.println(Colors.GRIS + "  Nivell assolit: " + Colors.BLANC + personatge.getLevel());
    System.out.println(Colors.GRIS + "  Experiència: " + Colors.CIAN + personatge.getExperiencia());
    System.out.println(Colors.GRIS + "  Sales explorades: " + Colors.BLANC
        + masmorra.getPercentatgeSalesExplorades() + "%" + Colors.RESET);
    System.out.println();
    ConsoleUtils.dormirSegons(SEGONS_LLARG);
    System.exit(0);

  }

  public static void mostrarError(String missatge) {
    System.out.println(Colors.VERMELL + "⚠ " + missatge + Colors.RESET);
    ConsoleUtils.dormirSegons(SEGONS_CURT);
  }

  public static void mostrarAvis(String missatge) {
    System.out.println(Colors.GROC + "⚠ " + missatge + Colors.RESET);
    ConsoleUtils.dormirSegons(SEGONS_CURT);
  }
}