package br.edu.ifg.po;

import java.util.Scanner;

public class MainTesteSimplex {
	public static void main(String[] args) {
		CalcularSolucaoSimplex calculaSolucao;
		Scanner scanner = new Scanner(System.in);
		double[] funcaoObjetivo;
		double[][] restricoes;
		double[] resultado;
		
		System.out.println("Quantas variáveis na Função Objetivo? ");
		int qtdFuncaoObjt = scanner.nextInt();
		System.out.println("Quantas linhas de Restrições? ");
		int qtdLinhaRestricoes = scanner.nextInt();
		
		int qtdRestricoes = qtdFuncaoObjt+1;
		funcaoObjetivo = new double[qtdFuncaoObjt];
		restricoes = new double[qtdLinhaRestricoes][qtdRestricoes];
		
		for(int i=0; i < qtdFuncaoObjt; i++) {
			int indice = 1+i;
			System.out.println("Digite o valor de X"+ indice +": ");
			double valor = scanner.nextDouble();
			
			funcaoObjetivo[i] = valor;
		}
		
		for(int j=0; j < qtdLinhaRestricoes; j++) {
			for(int i=0; i <= qtdFuncaoObjt; i++) {
				double valor;
				int indice = 1+i;
				
				if(i == qtdFuncaoObjt) {
					System.out.println("Digite o valor de b: ");
					valor = scanner.nextDouble();
				} else {
					System.out.println("Digite o valor de X"+ indice +": ");
					valor = scanner.nextDouble();
					
				}
				
				restricoes[j][i] = valor;
			}
		}
		
		calculaSolucao = new CalcularSolucaoSimplex(funcaoObjetivo, restricoes);
		
		resultado = calculaSolucao.geraCalculoSolucao();
		
		System.out.println("##################################");
		System.out.println("---- Solução ----");
		for(int i=0; i<resultado.length; i++) {
			int indice = i+1;
			if(i == resultado.length-1) {
				System.out.println("Z = " + resultado[i]);
			} else {
				System.out.println("X" + indice +" = " + resultado[i]);
			}
		}

		
	}
}
