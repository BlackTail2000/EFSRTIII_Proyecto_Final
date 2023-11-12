package com.cibertec.configurations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.cibertec.models.Usuario;
import com.cibertec.services.interfaces.IUsuarioService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	private IUsuarioService usuarioService;
	
	@Autowired
	public SecurityConfiguration(IUsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> {
			csrf.disable();
		});
		
		http.authorizeHttpRequests(auth -> {
			auth.requestMatchers("/usuario/mantenimiento/**", 
					             "/rol/mantenimiento/**")
			                    .hasRole("Administrador");
			
			auth.requestMatchers("/dispositivoMedico/mantenimiento/**",
					             "/productoFarmaceutico/mantenimiento/**")
			                    .hasRole("Gestor de Inventario");
			
			auth.requestMatchers("/solicitudAbastecimiento/generar",
					             "/solicitudAbastecimiento/nuevaSolicitud")
			                    .hasRole("Solicitante");
			
			auth.requestMatchers("/solicitudAbastecimiento/evaluar")
			                    .hasRole("Evaluador");
			
			auth.anyRequest().authenticated();
		});
		
		http.formLogin(login -> {
			login.loginPage("/login");
			login.usernameParameter("codUsua");
			login.passwordParameter("claveUsua");
			login.loginProcessingUrl("/login");
			login.defaultSuccessUrl("/", true).permitAll();
		});
		
		http.logout(logout -> {
			logout.logoutUrl("/logout");
			logout.logoutSuccessUrl("/login?logout").permitAll();
		});
		
		return http.build();
	}
	
	@Bean
	public UserDetailsManager userDetailsManager() {
		List<UserDetails> listaUserDetails = new ArrayList<>();
		
		for(Usuario item: usuarioService.obtenerTodosLosUsuarios()) {
			if(item.getEstUsua().equals("A")) {
				UserDetails userDetails = User.builder()
						                      .username(item.getCodUsua())
						                      .password(item.getClaveUsua())
						                      .roles(item.getRol().getNomRol())
						                      .build();
				
				listaUserDetails.add(userDetails);
			}
		}
		
		return new InMemoryUserDetailsManager(listaUserDetails);
	}
}
