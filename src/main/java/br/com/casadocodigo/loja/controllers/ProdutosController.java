package br.com.casadocodigo.loja.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.daos.ProdutoDAO;
import br.com.casadocodigo.loja.infra.FileSaver;
import br.com.casadocodigo.loja.models.Produto;
import br.com.casadocodigo.loja.models.TipoPreco;
import br.com.casadocodigo.loja.validation.ProdutoValidation;

@Controller
@RequestMapping("/produtos")
public class ProdutosController {

	@Autowired  /* injeta o produto dao*/
	private ProdutoDAO produtoDAO;
	
	@Autowired
	private FileSaver fileSaver;
	
	//Para ligar a validação
	@InitBinder
	public void initBinder(WebDataBinder binder){
		binder.addValidators(new ProdutoValidation());
	}
	
	//@RequestMapping("/produtos/form")
	@RequestMapping("/form")
	public ModelAndView form(Produto produto){
		//método que prepara a chamada do form.
		ModelAndView modelAndView = new ModelAndView("produtos/form");
		modelAndView.addObject("tipos",TipoPreco.values());
		
		return modelAndView;
	}
	
	//@RequestMapping(value="/produtos", method=RequestMethod.POST)
	// @@Valid - validação do Spring que utiliza o BeanVAlidation do Java.
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView grava(MultipartFile sumario , @Valid Produto produto, BindingResult result,  RedirectAttributes redirectAttributes){
		
		//VAlidando e voltando para o form
		if(result.hasErrors()){
			return form(produto);
		}

		//Tratando aquivo anexado
		String path = fileSaver.write("arquivos-sumario", sumario);
		produto.setSumarioPath(path);
		
		produtoDAO.gravar(produto);
		
		redirectAttributes.addFlashAttribute("sucesso","Produto Cadastrado com sucesso!"); //Mensagem de sucesso que será exibida na página de listagem, chamada na próxima linha.
		return new ModelAndView("redirect:produtos");
		//return listar(); teremos problemas, bug F5
	}
	
	//@RequestMapping(value="/produtos", method=RequestMethod.GET)
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView listar(){
		List<Produto> produtos = produtoDAO.listar();
		ModelAndView modelAndView = new ModelAndView("produtos/lista");
		modelAndView.addObject("produtos",produtos);
		
		return modelAndView;
	}
	
	//@RequestMapping("/detalhe") sem URL amigavel
	//public ModelAndView detalhe(Integer id){
	@RequestMapping("/detalhe/{id}")
	public ModelAndView detalhe(@PathVariable("id") Integer id){
		ModelAndView modelAndView = new ModelAndView("produtos/detalhe");
		Produto produto = produtoDAO.find(id);
		modelAndView.addObject("produto",produto);
		
		return modelAndView;
		
	}
	
}
