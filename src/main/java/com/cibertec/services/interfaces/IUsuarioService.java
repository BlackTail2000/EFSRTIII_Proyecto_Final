package com.cibertec.services.interfaces;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.cibertec.models.Usuario;

public interface IUsuarioService {

	List<Usuario> obtenerTodosLosUsuarios();
	Usuario obtenerUsuarioLogueado();
	Usuario obtenerUsuarioPorCodigo(String codUsua);
	String obtenerUltimoCodigo();
	Usuario registrarNuevoUsuario(Usuario usuario);
	String obtenerCodigoAutoIncrement();
	UserDetails crearUserDetails(Usuario usuario);
	Usuario cambiarRolUsuario(Usuario usuario);
	UserDetails modificarUserDetails(Usuario usuario);
	void eliminarUserDetails(Usuario usuario);
	Usuario guardarUsuario(Usuario usuario);
	boolean verificarNuevoUsuario(Usuario usuario);
	void cambiarContrasena(Usuario usuario);
}
