package com.cibertec.models;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Usuarios")
public class Usuario {

	@Id
	@Column(name = "cod_usua", columnDefinition = "CHAR(7)")
	private String codUsua;
	@Column(name = "nom_usua", length = 50, nullable = false)
	private String nomUsua;
	@Column(name = "ape_usua", length = 50, nullable = false)
	private String apeUsua;
	@Column(name = "email_usua", length = 50, nullable = false)
	private String emailUsua;
	@Column(name = "clave_usua", columnDefinition = "CHAR(60)", nullable = false)
	private String claveUsua;
	@Column(name = "est_usua", columnDefinition = "CHAR(1)", nullable = false)
	private String estUsua;
	@ManyToOne
	@JoinColumn(name = "cod_rol", nullable = false)
	private Rol rol;
	
	@OneToMany(mappedBy = "usuario")
	private List<SolicitudAbastecimiento> solicitudesAbastecimiento;
}
