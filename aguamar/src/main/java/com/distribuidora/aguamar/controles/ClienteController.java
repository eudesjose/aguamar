package com.distribuidora.aguamar.controles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.distribuidora.aguamar.modelos.Cliente;
import com.distribuidora.aguamar.modelos.Role;
import com.distribuidora.aguamar.modelos.Usuario;
import com.distribuidora.aguamar.repositorio.ClienteRepository;
import com.distribuidora.aguamar.repositorio.RoleRepository;
import com.distribuidora.aguamar.repositorio.UsuarioRepository;

@Controller
public class ClienteController {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	@RequestMapping(value="/novoCliente", method=RequestMethod.GET)
	public String formulario() {
		return "cliente/formNovoCliente";
	}
	
	@RequestMapping(value="/salvarCliente", method=RequestMethod.POST)
	public String formulario(Cliente cliente) {
		clienteRepository.save(cliente);
		return "redirect:/novoCliente";
	}
	
	@RequestMapping(value = "/clientes")
	public ModelAndView getClientes() {
		ModelAndView mv = new ModelAndView("cliente/clientes");
		Iterable<Cliente> clientes = clienteRepository.findAll();
		mv.addObject("clientes", clientes);
		return mv;
	}
	
	@RequestMapping(value = "/concederAutorizacao/{codigoCliente}", method = RequestMethod.GET)
	public String concederAutorizacao(@PathVariable("codigoCliente") int codigoCliente) {
		
		Cliente cliente = clienteRepository.findByCodigoCliente(codigoCliente);
		cliente.setAutorizacao(true);
		clienteRepository.save(cliente);
		
		Usuario usuario = new Usuario();
		usuario.setLogin(cliente.getLogin());
		String senha = new BCryptPasswordEncoder().encode(cliente.getSenha());
		usuario.setSenha(senha);
		usuarioRepository.save(usuario);
		
		Role role = new Role();
		role.setNomeRole("ROLE_CLIENTE");
		role.setUsuarios(usuario);
		
		usuario.setRoles(role);
		roleRepository.save(role);
		usuarioRepository.save(usuario);
		
		return "redirect:/clientes";
	} 
	
	@RequestMapping(value = "/{codigoCliente}", method = RequestMethod.GET)
	public ModelAndView editarCliente(@PathVariable("codigoCliente") int codigoCliente) {
		Cliente cliente = clienteRepository.findByCodigoCliente(codigoCliente);
		ModelAndView mv = new ModelAndView("cliente/editarCliente");
		mv.addObject("cliente", cliente);
		return mv;
	}
	
	@PostMapping("atualizarCliente/{codigoCliente}")
    public String atualizarProduto(@PathVariable("codigoCliente") int codigoCliente, Cliente cliente) {
		clienteRepository.save(cliente);
        return "redirect:/clientes";
    }
	
	@RequestMapping("excluirCliente/{codigoCliente}")
	public String excluirCliente(@PathVariable("codigoCliente") int codigoCliente) {
		Cliente cliente = clienteRepository.findByCodigoCliente(codigoCliente);
		clienteRepository.delete(cliente);
		Usuario usuario = usuarioRepository.findByLogin(cliente.getLogin());
		usuarioRepository.delete(usuario);
		
		return "redirect:/clientes";
	}
	
}
