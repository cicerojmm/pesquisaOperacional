package br.edu.ifg.po;

import java.util.ArrayList;

import br.edu.ifg.po.utils.Grafo;

public class CalcularSolucaoCantoNoroeste {
	private double[][] matrizCustos;
	private double[][] matrizSolucao;
	
	private double[][] matrizOtimalidade;
	
	private int sizeColMatriz;
	private int sizeLinMatriz;
	
	private Grafo grafo;
	
	public CalcularSolucaoCantoNoroeste(double[][] matrizCustos, ArrayList<Double> listaQuantidades, int sizeLinMatriz, int sizeColMatriz) {
		super();
		
		this.matrizCustos = matrizCustos;
		
		this.sizeLinMatriz = sizeLinMatriz;
		this.sizeColMatriz = sizeColMatriz;
		
		grafo = new Grafo(listaQuantidades, sizeLinMatriz, sizeColMatriz);
		
	}
	
	public void balanceamentoSistema() {
		int qtdOrigens = grafo.getQuantidadeOrigens();
		ArrayList<Double> listaQtd = grafo.getListaQuantidades();
		
		double somaDisp = 0;
		double somaNec = 0;
		
		for(int i=0; i<listaQtd.size(); i++) {
			if(i<qtdOrigens) {
				somaDisp += listaQtd.get(i);
				
			} else {
				somaNec += listaQtd.get(i);
			}
		}
		
		if(somaDisp < somaNec) {
			double faltante = somaNec - somaDisp;
			double[][] matrizTmp = this.matrizCustos;
			this.matrizCustos = new double[sizeLinMatriz+1][sizeColMatriz];
			//this.matrizCustos = matrizTmp;
			
			int limite = this.sizeLinMatriz;
			for(int i=0; i<limite; i++) {
				for(int j=0; j<this.sizeColMatriz; j++) {
					this.matrizCustos[i][j] = matrizTmp[i][j];
				}
				
			}
			
			grafo.corrigeGrafo("linha", faltante);
			
		} else if(somaNec < somaDisp) {
			double faltante = somaDisp - somaNec;
			double[][] matrizTmp = this.matrizCustos;
			this.matrizCustos = new double[sizeLinMatriz][sizeColMatriz+1];
			this.matrizCustos = matrizTmp;
			
			grafo.corrigeGrafo("coluna", faltante);
			
		}
		
	}
	
	public void calculaSolucaoCantoNoroeste() {
		matrizSolucao = new double[this.matrizCustos.length][this.matrizCustos[0].length];
		Integer[][] listaAdjascencia = grafo.getListaDeAdjacescia();
		ArrayList<Double> listaQtd = grafo.getListaQuantidades();
		
		Double valorSobrando = 0.0;
		Double valorFaltando = 0.0;
		Double valorTemp = 0.0;
		Double valorAcumulado = 0.0;
		
		int coluna = 0;
		
		
		for(int linha=0; linha < matrizSolucao.length; linha++) {
			
			if(coluna < matrizSolucao[0].length) {
				int indice = listaAdjascencia[linha][coluna];
				
				Double valorOrig = listaQtd.get(linha);
				Double valorDes = listaQtd.get(indice);
				
				//Caso se for maior
				//if(valorOrig > valorDes) {
					
					if(valorFaltando > 0.0) {
						this.matrizSolucao[linha][coluna] = valorFaltando;
						
						coluna++;
						
						valorAcumulado = 0.0;
						valorSobrando = valorOrig - valorFaltando;
						
						valorTemp = valorOrig - valorFaltando;
								
						this.matrizSolucao[linha][coluna] = valorTemp;
						
						valorFaltando = 0.0;
						
						valorAcumulado = valorAcumulado +  valorTemp;
						
						//if(valorAcumulado == valorDes) valorAcumulado = 0.0;
						
					} else if (valorSobrando > 0.0) {
						
						valorTemp = valorSobrando + valorOrig;
						
						if(valorTemp > valorDes) {
							valorSobrando = valorTemp - valorDes;
							valorTemp = valorOrig;
							
						} else if(valorTemp < valorDes) {
							valorFaltando = valorDes - valorTemp;	
							valorAcumulado += valorTemp;
							valorSobrando = 0.0;
							valorTemp = valorOrig;
						} else {
							valorSobrando = 0.0;
						}
						
						
						this.matrizSolucao[linha][coluna] = valorTemp;
						
						
						
					}/* else if(valorAcumulado > 0.0) {
						valorTemp = valorAcumulado - valorDes;
						
						valorSobrando = valorOrig - valorTemp;
						
						this.matrizSolucao[linha][coluna] = valorDes;
						coluna++;
						
						valorAcumulado = 0.0;
						
					}*/ else {
						if(valorDes < valorOrig) {
							valorSobrando = valorOrig - valorDes; 
							
							this.matrizSolucao[linha][coluna] = valorDes;
							coluna++;
							
							this.matrizSolucao[linha][coluna] = valorSobrando;
			
							
						} else if(valorDes > valorOrig) {
							valorFaltando = (valorDes - valorOrig) - valorAcumulado;
							valorAcumulado += valorOrig;
							
							this.matrizSolucao[linha][coluna] = valorOrig;							
							
						} else {
							this.matrizSolucao[linha][coluna] = valorOrig;
							coluna++;
						}
					}
				
				}
			}
			
		
	}
	
	
	public void calculaOtimalidade() {
		this.matrizOtimalidade = new double[this.matrizCustos.length][this.matrizCustos[0].length];

		double[] arrayU = new double[this.matrizCustos.length];
		double[] arrayV = new double[this.matrizCustos.length];
		
		double resultado;
		double valorTemp = 0;
		int linhaSel = 0;
		int colunaSel = 0;
		
		arrayU[0] = 0;
		
		for(int linha=0; linha < matrizOtimalidade.length; linha++) {
			for(int coluna=0; coluna < matrizOtimalidade[0].length; coluna++) {
				if(this.matrizSolucao[linha][coluna] > 0) {
					if(linha == 0) {
						resultado = this.matrizCustos[linha][coluna] - arrayU[linha] - arrayV[linha];
						arrayV[coluna] = resultado;
						
					} else if(arrayV[coluna] > 0) {
						resultado = this.matrizCustos[linha][coluna] - arrayU[linha] - arrayV[coluna];
						arrayV[coluna] = resultado;
					
					} else {
						resultado = this.matrizCustos[linha][coluna] - arrayU[linha] - arrayV[coluna];
						arrayU[linha] = resultado;
					}
					
				}
			}
		}
		
		for(int linha=0; linha < matrizOtimalidade.length; linha++) {
			for(int coluna=0; coluna < matrizOtimalidade[0].length; coluna++) {
				if(this.matrizSolucao[linha][coluna] == 0) {
					resultado = this.matrizCustos[linha][coluna] - arrayU[linha] - arrayV[coluna];
					
					
					if(resultado < valorTemp) {
						valorTemp = resultado;
						linhaSel = linha;
						colunaSel = coluna;
					}
				}
			}
		}
		
		
		//Verifica se a solução já é ótima
		if(valorTemp != 0) {
			montaCircuitoDeCompensacao(colunaSel, linhaSel);
		}
		
		
		
	}
	
	
	//Verifica questão de otimalidade
	private void montaCircuitoDeCompensacao(int colunaSel, int linhaSel) {
		double valorLinha = 0;
		double valorColuna = 0;
		int linha = linhaSel;
		int coluna = colunaSel;
		int linhaFinal =0;
		int qtdLinha = this.matrizSolucao.length;
		int qtdColuna = this.matrizSolucao[0].length;
		boolean sucesso = false;
		
		while(!sucesso) {
			linha++;
		
			
			if(linha < qtdLinha) {
				if(this.matrizSolucao[linha][coluna] > 0) {
					valorLinha = this.matrizSolucao[linha][coluna];
				} else {
					linha++;
					valorLinha = this.matrizSolucao[linha][coluna];
				}
			} else {
				linha--;
				linha--;
				if(this.matrizSolucao[linha][coluna] > 0) {
					valorLinha = this.matrizSolucao[linha][coluna];
				} else {
					linha--;
					valorLinha = this.matrizSolucao[linha][coluna];
				}
			}
			
			linhaFinal = linha;
			linha = linhaSel;
			coluna++;
			if(coluna < qtdColuna) {
				if(this.matrizSolucao[linha][coluna] > 0) {
					valorLinha = this.matrizSolucao[linha][coluna];
				} else {
					coluna++;
					valorLinha = this.matrizSolucao[linha][coluna];
				}
			} else {
				coluna--;
				coluna--;
				if(this.matrizSolucao[linha][coluna] > 0) {
					valorColuna = this.matrizSolucao[linha][coluna];
				} else {
					linha--;
					valorLinha = this.matrizSolucao[linha][coluna];
				}
			}
			
			
			if(this.matrizSolucao[linha+1][coluna+1] > 0) {
				sucesso = true;
				
			} else {
				sucesso = false;
			}
		}
		
		double valorFinal = valorLinha < valorColuna ? valorLinha : valorColuna;
		
		this.matrizSolucao[linhaSel][colunaSel] = valorFinal;
		this.matrizSolucao[linhaFinal][coluna] = 0;
		
	}

	//Monta todo o roteiro de calculo da solução canto Noroeste
	public void montaSolucao() {
		balanceamentoSistema();
		calculaSolucaoCantoNoroeste();
		calculaOtimalidade();
	}
	
	public double[][] getMatrizCustos() {
		return matrizCustos;
	}


	public void setMatrizCustos(double[][] matrizCustos) {
		this.matrizCustos = matrizCustos;
	}


	public double[][] getMatrizSolucao() {
		return matrizSolucao;
	}


	public void setMatrizSolucao(double[][] matrizSolucao) {
		this.matrizSolucao = matrizSolucao;
	}


	public int getSizeColMatriz() {
		return sizeColMatriz;
	}


	public void setSizeColMatriz(int sizeColMatriz) {
		this.sizeColMatriz = sizeColMatriz;
	}


	public int getSizeLinMatriz() {
		return sizeLinMatriz;
	}


	public void setSizeLinMatriz(int sizeLinMatriz) {
		this.sizeLinMatriz = sizeLinMatriz;
	}


	public Grafo getGrafo() {
		return grafo;
	}


	public void setGrafo(Grafo grafo) {
		this.grafo = grafo;
	}
	
}









