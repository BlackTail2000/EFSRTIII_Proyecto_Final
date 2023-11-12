package com.cibertec.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cibertec.models.DispositivoMedico;

@Repository
public interface IDispositivoMedicoRepository extends JpaRepository<DispositivoMedico, Integer> {

	List<DispositivoMedico> findAllByOrderByNomDisp();
}
