package br.com.casadocodigo.loja.controllers;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.models.CarrinhoCompras;
import br.com.casadocodigo.loja.models.DadosPagamento;

@Controller
@RequestMapping("/pagamento")
public class PagamentoController {

	@Autowired
	private CarrinhoCompras carrinho;
	
	@Autowired //RestTemplate está configurado no AppWebConfiguration
	private RestTemplate restTemplate;
	
	
	/*
	 * Atenção: Foi utilizado um recurso para performance.
	 * public ModelAndView finalizar(RedirectAttributes model){ Comportamento Sincrono. Pode demorar a cada chamada.
		Callable torna Assincrono, logo o tomCat libera a tread para outras operações.
	*/
	@RequestMapping(value="/finalizar", method=RequestMethod.POST)		
	public Callable<ModelAndView> finalizar(RedirectAttributes model){
		//Classe anonima do Java8
		return () -> {
			String uri = "http://book-payment.herokuapp.com/payment";
			
			//Carrinho está no escopo de sessão, logo não será necessário passar como parametro.
			try{
				//Efetuar o pagamento utilizando um serviço. JASON
				String response = restTemplate.postForObject(uri, new DadosPagamento(carrinho.getTotal()), String.class);
				System.out.println(response);
				model.addFlashAttribute("sucesso",response);
				return new ModelAndView("redirect:/produtos");
				
			}catch (Exception e) {
				/*O serviço possui uma regra a qual não permite que uma determinada comprar não pode ser maio que 500.
				Quando isso ocorre, será lançada uma exceção. */
				e.printStackTrace();
				System.out.println();
				model.addFlashAttribute("falha","Valor maior que o permitido");		
				return new ModelAndView("redirect:/produtos");
				
			}
		};
		
	}
}
