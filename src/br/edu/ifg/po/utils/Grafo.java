package br.edu.ifg.po.utils;

import java.util.ArrayList;

public class Grafo {
	private ArrayList<Double> listaQuantidades;
	private Integer[][] listaDeAdjacescia;
	private int quantidadeOrigens;
	private int quantidadeDestinos;


	public Grafo(ArrayList<Double> listaQuantidades, int quantidadeOrigens, int quantidadeDestinos) {
		super();
		
		this.listaQuantidades = listaQuantidades;
		this.quantidadeOrigens = quantidadeOrigens;
		this.quantidadeDestinos = quantidadeDestinos;
		
		montaRelacionamentoGrafo();
		//int total = quantidadeOrigens+quantidadeDestinos;
		
	}
	
	public Integer[][] montaRelacionamentoGrafo() {
		this.listaDeAdjacescia = new Integer[quantidadeOrigens][quantidadeOrigens];
		
		for(int i=0; i < this.quantidadeOrigens; i++) {
			for(int j=0; j < this.quantidadeDestinos; j++) {
				this.listaDeAdjacescia[i][j] = this.quantidadeOrigens + j;
			}
		}
		
		return this.listaDeAdjacescia;
	}
	
	public void corrigeGrafo(String referecia, double valor) {

		switch (referecia) {
		case "linha":			
				this.listaQuantidades.add(this.quantidadeOrigens, valor);
				
				this.quantidadeOrigens++;
				
				montaRelacionamentoGrafo();
			
			break;
			
		case "coluna":
		
			this.listaQuantidades.add(valor);
			
			montaRelacionamentoGrafo();
		
		break;

		default:
			break;
		}
	}
	
	
	public ArrayList<Double> getListaQuantidades() {
		return listaQuantidades;
	}

	public void setListaQuantidades(ArrayList<Double> listaQuantidades) {
		this.listaQuantidades = listaQuantidades;
	}

	public Integer[][] getListaDeAdjacescia() {
		return listaDeAdjacescia;
	}

	public void setListaDeAdjacescia(Integer[][] listaDeAdjacescia) {
		this.listaDeAdjacescia = listaDeAdjacescia;
	}

	public int getQuantidadeOrigens() {
		return quantidadeOrigens;
	}

	public void setQuantidadeOrigens(int quantidadeOrigens) {
		this.quantidadeOrigens = quantidadeOrigens;
	}

	public int getQuantidadeDestinos() {
		return quantidadeDestinos;
	}

	public void setQuantidadeDestinos(int quantidadeDestinos) {
		this.quantidadeDestinos = quantidadeDestinos;
	}
}
