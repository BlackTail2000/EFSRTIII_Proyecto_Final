package com.cibertec.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.models.DispositivoMedico;
import com.cibertec.repositories.IDispositivoMedicoRepository;
import com.cibertec.services.interfaces.IDispositivoMedicoService;

@Service
public class DispositivoMedicoService implements IDispositivoMedicoService {

	private IDispositivoMedicoRepository dispositivoMedicoRepository;
	
	@Autowired
	public DispositivoMedicoService(IDispositivoMedicoRepository dispositivoMedicoRepository) {
		this.dispositivoMedicoRepository = dispositivoMedicoRepository;
	}
	
	@Override
	public List<DispositivoMedico> obtenerTodosLosDispositivosMedicosOrdenadosPorNombre() {
		List<DispositivoMedico> dispositivosMedicos = dispositivoMedicoRepository.findAllByOrderByNomDisp();
		
		for(DispositivoMedico item: dispositivosMedicos) {
			item.setDetallesDispositivosSolicitud(null);
		}
		
		return dispositivosMedicos;
	}

	@Override
	public DispositivoMedico buscarDispositivoMedicoPorCodigo(int codProd) {
		DispositivoMedico dispositivoMedico = null;
		Optional<DispositivoMedico> optional = dispositivoMedicoRepository.findById(codProd);
		if(optional.isPresent())
			dispositivoMedico = optional.get();
		return dispositivoMedico;
	}

	@Override
	public List<DispositivoMedico> obtenerTodosLosDispositivosMedicos() {
		List<DispositivoMedico> dispositivos = dispositivoMedicoRepository.findAll();
		
		for(DispositivoMedico item: dispositivos) {
			item.setDetallesDispositivosSolicitud(null);
		}
		
		return dispositivos;
	}

	@Override
	public DispositivoMedico guardarDispositivoMedico(DispositivoMedico dispositivoMedico) {
		return dispositivoMedicoRepository.save(dispositivoMedico);
	}

}
