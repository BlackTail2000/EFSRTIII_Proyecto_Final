package com.cibertec.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.models.DetalleProductoSolicitud;
import com.cibertec.repositories.IDetalleProductoSolicitudRepository;
import com.cibertec.services.interfaces.IDetalleProductoSolicitudService;

@Service
public class DetalleProductoSolicitudService implements IDetalleProductoSolicitudService {

	private IDetalleProductoSolicitudRepository detalleProductoSolicitudRepository;
	
	@Autowired
	public DetalleProductoSolicitudService(IDetalleProductoSolicitudRepository detalleProductoSolicitudRepository) {
		this.detalleProductoSolicitudRepository = detalleProductoSolicitudRepository;
	}
	
	@Override
	public DetalleProductoSolicitud guardarDetalleProductoSolicitud(DetalleProductoSolicitud detalleProductoSolicitud) {
		return detalleProductoSolicitudRepository.save(detalleProductoSolicitud);
	}

}
