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

import com.cibertec.models.ProductoFarmaceutico;
import com.cibertec.models.Usuario;
import com.cibertec.services.interfaces.IProductoFarmaceuticoService;
import com.cibertec.services.interfaces.IUsuarioService;

@Controller
@RequestMapping("/productoFarmaceutico")
public class ProductoFarmaceuticoController {
	
	private IProductoFarmaceuticoService productoFarmaceuticoService;
	private IUsuarioService usuarioService;
	
	@Autowired
	public ProductoFarmaceuticoController(IProductoFarmaceuticoService productoFarmaceuticoService,
			IUsuarioService usuarioService) {
		this.productoFarmaceuticoService = productoFarmaceuticoService;
		this.usuarioService = usuarioService;
	}

	@GetMapping("/buscar/todos/ordenarPorNombre")
	@ResponseBody
	public List<ProductoFarmaceutico> obtenerTodosLosProductosFarmaceuticosOrdenadosPorNombre(){
		return productoFarmaceuticoService.obtenerTodosLosProductosFarmaceuticosOrdenadosPorNombre();
	}
	
	@GetMapping("/mantenimiento")
	public String mantenimientoProductosFarmaceuticos(Model model) {
		Usuario usuario = usuarioService.obtenerUsuarioLogueado();
		if(usuarioService.verificarNuevoUsuario(usuario)) {
			return "redirect:/usuario/cambiarContrasena?nuevo";
		} else {
			model.addAttribute("usuario", usuario);
		}
		return "mantenimiento/productos farmaceuticos";
	}
	
	@GetMapping("/buscar/todos")
	@ResponseBody
	public List<ProductoFarmaceutico> obtenerTodosLosProductoFarmaceuticos(){
		return productoFarmaceuticoService.obtenerTodosLosProductosFarmaceuticos();
	}
	
	@PostMapping("/mantenimiento/registrar")
	public ResponseEntity<String> registrarNuevoProductoFarmaceutico(@RequestBody ProductoFarmaceutico productoFarmaceutico){
		productoFarmaceuticoService.guardarProductoFarmaceutico(productoFarmaceutico);
		return ResponseEntity.ok("Producto Farmacéutico Registrado.");
	}
	
	@PutMapping("/mantenimiento/actualizar")
	public ResponseEntity<String> actualizarProductoFarmaceutico(@RequestBody ProductoFarmaceutico productoFarmaceutico){
		productoFarmaceuticoService.guardarProductoFarmaceutico(productoFarmaceutico);
		return ResponseEntity.ok("Producto Farmacéutico Actualizado.");
	}
	
	@PutMapping("/mantenimiento/eliminar/{codProd}")
	public ResponseEntity<String> eliminarProductoFarmaceutico(@PathVariable("codProd") int codProd){
		ProductoFarmaceutico productoFarmaceutico = productoFarmaceuticoService.buscarProductoFarmaceuticoPorCodigo(codProd);
		productoFarmaceutico.setEstProd("I");
		productoFarmaceutico.setStkProd(0);
		productoFarmaceuticoService.guardarProductoFarmaceutico(productoFarmaceutico);
		return ResponseEntity.ok("Producto Farmacéutico Eliminado.");
	}
}
