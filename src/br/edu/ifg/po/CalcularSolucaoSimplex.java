package br.edu.ifg.po;

import java.util.Arrays;

/*
 * ######################################################
 * ########### Classe para calcular a solucao de ########
 * ##########  Problemas de Pesquisa Operacional ########
 * ######################################################
 * ################### Autor: Cícero Moura ##############
 * ######################################################
 */

public class CalcularSolucaoSimplex {
	//private double[] funcaoObjetivo = {3, 5};
	//private double[] funcaoObjetivo = {2, 3, 1};
	private double[] funcaoObjetivo;
	private double[] funcaoOjetivoFinal;
	private int[] historicoLinhasPivo;
	private int[] historicoColunasPivo;
	//private double[][] restricoes = {{2, 4, 10}, {6, 1, 20}, {1, -1, 30}};
	//private double[][] restricoes = {{1, 1, 1, 40}, {2, 1, -1, 20}, {3, 2, -1, 30}};
	private double[][] restricoes;
	private double[][] tabelaSolucao;
	private double elementoPivo;
	private int colunaPivo;
	private int linhaPivo;
	private boolean solucaoOtima;
	
	
	public CalcularSolucaoSimplex(double[] funcaoObjetivo, double[][] restricoes) {
		super();
		
		this.funcaoObjetivo = funcaoObjetivo;
		this.restricoes = restricoes;
		
		this.solucaoOtima = false; //Inicializando solução final como falsa
	}
	
	//Função para deicar função negativa com valor negativo
	public void inverteFuncaoObjetivo() {
		int tamanho = this.funcaoObjetivo.length;
		double valor;
		
		for(int i=0; i < tamanho; i++) {
			valor = -this.funcaoObjetivo[i];
			this.funcaoObjetivo[i] = valor;
		}
	}
	
	//Função para definir variavel que entra na base
	public boolean defineVariavelBase() {
		double varivalBase = 0;
		double valor = 0;
		int tamanho = this.tabelaSolucao[0].length;
		
		this.solucaoOtima = true;
		
		for(int i=0; i < tamanho; i++) {
			valor = this.tabelaSolucao[0][i];
			if(valor < 0) {
				if(varivalBase > valor || varivalBase == 0) {
					varivalBase = valor;
					this.colunaPivo = i;
					this.solucaoOtima = false;
				}
			}
		}
		
		return this.solucaoOtima;
				
	}
	
	//Função para definir elemento Pivô
	public void defineElementoPivo() {
		int tamanho = this.tabelaSolucao.length;
		int tamanhoColuna = this.tabelaSolucao[0].length;
		double resultado = -1;
		double valorColuna = 0;
		double valorDireita = 0;
		double tmp = 0;
		
		for(int i=0; i < tamanho; i++) {
			valorColuna = this.tabelaSolucao[i][colunaPivo];
			valorDireita = this.tabelaSolucao[i][tamanhoColuna-1];
			
			if(valorColuna > 0 && valorDireita > 0) {
				tmp = valorDireita / valorColuna;
				if(resultado == -1 || tmp < resultado) {
					resultado = tmp; 
						
					this.elementoPivo = valorColuna;
					this.linhaPivo = i;
				}
			}
			
		}
	}
	
	//Calcula valor da nova linha Pivô
	public void calculaNovaLinhaPivo() {
		int linha = this.linhaPivo;
		//int coluna =  this.colunaPivo;
		int tamanho = this.tabelaSolucao[linha].length;
		double valor = 0;
				
		for(int i=0; i<tamanho; i++) {
			valor = this.tabelaSolucao[linha][i];
			
			this.tabelaSolucao[linha][i] = valor / this.elementoPivo;
		}
		
	}
		
	//Função para definir o tamanho da tabela de solução do problema
	public void criaTabelaDeSolucao() {
		int qtdLinha = this.restricoes.length + 1;
		int qtdColuna = this.restricoes.length + this.funcaoObjetivo.length + 2;
		
				
		this.tabelaSolucao = new double[qtdLinha][qtdColuna];
	}
	
	//Função para Completar tabela com 0 ou 1
	public void completaLinhaTabela(int situacao, int linha, int coluna, int qtd, int posNumUm) {
		double valor = 0;
		
		switch (situacao) {
			case 0:
				valor = 0;
				for(int i=0; i < qtd; i++) {
					this.tabelaSolucao[linha][coluna] = valor;
					
					coluna++;
				}
				break;
				
			case 1:
				for(int x=0; x < qtd; x++) {
					if(posNumUm == x) {
						valor = 1;
						this.tabelaSolucao[linha][coluna] = valor;
						
					} else {
						valor = 0;
						
						this.tabelaSolucao[linha][coluna] = valor;
					}
						
					coluna++;
				}
				break;
	
			default:
				break;
		}
		
		
	}
	
	//Função para prrencher a tabela de solução com as variaveis de folga
	public void preencheTabelSolucao() {
		int qtdVariaveis = this.funcaoObjetivo.length;
		int tamTabelaSolucao = this.tabelaSolucao.length;
		int tamTabelaSolCol = this.tabelaSolucao[0].length;
		int aux = 0;
		int tmp = 0;
		int controleLinha = 0;
		
		for(int i=0; i < tamTabelaSolucao; i++) {
			//for(int j=0; j < this.tabelaSolucao[i].length; j++) {
				
				//Primeira Linha da tabela
				if(i == 0) {
					aux =  this.funcaoObjetivo.length;
					tmp = 0;
					this.tabelaSolucao[i][tmp] = 1;
					
					
					for(int c = 0; c < aux; c++) {
						tmp++;
						
						this.tabelaSolucao[i][tmp] = this.funcaoObjetivo[c];
					}
					
					this.completaLinhaTabela(0, i, tmp+1, qtdVariaveis+1, controleLinha);					
				
				} else {
					aux = this.restricoes[i-1].length - 1;
					tmp = 0;
					this.tabelaSolucao[i][tmp] = 0;
					
					for(int c=0; c < aux; c++) {
						tmp++;
						this.tabelaSolucao[i][tmp] = this.restricoes[i-1][c];
					}
										
					this.completaLinhaTabela(1, i, tmp+1, qtdVariaveis+1, controleLinha);
					
					this.tabelaSolucao[i][tamTabelaSolCol-1] = this.restricoes[i-1][aux];
					controleLinha++;
				}
			}
		//}
	}
	
	//Função para multiplicar linha Pivo pelo valor da linha que entra
	public double[] multiplicarLinhaPivo(double valor, int linha) {
		double[] novaLinha = new double[this.tabelaSolucao[0].length];
		
		if(valor > 0) {
			valor = -valor;
		} else {
			valor = -valor;
		}
		
		for(int i=0; i < this.tabelaSolucao[linha].length; i++) {
			
			novaLinha[i] = this.tabelaSolucao[this.linhaPivo][i] * valor;
		}
		
		return novaLinha;
	}
	
	//Calcula a nova linha e altera o valor na tabela de Solução
	public void alteraLinhaDaSolucao(double[] vetor, int linha) {
		double resultado;
		for(int i=0; i < this.tabelaSolucao[linha].length; i++) {
			resultado = this.tabelaSolucao[linha][i] + vetor[i];
			
			this.tabelaSolucao[linha][i] = resultado;
		}
		
		//resultado = 0;
		

	}
	
	//Realiza o calcula da nova linha para da tabela de Solução
	public void calculaNovasLinhas() {
		double valor = 0;
		double[] novaLinhaMult;
		int tamanhoLinha =  this.tabelaSolucao.length;
		
		for(int i=0; i < tamanhoLinha; i++) {
			if(i != this.linhaPivo) {
				valor = this.tabelaSolucao[i][this.colunaPivo];
				
				novaLinhaMult = multiplicarLinhaPivo(valor, i);
				this.alteraLinhaDaSolucao(novaLinhaMult, i);
			}
		}
		
		this.preecnheVetordaSolucao(0);
	}
	
	//Função para preencher vetor com a solução final em ordem do problema
	public void preecnheVetordaSolucao(int key) {
		int tamanho = this.funcaoObjetivo.length;
		
		switch (key) {
			case 0:	{
				if(this.colunaPivo > 0 && this.colunaPivo <= this.funcaoObjetivo.length) {
					this.historicoLinhasPivo[this.colunaPivo-1] = this.linhaPivo;
					this.historicoColunasPivo[this.colunaPivo-1] = this.colunaPivo;
				}
			}
			break;
				
			case 1: {
				int coluna;
				int linha;
				
				for(int i=0; i < tamanho; i++) {
					coluna = this.historicoColunasPivo[i];
					linha = this.historicoLinhasPivo[i];
					if(coluna != 0 && linha != 0) {
						this.funcaoOjetivoFinal[coluna-1] = this.tabelaSolucao[linha][this.tabelaSolucao[linha].length-1]; 
					}
				}
				
				this.funcaoOjetivoFinal[tamanho] = this.tabelaSolucao[0][this.tabelaSolucao[0].length-1]; 
					
			}
			break;
				
			case 2: {			
				this.funcaoOjetivoFinal = new double[tamanho+1];
				this.historicoColunasPivo = new int[tamanho];
				this.historicoLinhasPivo = new int[tamanho];
			}
			break;
						
			default:
				break;
		}
	}
	
	//Chama todas as funções para gerar todos os calculos
	public double[] geraCalculoSolucao() {
		this.preecnheVetordaSolucao(2);
		this.inverteFuncaoObjetivo();
		this.criaTabelaDeSolucao();
		this.preencheTabelSolucao();
		//this.defineVariavelBase();
		
		
		while(!this.defineVariavelBase()) {
			this.defineElementoPivo();
			this.calculaNovaLinhaPivo();
			this.calculaNovasLinhas();
		}
		
		this.preecnheVetordaSolucao(1);
		
		
		return this.funcaoOjetivoFinal;
		
	}
	

	/*
	 * #######################
	 * ### Getters e Setters
	 * #######################
	 */
	public double[] getFuncaoObjetivo() {
		return funcaoObjetivo;
	}

	public void setFuncaoObjetivo(double[] funcaoObjetivo) {
		this.funcaoObjetivo = funcaoObjetivo;
	}

	public double[][] getRestricoes() {
		return restricoes;
	}

	public void setRestricoes(double[][] restricoes) {
		this.restricoes = restricoes;
	}

	public double[][] getTabelaSolucao() {
		return tabelaSolucao;
	}

	public void setTabelaSolucao(double[][] tabelaSolucao) {
		this.tabelaSolucao = tabelaSolucao;
	}

	public double getElementoPivo() {
		return elementoPivo;
	}

	public void setElementoPivo(double elementoPivo) {
		this.elementoPivo = elementoPivo;
	}

	public int getColunaPivo() {
		return colunaPivo;
	}

	public void setColunaPivo(int colunaPivo) {
		this.colunaPivo = colunaPivo;
	}

	public boolean isSolucaoOtima() {
		return solucaoOtima;
	}

	public void setSolucaoOtima(boolean solucaoOtima) {
		this.solucaoOtima = solucaoOtima;
	}

	public int getLinhaPivo() {
		return linhaPivo;
	}

	public void setLinhaPivo(int linhaPivo) {
		this.linhaPivo = linhaPivo;
	}
	
	public double[] getFuncaoOjetivoFinal() {
		return funcaoOjetivoFinal;
	}

	public void setFuncaoOjetivoFinal(double[] funcaoOjetivoFinal) {
		this.funcaoOjetivoFinal = funcaoOjetivoFinal;
	}
	
	public int[] getHistoricoLinhasPivo() {
		return historicoLinhasPivo;
	}

	public void setHistoricoLinhasPivo(int[] historicoLinhasPivo) {
		this.historicoLinhasPivo = historicoLinhasPivo;
	}
}