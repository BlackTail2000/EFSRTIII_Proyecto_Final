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

import com.cibertec.models.SolicitudAbastecimiento;
import com.cibertec.models.Usuario;
import com.cibertec.services.interfaces.ISolicitudAbastecimientoService;
import com.cibertec.services.interfaces.IUsuarioService;

@Controller
@RequestMapping("/solicitudAbastecimiento")
public class SolicitudAbastecimientoController {
	
	IUsuarioService usuarioService;
	ISolicitudAbastecimientoService solicitudAbastecimientoService;
	
	@Autowired
	public SolicitudAbastecimientoController(IUsuarioService usuarioService, ISolicitudAbastecimientoService solicitudAbastecimientoService) {
		this.usuarioService = usuarioService;
		this.solicitudAbastecimientoService = solicitudAbastecimientoService;
	}

	@GetMapping("/generar")
	public String verListadoSolicitudesAbastecimiento(Model model) {
		Usuario usuario = usuarioService.obtenerUsuarioLogueado();
		if(usuarioService.verificarNuevoUsuario(usuario)) {
			return "redirect:/usuario/cambiarContrasena?nuevo";
		} else {
			model.addAttribute("usuario", usuario);
		}
		return "solicitud de abastecimiento/ver listado 1";
	}
	
	@GetMapping("/nuevaSolicitud")
	public String nuevaSolicitudAbastecimiento(Model model) {
		Usuario usuario = usuarioService.obtenerUsuarioLogueado();
		if(usuarioService.verificarNuevoUsuario(usuario)) {
			return "redirect:/usuario/cambiarContrasena?nuevo";
		} else {
			model.addAttribute("usuario", usuario);
		}
		return "solicitud de abastecimiento/nueva solicitud";
	}
	
	@PostMapping("/generar")
	public ResponseEntity<String> generarSolicitudAbastecimiento(@RequestBody SolicitudAbastecimiento solicitudAbastecimiento){
		solicitudAbastecimientoService.generarSolicitudAbastecimiento(solicitudAbastecimiento);
		return ResponseEntity.ok("Solicitud de Abastecimiento generada");
	}
	
	@GetMapping("/buscar/todos/porUsuarioLogueado")
	@ResponseBody
	public List<SolicitudAbastecimiento> obtenerTodasLasSolicitudesDeAbastecimientoPorUsuarioLogueado(){
		return solicitudAbastecimientoService.obtenerTodasLasSolicitudesDelUsuarioLogueado();
	}
	
	@GetMapping("/buscar/todos")
	@ResponseBody
	public List<SolicitudAbastecimiento> obtenerTodasLasSolicitudesDeAbastecimiento(){
		return solicitudAbastecimientoService.obtenerTodasLasSolicitudes();
	}
	
	@GetMapping("/evaluar")
	public String evaluarSolicitudes(Model model) {
		Usuario usuario = usuarioService.obtenerUsuarioLogueado();
		if(usuarioService.verificarNuevoUsuario(usuario)) {
			return "redirect:/usuario/cambiarContrasena?nuevo";
		} else {
			model.addAttribute("usuario", usuario);
		}
		return "solicitud de abastecimiento/ver listado 2";
	}
	
	@PutMapping("/aprobarSolicitud/{numSoli}")
	public ResponseEntity<String> aprobarSolicitud(@PathVariable("numSoli") int numSoli){
		SolicitudAbastecimiento solicitudAbastecimiento = solicitudAbastecimientoService.buscarPorCodigo(numSoli);
		solicitudAbastecimiento.setEstSoli("Aprobada");
		solicitudAbastecimientoService.guardarSolicitudAbastecimiento(solicitudAbastecimiento);
		return ResponseEntity.ok("Solicitud Aprobada");
	}
	
	@PutMapping("/desaprobarSolicitud/{numSoli}")
	public ResponseEntity<String> desaprobarSolicitud(@PathVariable("numSoli") int numSoli){
		SolicitudAbastecimiento solicitudAbastecimiento = solicitudAbastecimientoService.buscarPorCodigo(numSoli);
		solicitudAbastecimiento.setEstSoli("Desaprobada");
		solicitudAbastecimientoService.guardarSolicitudAbastecimiento(solicitudAbastecimiento);
		return ResponseEntity.ok("Solicitud Desaprobada");
	}
}
