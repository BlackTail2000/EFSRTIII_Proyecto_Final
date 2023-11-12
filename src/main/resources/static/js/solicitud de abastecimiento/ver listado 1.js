document.addEventListener("DOMContentLoaded", function() {
	fetch("/solicitudAbastecimiento/buscar/todos/porUsuarioLogueado").then(response => {
		return response.ok ? response.json() : Promise.reject(response);
	}).then(responseJson => {
		$("#tablaSolicitudes tbody").html("");
		if(responseJson.length > 0){
			responseJson.forEach((solicitud) => {
				$("#tablaSolicitudes tbody").append(
					$("<tr>").append(
						$("<th>").text(solicitud.numSoli),
						$("<td>").text(solicitud.fecSoli),
						$("<td>").text(solicitud.estSoli),
						$('<td class="text-center">').append(
							$('<button class="btn btn-outline-success btn-ver-detalles">')
							.data("dataSolicitud", solicitud).append(
								$('<i class="bi bi-file-earmark-text">').text(" Ver Solicitud")
							)
						)
					)
				)
			})
		} else {
			$("#tablaSolicitudes tbody").append(
				$("<tr>").append(
					$('<th colspan="4" class="text-center">').text("No se encontraron solicitudes")
				)
			)
		}
	})
})

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