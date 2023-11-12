package com.cibertec.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cibertec.models.SolicitudAbastecimiento;

@Repository
public interface ISolicitudAbastecimientoRepository extends JpaRepository<SolicitudAbastecimiento, Integer> {

	@Query("Select s From SolicitudAbastecimiento s Where s.usuario.codUsua = ?1")
	List<SolicitudAbastecimiento> findAllByUsuario(String codUsua);
}
