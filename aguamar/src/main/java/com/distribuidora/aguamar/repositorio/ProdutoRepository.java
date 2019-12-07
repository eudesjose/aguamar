package com.distribuidora.aguamar.repositorio;

import org.springframework.data.repository.CrudRepository;

import com.distribuidora.aguamar.modelos.Produto;

public interface ProdutoRepository extends CrudRepository<Produto, String>{

	Produto findByIdProduto(long id);
	
	Produto findByNomeProduto(String nome);
}
