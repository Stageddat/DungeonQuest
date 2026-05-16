package controlador;

import model.Accions;
import model.Personatge;
import utils.Colors;
import utils.Estils;

import java.util.Scanner;

public class ControladorMenu {
  private final Scanner teclado;
  private final Personatge personatge;

  public ControladorMenu(Scanner teclado, Personatge personatge) {
    this.teclado = teclado;
    this.personatge = personatge;
  }

  public Accions demanarSeguentAccio() {
    mostrarMenuAccions();

    String entrada = teclado.nextLine().toLowerCase();
    if (entrada.isEmpty())
      return null;

    return switch (entrada.charAt(0)) {
      case 'm' -> Accions.MOURE;
      case 'e' -> Accions.EXPLORAR;
      case 'i' -> Accions.OBRIR_INVENTARI;
      case 'h' -> personatge.getPuntsDisponibles() > 0 ? Accions.FICAR_PUNTS : null;
      case 'q' -> Accions.SORTIR;
      default -> null;
    };
  }

  private void mostrarMenuAccions() {
    System.out.println();
    System.out.println(Estils.TITOL + "=== Què vols fer? ===" + Colors.RESET);
    System.out.println(Estils.PREGUNTA + "Tria una opció:" + Colors.RESET);
    System.out.println(Colors.VERD + "  M. Moure's per la masmorra");
    System.out.println(Colors.TARONJA + "  E. Explorar la sala actual");
    System.out.println(Colors.BLAU + "  I. Mostrar inventari");
    if (personatge.getPuntsDisponibles() > 0) {
      System.out.println(Colors.GROC + "  H. Repartir punts d'atributs" + Colors.RESET);
    }
    System.out.println(Colors.VERMELL + "  Q. Sortir del joc" + Colors.RESET);
    System.out.print(Estils.RESPOSTA);
  }
}