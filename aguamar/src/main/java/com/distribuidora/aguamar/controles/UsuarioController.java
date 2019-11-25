package com.distribuidora.aguamar.controles;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UsuarioController {

	@RequestMapping(value="/novoUsuario", method=RequestMethod.GET)
	public String formulario() {
		return "usuario/formNovoUsuario";
	}
	
}
