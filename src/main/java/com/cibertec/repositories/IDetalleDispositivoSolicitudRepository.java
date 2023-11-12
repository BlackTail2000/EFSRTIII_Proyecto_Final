package com.cibertec.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cibertec.models.DetalleDispositivoSolicitud;

@Repository
public interface IDetalleDispositivoSolicitudRepository extends JpaRepository<DetalleDispositivoSolicitud, Integer> {

}
