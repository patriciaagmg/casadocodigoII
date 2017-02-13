package br.com.casadocodigo.loja.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class Usuario  implements UserDetails{

	private static final long serialVersionUID = 1L;

	@Id
	private String email;
	
	private String senha;
	
	private String nome;
	
	@OneToMany(fetch=FetchType.EAGER)
	private List<Role> roles = new ArrayList<Role>();
	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles;
	}

	@Override
	public String getPassword() {
		return this.senha;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	/**
	 * Se a conta não esta expirada, retorna true. 
	 * Esta parte não será controlada neste exemplo, logo sempre retornará true.
	 * @return true.
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * Esta parte não será controlada neste exemplo, logo sempre retornará true.
	 * @return
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 * Esta parte não será controlada neste exemplo, logo sempre retornará true.
	 * @return
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * Esta parte não será controlada neste exemplo, logo sempre retornará true.
	 * @return
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}

	
	
}
