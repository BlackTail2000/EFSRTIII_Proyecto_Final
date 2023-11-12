var modeloProducto = {
	codProd: 0,
	nomProd: "",
	stkProd: 0,
	preProd: 0.0,
	formaFarmaceutica: "",
	estProd: "A"
}

var productoData1;

async function ObtenerTodosLosProductosFarmaceuticos(){
	const response = await fetch("/productoFarmaceutico/buscar/todos");
	const responseJson = await response.json();
	return responseJson;
}

function ListarProductosFarmaceuticos(productoData){
	$("#tablaProductosFarmaceuticos tbody").html("");
	if(productoData.length > 0){
		productoData.forEach((producto) => {
			if(producto.estProd == "A"){
				$("#tablaProductosFarmaceuticos tbody").append(
					$("<tr>").append(
						$("<th>").text(producto.codProd),
						$("<td>").text(producto.nomProd),
						$("<td>").text(producto.preProd),
						$("<td>").text(producto.stkProd),
						$("<td>").text(producto.formaFarmaceutica),
						$('<td class="text-center">').append(
							$('<button class="btn btn-outline-primary btn-editar-producto">')
							.data("dataProducto", producto).append(
								$('<i class="bi bi-pencil">').text(" Editar")
							)
						),
						$('<td class="text-center">').append(
							$('<button class="btn btn-outline-danger btn-eliminar-producto">')
							.data("dataProducto", producto).append(
								$('<i class="bi bi-trash">').text(" Eliminar")
							)
						)
					)
				)
			}
		})
	} else {
		$("#tablaProductosFarmaceuticos tbody").append(
			$("<tr>").append(
				$('<th class="text-center" colspan="7">').text("No se encontraron Productos Farmacéuticos.")
			)
		)
	}
}

function AbrirProductoModal(){
	$("#nomProd").val(modeloProducto.nomProd);
	$("#preProd").val(modeloProducto.preProd);
	$("#stkProd").val(modeloProducto.stkProd);
	$("#formaFarmaceutica").val(modeloProducto.formaFarmaceutica);
	$("#error1").html("");
	$("#modal-crud-productos").modal("show");
}

document.addEventListener("DOMContentLoaded", async function() {
	productoData1 = await ObtenerTodosLosProductosFarmaceuticos();
	ListarProductosFarmaceuticos(productoData1);
})

$(document).on("click", "#btn-nuevo-producto", function() {
	modeloProducto.codProd = 0;
	modeloProducto.nomProd = "";
	modeloProducto.preProd = 0.0;
	modeloProducto.formaFarmaceutica = "";
	modeloProducto.estProd = "A";
	modeloProducto.stkProd = 0;
	
	$("#modal-title-crud-productos").html("Nuevo Producto Farmacéutico");
	$("#btn-guardar-cambios").removeClass("btn-success");
	$("#btn-guardar-cambios").removeClass("btn-primary");
	$("#btn-guardar-cambios").addClass("btn-success");
	AbrirProductoModal();
})

$(document).on("click", ".btn-editar-producto", function() {
	const producto = $(this).data("dataProducto");
	modeloProducto.codProd = producto.codProd;
	modeloProducto.nomProd = producto.nomProd;
	modeloProducto.preProd = producto.preProd;
	modeloProducto.estProd = producto.estProd;
	modeloProducto.formaFarmaceutica = producto.formaFarmaceutica;
	modeloProducto.stkProd = producto.stkProd;
	
	$("#modal-title-crud-productos").html("Editar Producto Farmacéutico");
	$("#btn-guardar-cambios").removeClass("btn-primary");
	$("#btn-guardar-cambios").removeClass("btn-success");
	$("#btn-guardar-cambios").addClass("btn-primary");
	AbrirProductoModal();
})

$(document).on("click", "#btn-guardar-cambios", function() {
	const modelo = {
		codProd: modeloProducto.codProd,
		nomProd: $("#nomProd").val().trim(),
		stkProd: $("#stkProd").val(),
		preProd: $("#preProd").val(),
		formaFarmaceutica: $("#formaFarmaceutica").val().trim(),
		estProd: "A"
	}
	
	if(modelo.nomProd == ""){
		$("#error1").html("Ingresar nombre de nuevo producto farmacéutico.");
		return;
	} else if(modelo.nomProd.length > 100){
		$("#error1").html("Nombre de producto farmacéutico demasiado largo.");
		return;
	} else if(productoData1.some(producto => producto.nomProd == modelo.nomProd && producto.codProd != modelo.codProd)){
		$("#error1").html("Ya existe un producto registrado con ese nombre.");
		return;
	} else if(modelo.preProd == "" || modelo.preProd <= 0){
		$("#error1").html("Ingresar un precio válido para el producto farmacéutico.");
		return;
	} else if(modelo.stkProd == "" || modelo.stkProd < 0){
		$("#error1").html("Ingresar un stock válido para el producto farmacéutico.");
		return;
	} else if(modelo.formaFarmaceutica == ""){
		$("#error1").html("Ingresar una forma farmacéutica.");
		return;
	} else if(formaFarmaceutica.length > 40){
		$("#error1").html("Forma farmacéutica demasiado larga.");
		return;
	}
	
	$("#error1").html("");
	
	if(modelo.codProd == 0){
		fetch("/productoFarmaceutico/mantenimiento/registrar", {
			method: "POST",
			headers: { "Content-Type": "application/json;charset=utf-8" },
			body: JSON.stringify(modelo)
		}).then(response => {
			if(response.ok){
				Swal.fire("¡Operación Exitosa!", "Producto Farmacéutico Registrado", "success");
				$("#modal-crud-productos").modal("hide");
				return Promise.all([ObtenerTodosLosProductosFarmaceuticos()]);
			} else {
				Swal.fire("¡Operación No Exitosa!", "Producto Farmacéutico No Registrado", "error");
			}
		}).then(([data1]) => {
			productoData1 = data1;
			ListarProductosFarmaceuticos(productoData1);
		})
	} else {
		fetch("/productoFarmaceutico/mantenimiento/actualizar", {
			method: "PUT",
			headers: { "Content-Type": "application/json;charset=utf-8" },
			body: JSON.stringify(modelo)
		}).then(response => {
			if(response.ok){
				Swal.fire("¡Operación Exitosa!", "Producto Farmacéutico Actualizado", "success");
				$("#modal-crud-productos").modal("hide");
				return Promise.all([ObtenerTodosLosProductosFarmaceuticos()]);
			} else {
				Swal.fire("¡Operación No Exitosa!", "Producto Farmacéutico No Actualizado", "error");
			}
		}).then(([data1]) => {
			productoData1 = data1;
			ListarProductosFarmaceuticos(productoData1);
		})
	}
})

$(document).on("click", ".btn-eliminar-producto", function() {
	const producto = $(this).data("dataProducto");
	
	Swal.fire({
		title: "¿Eliminar Producto Farmacéutico?",
		icon: "warning",
		showCancelButton: true,
		confirmButtonText: "Aceptar",
		cancelButtonText: "Cancelar"
	}).then(result => {
		if(result.isConfirmed){
			fetch(`/productoFarmaceutico/mantenimiento/eliminar/${producto.codProd}`, {
				method: "PUT",
				headers: { "Content-Type": "application/json;charset=utf-8" }
			}).then(response => {
				if(response.ok){
					Swal.fire("¡Operación Exitosa!", "Producto Farmacéutico Eliminado", "success");
					return Promise.all([ObtenerTodosLosProductosFarmaceuticos()]);
				} else {
					Swal.fire("¡Operación No Exitosa!", "Producto Farmacéutico No Eliminado", "error");
				}
			}).then(([data1]) => {
				productoData1 = data1;
				ListarProductosFarmaceuticos(productoData1);
			})
		}
	})
})