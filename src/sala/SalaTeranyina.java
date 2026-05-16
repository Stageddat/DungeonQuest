package sala;

//ES POT SORTIR
//OBLIGAT A TIRAR EL DAU A FORZA
//SI PERD -1VIDA Y NO POT SORTIR
import model.Monstre;
import model.Personatge;
import model.Tresor;

public class SalaTeranyina extends Sala {
	private Personatge personatge;

	public SalaTeranyina(Tresor tresor, Monstre monstre, boolean explorada, Personatge personatge) {
		super(tresor, monstre, explorada);
		this.personatge = personatge;
	}

	public boolean intentarSortir() {
		return personatge.ferTiradaForsa();
	}
}
