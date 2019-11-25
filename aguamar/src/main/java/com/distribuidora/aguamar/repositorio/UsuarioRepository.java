package com.distribuidora.aguamar.repositorio;

import org.springframework.data.repository.CrudRepository;

import com.distribuidora.aguamar.modelos.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, String>{

	Usuario findByLogin(String login);
}
