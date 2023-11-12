package com.cibertec.models;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Solicitudes_Abastecimiento")
public class SolicitudAbastecimiento {

	@Id
	@Column(name = "num_soli")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int numSoli;
	@Column(name = "fec_soli", columnDefinition = "DATE", nullable = false)
	private String fecSoli;
	@Column(name = "des_soli", columnDefinition = "LONGTEXT", nullable = false)
	private String desSoli;
	@Column(name = "est_soli", length = 15, nullable = false)
	private String estSoli;
	@ManyToOne
	@JoinColumn(name = "cod_usua", nullable = false)
	private Usuario usuario;
	
	@OneToMany(mappedBy = "solicitudAbastecimiento")
	private List<DetalleDispositivoSolicitud> detallesDispositivosSolicitud;
	@OneToMany(mappedBy = "solicitudAbastecimiento")
	private List<DetalleProductoSolicitud> detallesProductosSolicitud;
}
