package core;

import configuracio.ConfiguracioPartida;
import controlador.ControladorCombat;
import controlador.ControladorMenu;
import controlador.ControladorMoviment;
import model.Accions;
import model.Personatge;
import utils.ConsoleUtils;
import vista.Vistes;

import java.util.Scanner;

public class GestorJoc {
  private final Scanner teclado;
  private final Personatge personatge;
  private final Masmorra masmorra;
  private final ControladorCombat controladorCombat;
  private final ControladorMoviment controladorMoviment;
  private final ControladorMenu controladorMenu;
  private final ConfiguracioPartida configuracio;

  public GestorJoc(Scanner teclado, Personatge personatge, Masmorra masmorra) {
    this.teclado = teclado;
    this.personatge = personatge;
    this.masmorra = masmorra;
    this.configuracio = new ConfiguracioPartida(teclado, false);

    this.controladorCombat = new ControladorCombat(teclado, personatge, masmorra);
    this.controladorMoviment = new ControladorMoviment(teclado, personatge, masmorra);
    this.controladorMenu = new ControladorMenu(teclado, personatge);

    // dependencia circular entre controlador de movimiento y de combate
    this.controladorCombat.setControladorMoviment(controladorMoviment);
    this.controladorMoviment.setControladorCombat(controladorCombat);
  }

  public void iniciar() {
    boolean jocFinalitzat = false;

    while (!jocFinalitzat) {
      if (!personatge.estaViu()) {
        Vistes.mostrarGameOver(personatge, masmorra);
        break;
      }

      ConsoleUtils.saltarPagina();
      Vistes.mostrarMapaAmbStats(personatge, masmorra);

      Accions accio = controladorMenu.demanarSeguentAccio();

      switch (accio) {
        case MOURE -> controladorMoviment.processarMoure();
        case EXPLORAR -> controladorMoviment.explorarSala();
        case OBRIR_INVENTARI -> Vistes.mostrarInventari(personatge);
        case FICAR_PUNTS -> configuracio.repartirPunts(personatge);
        case SORTIR -> jocFinalitzat = true;
        case null -> Vistes.mostrarError("Opció invàlida!");
      }
    }
    
    teclado.close();

  }
}