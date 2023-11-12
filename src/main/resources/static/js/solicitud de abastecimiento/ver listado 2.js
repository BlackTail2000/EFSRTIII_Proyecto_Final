var solicitudData1;

async function ObtenerTodasLasSolicitudes(){
	const response = await fetch("/solicitudAbastecimiento/buscar/todos");
	const responseJson = response.json();
	return responseJson;
}

function ListarSolicitudes(solicitudData){
	$("#tablaSolicitudes tbody").html("");
	if(solicitudData.length > 0){
		solicitudData.forEach((solicitud) => {
			$("#tablaSolicitudes tbody").append(
				$("<tr>").append(
					$("<th>").text(solicitud.numSoli),
					$("<td>").text(solicitud.fecSoli),
					$("<td>").text(solicitud.estSoli),
					$("<td>").append(
						$('<button class="btn btn-outline-success btn-ver-detalles">')
						.data("dataSolicitud", solicitud).append(
							$('<i class="bi bi-file-earmark-text">')
						)
					),
					solicitud.estSoli == "En Espera" ?
					$("<td>").append(
						$('<button class="btn btn-outline-primary btn-evaluar-solicitud">')
						.data("dataSolicitud", solicitud).append(
							$('<i class="bi bi-clipboard-data">')
						)
					) : solicitud.estSoli == "Aprobada" ?
					$("<td>").append(
						$('<button class="btn btn-outline-success" disabled>')
						.data("dataSolicitud", solicitud).append(
							$('<i class="bi bi-clipboard-check">')
						)
					) :
					$("<td>").append(
						$('<button class="btn btn-outline-danger" disabled>')
						.data("dataSolicitud", solicitud).append(
							$('<i class="bi bi-clipboard-x">')
						)
					)
				)
			)
		})
	} else {
		$("#tablaSolicitudes tbody").append(
			$("<tr>").append(
				$('<th colspan="6" class="text-center">').text("No se encontraron solicitudes de Abastecimiento")
			)
		)
	}
}

$(document).on("click", ".btn-ver-detalles", function() {
	const solicitud = $(this).data("dataSolicitud");
	$("#modal-ver-solicitud .modal-body").html("");
	$("#modal-ver-solicitud .modal-body").append(
		$('<div class="mb-3">').append(
			$('<label class="form-label">').text("Nro. de Solicitud:"),
			$('<input class="form-control" readonly>').val(solicitud.numSoli)
		),
		$('<div class="mb-3">').append(
			$('<label class="form-label">').text("Fecha de solicitud:"),
			$('<input class="form-control" readonly>').val(solicitud.fecSoli)
		),
		$('<div class="mb-3">').append(
			$('<label class="form-label">').text("Estado de solicitud:"),
			$('<input class="form-control" readonly>').val(solicitud.estSoli)
		),
		$('<div class="mb-3">').append(
			$('<label class="form-label">').text("Descripción:"),
			$('<textarea class="form-control" style="height: 150px; resize: none;" readonly>').text(solicitud.desSoli)
		),
		$('<div class="mb-3">').append(
			$('<label class="form-label">').text("Solicitante:"),
			$('<input class="form-control" readonly>').val(solicitud.usuario.apeUsua + " " + solicitud.usuario.nomUsua)
		),
		$('<div class="mb-3">').append(
			$('<label class="form-label">').text("Correo:"),
			$('<input class="form-control" readonly>').val(solicitud.usuario.emailUsua)
		),
		$("<hr>"),
		$("<h3>").text("Detalles")
	)
	if(solicitud.detallesProductosSolicitud.length > 0){
		$("#modal-ver-solicitud .modal-body").append(
			$('<h5 class="mt-3 text-primary">').text("Productos Farmacéuticos")
		)
		solicitud.detallesProductosSolicitud.forEach((detalle) => {
			$("#modal-ver-solicitud .modal-body").append(
				$('<h5 class="mt-3">').text(detalle.productoFarmaceutico.nomProd),
				$('<label class="form-label">').text("Cantidad:"),
				$('<input class="form-control mb-3" readonly>').val(detalle.cantidad),
				$('<label class="form-label">').text("Unidad de Medida:"),
				$('<input class="form-control" readonly>').val(detalle.uniMedida)
			)
		})
	}
	if(solicitud.detallesDispositivosSolicitud.length > 0){
		$("#modal-ver-solicitud .modal-body").append(
			$('<h5 class="mt-3 text-primary">').text("Dispositivos Médicos")
		)
		solicitud.detallesDispositivosSolicitud.forEach((detalle) => {
			$("#modal-ver-solicitud .modal-body").append(
				$('<h5 class="mt-3">').text(detalle.dispositivoMedico.nomDisp),
				$('<label class="form-label">').text("Cantidad:"),
				$('<input class="form-control" readonly>').val(detalle.cantidad)
			)
		})
	}
	$("#modal-ver-solicitud").modal("show");
})

$(document).on("click", ".btn-evaluar-solicitud", function() {
	const solicitud = $(this).data("dataSolicitud");
	Swal.fire({
		title: "Evaluar Solicitud",
		icon: "warning",
		showCancelButton: true,
		confirmButtonText: "Aprobar",
		cancelButtonText: "Desaprobar"
	}).then(result => {
		if(result.isConfirmed){
			fetch(`/solicitudAbastecimiento/aprobarSolicitud/${solicitud.numSoli}`, {
				method: "PUT",
				headers: { "Content-Type": "application/json;charset=utf-8" }
			}).then(response => {
				if(response.ok){
					Swal.fire("¡Solicitud Aprobada!", "", "success")
					return Promise.all([ObtenerTodasLasSolicitudes()]);
				} else {
					Swal.fire("¡Ocurrió un error!", "No se pudo aprobar la solicitud", "error")
				}
			}).then(([data1]) => {
				solicitudData1 = data1;
				ListarSolicitudes(solicitudData1);
			})
		} else {
			fetch(`/solicitudAbastecimiento/desaprobarSolicitud/${solicitud.numSoli}`, {
				method: "PUT",
				headers: { "Content-Type": "application/json;charset=utf-8" }
			}).then(response => {
				if(response.ok){
					Swal.fire("¡Solicitud Desaprobada!", "", "success")
					return Promise.all([ObtenerTodasLasSolicitudes()]);
				} else {
					Swal.fire("¡Ocurrió un error!", "No se pudo desaprobar la solicitud", "error")
				}
			}).then(([data1]) => {
				solicitudData1 = data1;
				ListarSolicitudes(solicitudData1);
			})
		}
	})
})

document.addEventListener("DOMContentLoaded", async function() {
	solicitudData1 = await ObtenerTodasLasSolicitudes();
	ListarSolicitudes(solicitudData1);
})