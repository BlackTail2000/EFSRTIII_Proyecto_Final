function ValidarEmail(email){
	const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
	return regex.test(email);
}

$(document).on("click", "#btn-guardar-datos", function() {
	const nomUsua = $("#nomUsua").val().trim();
	const apeUsua = $("#apeUsua").val().trim();
	const emailUsua = $("#emailUsua").val().trim();
	
	if(nomUsua == ""){
		$("#error1").html("Ingresar nombre.");
		return;
	} else if(nomUsua.length > 50){
		$("#error1").html("Nombre demasiado largo.");
		return;
	} else if(apeUsua == ""){
		$("#error1").html("Ingresar apellido.");
		return;
	} else if(apeUsua.length > 50){
		$("#error1").html("Apellido demasiado largo.");
		return;
	} else if(emailUsua == ""){
		$("#error1").html("Ingresar correo.");
		return;
	} else if(emailUsua.length > 50){
		$("#error1").html("Correo demasiado largo.");
		return;
	} else if(!ValidarEmail(emailUsua)){
		$("#error1").html("Correo ingresado no es válido.");
		return;
	}
	
	$("#error1").html("");
	
	fetch(`/usuario/datosPersonales/${nomUsua}/${apeUsua}/${emailUsua}`, {
		method: "PUT",
		headers: { "Content-Type": "application/json;charset=utf-8" }
	}).then(response => {
		if(response.ok){
			window.location.href = "/";
		}
	})
})