package com.cibertec.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cibertec.models.DetalleProductoSolicitud;

@Repository
public interface IDetalleProductoSolicitudRepository extends JpaRepository<DetalleProductoSolicitud, Integer> {

}
