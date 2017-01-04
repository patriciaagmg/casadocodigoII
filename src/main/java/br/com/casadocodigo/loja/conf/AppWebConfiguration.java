package br.com.casadocodigo.loja.conf;



import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import br.com.casadocodigo.loja.controllers.HomeController;
import br.com.casadocodigo.loja.daos.ProdutoDAO;
import br.com.casadocodigo.loja.infra.FileSaver;
import br.com.casadocodigo.loja.models.CarrinhoCompras;

/**
 * Configura o Spring que será utilizado o webmvc através da anotação: @EnableWebMvc.
 * Assim estaremos habilitando o webmvc no projeto.
 * Mapeando Controllers ,através do ComponentScam.
 * ESta classe contém as conversões básicas do projeto SpringMVC. Atenção ao mante-la.
 *
 */
@EnableWebMvc
@ComponentScan(basePackageClasses={HomeController.class,ProdutoDAO.class, FileSaver.class,CarrinhoCompras.class})
public class AppWebConfiguration {

	
	/**
	 * Configurações AppWebConfiguration:
	 * Retorna um método para mapeamento das jsp's.
	 * Resolvedor interno de views.
	 * @Bean - indica que o método pe gerenciado pelo Spring.
	 */
	@Bean
	public InternalResourceViewResolver internalResourceViewResolver(){
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		
		//resolver.setExposeContextBeansAsAttributes(true); Todos os beans expostos na JSP
		resolver.setExposedContextBeanNames("carrinhoCompras"); //mesmo nome da classe, porém com letra minuscula.
		
		return resolver;
	}
	
	
	/**
	 * Configurações AppWebConfiguration:
	 * Método para configuração do messageSource
	 * @return messageSource - onde estão os arquivos de mensagens.
	 */
	@Bean
	public MessageSource messageSource(){
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		
		messageSource.setBasename("/WEB-INF/messages");
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(1);//Atualização do arquivo automaticamente.
		
		return messageSource;
	}
	
	
	/**
	 * 
	 * Configurações AppWebConfiguration:
	 * Converte data em String em Calendar.
	 * O proprio Spring espera esse nome de metodo com "mvcConversionService".
	 * Atenção:Ao invés de usar este método, poderia usar para TODO atributo data esta anotação:@DateTimeFormat(pattern="dd/MM/yyyy").
	 * Usando este método, só é necessário usar @DateTimeFormat no atributo data.
	 * @return conversao da data Sprong em Calendar.
	 */
	@Bean
	public FormattingConversionService mvcConversionService(){
		//Formato
		DefaultFormattingConversionService conversionalSevice 
		= new DefaultFormattingConversionService();		
		DateFormatterRegistrar registrar = new DateFormatterRegistrar();		
		registrar.setFormatter(new DateFormatter("dd/MM/yyyy"));
		registrar.registerFormatters(conversionalSevice);
		
		return conversionalSevice;
		
	}
	
	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();		
	}
	
}
