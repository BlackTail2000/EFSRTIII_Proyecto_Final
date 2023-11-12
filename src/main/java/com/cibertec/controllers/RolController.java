package com.cibertec.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cibertec.models.Rol;
import com.cibertec.models.Usuario;
import com.cibertec.services.interfaces.IRolService;
import com.cibertec.services.interfaces.IUsuarioService;

@Controller
@RequestMapping("/rol")
public class RolController {
	
	IRolService rolService;
	IUsuarioService usuarioService;
	
	@Autowired
	public RolController(IRolService rolService, IUsuarioService usuarioService) {
		this.rolService = rolService;
		this.usuarioService = usuarioService;
	}
	
	@GetMapping("/mantenimiento")
	public String mantenimientoRoles(Model model) {
		Usuario usuario = usuarioService.obtenerUsuarioLogueado();
		if(usuarioService.verificarNuevoUsuario(usuario)) {
			return "redirect:/usuario/cambiarContrasena?nuevo";
		} else {
			model.addAttribute("usuario", usuario);
		}
		return "mantenimiento/roles";
	}

	@GetMapping("/buscar/todos/ordenarPorNombre")
	@ResponseBody
	public List<Rol> obtenerTodosLosRolesOrdenadosPorNombre(){
		return rolService.obtenerTodosLosRolesOrdenadosPorNombre();
	}
	
	@GetMapping("/buscar/todos")
	@ResponseBody
	public List<Rol> obtenerTodosLosRoles(){
		return rolService.obtenerTodosLosRoles();
	}
	
	@PostMapping("/mantenimiento/registrar")
	public ResponseEntity<String> registrarNuevoRol(@RequestBody Rol rol){
		rolService.guardarRol(rol);
		return ResponseEntity.ok("Rol registrado");
	}
	
	@PutMapping("/mantenimiento/actualizar")
	public ResponseEntity<String> actualizarRol(@RequestBody Rol rol){
		rolService.guardarRol(rol);
		return ResponseEntity.ok("Rol registrado");
	}
	
	@DeleteMapping("/mantenimiento/eliminar/{codRol}")
	public ResponseEntity<String> eliminarRol(@PathVariable("codRol") int codRol){
		Rol rol = rolService.obtenerRolPorCodigo(codRol);
		rolService.eliminarRol(rol);
		return ResponseEntity.ok("Rol registrado");
	}
}
