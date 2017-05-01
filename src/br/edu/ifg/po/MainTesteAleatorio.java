package br.edu.ifg.po;

import java.text.DecimalFormat;
import java.util.Scanner;

public class MainTesteAleatorio {

	public static void main(String[] args) {
		CalcularSolucaoAleatoria calculaAleatorio;
		DecimalFormat df = new DecimalFormat("#.00");
		Scanner scanner = new Scanner(System.in);
		
		
		System.out.println("=== Método de Transporte - Utilizando Cálculo Aleatório ==");
		System.out.println("Quantas Origens? ");
		int origens = scanner.nextInt();
		System.out.println("Quantos Destinos? ");
		int destinos = scanner.nextInt();
		
		double[][] matriz = new double[origens][destinos];
		double[] disponibilidadeOrigen = new double[origens];
		double[] disponibilidadeDestinos = new double[destinos];
		
		for(int i=0; i<origens; i++) {
			for(int j=0; j<destinos; j++) {
				int origem = i+1;
				int destino = j+1;
				System.out.println("Qual Custo da Origem " + origem + " Destino " + destino + " : ");
				double valor = scanner.nextDouble();
				matriz[i][j] = valor;
			}
		}
		
		for(int i=0; i<matriz.length; i++) {
			int indice = i+1;
			System.out.println("Disponibilidade da Origem " + indice + " : ");
			double valor = scanner.nextDouble();
			disponibilidadeOrigen[i] = valor;
		}
		
		for(int i=0; i<matriz[0].length; i++) {
			int indice = i+1;
			System.out.println("Necessidade do Destino " + indice + " : ");
			double valor = scanner.nextDouble();
			disponibilidadeDestinos[i] = valor;
		}
		
		System.out.println("Calculando...");
		
		calculaAleatorio = new CalcularSolucaoAleatoria(matriz, disponibilidadeOrigen, disponibilidadeDestinos);
		calculaAleatorio.geraSolucaoAleatoria();
		double[][] retorno = calculaAleatorio.getMatrizSolucao();
		
		System.out.println("");
		System.out.println("----- SOLUÇÂO -----");
		for(int i = 0; i<retorno.length; i++) {
			for(int j = 0; j<retorno[0].length; j++) {
				System.out.print("{" + df.format(retorno[i][j]) + "}");
			}
			System.out.println("");
		}
		
		
		double total = calculaAleatorio.somaCustoTransporte();		
		System.out.println("Custo Total Transporte: " + total);

	}

}
