package com.cibertec.models;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Roles")
public class Rol {

	@Id
	@Column(name = "cod_rol")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codRol;
	@Column(name = "nom_rol", length = 35, nullable = false)
	private String nomRol;
	
	@OneToMany(mappedBy = "rol")
	private List<Usuario> usuarios;
}
