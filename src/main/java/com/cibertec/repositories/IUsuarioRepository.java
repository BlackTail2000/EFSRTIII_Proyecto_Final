package com.cibertec.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cibertec.models.Usuario;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, String> {

	@Query("Select Max(u.codUsua) From Usuario u")
	String findMaxCodUsua();
}
