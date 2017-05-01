package br.edu.ifg.po;

import java.util.ArrayList;

public class MainTesteNoroeste {

	public static void main(String[] args) {
		//double[][] matrizCustos = {{10, 12}, {20, 8}, {6, 15}};
		//double[][] matrizCustos = {{10, 12, 9}, {4, 9, 8}, {6, 12, 10}};
		double[][] matrizCustos = {{6, 5, 8}, {13, 12, 1}, {7, 9, 5}, {10, 6, 4}};
		ArrayList<Double> listQtd = new ArrayList<>();
		int qtdOrigens = 4;
		int qtdDestinos = 3;
		
		//listQtd.add(20.0);
		//listQtd.add(30.0);
		//listQtd.add(10.0);
		//listQtd.add(25.0);
		//listQtd.add(36.0);
		//listQtd.add(5.0);
		
		listQtd.add(10.0);
		listQtd.add(20.0);
		listQtd.add(12.0);
		listQtd.add(13.0);
		listQtd.add(8.0);
		listQtd.add(32.0);
		listQtd.add(15.0);
		
		CalcularSolucaoCantoNoroeste calcular = new CalcularSolucaoCantoNoroeste(matrizCustos, listQtd, qtdOrigens, qtdDestinos);
		
		calcular.montaSolucao();
		calcular.getMatrizSolucao();
	
	}

}
