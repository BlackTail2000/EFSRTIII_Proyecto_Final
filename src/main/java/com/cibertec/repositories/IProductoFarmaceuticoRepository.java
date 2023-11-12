package com.cibertec.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cibertec.models.ProductoFarmaceutico;

@Repository
public interface IProductoFarmaceuticoRepository extends JpaRepository<ProductoFarmaceutico, Integer> {

	List<ProductoFarmaceutico> findAllByOrderByNomProd();
}
