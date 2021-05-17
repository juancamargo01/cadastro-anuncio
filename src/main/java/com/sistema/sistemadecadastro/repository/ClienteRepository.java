package com.sistema.sistemadecadastro.repository;


import com.sistema.sistemadecadastro.models.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface ClienteRepository extends CrudRepository<Cliente, Long> {

	Cliente findByNome(String nome);

}