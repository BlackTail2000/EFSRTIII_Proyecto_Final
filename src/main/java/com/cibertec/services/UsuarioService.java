package com.cibertec.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import com.cibertec.models.Usuario;
import com.cibertec.repositories.IUsuarioRepository;
import com.cibertec.services.interfaces.IUsuarioService;

import jakarta.transaction.Transactional;

@Service
public class UsuarioService implements IUsuarioService {
	
	private IUsuarioRepository usuarioRepository;
	private PasswordEncoder passwordEncoder;
	private UserDetailsManager userDetailsManager;
	
	@Autowired
	public UsuarioService(IUsuarioRepository usuarioRepository, @Lazy PasswordEncoder passwordEncoder,
			@Lazy UserDetailsManager userDetailsManager) {
		this.usuarioRepository = usuarioRepository;
		this.passwordEncoder = passwordEncoder;
		this.userDetailsManager = userDetailsManager;
	}

	@Override
	public List<Usuario> obtenerTodosLosUsuarios() {
		List<Usuario> usuarios = usuarioRepository.findAll();
		
		for(Usuario item: usuarios) {
			item.setSolicitudesAbastecimiento(null);
			item.getRol().setUsuarios(null);
		}
		
		return usuarios;
	}

	@Override
	public Usuario obtenerUsuarioLogueado() {
		Usuario usuario = null;
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(userDetails!=null) {
			usuario = this.obtenerUsuarioPorCodigo(userDetails.getUsername());
			usuario.setSolicitudesAbastecimiento(null);
			usuario.getRol().setUsuarios(null);
		}
		return usuario;
	}

	@Override
	public Usuario obtenerUsuarioPorCodigo(String codUsua) {
		Usuario usuario = null;
		Optional<Usuario> optional = usuarioRepository.findById(codUsua);
		if(optional.isPresent())
			usuario = optional.get();
		return usuario;
	}

	@Override
	public String obtenerUltimoCodigo() {
		return usuarioRepository.findMaxCodUsua();
	}

	@Override
	@Transactional
	public Usuario registrarNuevoUsuario(Usuario usuario) {
		usuario.setCodUsua(this.obtenerCodigoAutoIncrement());
		usuario.setClaveUsua(passwordEncoder.encode(usuario.getClaveUsua()));
		usuario = usuarioRepository.save(usuario);
		this.crearUserDetails(usuario);
		return usuario;
	}

	@Override
	public String obtenerCodigoAutoIncrement() {
		String codUsua = "USUA001";
		String maxCodUsua = this.obtenerUltimoCodigo();
		if(maxCodUsua!=null) {
			int codUsuaInt = Integer.parseInt(maxCodUsua.substring(4, 7));
			codUsuaInt++;
			if(codUsuaInt < 10)
				codUsua = "USUA00";
			else if(codUsuaInt < 100)
				codUsua = "USUA0";
			else
				codUsua = "USUA";
			
			codUsua+=codUsuaInt;
		}
		
		return codUsua;
	}

	@Override
	public UserDetails crearUserDetails(Usuario usuario) {
		UserDetails userDetails = User.builder()
				                      .username(usuario.getCodUsua())
				                      .password(usuario.getClaveUsua())
				                      .roles(usuario.getRol().getNomRol())
				                      .build();
		
		userDetailsManager.createUser(userDetails);
		return userDetails;
	}

	@Override
	public Usuario cambiarRolUsuario(Usuario usuario) {
		usuario = usuarioRepository.save(usuario);
		this.modificarUserDetails(usuario);
		return usuario;
	}

	@Override
	public UserDetails modificarUserDetails(Usuario usuario) {
		UserDetails userDetails = userDetailsManager.loadUserByUsername(usuario.getCodUsua());
		
		userDetails = User.withUserDetails(userDetails)
				          .password(usuario.getClaveUsua())
				          .roles(usuario.getRol().getNomRol())
				          .build();
		
		userDetailsManager.updateUser(userDetails);
		return userDetails;
	}

	@Override
	public void eliminarUserDetails(Usuario usuario) {
		userDetailsManager.deleteUser(usuario.getCodUsua());
	}

	@Override
	public Usuario guardarUsuario(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

	@Override
	public boolean verificarNuevoUsuario(Usuario usuario) {
		String claUsuaCifrada = usuario.getClaveUsua();
		String claUsuaNueva = "123";
		boolean resultado = passwordEncoder.matches(claUsuaNueva, claUsuaCifrada);
		return resultado;
	}

	@Override
	public void cambiarContrasena(Usuario usuario) {
		usuario.setClaveUsua(passwordEncoder.encode(usuario.getClaveUsua()));
		usuario = this.guardarUsuario(usuario);
		this.modificarUserDetails(usuario);
	}

}
