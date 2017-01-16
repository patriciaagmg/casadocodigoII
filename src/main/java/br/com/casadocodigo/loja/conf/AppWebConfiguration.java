package br.com.casadocodigo.loja.conf;



import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.text.InternationalFormatter;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.google.common.cache.CacheBuilder;

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
@EnableCaching  //habilita o cache
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
	
	/**
	 * Configurações AppWebConfiguration:
	 * Método para prover o cache.Gerente de cache. Não usar o ConcurrentMapCacheManager do Spring.
	 * O cache guardará até 100 livros, e estará ativo por 5 minutos.
	 * @return CacheManager
	 */
	@Bean
	public CacheManager cacheManager(){
		CacheBuilder<Object,Object> builder = CacheBuilder.newBuilder()
		.maximumSize(100)//100 foi escolhido devido a quantidade de livros carregados na tela onde o cache é utilizado.
		.expireAfterAccess(5, TimeUnit.MINUTES);
		
		GuavaCacheManager manager = new GuavaCacheManager();		
		manager.setCacheBuilder(builder);
		
		return manager;
	
	}
	
	/**
	 * Vai resolver o retorno, se JSON ou html.
	 */
	@Bean
	public ViewResolver contentNegociationViewResolver(ContentNegotiationManager manager){
		
		ArrayList<ViewResolver> viewResolvers = new ArrayList<>();
		viewResolvers.add(internalResourceViewResolver());
		viewResolvers.add(new JsonViewResolver());
		
		ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
		resolver.setViewResolvers(viewResolvers);
		resolver.setContentNegotiationManager(manager);
		
		
		return resolver;
		
	}
	

	
}
