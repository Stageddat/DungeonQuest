package model;

/**
 * Representació d'un Tresor.
 */
public class Tresor {
	private String nom;
	private int valor;
	private double pes;

	public Tresor(String nom, int valor, double pes) {
		// Utilitzar setters ja que tenen validacions
		setNom(nom);
		setValor(valor);
		setPes(pes);
	}

	// Getters
	public String getNom() {
		return nom;
	}

	public int getValor() {
		return valor;
	}

	public double getPes() {
		return pes;
	}

	// Setters
	public void setNom(String nom) {
		if (nom == null || nom.isEmpty()) {
			this.nom = "Default";
		} else {
			this.nom = nom;
		}
	}

	public void setValor(int valor) {
		this.valor = (valor > 0) ? valor : 0;
	}

	public void setPes(double pes) {
		this.pes = (pes > 0) ? pes : 0;
	}

	@Override
	public String toString() {
		return "Nom: " + nom + " | Valor: " + valor;
	}
}
