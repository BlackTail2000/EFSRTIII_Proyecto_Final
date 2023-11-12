package com.cibertec.models;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Detalles_Dispositivos_Solicitud")
public class DetalleDispositivoSolicitud {

	@Id
	@Column(name = "num_detalle_disp_soli")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int numDetalleDispSoli;
	@ManyToOne
	@JoinColumn(name = "num_soli", nullable = false)
	private SolicitudAbastecimiento solicitudAbastecimiento;
	@ManyToOne
	@JoinColumn(name = "cod_disp", nullable = false)
	private DispositivoMedico dispositivoMedico;
	private int cantidad;
}
