var usuarioData1;

async function ObtenerTodosLosUsuarios(){
	const response = await fetch("/usuario/buscar/todos");
	const responseJson = await response.json();
	return responseJson;
}

async function ListarUsuarios(usuarioData) {
	$("#tablaUsuarios tbody").html("");
	if(usuarioData.length > 0){
		usuarioData.forEach((usuario) => {
			if(usuario.rol.nomRol !== "Administrador"){
				$("#tablaUsuarios tbody").append(
					$("<tr>").append(
						$("<th>").text(usuario.codUsua),
						$("<td>").text(usuario.apeUsua + " " + usuario.nomUsua),
						$("<td>").text(usuario.emailUsua),
						$("<td>").text(usuario.rol.nomRol),
						$("<td>").text(usuario.estUsua == "A" ? "Activo" : "Inactivo"),
						$('<td class="text-center">').append(
							$('<button class="btn btn-outline-primary btn-cambiar-perfil">')
							.data("dataUsuario", usuario).append(
								$('<i class="bi bi-pencil">').text(" Cambiar Perfil")
							)
						),
						$('<td class="text-center">').append(
							usuario.estUsua == "A" ?
							$('<button class="btn btn-outline-danger btn-deshabilitar">')
							.data("dataUsuario", usuario).append(
								$('<i class="bi bi-lock">').text(" Deshabilitar usuario")
							) : 
							$('<button class="btn btn-outline-warning btn-habilitar">')
							.data("dataUsuario", usuario).append(
								$('<i class="bi bi-unlock">').text(" Habilitar usuario")
							)
						)
					)
				)
			}
		})
	} else {
		$("#tablaUsuarios tbody").append(
			$("<tr>").append(
				$('<th colspan="7" class="text-center">').text("No se encontraron usuarios")
			)
		)
	}
}

function ListarRoles(){
	fetch("/rol/buscar/todos/ordenarPorNombre").then(response => {
		return response.ok ? response.json() : Promise.reject(response);
	}).then(responseJson => {
		if(responseJson.length > 0){
			responseJson.forEach((rol) => {
				$("#rol").append(
					$("<option>").val(rol.codRol).text(rol.nomRol)
				),
				$("#nuevoRol2").append(
					$("<option>").val(rol.codRol).text(rol.nomRol)
				)
			})
		}
	})
}

function ValidarEmail(email){
	const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
	return regex.test(email);
}

document.addEventListener("DOMContentLoaded", async function() {
	usuarioData1 = await ObtenerTodosLosUsuarios();
	ListarUsuarios(usuarioData1);
	ListarRoles();
})

$(document).on("click", "#btn-registrar-usuario", function() {
	const modelo = {
		codUsua: 0,
		nomUsua: $("#nomUsua1").val().trim(),
		apeUsua: $("#apeUsua1").val().trim(),
		emailUsua: $("#emailUsua1").val().trim(),
		claveUsua: "123",
		estUsua: "A",
		rol: {
			codRol: $("#rol").val()
		}
	}
	
	if(modelo.nomUsua == ""){
		$("#error1").html("Ingresar nombre.");
		return;
	} else if(modelo.nomUsua.length > 50){
		$("#error1").html("Nombre demasiado largo");
		return;
	} else if(modelo.apeUsua == ""){
		$("#error1").html("Ingresar apellido.");
		return;
	} else if(modelo.apeUsua.length > 50){
		$("#error1").html("Apellido demasiado largo.");
		return;
	} else if(modelo.emailUsua == ""){
		$("#error1").html("Ingresar correo.");
		return;
	} else if(!ValidarEmail(modelo.emailUsua)) {
		$("#error1").html("Ingresar un correo válido.");
		return;
	} else if(modelo.emailUsua.length > 50){
		$("#error1").html("Correo demasiado largo.");
		return;
	} else if(modelo.rol.codRol == -1){
		$("#error1").html("Seleccionar un rol.");
		return;
	}
	
	$("#error1").html("");
	
	fetch("/usuario/mantenimiento/registrar", {
		method: "POST",
		headers: { "Content-Type": "application/json;charset=utf-8" },
		body: JSON.stringify(modelo)
	}).then(response => {
		if(response.ok){
			Swal.fire("¡Operación Exitosa!", "Usuario registrado", "success");
			$("#modal-nuevo-usuario").modal("hide");
			$("#nomUsua1").val("");
			$("#apeUsua1").val("");
			$("#emailUsua1").val("");
			$("#rol").val(-1);
			return Promise.all([ObtenerTodosLosUsuarios()]);
		} else {
			Swal.fire("¡Operación No Exitosa!", "Usuario no registrado", "error");
		}
	}).then(([data1]) => {
		usuarioData1 = data1;
		ListarUsuarios(usuarioData1);
	})
})

$(document).on("click", ".btn-cambiar-perfil", function() {
	const usuario = $(this).data("dataUsuario");	
	$("#codUsua2").val(usuario.codUsua);
	$("#usuario2").val(usuario.apeUsua + " " + usuario.nomUsua);
	$("#rolActual2").val(usuario.rol.nomRol);
	$("#nuevoRol2").val(usuario.rol.codRol);
	$("#error2").html("");
	$("#modal-cambiar-perfil").modal("show");
})

$(document).on("click", "#btn-actualizar-perfil", function() {
	const codUsua = $("#codUsua2").val();
	const codRol = $("#nuevoRol2").val();
	
	if(codRol == -1){
		$("#error2").html("Seleccionar un nuevo rol");
		return;
	}
	
	$("#error2").html("");
	fetch(`/usuario/mantenimiento/cambiarRol/${codUsua}/${codRol}`, {
		method: "PUT",
		headers: { "Content-Type": "application/json;charset=utf-8" }
	}).then(response => {
		if(response.ok){
			Swal.fire("¡Operación Exitosa!", "Rol de usuario actualizado", "success");
			$("#modal-cambiar-perfil").modal("hide");
			return Promise.all([ObtenerTodosLosUsuarios()]);
		} else {
			Swal.fire("¡Operación No Exitosa!", "Rol de usuario no actualizado", "error");
		}
	}).then(([data1]) => {
		usuarioData1 = data1;
		ListarUsuarios(usuarioData1);
	})
})

$(document).on("click", ".btn-deshabilitar, .btn-habilitar", function(){
	const usuario = $(this).data("dataUsuario");
	Swal.fire({
		title:usuario.estUsua == "A" ? "¿Deshabilitar Usuario?" : "¿Habilitar Usuario?",
		icon: "question",
		showCancelButton: true,
		confirmButtonText: "Aceptar",
		cancelButtonText: "Cancelar"
	}).then(result => {
		if(result.isConfirmed){
			fetch(`/usuario/mantenimiento/cambiarEstado/${usuario.codUsua}`, {
				method: "PUT",
				headers: { "Content-Type": "application/json;charset=utf-8" }
			}).then(response => {
				if(response.ok){
					Swal.fire("¡Operación Exitosa!",
					usuario.estUsua == "A" ? "Usuario deshabilitado": "Usuario habilitado", "success")
					
					return Promise.all([ObtenerTodosLosUsuarios()]);
				} else {
					Swal.fire("¡Operación No Exitosa!",
					usuario.estUsua == "A" ? "No se pudo deshabilitar al Usuario" : "No de pudo habilitar al Usuario", "error");
				}
			}).then(([data1]) => {
				usuarioData1 = data1;
				ListarUsuarios(usuarioData1);
			})
		}
	})
})