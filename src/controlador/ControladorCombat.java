package controlador;

import core.Masmorra;
import model.Monstre;
import model.Personatge;
import sala.Sala;
import sala.SalaJefe;
import utils.Colors;
import utils.ConsoleUtils;
import utils.Estils;
import utils.Missatges;
import vista.Vistes;

import java.util.Scanner;

public class ControladorCombat {

  private static final double SEGONS_COMBAT = 1.5;
  private static final double SEGONS_FUGIDA = 2.0;
  private static final double SEGONS_LLARG = 3.0;

  private final Scanner teclado;
  private final Personatge personatge;
  private final Masmorra masmorra;

  private ControladorMoviment controladorMoviment;

  public ControladorCombat(Scanner teclado, Personatge personatge, Masmorra masmorra) {
    this.teclado = teclado;
    this.personatge = personatge;
    this.masmorra = masmorra;
  }

  public void setControladorMoviment(ControladorMoviment controladorMoviment) {
    this.controladorMoviment = controladorMoviment;
  }

  public void iniciarCombat(Sala sala) {
    if (sala.esSalaJefe()) {
      combatreJefe((SalaJefe) sala);
    } else {
      combatre(sala.getMonstre(), sala);
    }
  }

  private boolean combatre(Monstre monstre, Sala sala) {
    boolean victoria = false;

    while (personatge.potLluitar(monstre)) {
      ConsoleUtils.saltarPagina();
      Missatges.mostrarMenuCombat(personatge, monstre);

      String entrada = teclado.nextLine().toUpperCase();
      if (entrada.isEmpty())
        continue;

      switch (entrada.charAt(0)) {
        case 'A' -> victoria = processarAtac(monstre, sala);
        case 'F' -> {
          return processarFugida(monstre);
        }
        default -> Vistes.mostrarError("Opció invàlida!");
      }

      if (!personatge.estaViu() || victoria)
        break;
    }

    return victoria;
  }

  private boolean processarAtac(Monstre monstre, Sala sala) {
    int danyInfligit = personatge.atacar(monstre);
    System.out.println();
    System.out.println(Colors.TARONJA + "Has atacat el " + monstre.getNom()
        + " i has fet " + danyInfligit + " de dany!" + Colors.RESET);

    if (!monstre.estaViu()) {
      processarMonstruDerrotat(monstre, sala);
      return true;
    }

    processarContratac(monstre);
    return false;
  }

  private void processarMonstruDerrotat(Monstre monstre, Sala sala) {
    sala.setMonstre(null);
    personatge.sumarExperiencia(monstre.getValorExperiencia());
    System.out.println(Colors.VERD + Estils.NEGRETA + "Has derrotat el "
        + monstre.getNom() + "!" + Colors.RESET);
    System.out.println(Colors.CIAN + "  +" + monstre.getValorExperiencia()
        + " experiència" + Colors.RESET);
    ConsoleUtils.dormirSegons(SEGONS_COMBAT);
  }

  private void processarContratac(Monstre monstre) {
    int danyRebut = monstre.calcularAtac();
    personatge.rebreDany(danyRebut);
    System.out.println(Colors.VERMELL + "El " + monstre.getNom()
        + " t'ha atacat i has rebut " + danyRebut + " de dany!" + Colors.RESET);
    if (!personatge.estaViu()) {
      System.out.println(Colors.VERMELL + Estils.NEGRETA + "Has mort en combat!" + Colors.RESET);
    }
    ConsoleUtils.dormirSegons(SEGONS_COMBAT);
  }

  private boolean processarFugida(Monstre monstre) {
    int danyFugida = monstre.getVida();
    personatge.rebreDany(danyFugida);
    System.out.println(Colors.VERMELL + "Has intentat fugir i has rebut "
        + danyFugida + " de dany!" + Colors.RESET);

    if (!personatge.estaViu()) {
      ConsoleUtils.saltarPagina();
      System.out.println(Colors.VERMELL + "Has mort intentant escapar..." + Colors.RESET);
      ConsoleUtils.dormirSegons(SEGONS_COMBAT);
      return false;
    }

    ConsoleUtils.saltarPagina("FUGINT...");
    System.out.println(Colors.GROC + "Ràpid! Escull cap a on vols fugir:" + Colors.RESET);

    boolean haFugitAmbExit = controladorMoviment.demanarMoviment();

    if (haFugitAmbExit) {
      System.out.println(Colors.VERD + "Has aconseguit escapar a una altra sala!" + Colors.RESET);
      controladorMoviment.processarSalaActual();
    } else {
      System.out.println(Colors.TARONJA + "No has pogut fugir a una altra sala!" + Colors.RESET);
    }

    ConsoleUtils.dormirSegons(SEGONS_FUGIDA);
    return false;
  }

  private void combatreJefe(SalaJefe sala) {
    Monstre jefe = sala.getMonstre();
    ConsoleUtils.saltarPagina(Colors.VERMELL + Estils.NEGRETA);
    // Missatges.mostrarBannerJefe(jefe);

    System.out.print(Colors.RESET);
    System.out.println(Colors.VERMELL + jefe.getNom() + " et talla el pas!");
    System.out.println("No pots sortir fins que l'hagis derrotat..." + Colors.RESET);
    ConsoleUtils.dormirSegons(SEGONS_LLARG);

    boolean victoria = executarCombatJefe(jefe, sala);

    if (victoria) {
      ConsoleUtils.dormirSegons(SEGONS_FUGIDA);
      Vistes.mostrarVictoriaFinal(personatge, masmorra);
    }
  }

  private boolean executarCombatJefe(Monstre jefe, SalaJefe sala) {
    boolean combatActiu = true;
    boolean victoria = false;

    while (combatActiu && personatge.potLluitar(jefe)) {
      ConsoleUtils.saltarPagina();
      Missatges.mostrarMenuCombatJefe(personatge, jefe);

      String entrada = teclado.nextLine().toUpperCase();
      if (entrada.isEmpty())
        continue;

      switch (entrada.charAt(0)) {
        case 'A' -> {
          victoria = processarAtacJefe(jefe, sala);
          combatActiu = personatge.estaViu() && !victoria;
        }
        case 'F' -> System.out.println(Colors.VERMELL + "No pots fugir fins..." + Colors.RESET);
        default -> Vistes.mostrarError("Opció invàlida!");
      }
    }

    return victoria;
  }

  private boolean processarAtacJefe(Monstre jefe, SalaJefe sala) {
    int danyInfligit = personatge.atacar(jefe);
    System.out.println();
    System.out.println(Colors.TARONJA + "Ataques a " + jefe.getNom()
        + " i fas " + danyInfligit + " de dany!" + Colors.RESET);

    if (!jefe.estaViu()) {
      processarJefeDerrotat(jefe, sala);
      return true;
    }

    processarContratacJefe(jefe);
    return false;
  }

  private void processarJefeDerrotat(Monstre jefe, SalaJefe sala) {
    ConsoleUtils.dormirSegons(SEGONS_COMBAT);
    System.out.println(Colors.VERD + Estils.NEGRETA);
    ConsoleUtils.saltarPagina("  ╔═════════════════════════════════╗");
                  System.out.println("  ║       !! VICTÒRIA FINAL !!      ║");
                  System.out.println("  ╚═════════════════════════════════╝");
    System.out.println(Colors.RESET);

    personatge.sumarExperiencia(jefe.getValorExperiencia());
    System.out.println(Colors.CIAN + "  +" + jefe.getValorExperiencia() + " experiència!" + Colors.RESET);

    if (sala.getTresor() != null) {
      personatge.intentarAfegirTresor(sala.getTresor());
      System.out.println(Colors.GROC + "  Has obtingut: " + sala.getTresor().getNom() + "!" + Colors.RESET);
    }

    sala.setMonstre(null);
    sala.setExplorada(true);
    ConsoleUtils.dormirSegons(SEGONS_COMBAT);
  }

  private void processarContratacJefe(Monstre jefe) {
    int danyRebut = jefe.calcularAtac();
    personatge.rebreDany(danyRebut);
    System.out.println(Colors.VERMELL + jefe.getNom()
        + " t'ataca i reps " + danyRebut + " de dany!" + Colors.RESET);
    if (!personatge.estaViu()) {
      System.out.println(Colors.VERMELL + Estils.NEGRETA
          + jefe.getNom() + " t'ha derrotat!" + Colors.RESET);
    }
    ConsoleUtils.dormirSegons(SEGONS_COMBAT);
  }
}