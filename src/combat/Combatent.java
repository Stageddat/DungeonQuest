package combat;

public interface Combatent {
	int getVida();

	void setVida(int vida);

	int calcularAtac();

	default boolean estaViu() {
		return getVida() > 0;
	};

	default void rebreDany(int quantitat) {
		setVida(Math.max(0, getVida() - quantitat));
	}

	/**
	 * Comprova si el combatent pot lluitar.
	 * Es pot lluitar en cas que el combatent estigui viu i el openent també estigui
	 * viu.
	 * 
	 * @param oponent El combatent oponent.
	 * @return El combatent pot lluitar o no.
	 */
	default boolean potLluitar(Combatent oponent) {
		return this.estaViu() && oponent != null && oponent.estaViu();
	}
}