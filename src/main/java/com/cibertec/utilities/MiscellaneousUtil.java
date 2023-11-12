package com.cibertec.utilities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class MiscellaneousUtil {

	public String obtenerFechaActual() {
		LocalDate fechaActual = LocalDate.now();
		DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String fechaFormateada = fechaActual.format(formatoFecha);
		return fechaFormateada;
	}
}
