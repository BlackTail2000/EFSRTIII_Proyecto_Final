package com.cibertec.services.interfaces;

import java.util.List;

import com.cibertec.models.ProductoFarmaceutico;

public interface IProductoFarmaceuticoService {

	List<ProductoFarmaceutico> obtenerTodosLosProductosFarmaceuticosOrdenadosPorNombre();
	ProductoFarmaceutico buscarProductoFarmaceuticoPorCodigo(int codProd);
	List<ProductoFarmaceutico> obtenerTodosLosProductosFarmaceuticos();
	ProductoFarmaceutico guardarProductoFarmaceutico(ProductoFarmaceutico productoFarmaceutico);
}
