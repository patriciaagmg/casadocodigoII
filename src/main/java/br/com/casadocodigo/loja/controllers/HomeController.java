package br.com.casadocodigo.loja.controllers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.casadocodigo.loja.daos.ProdutoDAO;
import br.com.casadocodigo.loja.models.Produto;

@Controller
public class HomeController {

	@Autowired
	ProdutoDAO produtoDao;
	
	
	/**
	 * Método responsável por carregar o index, tela principal.
	 * @Cacheable: Cacheando o retorno do método. Ver em AppConfiguration a annotation para habilitar o cache.
	 * @return
	 */
	@RequestMapping("/")
	@Cacheable(value="produtoHome")  
	public ModelAndView index(){
		
		List<Produto> produtos = produtoDao.listar();		
	    ModelAndView modelAndView = new ModelAndView("home");
		modelAndView.addObject("produtos", produtos);

		return modelAndView;
	}
}
