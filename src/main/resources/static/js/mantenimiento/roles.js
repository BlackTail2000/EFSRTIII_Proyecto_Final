var modeloRol = {
	codRol: 0,
	nomRol: ""
}

var rolData1;

async function ObtenerTodosLosRoles(){
	const response = await fetch("/rol/buscar/todos");
	const responseJson = await response.json();
	return responseJson;
}

function ListarRoles(rolData){
	$("#tablaRoles tbody").html("");
	if(rolData.length > 0){
		rolData.forEach((rol) => {
			$("#tablaRoles tbody").append(
				$("<tr>").append(
					$("<th>").text(rol.codRol),
					$("<td>").text(rol.nomRol),
					$('<td class="text-center">').append(
						$('<button class="btn btn-outline-primary btn-editar-rol">')
						.data("dataRol", rol).append(
							$('<i class="bi bi-pencil">').text(" Editar")
						)
					),
					$('<td class="text-center">').append(
						$('<button class="btn btn-outline-danger btn-eliminar-rol">')
						.data("dataRol", rol).append(
							$('<i class="bi bi-trash">').text(" Eliminar")
						)
					)
				)
			)
		})
	} else {
		$("#tablaRoles tbody").append(
			$("<tr>").append(
				$('<th colspan="4" class="text-center">').text("No se encontraron Roles.")
			)
		)
	}
}

function AbrirRolModal(){
	$("#nomRol").val(modeloRol.nomRol);
	$("#error1").html("");
	$("#modal-crud-roles").modal("show");
}

document.addEventListener("DOMContentLoaded", async function() {
	rolData1 = await ObtenerTodosLosRoles();
	ListarRoles(rolData1);
})

$(document).on("click", "#btn-nuevo-rol", function() {
	modeloRol.codRol = 0;
	modeloRol.nomRol = "";
	$("#modal-title-crud-roles").html("Nuevo Rol");
	$("#btn-guardar-cambios").removeClass("btn-success");
	$("#btn-guardar-cambios").removeClass("btn-primary");
	$("#btn-guardar-cambios").addClass("btn-success");
	AbrirRolModal();
})

$(document).on("click", ".btn-editar-rol", function() {
	const rol = $(this).data("dataRol");
	modeloRol.codRol = rol.codRol;
	modeloRol.nomRol = rol.nomRol;
	$("#modal-title-crud-roles").html("Editar Rol");
	AbrirRolModal();
})

$(document).on("click", "#btn-guardar-cambios", function() {
	const modelo = {
		codRol: modeloRol.codRol,
		nomRol: $("#nomRol").val().trim()
	}
	
	if(modelo.nomRol == ""){
		$("#error1").html("Ingresar nombre de rol.");
		return;
	} else if(modelo.nomRol.length > 35){
		$("#error1").html("Nombre de rol demasiado largo.");
		return;
	} else if(rolData1.some(rol => rol.nomRol == modelo.nomRol && rol.codRol !== modelo.codRol)){
		$("#error1").html("Ya existe un rol registrado con ese nombre.");
		return;
	}
	
	$("#error1").html("");
	if(modelo.codRol == 0){
		fetch("/rol/mantenimiento/registrar", {
			method: "POST",
			headers: { "Content-Type": "application/json;charset=utf-8" },
			body: JSON.stringify(modelo)
		}).then(response => {
			if(response.ok){
				Swal.fire("¡Operación Exitosa!", "Rol registrado", "success");
				$("#modal-crud-roles").modal("hide");
				return Promise.all([ObtenerTodosLosRoles()]);
			} else {
				Swal.fire("¡Operación No Exitosa!", "Rol no fue registrado", "error");
			}
		}).then(([data1]) => {
			rolData1 = data1;
			ListarRoles(rolData1);
		})
	} else {
		fetch("/rol/mantenimiento/actualizar", {
			method: "PUT",
			headers: { "Content-Type": "application/json;charset=utf-8" },
			body: JSON.stringify(modelo)
		}).then(response => {
			if(response.ok){
				Swal.fire("¡Operación Exitosa!", "Rol actualizado", "success");
				$("#modal-crud-roles").modal("hide");
				return Promise.all([ObtenerTodosLosRoles()]);
			} else {
				Swal.fire("¡Operación No Exitosa!", "Rol no fue actualizado", "error");
			}
		}).then(([data1]) => {
			rolData1 = data1;
			ListarRoles(rolData1);
		})
	}
})

$(document).on("click", ".btn-eliminar-rol", function() {
	const rol = $(this).data("dataRol");
	
	Swal.fire({
		title: "¿Eliminar Rol?",
		icon: "warning",
		showCancelButton: true,
		confirmButtonText: "Aceptar",
		cancelButtonText: "Cancelar"
	}).then(result => {
		if(result.isConfirmed){
			fetch(`/rol/mantenimiento/eliminar/${rol.codRol}`, {
				method: "DELETE",
				headers: { "Content-Type": "application/json;charset=utf-8" },
			}).then(response => {
				if(response.ok){
					Swal.fire("¡Operación Exitosa!", "Rol eliminado", "success");
					return Promise.all([ObtenerTodosLosRoles()]);
				} else {
					Swal.fire("¡Operación No Exitosa!", "Ya existen usuarios registrados con ese rol", "error");
				}
			}).then(([data1]) => {
				rolData1 = data1;
				ListarRoles(rolData1);
			})
		}
	})
})