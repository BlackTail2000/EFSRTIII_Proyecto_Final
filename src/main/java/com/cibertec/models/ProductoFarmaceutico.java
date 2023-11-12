package com.cibertec.models;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Productos_Farmaceuticos")
public class ProductoFarmaceutico {

	@Id
	@Column(name = "cod_prod")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codProd;
	@Column(name = "nom_prod", length = 100, nullable = false)
	private String nomProd;
	@Column(name = "stk_prod")
	private int stkProd;
	@Column(name = "pre_prod", columnDefinition = "DECIMAL(8,2)", nullable = false)
	private double preProd;
	@Column(name = "forma_farmaceutica", length = 40, nullable = false)
	private String formaFarmaceutica;
	@Column(name = "est_prod", columnDefinition = "CHAR(1)", nullable = false)
	private String estProd;
	
	@OneToMany(mappedBy = "productoFarmaceutico")
	private List<DetalleProductoSolicitud> detallesProductosSolicitud;
}
