package br.com.casadocodigo.loja.models;

import java.math.BigDecimal;

/**
 * Classe criada para montar o objeto (atributos) esperado pelo serviço.
 * O seviço espera receber o valor do livro nesta estrutura: Exemplo: 'value'={200}
 * O valor decerá ir dentro do atributo chamado 'value'.
 * ESte atributo foi definido pelo serviço. 
 *
 */
public class DadosPagamento {


	private BigDecimal value;
	
	public DadosPagamento(BigDecimal value){
		this.value = value;		
	}
	
	public DadosPagamento(){
		
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}
	
	

}
