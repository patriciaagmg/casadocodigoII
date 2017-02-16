package br.com.casadocodigo.loja.controllers;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * ControllerAdvice, observa todos os outros controllers
 *
 */
@ControllerAdvice
public class ExceptionHandlerController {

	
	@ExceptionHandler(Exception.class)
	public ModelAndView trataExceptionGenerica(Exception exception){
		//logar log4J
		exception.printStackTrace();
		
		ModelAndView modelAndView = new ModelAndView("error");
		modelAndView.addObject("exception", exception);
		return modelAndView;
	}
	
	
}
