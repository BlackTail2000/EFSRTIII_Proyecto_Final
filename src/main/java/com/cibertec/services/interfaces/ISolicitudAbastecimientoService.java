package com.cibertec.services.interfaces;

import java.util.List;

import com.cibertec.models.SolicitudAbastecimiento;

public interface ISolicitudAbastecimientoService {

	SolicitudAbastecimiento generarSolicitudAbastecimiento(SolicitudAbastecimiento solicitudAbastecimiento);
	SolicitudAbastecimiento guardarSolicitudAbastecimiento(SolicitudAbastecimiento solicitudAbastecimiento);
	List<SolicitudAbastecimiento> obtenerTodasLasSolicitudesDelUsuarioLogueado();
	List<SolicitudAbastecimiento> obtenerTodasLasSolicitudes();
	SolicitudAbastecimiento buscarPorCodigo(int numSoli);
}
