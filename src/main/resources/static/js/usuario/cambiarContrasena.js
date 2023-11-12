$(document).on("click", "#btn-guardar-contrasena", function() {
	const claveNueva1 = $("#claveUsua1").val().trim();
	const claveNueva2 = $("#claveUsua2").val().trim();
	
	if(claveNueva1 == ""){
		$("#error1").html("Ingresar nueva contraseña.");
		return;
	} else if(claveNueva1.length < 8){
		$("#error1").html("La nueva contraseña debe tener como mínimo 8 caracteres.");
		return;
	} else if(claveNueva2 == ""){
		$("#error2").html("Volver a ingresar su nueva contraseña.");
		return;
	} else if(claveNueva1 !== claveNueva2){
		$("#error1").html("Las contraseñas no coinciden.");
		return;
	} else if(/^USUA\d{3}$/.test(claveNueva1)){
		$("#error1").html("No puede utilizar el código de un usuario como nueva contraseña.");
		return;
	}
	
	$("#error1").html("");
	fetch(`/usuario/cambiarContrasena/${claveNueva1}`, {
		method: "PUT",
		headers: { "Content-Type": "application/json;charset=utf-8" }
	}).then(response => {
		if(response.ok){
			window.location.href = "/";
		}
	})
})