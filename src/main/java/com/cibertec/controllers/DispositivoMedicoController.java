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

import com.cibertec.models.DispositivoMedico;
import com.cibertec.models.Usuario;
import com.cibertec.services.interfaces.IDispositivoMedicoService;
import com.cibertec.services.interfaces.IUsuarioService;

@Controller
@RequestMapping("/dispositivoMedico")
public class DispositivoMedicoController {
	
	private IDispositivoMedicoService dispositivoMedicoService;
	private IUsuarioService usuarioService;
	
	@Autowired
	public DispositivoMedicoController(IDispositivoMedicoService dispositivoMedicoService,
			IUsuarioService usuarioService) {
		this.dispositivoMedicoService = dispositivoMedicoService;
		this.usuarioService = usuarioService;
	}

	@GetMapping("/buscar/todos/ordenadosPorNombre")
	@ResponseBody
	public List<DispositivoMedico> obtenerTodosLosDispositivosMedicosOrdenadosPorNombre(){
		return dispositivoMedicoService.obtenerTodosLosDispositivosMedicosOrdenadosPorNombre();
	}
	
	@GetMapping("/mantenimiento")
	public String mantenimientoDispositivosMedicos(Model model) {
		Usuario usuario = usuarioService.obtenerUsuarioLogueado();
		if(usuarioService.verificarNuevoUsuario(usuario)) {
			return "redirect:/usuario/cambiarContrasena?nuevo";
		} else {
			model.addAttribute("usuario", usuario);
		}
		return "mantenimiento/dispositivos medicos";
	}
	
	@GetMapping("/buscar/todos")
	@ResponseBody
	public List<DispositivoMedico> obtenerTodosLosDispositivosMedicos(){
		return dispositivoMedicoService.obtenerTodosLosDispositivosMedicos();
	}
	
	@PostMapping("/mantenimiento/registrar")
	public ResponseEntity<String> registrarNuevoDispositivoMedico(@RequestBody DispositivoMedico dispositivoMedico){
		dispositivoMedicoService.guardarDispositivoMedico(dispositivoMedico);
		return ResponseEntity.ok("Dispositivo Médico registrado.");
	}
	
	@PutMapping("/mantenimiento/actualizar")
	public ResponseEntity<String> actualizarDispositivoMedico(@RequestBody DispositivoMedico dispositivoMedico){
		dispositivoMedicoService.guardarDispositivoMedico(dispositivoMedico);
		return ResponseEntity.ok("Dispositivo Médico actualizado.");
	}
	
	@PutMapping("/mantenimiento/eliminar/{codDisp}")
	public ResponseEntity<String> eliminarDispositivoMedico(@PathVariable("codDisp") int codDisp){
		DispositivoMedico dispositivoMedico = dispositivoMedicoService.buscarDispositivoMedicoPorCodigo(codDisp);
		dispositivoMedico.setEstDisp("I");
		dispositivoMedico.setStkDisp(0);
		dispositivoMedicoService.guardarDispositivoMedico(dispositivoMedico);
		return ResponseEntity.ok("Dispositivo Médico eliminado.");
	}
}
