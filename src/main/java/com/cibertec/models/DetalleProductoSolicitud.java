package com.cibertec.models;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Detalles_Productos_Solicitud")
public class DetalleProductoSolicitud {

	@Id
	@Column(name = "num_detalle_prod_soli")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int numDetalleProdSoli;
	@ManyToOne
	@JoinColumn(name = "num_soli", nullable = false)
	private SolicitudAbastecimiento solicitudAbastecimiento;
	@ManyToOne
	@JoinColumn(name = "cod_prod", nullable = false)
	private ProductoFarmaceutico productoFarmaceutico;
	private int cantidad;
	@Column(name = "uni_medida", length = 40, nullable = false)
	private String uniMedida;
}
