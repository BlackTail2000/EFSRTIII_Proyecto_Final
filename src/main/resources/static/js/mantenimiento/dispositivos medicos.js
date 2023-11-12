var modeloDispositivo = {
	codDisp: 0,
	nomDisp: "",
	tipoDisp: "",
	stkDisp: 0,
	estDisp: ""
}

var dispositivoData1;

async function ObtenerTodosLosDispositivosMedicos(){
	const response = await fetch("/dispositivoMedico/buscar/todos");
	const responseJson = await response.json();
	return responseJson;
}

function ListarDispositivosMedicos(dispositivoData){
	$("#tablaDispositivosMedicos tbody").html("");
	if(dispositivoData.length > 0){
		dispositivoData.forEach((dispositivo) => {
			if(dispositivo.estDisp == "A"){
				$("#tablaDispositivosMedicos tbody").append(
					$("<tr>").append(
						$("<th>").text(dispositivo.codDisp),
						$("<td>").text(dispositivo.nomDisp),
						$("<td>").text(dispositivo.tipoDisp),
						$("<td>").text(dispositivo.stkDisp),
						$('<td class="text-center">').append(
							$('<button class="btn btn-outline-primary btn-editar-dispositivo">')
							.data("dataDispositivo", dispositivo).append(
								$('<i class="bi bi-pencil">').text(" Editar")
							)
						),
						$('<td class="text-center">').append(
							$('<button class="btn btn-outline-danger btn-eliminar-dispositivo">')
							.data("dataDispositivo", dispositivo).append(
								$('<i class="bi bi-trash">').text(" Eliminar")
							)
						)
					)
				)
			}
		})
	} else {
		$("#tablaDispositivosMedicos tbody").append(
			$("<tr>").append(
				$('<th class="text-center" colspan="6">').text("No se encontraron dispositivos médicos.")
			)
		)
	}
}

function AbrirDispositivoModal(){
	$("#nomDisp").val(modeloDispositivo.nomDisp);
	$("#tipoDisp").val(modeloDispositivo.tipoDisp);
	$("#stkDisp").val(modeloDispositivo.stkDisp);
	$("#error1").html("");
	$("#modal-crud-dispositivos").modal("show");
}

document.addEventListener("DOMContentLoaded", async function() {
	dispositivoData1 = await ObtenerTodosLosDispositivosMedicos();
	ListarDispositivosMedicos(dispositivoData1);
})

$(document).on("click", "#btn-nuevo-dispositivo", function() {
	modeloDispositivo.codDisp = 0;
	modeloDispositivo.nomDisp = "";
	modeloDispositivo.tipoDisp = "";
	modeloDispositivo.stkDisp = 0;
	modeloDispositivo.estDisp = "A";
	
	$("#modal-title-crud-dispositivos").html("Nuevo Dispositivo Médico");
	$("#btn-guardar-cambios").removeClass("btn-success");
	$("#btn-guardar-cambios").removeClass("btn-primary");
	$("#btn-guardar-cambios").addClass("btn-success");
	AbrirDispositivoModal();
})

$(document).on("click", ".btn-editar-dispositivo", function() {
	const dispositivo = $(this).data("dataDispositivo");
	modeloDispositivo.codDisp = dispositivo.codDisp;
	modeloDispositivo.nomDisp = dispositivo.nomDisp;
	modeloDispositivo.tipoDisp = dispositivo.tipoDisp;
	modeloDispositivo.stkDisp = dispositivo.stkDisp;
	modeloDispositivo.estDisp = dispositivo.estDisp;
	
	$("#modal-title-crud-dispositivos").html("Editar Dispositivo Médico");
	$("#btn-guardar-cambios").removeClass("btn-primary");
	$("#btn-guardar-cambios").removeClass("btn-success");
	$("#btn-guardar-cambios").addClass("btn-primary");
	AbrirDispositivoModal();
})

$(document).on("click", "#btn-guardar-cambios", function() {
	const modelo = {
		codDisp: modeloDispositivo.codDisp,
		nomDisp: $("#nomDisp").val().trim(),
		tipoDisp: $("#tipoDisp").val().trim(),
		stkDisp: $("#stkDisp").val(),
		estDisp: "A"
	}
	
	if(modelo.nomDisp == ""){
		$("#error1").html("Ingresar nombre de nuevo dispositivo médico.");
		return;
	} else if(modelo.nomDisp.length > 100){
		$("#error1").html("Nombre de dispositivo médico demasiado largo.");
		return;
	} else if(dispositivoData1.some(dispositivo => dispositivo.nomDisp == modelo.nomDisp && dispositivo.codDisp != modelo.codDisp)){
		$("#error1").html("Ya existe un dispositivo médico registrado con ese nombre.");
		return;
	} else if(modelo.tipoDisp == ""){
		$("#error1").html("Ingresar tipo de nuevo dispositivo médico.");
		return;
	} else if(modelo.tipoDisp.length > 40){
		$("#error1").html("Tipo de dispositivo médico demasiado largo.");
		return;
	} else if(modelo.stkDisp == "" || modelo.stkDisp < 0){
		$("#error1").html("Stock ingresado no válido.");
		return;
	}
	
	$("#error1").html("");
	
	if(modelo.codDisp == 0){
		fetch("/dispositivoMedico/mantenimiento/registrar", {
			method: "POST",
			headers: { "Content-Type": "application/json;charset=utf-8" },
			body: JSON.stringify(modelo)
		}).then(response => {
			if(response.ok){
				Swal.fire("¡Operación Exitosa!", "Dispositivo Médico Registrado", "success");
				$("#modal-crud-dispositivos").modal("hide");
				return Promise.all([ObtenerTodosLosDispositivosMedicos()]);
			} else {
				Swal.fire("¡Operación No Exitosa!", "Dispositivo Médico No Registrado", "error");
			}
		}).then(([data1]) => {
			dispositivoData1 = data1;
			ListarDispositivosMedicos(dispositivoData1);
		})
	} else {
		fetch("/dispositivoMedico/mantenimiento/actualizar", {
			method: "PUT",
			headers: { "Content-Type": "application/json;charset=utf-8" },
			body: JSON.stringify(modelo)
		}).then(response => {
			if(response.ok){
				Swal.fire("¡Operación Exitosa!", "Dispositivo Médico Actualizado", "success");
				$("#modal-crud-dispositivos").modal("hide");
				return Promise.all([ObtenerTodosLosDispositivosMedicos()]);
			} else {
				Swal.fire("¡Operación No Exitosa!", "Dispositivo Médico No Actualizado", "error");
			}
		}).then(([data1]) => {
			dispositivoData1 = data1;
			ListarDispositivosMedicos(dispositivoData1);
		})
	}
})

$(document).on("click", ".btn-eliminar-dispositivo", function() {
	const dispositivo = $(this).data("dataDispositivo");
	
	Swal.fire({
		title: "¿Eliminar Dispositivo Médico?",
		icon: "warning",
		showCancelButton: true,
		confirmButtonText: "Aceptar",
		cancelButtonText: "Cancelar"
	}).then(result => {
		if(result.isConfirmed){
			fetch(`/dispositivoMedico/mantenimiento/eliminar/${dispositivo.codDisp}`, {
				method: "PUT",
				headers: { "Content-Type": "application/json;charset=utf-8" }
			}).then(response => {
				if(response.ok){
					Swal.fire("¡Operación Exitosa!", "Dispositivo Médico Eliminado", "success")
					return Promise.all([ObtenerTodosLosDispositivosMedicos()]);
				} else {
					Swal.fire("¡Operación No Exitosa!", "Dispositivo Médico No Eliminado", "error")
				}
			}).then(([data1]) => {
				dispositivoData1 = data1;
				ListarDispositivosMedicos(dispositivoData1);
			})
		}
	})
})