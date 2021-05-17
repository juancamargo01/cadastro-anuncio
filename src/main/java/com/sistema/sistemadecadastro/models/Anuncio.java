package com.sistema.sistemadecadastro.models;

import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "anuncio")
public class Anuncio implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "cliente")
	@Cascade(org.hibernate.annotations.CascadeType.PERSIST)
	private Cliente cliente;

	@NotEmpty
	private String nome;

	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataInicio;

	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataFinal;

	@NotNull
	private BigDecimal valor;

	private BigDecimal valorTotal;

	private BigDecimal qtdClick;

	private BigDecimal qtdCompartilhamento;

	private BigDecimal qtdVisualizacao;

	@Transient
	private BigDecimal diferncaDias;

	public Anuncio() {
		cliente = new Cliente();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalDate getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(LocalDate dataFinal) {
		this.dataFinal = dataFinal;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public BigDecimal getQtdClick() {
		return qtdClick;
	}

	public void setQtdClick(BigDecimal qtdClick) {
		this.qtdClick = qtdClick;
	}

	public BigDecimal getQtdCompartilhamento() {
		return qtdCompartilhamento;
	}

	public void setQtdCompartilhamento(BigDecimal qtdCompartilhamento) {
		this.qtdCompartilhamento = qtdCompartilhamento;
	}

	public BigDecimal getQtdVisualizacao() {
		return qtdVisualizacao;
	}

	public void setQtdVisualizacao(BigDecimal qtdVisualizacao) {
		this.qtdVisualizacao = qtdVisualizacao;
	}

	public BigDecimal getDiferncaDias() {
		return diferncaDias;
	}

	public void setDiferncaDias(BigDecimal diferncaDias) {
		this.diferncaDias = diferncaDias;
	}
}
