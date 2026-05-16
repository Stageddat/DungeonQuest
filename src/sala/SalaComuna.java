package sala;

import model.Monstre;
import model.Tresor;

//ES POT SORTIR
public class SalaComuna extends Sala {
	public SalaComuna(Tresor tresor, Monstre monstre, boolean explorada) {
		super(tresor, monstre, explorada);
	}

	@Override
	public boolean intentarSortir() {
		return true;
	}
}
