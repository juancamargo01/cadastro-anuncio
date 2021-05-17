package com.sistema.sistemadecadastro.service;

import com.sistema.sistemadecadastro.models.Anuncio;
import com.sistema.sistemadecadastro.models.Cliente;
import com.sistema.sistemadecadastro.repository.AnuncioRepository;
import com.sistema.sistemadecadastro.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class AnuncioService {

	@Autowired
	AnuncioRepository anuncioRepository;

	@Autowired
	ClienteRepository clienteRepository;

	static Integer MAX_COMPARTILHAMENTO = 4;

	/**
	 * Método que busca todos os anuncios
	 *
	 * @return
	 */
	public List<Anuncio> findAll() {
		List<Anuncio> anuncios = new ArrayList<>();
		anuncioRepository.findAll().forEach(anuncio -> anuncios.add(anuncio));
		return anuncios;
	}

	/**
	 * Método que busca um anuncio por
	 *
	 * @param id
	 * @return
	 */
	public Optional<Anuncio> findById(Long id) {
		return anuncioRepository.findById(id);
	}

	/**
	 * Método que salva um anuncio
	 *
	 * @param anuncio
	 */
	public void save(Anuncio anuncio) {

		preparaanuncio(anuncio);
		verificaClienteExistente(anuncio);

		anuncioRepository.save(anuncio);
	}

	/**
	 * Método que deleta um anuncio
	 *
	 * @param id
	 */
	public void deleteById(Long id) {
		anuncioRepository.deleteById(id);
	}

	/**
	 * Método que busca um anúncio pelo Nome
	 *
	 * @param nome
	 * @return
	 */
	public Anuncio findByNome(String nome) {
		return anuncioRepository.findByNome(nome);
	}

	/**
	 * Método que busca os dados para o relatório de Anúncios
	 *
	 * @param anuncio
	 * @return
	 */
	public List<Anuncio> relatorioAnuncio(Anuncio anuncio) {

		List<Anuncio> relatorioAnuncio = null;

		if (anuncio != null) {

			//Valida se existe um Cliente com esse nome cadastrado
			if (!anuncio.getCliente().getNome().isEmpty()) {
				Cliente cliente = clienteRepository.findByNome(anuncio.getCliente().getNome());

				if (cliente != null) {
					anuncio.setCliente(cliente);
				} else {

					return null;

				}

			}

			//Busca uma lista de anúncios com os filtros completos
			if (anuncio.getDataInicio() != null && anuncio.getDataFinal() != null && !anuncio.getCliente().getNome().isEmpty()) {

				relatorioAnuncio = anuncioRepository.findByDataInicioGreaterThanEqualAndDataInicioLessThanEqualAndCliente(
						anuncio.getDataInicio(), anuncio.getDataFinal(), anuncio.getCliente());

			} else if (anuncio.getDataInicio() != null && anuncio.getDataFinal() != null && anuncio.getCliente().getNome().isEmpty()) {
				//Busca uma lista de Anúncios com intervalo de e ate da Data de Início
				relatorioAnuncio = anuncioRepository.findByDataInicioGreaterThanEqualAndDataInicioLessThanEqual(anuncio.getDataInicio(), anuncio.getDataFinal());


			} else if (anuncio.getDataInicio() != null && anuncio.getDataFinal() == null && !anuncio.getCliente().getNome().isEmpty()) {
				//Busca uma lista de Anúncios com intervalo de da Data de Início e o Cliente
				relatorioAnuncio = anuncioRepository.findByDataInicioGreaterThanEqualAndCliente(anuncio.getDataInicio(), anuncio.getCliente());

			} else if (anuncio.getDataInicio() == null && anuncio.getDataFinal() != null && !anuncio.getCliente().getNome().isEmpty()) {
				//Busca uma lista de Anúncios com intervalo ate da Data de Início e o Cliente
				relatorioAnuncio = anuncioRepository.findByDataInicioLessThanEqualAndCliente(anuncio.getDataFinal(), anuncio.getCliente());

			} else if (anuncio.getDataInicio() != null) {
				//Busca uma lista de Anúncio com apenas a data Inicial
				relatorioAnuncio = anuncioRepository.findByDataInicioGreaterThanEqual(anuncio.getDataInicio());


			} else if (anuncio.getDataFinal() != null) {
				//Busca uma lista de Anúncio com apenas a data Inicio Ate
				relatorioAnuncio = anuncioRepository.findByDataInicioLessThanEqual(anuncio.getDataFinal());


			} else if (!anuncio.getCliente().getNome().isEmpty()) {
				//Busca uma lista de Anúncios pelo Cliente
				relatorioAnuncio = anuncioRepository.findByCliente(anuncio.getCliente());

			} else {
				relatorioAnuncio = findAll();

			}


		}

		return relatorioAnuncio;

	}


	/**
	 * Método centralizador da regra de negocio para salvar um novo anuncio
	 *
	 * @param anuncio
	 */
	private void preparaanuncio(Anuncio anuncio) {

		diasTotal(anuncio);
		calculaDados(anuncio);
	}


	/**
	 * Método que calcula quantos dias o anuncio investiu
	 *
	 * @param anuncio
	 */
	private void diasTotal(Anuncio anuncio) {

		//Pega o valor entre as datas e soma o dia + 1
		anuncio.setDiferncaDias(new BigDecimal(DAYS.between(anuncio.getDataInicio(), anuncio.getDataFinal())).add(BigDecimal.ONE));
		anuncio.setValorTotal(anuncio.getDiferncaDias().multiply(anuncio.getValor()));
	}


	/**
	 * Método que calcula qnt de:clicada, compartilhamento, visualização
	 *
	 * @param anuncio
	 */
	private void calculaDados(Anuncio anuncio) {

		BigDecimal visualizacaoPrimaria = calculaQtdVisualizacaoPrimaria(anuncio.getValor());
		BigDecimal visualizacaoBase = visualizacaoPrimaria;
		BigDecimal clicadaTotal = BigDecimal.ZERO;
		BigDecimal compatilhadaTotal = BigDecimal.ZERO;
		BigDecimal novasVisualizacoesTotal = BigDecimal.ZERO;
		BigDecimal visualizacoesTotal = BigDecimal.ZERO;

		//Faz os cálculos de 1 dia
		for (int controlador = 0; controlador < MAX_COMPARTILHAMENTO; controlador++) {
			BigDecimal clicada = calculaQtdClicks(visualizacaoBase);
			BigDecimal compartilhada = calculaQtdCompartilhamento(clicada);
			BigDecimal novaVisualizacoes = calculaQtdNovaVisualizacao(compartilhada);

			novasVisualizacoesTotal = novasVisualizacoesTotal.add(novaVisualizacoes);
			visualizacaoBase = novaVisualizacoes;
			clicadaTotal = clicadaTotal.add(clicada);
			compatilhadaTotal = compatilhadaTotal.add(compartilhada);
		}

		visualizacoesTotal = visualizacaoPrimaria.add(novasVisualizacoesTotal);

		//Seta os Valores multiplicando pela quantidade de dias
		anuncio.setQtdClick(clicadaTotal.multiply(anuncio.getDiferncaDias()));
		anuncio.setQtdCompartilhamento(compatilhadaTotal.multiply(anuncio.getDiferncaDias()));
		anuncio.setQtdVisualizacao(visualizacoesTotal.multiply(anuncio.getDiferncaDias()));

	}

	/**
	 * Método que calcula quantidade de visualização primária
	 *
	 * @param valor
	 * @return
	 */
	public BigDecimal calculaQtdVisualizacaoPrimaria(BigDecimal valor) {

		return valor.multiply(BigDecimal.valueOf(30));
	}


	/**
	 * Método que qualcula quantidade de pessoas que clicaram no Anúncio
	 *
	 * @param visualizacao
	 * @return
	 */
	public BigDecimal calculaQtdClicks(BigDecimal visualizacao) {

		return visualizacao.multiply(BigDecimal.valueOf(0.12));
	}

	/**
	 * Método que calcula quantidade de pessoas que compartilharam o Anúncio
	 *
	 * @param clicadas
	 * @return
	 */
	public BigDecimal calculaQtdCompartilhamento(BigDecimal clicadas) {

		return clicadas.multiply(BigDecimal.valueOf(0.15));
	}

	/**
	 * Método que calcula quantidade de novas visualizações
	 *
	 * @param compartilhamento
	 * @return
	 */
	public BigDecimal calculaQtdNovaVisualizacao(BigDecimal compartilhamento) {

		return compartilhamento.multiply(BigDecimal.valueOf(40));
	}

	/**
	 * Método que verifica se o cliente já existe
	 *
	 * @param anuncio
	 */
	private void verificaClienteExistente(Anuncio anuncio) {

		Cliente cliente = clienteRepository.findByNome(anuncio.getCliente().getNome());

		if (cliente != null) {
			anuncio.setCliente(cliente);
		}

	}


}
