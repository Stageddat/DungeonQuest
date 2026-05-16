package model;

import combat.Combatent;
import utils.MathUtils;

public class Monstre implements Combatent {

	private String nom;
	private int vida;
	private int atac;
	private int penalitzacio;
	private int valorExperiencia;

	public Monstre(String nom, int vida, int atac, int penalitzacio) {
		super();
		this.nom = nom;
		this.vida = vida;
		this.atac = atac;
		this.penalitzacio = MathUtils.ajustarRang(0, 3, penalitzacio);
		this.valorExperiencia = this.vida * 2;
	}

	@Override
	public int calcularAtac() {
		return MathUtils.generarNumeroAleatori(1, this.vida);
	}

	@Override
	public int getVida() {
		return vida;
	}

	@Override
	public void setVida(int vida) {
		this.vida = (vida > 0) ? vida : 0;
	}

	public int getAtac() {
		return atac;
	}

	public void setAtac(int atac) {
		this.atac = (atac > 0) ? atac : 0;
	}

	public String getNom() {
		return nom;
	}

	public int getPenalitzacio() {
		return penalitzacio;
	}

	public int getValorExperiencia() {
		return valorExperiencia;
	}

	@Override
	public String toString() {
		return "Nom: " + nom + " | Vida: " + vida;
	}

}
