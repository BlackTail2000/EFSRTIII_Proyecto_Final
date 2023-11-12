document.addEventListener("DOMContentLoaded", function() {
	fetch("/productoFarmaceutico/buscar/todos/ordenarPorNombre").then(response => {
		return response.ok ? response.json() : Promise.reject(response);
	}).then(responseJson => {
		if(responseJson.length > 0){
			$("#tablaProductos tbody").html("");
			responseJson.forEach((producto) => {
				$("#tablaProductos tbody").append(
					$("<tr>").append(
						$("<td>").text(producto.nomProd),
						$('<td class="text-center">').append(
							$('<button class="btn btn-outline-warning btn-agregar-producto">')
							.data("dataProducto", producto).append(
								$('<i class="bi bi-check">').text(" Agregar")
							)
						)
					)
				)
			})
		}
	})
	
	fetch("/dispositivoMedico/buscar/todos/ordenadosPorNombre").then(response => {
		return response.ok ? response.json() : Promise.reject(response);
	}).then(responseJson => {
		if(responseJson.length > 0){
			$("#tablaDispositivos tbody").html("");
			responseJson.forEach((dispositivo) => {
				$("#tablaDispositivos tbody").append(
					$("<tr>").append(
						$("<td>").text(dispositivo.nomDisp),
						$('<td class="text-center">').append(
							$('<button class="btn btn-outline-warning btn-agregar-dispositivo">')
							.data("dataDispositivo", dispositivo).append(
								$('<i class="bi bi-check">').text(" Agregar")
							)
						)
					)
				)
			})
		}
	})
})

$(document).on("click", ".btn-agregar-producto", function() {
	const producto = $(this).data("dataProducto");
	
	if($("#div_producto_" + producto.codProd).length > 0){
		return;
	}
	
	const inputCodigo = `<input type="hidden" id="codProd_${producto.codProd}" value="${producto.codProd}">`;
	const inputNombre = `<h5>${producto.nomProd}</h5>`;
	const inputCantidad = `<div class="mb-3">
	                           <label class="form-label">Cantidad:</label>
	                           <input type="number" class="form-control" id="cantidad_producto_${producto.codProd}">
	                       </div>`;
	const inputUniMedida = `<div class="mb-3">
	                            <label class="form-label">Unidad de Medida:</label>
	                            <input type="text" class="form-control" id="uniMed_producto_${producto.codProd}">
	                        </div>`;
    const divProducto = $(`<div id="div_producto_${producto.codProd}" class="mb-3">` + inputCodigo + inputNombre + inputCantidad + inputUniMedida + "</div>");
    const btnRemover = $(`<button type="button" class="btn btn-outline-danger btn-remover-producto my-3"><i class="bi bi-x"></i> Remover</button>`);
    btnRemover.data("dataProducto", producto);
    divProducto.append(btnRemover);
    $("#detallesProductosSolicitud").append(divProducto);
})

$(document).on("click", ".btn-agregar-dispositivo", function() {
	const dispositivo = $(this).data("dataDispositivo");
	
	if($("#div_dispositivo_" + dispositivo.codDisp).length > 0){
		return;
	}
	
	const inputCodigo = `<input type="hidden" id="codDisp_${dispositivo.codDisp}" value="${dispositivo.codDisp}">`;
	const inputNombre = `<h5>${dispositivo.nomDisp}</h5>`;
	const inputCantidad = `<div class="mb-3">
	                           <label class="form-label">Cantidad:</label>
	                           <input type="number" class="form-control" id="cantidad_dispositivo_${dispositivo.codDisp}">
	                       </div>`;
	const divDispositivo = $(`<div id="div_dispositivo_${dispositivo.codDisp}" class="mb-3">` + inputCodigo + inputNombre + inputCantidad + "</h5>");
	const btnRemover = $(`<button type="button" class="btn btn-outline-danger btn-remover-dispositivo my-3"><i class="bi bi-x"></i> Remover</button>`);
	btnRemover.data("dataDispositivo", dispositivo);
	divDispositivo.append(btnRemover);
	$("#detallesDispositivosSolicitud").append(divDispositivo);
})

$(document).on("click", ".btn-remover-producto", function() {
	const producto = $(this).data("dataProducto");
	$("#div_producto_" + producto.codProd).remove();
})

$(document).on("click", ".btn-remover-dispositivo", function() {
	const dispositivo = $(this).data("dataDispositivo");
	$("#div_dispositivo_" + dispositivo.codDisp).remove();
})

$(document).on("click", ".btn-generar-solicitud", function() {
	let isValid = true;
	const detallesProductosSolicitud = [];
	$("#detallesProductosSolicitud > div").each(function() {
		const codProd = $(this).find("input[type='hidden']").val();
		const cantidad = $(this).find("input[type='number']").val();
		const uniMedida = $(this).find("input[type='text']").val().trim();
		
		if(cantidad == "" || cantidad <= 0){
			$("#error1").html("Ingresar cantidades válidas para los productos farmacéuticos solicitados.");
			isValid = false;
		} else if(uniMedida == ""){
			$("#error1").html("Ingresar unidades de medida para los producto farmacéuticos solicitados.");
			isValid = false;
		} else {
			const detalleProductoSolicitud = {
				numDetalleProdSoli: 0,
				solicitudAbastecimiento: null,
				productoFarmaceutico: {
					codProd: codProd
				},
				cantidad: cantidad,
				uniMedida: uniMedida
			};
			detallesProductosSolicitud.push(detalleProductoSolicitud);
		}
	})
	const detallesDispositivosSolicitud = [];
	$("#detallesDispositivosSolicitud > div").each(function() {
		const codDisp = $(this).find("input[type='hidden']").val();
		const cantidad = $(this).find("input[type='number']").val();
		if(cantidad == "" || cantidad <= 0){
			$("#error1").html("Ingresar cantidades válidas para los dispositivos médicos solicitadas.");
			isValid = false;
		} else {
			const detalleDispositivoSolicitud = {
				numDetalleDispSoli: 0,
				solicitudAbastecimiento: null,
				dispositivoMedico: { codDisp: codDisp},
				cantidad: cantidad
			};
			detallesDispositivosSolicitud.push(detalleDispositivoSolicitud);
		}
	})
	const numDetallesProductos = $("#detallesProductosSolicitud > div").length;
	const numDetallesDispositivos = $("#detallesDispositivosSolicitud > div").length;
	if(numDetallesProductos == 0 && numDetallesDispositivos == 0){
		$("#error1").html("Debe seleccionar al menos un producto farmacéutico o dispositivo médico a la solicitud.");
		isValid = false;
	}
	const desSoli = $("#desSoli").val().trim();
	if(desSoli.length == 0 || desSoli.length < 100){
		$("#error1").html("Ingresar una descripción de mínimo 100 caracteres.");
		isValid = false;
	}
	
	if(isValid){
		$("#error1").html("");
		
		modelo = {
			numSoli: 0,
			fecSoli: "",
			desSoli: desSoli,
			estSoli: "En Espera",
			usuario: null,
			detallesProductosSolicitud: detallesProductosSolicitud,
			detallesDispositivosSolicitud: detallesDispositivosSolicitud,
			requerimientosAbastecimiento: null
		}
		
		fetch("/solicitudAbastecimiento/generar", {
			method: "POST",
			headers: { "Content-Type": "application/json;charset=utf-8" },
			body: JSON.stringify(modelo)
		}).then(response => {
			if(response.ok){
				$("#error1").html("Solicitud generada");
				window.location.href = "/solicitudAbastecimiento/generar?generado";
			}
		})
	}
})