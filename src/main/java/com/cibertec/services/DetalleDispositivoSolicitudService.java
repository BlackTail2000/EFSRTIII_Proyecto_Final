package com.cibertec.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.models.DetalleDispositivoSolicitud;
import com.cibertec.repositories.IDetalleDispositivoSolicitudRepository;
import com.cibertec.services.interfaces.IDetalleDispositivoSolicitudService;

@Service
public class DetalleDispositivoSolicitudService implements IDetalleDispositivoSolicitudService {

	private IDetalleDispositivoSolicitudRepository detalleDispositivoSolicitudRepository;
	
	@Autowired
	public DetalleDispositivoSolicitudService(IDetalleDispositivoSolicitudRepository detalleDispositivoSolicitudRepository) {
		this.detalleDispositivoSolicitudRepository = detalleDispositivoSolicitudRepository;
	}
	
	@Override
	public DetalleDispositivoSolicitud guardarDetalleDispositivoSolicitud(
			DetalleDispositivoSolicitud detalleDispositivoSolicitud) {
		return detalleDispositivoSolicitudRepository.save(detalleDispositivoSolicitud);
	}

}
