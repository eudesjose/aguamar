package com.distribuidora.aguamar.controles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.distribuidora.aguamar.modelos.Role;
import com.distribuidora.aguamar.modelos.Usuario;
import com.distribuidora.aguamar.repositorio.RoleRepository;
import com.distribuidora.aguamar.repositorio.UsuarioRepository;

@Controller
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@RequestMapping(value = "/novoUsuario", method = RequestMethod.GET)
	public String formulario() {
		return "usuario/formNovoUsuario";
	}

	@RequestMapping(value = "/salvarUsuario", method = RequestMethod.POST)
	public String formulario(Usuario usuario, @RequestParam("tipoUsuario") String tipoUsuario) {
		String senha = new BCryptPasswordEncoder().encode(usuario.getSenha());
		usuario.setSenha(senha);

		Role role = new Role();
		role.setNomeRole("ROLE_"+tipoUsuario);
		roleRepository.save(role);

		role.setUsuarios(usuario);
		usuario.setRoles(role);

		usuarioRepository.save(usuario);
		return "redirect:/novoUsuario";
	}

}
