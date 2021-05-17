package com.sistema.sistemadecadastro.service;

import com.sistema.sistemadecadastro.models.Cliente;
import com.sistema.sistemadecadastro.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

	@Autowired
	ClienteRepository clienteRepository;

	/**
	 * Método que busca todos os clientes
	 *
	 * @return
	 */
	public List<Cliente> findAll() {
		List<Cliente> clientes = new ArrayList<>();
		clienteRepository.findAll().forEach(cliente -> clientes.add(cliente));
		return clientes;
	}

	/**
	 * Método que busca um cliente por
	 *
	 * @param id
	 * @return
	 */
	public Optional<Cliente> findById(Long id) {
		return clienteRepository.findById(id);
	}

	/**
	 * Método que salva um cliente
	 *
	 * @param cliente
	 */
	public void save(Cliente cliente) {

		clienteRepository.save(cliente);
	}

	/**
	 * Método que deleta um cliente
	 *
	 * @param id
	 */
	public void deleteById(Long id) {
		clienteRepository.deleteById(id);
	}

}
