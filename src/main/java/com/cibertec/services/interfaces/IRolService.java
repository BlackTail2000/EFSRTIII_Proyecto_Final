package com.cibertec.services.interfaces;

import java.util.List;

import com.cibertec.models.Rol;

public interface IRolService {

	List<Rol> obtenerTodosLosRolesOrdenadosPorNombre();
	Rol obtenerRolPorCodigo(int codRol);
	List<Rol> obtenerTodosLosRoles();
	Rol guardarRol(Rol rol);
	void eliminarRol(Rol rol);
}
