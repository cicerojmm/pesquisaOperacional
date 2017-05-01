package br.edu.ifg.po;

import java.util.Arrays;
import java.util.Random;

public class CalcularSolucaoAleatoria {
	//private double[][] funcaoObjetivo = {{10, 12}, {20, 8}, {6, 15}};
	//private double[] retricoesLinha = {50, 100, 120};
	//private double[] restricoesColuna = {100, 170};
	
	private double[][] funcaoObjetivo;
	private double[] retricoesLinha;
	private double[] restricoesColuna;
	private double[][] matrizValoresLimite;
	private double[][] matrizSolucao;

	private int sizeLinhaFO;
	private int sizeColunaFO;
	private int sizeRestLinha;
	private int sizeRestCol;
	
	public CalcularSolucaoAleatoria(double[][] matriz, double[] dispOrigem, double[] dispDestino) {
		super();
		
		this.funcaoObjetivo = matriz;
		this.retricoesLinha = dispOrigem;
		this.restricoesColuna = dispDestino;
		
		this.sizeLinhaFO =  this.funcaoObjetivo.length;
		this.sizeColunaFO = this.funcaoObjetivo[0].length;
		this.sizeRestCol = this.restricoesColuna.length;
		this.sizeRestLinha = this.retricoesLinha.length;
	}
	
	
	public void defineLimites() {
		double valor = 0;
		this.matrizValoresLimite = new double[this.sizeLinhaFO][this.sizeColunaFO];
		
		for(int i=0; i < this.sizeColunaFO; i++) {
			for(int j=0; j < this.sizeLinhaFO; j++) {
				if(this.retricoesLinha[j] < this.restricoesColuna[i]) {
					valor = this.retricoesLinha[j];
				
				} else {
					valor = this.restricoesColuna[i];
				}
				
				this.matrizValoresLimite[j][i] = valor;
			}
		}
	}
	
	public void calculoSolucao() {
		this.matrizSolucao = new double[this.sizeLinhaFO][this.sizeColunaFO];
		Random random = new Random();
		double valorGerado = 0;
		boolean proximo = false;
		
		for(int i=0; i < this.sizeLinhaFO; i++) {
			for(int j=0; j < this.sizeColunaFO; j++) {
			
				while(!proximo) {
					//valorGerado = random.nextDouble() * 1000;
					valorGerado = random.nextInt((int)this.matrizValoresLimite[i][j]);
					this.matrizSolucao[i][j] = valorGerado;
					
					if(verificaCalculoSolucao(valorGerado, i, j)) {
						proximo = true;
					}
				}
				
				proximo = false;
			}
		}
	}
	
	public boolean verificaCalculoSolucao(double valor, int linha, int coluna) {
		boolean sucesso = false;
		double somaColuna = 0;
		double somaLinha = 0;
		
		if(valor <= this.matrizValoresLimite[linha][coluna]) {
			for(int i=0; i < this.sizeColunaFO; i++) {
				somaLinha += this.matrizSolucao[linha][i];							
			}
			
			for(int i=0; i < this.sizeLinhaFO; i++) {
				somaColuna += this.matrizSolucao[i][coluna];
			}
			
			if(somaLinha <= this.retricoesLinha[linha] && somaColuna <= this.restricoesColuna[coluna]) {
				sucesso = true;
			}
			
		}
		
		
		return sucesso;
	}
	
	public double somaCustoTransporte() {
		double total = 0;
		

		for(int i=0; i < this.sizeLinhaFO; i++) {
			for(int j=0; j < this.sizeColunaFO; j++) {
			
				total += this.funcaoObjetivo[i][j] * this.matrizSolucao[i][j];
			}
		}
		
		
		return total;
	}
	
	public void geraSolucaoAleatoria() {
		defineLimites();
		calculoSolucao();
	}
	

	
	public double[][] getFuncaoObjetivo() {
		return funcaoObjetivo;
	}


	public void setFuncaoObjetivo(double[][] funcaoObjetivo) {
		this.funcaoObjetivo = funcaoObjetivo;
	}


	public double[] getRetricoesLinha() {
		return retricoesLinha;
	}


	public void setRetricoesLinha(double[] retricoesLinha) {
		this.retricoesLinha = retricoesLinha;
	}


	public double[] getRestricoesColuna() {
		return restricoesColuna;
	}


	public void setRestricoesColuna(double[] restricoesColuna) {
		this.restricoesColuna = restricoesColuna;
	}


	public double[][] getMatrizValoresLimite() {
		return matrizValoresLimite;
	}


	public void setMatrizValoresLimite(double[][] matrizValoresLimite) {
		this.matrizValoresLimite = matrizValoresLimite;
	}


	public double[][] getMatrizSolucao() {
		return matrizSolucao;
	}


	public void setMatrizSolucao(double[][] matrizSolucao) {
		this.matrizSolucao = matrizSolucao;
	}


	public int getSizeLinhaFO() {
		return sizeLinhaFO;
	}


	public void setSizeLinhaFO(int sizeLinhaFO) {
		this.sizeLinhaFO = sizeLinhaFO;
	}


	public int getSizeColunaFO() {
		return sizeColunaFO;
	}


	public void setSizeColunaFO(int sizeColunaFO) {
		this.sizeColunaFO = sizeColunaFO;
	}


	public int getSizeRL() {
		return sizeRestLinha;
	}


	public void setSizeRL(int sizeRL) {
		this.sizeRestLinha = sizeRL;
	}


	public int getSizeRC() {
		return sizeRestCol;
	}


	public void setSizeRC(int sizeRC) {
		this.sizeRestCol = sizeRC;
	}
	
}
