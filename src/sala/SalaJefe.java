package sala;

//NO ES POT SORTIR
//OBLIGAT A COMBATRE AL JEFE
import model.Monstre;
import model.Tresor;

public class SalaJefe extends Sala {
	public SalaJefe(Tresor tresor, Monstre monstre, boolean explorada) {
		super(tresor, monstre, explorada);
	}

	public boolean jefeViu() {
		return teMonstre();
	}

	@Override
	public boolean esSalaJefe() {
		return true;
	}

	public boolean intentarSortir() {
		return !teMonstre();
	}
}
