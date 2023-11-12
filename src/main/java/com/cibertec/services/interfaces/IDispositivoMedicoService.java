package com.cibertec.services.interfaces;

import java.util.List;

import com.cibertec.models.DispositivoMedico;

public interface IDispositivoMedicoService {

	List<DispositivoMedico> obtenerTodosLosDispositivosMedicosOrdenadosPorNombre();
	DispositivoMedico buscarDispositivoMedicoPorCodigo(int codProd);
	List<DispositivoMedico> obtenerTodosLosDispositivosMedicos();
	DispositivoMedico guardarDispositivoMedico(DispositivoMedico dispositivoMedico);
}
