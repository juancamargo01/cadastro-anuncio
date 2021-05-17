package com.sistema.sistemadecadastro.service;

import com.sistema.sistemadecadastro.models.Anuncio;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AnuncioServiceTest {

    AnuncioService anuncioService = new AnuncioService();

    @Test
    void calculaQtdVisualizacaoPriamria() {
        BigDecimal valor = BigDecimal.valueOf(1);
        BigDecimal ress = anuncioService.calculaQtdVisualizacaoPrimaria(valor);
        assertEquals(new BigDecimal(30),new BigDecimal(String.valueOf(ress)));

    }

    @Test
    void calculaQtdClicks() {
        BigDecimal valor = BigDecimal.valueOf(100);
        BigDecimal ress = anuncioService.calculaQtdClicks(valor);
        assertEquals(new BigDecimal(12),new BigDecimal(String.format("%.0f", ress)));
    }

    @Test
    void calculaQtdCompartilhamento() {
        BigDecimal valor = BigDecimal.valueOf(100);
        BigDecimal ress = anuncioService.calculaQtdCompartilhamento(valor);
        assertEquals(new BigDecimal(15),new BigDecimal(String.format("%.0f", ress)));
    }

    @Test
    void calculaQtdNovaVisualizacao() {

        BigDecimal valor = BigDecimal.valueOf(1);
        BigDecimal ress = anuncioService.calculaQtdNovaVisualizacao(valor);
        assertEquals(new BigDecimal(40),new BigDecimal(String.format("%.0f", ress)));
    }


    @Test
    void findAll() {
    }

    @Test
    void findById() {
    }

    @Test
    void save() {

        //Mock de Anuncio
        Anuncio anuncioMock = new Anuncio();
        anuncioMock.setNome("a");
        anuncioMock.getCliente().setNome("Teste");

        //Salvar
        anuncioService.save(anuncioMock);

        //Busca
        Anuncio anuncio = anuncioService.findByNome(anuncioMock.getNome());

        //validações


    }

    @Test
    void deleteById() {
    }

    @Test
    void relatorioAnuncio() {
    }

}