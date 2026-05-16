package controlador;

import core.Masmorra;
import model.Direccions;
import model.Personatge;
import sala.Sala;
import sala.SalaPont;
import sala.SalaTeranyina;
import utils.Colors;
import utils.ConsoleUtils;
import utils.Estils;
import vista.Vistes;

import java.util.Scanner;

public class ControladorMoviment {

  private static final double SEGONS_ERROR = 1.0;

  private final Scanner teclado;
  private final Personatge personatge;
  private final Masmorra masmorra;

  private ControladorCombat controladorCombat;

  public ControladorMoviment(Scanner teclado, Personatge personatge, Masmorra masmorra) {
    this.teclado = teclado;
    this.personatge = personatge;
    this.masmorra = masmorra;
  }

  public void setControladorCombat(ControladorCombat controladorCombat) {
    this.controladorCombat = controladorCombat;
  }

  public void processarMoure() {
    if (demanarMoviment()) {
      processarSalaActual();
    }
  }

  public boolean demanarMoviment() {
    Sala salaActual = masmorra.getSalaActual();

    if (!salaActual.intentarSortir()) {
      mostrarMissatgeNoPodenSortir(salaActual);
      return false;
    }

    ConsoleUtils.saltarPagina();
    Vistes.mostrarMapaAmbStats(personatge, masmorra);
    System.out.println("\n" + Estils.TITOL + "=== Moure per la masmorra ===" + Colors.RESET);
    System.out.println(Estils.PREGUNTA + "Escull una direcció (W/A/S/D): " + Colors.RESET);
    System.out.print(Estils.RESPOSTA);

    String entrada = teclado.nextLine().toUpperCase();
    if (entrada.isEmpty())
      return false;

    Direccions direccio = parsearDireccio(entrada.charAt(0));
    if (direccio == null) {
      Vistes.mostrarError("Opció invàlida!");
      return false;
    }

    int[] desti = masmorra.calcularNovaPosicio(personatge.getPosicio(), direccio);
    if (desti == null) {
      Vistes.mostrarError("No pots sortir de la masmorra!");
      return false;
    }

    personatge.moure(desti);
    return true;
  }

  public void processarSalaActual() {
    Sala sala = masmorra.getSalaActual();
    if (!sala.teMonstre())
      return;

    System.out.println(Colors.VERMELL + Estils.NEGRETA
        + "⚠ Has entrat a una sala amb un " + sala.getMonstre().getNom() + "!"
        + Colors.RESET);
    ConsoleUtils.dormirSegons(SEGONS_ERROR);
    controladorCombat.iniciarCombat(sala);
  }

  public void explorarSala() {
    Sala sala = masmorra.getSalaActual();

    if (sala.teMonstre()) {
      System.out.println(Colors.VERMELL + "⚠ Hi ha un monstre!" + Colors.RESET);
      ConsoleUtils.dormirSegons(SEGONS_ERROR);
      controladorCombat.iniciarCombat(sala);
      return;
    }

    if (sala.estaExplorada()) {
      Vistes.mostrarSalaJaExplorada();
      return;
    }

    Vistes.mostrarBarraExploracio();
    personatge.explorar(sala);
    masmorra.sumarSalesExplorades();
    Vistes.mostrarResultatExploracio(personatge, sala);
  }

  private Direccions parsearDireccio(char opcio) {
    return switch (opcio) {
      case 'W' -> Direccions.NORD;
      case 'S' -> Direccions.SUD;
      case 'A' -> Direccions.OEST;
      case 'D' -> Direccions.EST;
      default -> null;
    };
  }

  private void mostrarMissatgeNoPodenSortir(Sala sala) {
    if (sala instanceof SalaPont) {
      System.out.println(Colors.TARONJA + "⚠ T'has caigut del pont!" + Colors.RESET);
    } else if (sala instanceof SalaTeranyina) {
      System.out.println(Colors.TARONJA + "⚠ Hi ha masses teranyines!" + Colors.RESET);
    } else {
      System.out.println(Colors.VERMELL + "⚠ No pots sortir d'aquesta sala!" + Colors.RESET);
    }
    ConsoleUtils.dormirSegons(SEGONS_ERROR);
  }
}