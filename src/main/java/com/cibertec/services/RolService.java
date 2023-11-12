package com.cibertec.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.models.Rol;
import com.cibertec.repositories.IRolRepository;
import com.cibertec.services.interfaces.IRolService;

@Service
public class RolService implements IRolService {
	
	IRolRepository rolRepository;
	
	@Autowired
	public RolService(IRolRepository rolRepository) {
		this.rolRepository = rolRepository;
	}

	@Override
	public List<Rol> obtenerTodosLosRolesOrdenadosPorNombre() {
		List<Rol> roles = rolRepository.findAllByOrderByNomRol();
		for(Rol item: roles) {
			item.setUsuarios(null);
		}
		return roles;
	}

	@Override
	public Rol obtenerRolPorCodigo(int codRol) {
		Rol rol = null;
		Optional<Rol> optional = rolRepository.findById(codRol);
		if(optional.isPresent())
			rol = optional.get();
		return rol;
	}

	@Override
	public List<Rol> obtenerTodosLosRoles() {
		List<Rol> roles = rolRepository.findAll();
		for(Rol item: roles) {
			item.setUsuarios(null);
		}
		return roles;
	}

	@Override
	public Rol guardarRol(Rol rol) {
		return rolRepository.save(rol);
	}

	@Override
	public void eliminarRol(Rol rol) {
		rolRepository.delete(rol);
	}

}
