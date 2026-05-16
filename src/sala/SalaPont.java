package sala;

import model.Monstre;
import model.Personatge;
import model.Tresor;

//ES POT SORTIR
//OBLIGAT A TIRAR EL DAU A AGILIDAD
//SI PERD NO POT SORTIR
public class SalaPont extends Sala {
	private Personatge personatge;

	public SalaPont(Tresor tresor, Monstre monstre, boolean explorada, Personatge personatge) {
		super(tresor, monstre, explorada);
		this.personatge = personatge;
	}

	public boolean intentarSortir() {
		boolean exit = personatge.ferTiradaAgilitat();
		if (!exit) {
			personatge.rebreDany(1);
		}
		return exit;
	}
}
