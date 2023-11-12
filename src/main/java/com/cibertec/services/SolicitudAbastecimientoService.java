package com.cibertec.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.models.DetalleDispositivoSolicitud;
import com.cibertec.models.DetalleProductoSolicitud;
import com.cibertec.models.SolicitudAbastecimiento;
import com.cibertec.repositories.ISolicitudAbastecimientoRepository;
import com.cibertec.services.interfaces.IDetalleDispositivoSolicitudService;
import com.cibertec.services.interfaces.IDetalleProductoSolicitudService;
import com.cibertec.services.interfaces.IDispositivoMedicoService;
import com.cibertec.services.interfaces.IProductoFarmaceuticoService;
import com.cibertec.services.interfaces.ISolicitudAbastecimientoService;
import com.cibertec.services.interfaces.IUsuarioService;
import com.cibertec.utilities.MiscellaneousUtil;

@Service
public class SolicitudAbastecimientoService implements ISolicitudAbastecimientoService {

	private MiscellaneousUtil miscellaneousUtil;
	private IUsuarioService usuarioService;
	private ISolicitudAbastecimientoRepository solicitudAbastecimientoRepository;
	private IProductoFarmaceuticoService productoFarmaceuticoService;
	private IDispositivoMedicoService dispositivoMedicoService;
	private IDetalleProductoSolicitudService detalleProductoSolicitudService;
	private IDetalleDispositivoSolicitudService detalleDispositivoSolicitudService;
	
	@Autowired
	public SolicitudAbastecimientoService(MiscellaneousUtil miscellaneousUtil,
			IUsuarioService usuarioService, ISolicitudAbastecimientoRepository solicitudAbastecimientoRepository,
			IDispositivoMedicoService dispositivoMedicoService, IProductoFarmaceuticoService productoFarmaceuticoService,
			IDetalleProductoSolicitudService detalleProductoSolicitudService,
			IDetalleDispositivoSolicitudService detalleDispositivoSolicitudService) {
		this.miscellaneousUtil = miscellaneousUtil;
		this.usuarioService = usuarioService;
		this.solicitudAbastecimientoRepository = solicitudAbastecimientoRepository;
		this.dispositivoMedicoService = dispositivoMedicoService;
		this.productoFarmaceuticoService = productoFarmaceuticoService;
		this.detalleDispositivoSolicitudService = detalleDispositivoSolicitudService;
		this.detalleProductoSolicitudService = detalleProductoSolicitudService;
		
	}
	
	@Override
	public SolicitudAbastecimiento generarSolicitudAbastecimiento(SolicitudAbastecimiento solicitudAbastecimiento) {
		solicitudAbastecimiento.setFecSoli(miscellaneousUtil.obtenerFechaActual());
		solicitudAbastecimiento.setUsuario(usuarioService.obtenerUsuarioLogueado());
		this.guardarSolicitudAbastecimiento(solicitudAbastecimiento);
		for(DetalleProductoSolicitud item: solicitudAbastecimiento.getDetallesProductosSolicitud()) {
			item.setSolicitudAbastecimiento(solicitudAbastecimiento);
			item.setProductoFarmaceutico(productoFarmaceuticoService.buscarProductoFarmaceuticoPorCodigo(item.getProductoFarmaceutico().getCodProd()));
			detalleProductoSolicitudService.guardarDetalleProductoSolicitud(item);
		}
		for(DetalleDispositivoSolicitud item: solicitudAbastecimiento.getDetallesDispositivosSolicitud()) {
			item.setSolicitudAbastecimiento(solicitudAbastecimiento);
			item.setDispositivoMedico(dispositivoMedicoService.buscarDispositivoMedicoPorCodigo(item.getDispositivoMedico().getCodDisp()));
			detalleDispositivoSolicitudService.guardarDetalleDispositivoSolicitud(item);
		}
		return solicitudAbastecimiento;
	}

	@Override
	public SolicitudAbastecimiento guardarSolicitudAbastecimiento(SolicitudAbastecimiento solicitudAbastecimiento) {
		return solicitudAbastecimientoRepository.save(solicitudAbastecimiento);
	}

	@Override
	public List<SolicitudAbastecimiento> obtenerTodasLasSolicitudesDelUsuarioLogueado() {
		List<SolicitudAbastecimiento> solicitudesAbastecimientos = solicitudAbastecimientoRepository.findAllByUsuario(usuarioService.obtenerUsuarioLogueado().getCodUsua());
		
		for(SolicitudAbastecimiento item: solicitudesAbastecimientos) {
			for(DetalleProductoSolicitud prod : item.getDetallesProductosSolicitud()) {
				prod.getProductoFarmaceutico().setDetallesProductosSolicitud(null);
				prod.setSolicitudAbastecimiento(null);
			}
			for(DetalleDispositivoSolicitud disp : item.getDetallesDispositivosSolicitud()) {
				disp.getDispositivoMedico().setDetallesDispositivosSolicitud(null);
				disp.setSolicitudAbastecimiento(null);
			}
			item.getUsuario().setSolicitudesAbastecimiento(null);
			item.getUsuario().getRol().setUsuarios(null);
		}
		
		return solicitudesAbastecimientos;
	}

	@Override
	public List<SolicitudAbastecimiento> obtenerTodasLasSolicitudes() {
        List<SolicitudAbastecimiento> solicitudesAbastecimientos = solicitudAbastecimientoRepository.findAll();
		
		for(SolicitudAbastecimiento item: solicitudesAbastecimientos) {
			for(DetalleProductoSolicitud prod : item.getDetallesProductosSolicitud()) {
				prod.getProductoFarmaceutico().setDetallesProductosSolicitud(null);
				prod.setSolicitudAbastecimiento(null);
			}
			for(DetalleDispositivoSolicitud disp : item.getDetallesDispositivosSolicitud()) {
				disp.getDispositivoMedico().setDetallesDispositivosSolicitud(null);
				disp.setSolicitudAbastecimiento(null);
			}
			item.getUsuario().setSolicitudesAbastecimiento(null);
			item.getUsuario().getRol().setUsuarios(null);
		}
		
		return solicitudesAbastecimientos;
	}

	@Override
	public SolicitudAbastecimiento buscarPorCodigo(int numSoli) {
		SolicitudAbastecimiento solicitud = null;
		Optional<SolicitudAbastecimiento> optional = solicitudAbastecimientoRepository.findById(numSoli);
		if(optional.isPresent())
			solicitud = optional.get();
		return solicitud;
	}

}
