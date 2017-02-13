package br.com.casadocodigo.loja.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.com.casadocodigo.loja.daos.UsuarioDAO;

/**
 * Classe de configuração do Spring Security.
 * @EnableWebMvcSecurity - provê a parte de segurança que necessitamos oferecidas pelo Spring.
 * Atenção: Adicionar esta classe em ServletSpringMVC no método getRootConfigClasses, 
 * para que seja iniciada esta configuração assim que o sistema subir.
 * 
 */
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UsuarioDAO usuarioDAO;
	
	/**
	 * Dando acesso e/ou restringindo.
	 * A restrição poderá ser feita através de URL ou dos métodos.
	 */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.authorizeRequests()
    	.antMatchers("/produtos/form").hasRole("ADMIN") // No banco está ROLE_ADMIN, é padrão. O spring já add o "ROLE_" antes do "ADMIN".
    	.antMatchers("/carrinho/**").permitAll()
    	.antMatchers(HttpMethod.POST,"/produtos").hasRole("ADMIN")  //Só permite para quem tem a hole ADMIN.
    	.antMatchers(HttpMethod.GET,"/produtos").permitAll()  //llberando os acessos aos métodos GET.
    	.antMatchers("/produtos/**").permitAll() //dando acesso a qq url que tenha depois do /produtos/ 
    	.antMatchers("/resources/**").permitAll()
    	.antMatchers("/").permitAll()
    	.anyRequest().authenticated()
    	.and().formLogin().loginPage("/login").permitAll()
    	.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
    	
    	super.configure(http);
    }

    
    /**
     * Será usado o encriptador do Spring: BCryptPasswordEncoder.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.userDetailsService(usuarioDAO)
    	.passwordEncoder(new BCryptPasswordEncoder());
    
    }
    
}
