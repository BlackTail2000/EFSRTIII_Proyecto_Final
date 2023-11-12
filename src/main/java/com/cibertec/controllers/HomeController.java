package com.cibertec.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cibertec.models.Usuario;
import com.cibertec.services.interfaces.IUsuarioService;

@Controller
public class HomeController {

	private IUsuarioService usuarioService;
	
	@Autowired
	public HomeController(IUsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/")
	public String index(Model model) {
		Usuario usuario = usuarioService.obtenerUsuarioLogueado();
		if(usuarioService.verificarNuevoUsuario(usuario)) {
			return "redirect:/usuario/cambiarContrasena?nuevo";
		} else {
			model.addAttribute("usuario", usuario);
		}
		
		return "index";
	}
	
	@GetMapping("/usuario/cambiarContrasena")
	public String cambiarContrasena(Model model) {
		model.addAttribute("usuario", usuarioService.obtenerUsuarioLogueado());
		return "cambiar contrasena";
	}
}
