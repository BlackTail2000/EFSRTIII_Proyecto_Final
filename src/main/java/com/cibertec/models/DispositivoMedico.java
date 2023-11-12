package com.cibertec.models;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Dispositivos_Medicos")
public class DispositivoMedico {

	@Id
	@Column(name = "cod_disp")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codDisp;
	@Column(name = "tipo_disp", length = 40, nullable = false)
	private String tipoDisp;
	@Column(name = "nom_disp", length = 100, nullable = false)
	private String nomDisp;
	@Column(name = "stk_disp")
	private int stkDisp;
	@Column(name = "est_disp", columnDefinition = "CHAR(1)", nullable = false)
	private String estDisp;
	
	@OneToMany(mappedBy = "dispositivoMedico")
	private List<DetalleDispositivoSolicitud> detallesDispositivosSolicitud;
}
