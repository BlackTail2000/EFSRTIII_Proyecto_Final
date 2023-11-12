package com.cibertec.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.models.ProductoFarmaceutico;
import com.cibertec.repositories.IProductoFarmaceuticoRepository;
import com.cibertec.services.interfaces.IProductoFarmaceuticoService;

@Service
public class ProductoFarmaceuticoService implements IProductoFarmaceuticoService {
	
	private IProductoFarmaceuticoRepository productoFarmaceuticoRepository;
	
	@Autowired
	public ProductoFarmaceuticoService(IProductoFarmaceuticoRepository productoFarmaceuticoRepository) {
		this.productoFarmaceuticoRepository = productoFarmaceuticoRepository;
	}

	@Override
	public List<ProductoFarmaceutico> obtenerTodosLosProductosFarmaceuticosOrdenadosPorNombre() {
		List<ProductoFarmaceutico> productoFarmaceuticos = productoFarmaceuticoRepository.findAllByOrderByNomProd();
		
		for(ProductoFarmaceutico item: productoFarmaceuticos) {
			item.setDetallesProductosSolicitud(null);
		}
		
		return productoFarmaceuticos;
	}

	@Override
	public ProductoFarmaceutico buscarProductoFarmaceuticoPorCodigo(int codProd) {
		ProductoFarmaceutico productoFarmaceutico = null;
		Optional<ProductoFarmaceutico> optional = productoFarmaceuticoRepository.findById(codProd);
		if(optional.isPresent())
			productoFarmaceutico = optional.get();
		return productoFarmaceutico;
	}

	@Override
	public List<ProductoFarmaceutico> obtenerTodosLosProductosFarmaceuticos() {
		List<ProductoFarmaceutico> productos = productoFarmaceuticoRepository.findAll();
		for(ProductoFarmaceutico item: productos) {
			item.setDetallesProductosSolicitud(null);
		}
		return productos;
	}

	@Override
	public ProductoFarmaceutico guardarProductoFarmaceutico(ProductoFarmaceutico productoFarmaceutico) {
		return productoFarmaceuticoRepository.save(productoFarmaceutico);
	}

}
