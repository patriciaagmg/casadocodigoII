package br.com.casadocodigo.loja.conf;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Classe de configurações básicas para configurações do Spring.
 * Informa ao servidor que o Spring atenderá as requisições do projeto.
 * AbstractAnnotationConfigDispatcherServletInitializer - Inicializa o Servlet do Spring.
 */
public class ServletSpringMVC extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[]{SecurityConfiguration.class,AppWebConfiguration.class, JPAConfiguration.class};
	}

	/**
	 * Configurações: COnfigura o controller que será utilizado
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[]{};
		}

	/**
	 * Configurações: Mapeia o servlet do Spring. NA URL, a partir da / conforme abaixo, deverá ser mapeado pelo Spring.
	 */
	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}

	/**
	 * Configurações: Tratando UTF-8
	 */
	@Override 
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding("UTF-8");
		
		return new Filter[] {encodingFilter};
	}
	
	/**
	 * Configurações: Registrando o multpart - Tratando Arquivos.
	 */
	@Override
	protected void customizeRegistration(Dynamic registration) {
		registration.setMultipartConfig(new MultipartConfigElement(""));
	}
	

	/**
	 * Definindo no contexto do spring que a aplicação usuará o contexto de "dev". (acesso ao banco)
	 */
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);
		servletContext.addListener(RequestContextListener.class);//vai ouvir os contexto da aplicação
		servletContext.setInitParameter("spring.profiles.active", "dev");//Profile ativo no servidor.
	}
}
