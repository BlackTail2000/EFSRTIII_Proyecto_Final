package com.cibertec.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cibertec.models.Usuario;
import com.cibertec.services.interfaces.IRolService;
import com.cibertec.services.interfaces.IUsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	private IRolService rolService;
	private IUsuarioService usuarioService;
	
	@Autowired
	public UsuarioController(IUsuarioService usuarioService, IRolService rolService) {
		this.usuarioService = usuarioService;
		this.rolService = rolService;
	}

	@GetMapping("/mantenimiento")
	public String mantenimientoUsuarios(Model model) {
		Usuario usuario = usuarioService.obtenerUsuarioLogueado();
		if(usuarioService.verificarNuevoUsuario(usuario)) {
			return "redirect:/usuario/cambiarContrasena?nuevo";
		} else {
			model.addAttribute("usuario", usuario);
		}
		return "mantenimiento/usuarios";
	}
	
	@PostMapping("/mantenimiento/registrar")
	public ResponseEntity<String> registrarNuevoUsuario(@RequestBody Usuario usuario){
		usuarioService.registrarNuevoUsuario(usuario);
		return ResponseEntity.ok("Usuario registrado");
	}
	
	@GetMapping("/buscar/todos")
	@ResponseBody
	public List<Usuario> obtenerTodosLosUsuarios(){
		return usuarioService.obtenerTodosLosUsuarios();
	}
	
	@PutMapping("/mantenimiento/cambiarRol/{codUsua}/{codRol}")
	public ResponseEntity<String> cambiarPerfilUsuario(@PathVariable("codUsua") String codUsua, @PathVariable("codRol") int codRol){
		Usuario usuario = usuarioService.obtenerUsuarioPorCodigo(codUsua);
		usuario.setRol(rolService.obtenerRolPorCodigo(codRol));
		usuarioService.cambiarRolUsuario(usuario);
		return ResponseEntity.ok("Rol de Usuario actualizado.");
	}
	
	@PutMapping("/mantenimiento/cambiarEstado/{codUsua}")
	public ResponseEntity<String> cambiarEstadoUsuario(@PathVariable("codUsua") String codUsua){
		Usuario usuario = usuarioService.obtenerUsuarioPorCodigo(codUsua);
		if(usuario.getEstUsua().equals("A")) {
			usuario.setEstUsua("I");
			usuarioService.guardarUsuario(usuario);
			usuarioService.eliminarUserDetails(usuario);
		} else {
			usuario.setEstUsua("A");
			usuarioService.guardarUsuario(usuario);
			usuarioService.crearUserDetails(usuario);
		}
		
		return ResponseEntity.ok("Estado de Usuario actualizado.");
	}
	
	@PutMapping("/cambiarContrasena/{nuevaClave}")
	public ResponseEntity<String> cambiarContrasenaActual(@PathVariable("nuevaClave") String nuevaClave){
		Usuario usuario = usuarioService.obtenerUsuarioLogueado();
		usuario.setClaveUsua(nuevaClave);
		usuarioService.cambiarContrasena(usuario);
		return ResponseEntity.ok("Cambio de contraseña completado.");
	}
	
	@GetMapping("/datosPersonales")
	public String datosPersonales(Model model) {
		Usuario usuario = usuarioService.obtenerUsuarioLogueado();
		if(usuarioService.verificarNuevoUsuario(usuario)) {
			return "redirect:/usuario/cambiarContrasena?nuevo";
		} else {
			model.addAttribute("usuario", usuario);
		}
		return "datos personales";
	}
	
	@PutMapping("/datosPersonales/{nomUsua}/{apeUsua}/{emailUsua}")
	public ResponseEntity<String> cambiarDatosPersonales(@PathVariable("nomUsua") String nomUsua, @PathVariable("apeUsua") String apeUsua,
			@PathVariable("emailUsua") String emailUsua){
		Usuario usuario = usuarioService.obtenerUsuarioLogueado();
		usuario.setNomUsua(nomUsua);
		usuario.setApeUsua(apeUsua);
		usuario.setEmailUsua(emailUsua);
		usuarioService.guardarUsuario(usuario);
		return ResponseEntity.ok("Datos personales actualizados.");
	}
}
