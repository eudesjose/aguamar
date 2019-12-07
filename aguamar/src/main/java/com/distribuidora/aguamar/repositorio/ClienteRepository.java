package com.distribuidora.aguamar.repositorio;

import org.springframework.data.repository.CrudRepository;

import com.distribuidora.aguamar.modelos.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente, String>{

	Cliente findByCodigoCliente(int codigoCliente);
	
	Cliente findByLogin(String login);
	
}
