package com.distribuidora.aguamar.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private ImplementsUserDetailsService userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.csrf().disable().authorizeRequests()
		.antMatchers(HttpMethod.GET, "/").permitAll()
		.antMatchers(HttpMethod.GET, "/novoProduto").hasRole("ADMIN")
		.antMatchers(HttpMethod.POST, "/salvarProduto").hasRole("ADMIN")
		.antMatchers(HttpMethod.GET, "/novoUsuario").hasRole("ADMIN")
		.antMatchers(HttpMethod.POST, "/salvarUsuario").hasRole("ADMIN")
		.antMatchers(HttpMethod.GET, "/novoCliente").permitAll()
		.antMatchers(HttpMethod.POST, "/salvarCliente").permitAll()
		.antMatchers(HttpMethod.GET, "/clientes").hasRole("ADMIN")
		.antMatchers(HttpMethod.GET, "/novoPedido").hasRole("CLIENTE")
		.antMatchers(HttpMethod.POST, "/salvarPedido").hasRole("CLIENTE")
		.antMatchers(HttpMethod.GET, "/pedidos").hasRole("ENTREGADOR")
		.antMatchers(HttpMethod.POST, "/registrarPedidoEntregue").hasRole("ENTREGADOR")
		.anyRequest().authenticated()
		.and().formLogin().permitAll()
		.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	public void configure(WebSecurity web) throws Exception{
		web.ignoring().antMatchers("/materialize/**", "/style/**","/img/**");
	}
}
