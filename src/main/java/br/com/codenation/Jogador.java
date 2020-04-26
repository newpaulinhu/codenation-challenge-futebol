package br.com.codenation;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Jogador {

	private Long id;
	
	private Time time;
	
	private String nome;
	
	private LocalDate dataNascimento;
	
	private Integer nivelHabilidade;
	
	private BigDecimal salario;
	
	private boolean capitao;

	public Jogador() {}

	public Jogador(Long id, Time time, String nome, LocalDate dataNascimento, Integer nivelHabilidade, BigDecimal salario) {
		this.id = id;
		this.time = time;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.nivelHabilidade = nivelHabilidade;
		this.salario = salario;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdTime() {
		return time.getId();
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Integer getNivelHabilidade() {
		return nivelHabilidade;
	}

	public void setNivelHabilidade(Integer nivelHabilidade) {
		this.nivelHabilidade = nivelHabilidade;
	}

	public BigDecimal getSalario() {
		return salario;
	}

	public void setSalario(BigDecimal salario) {
		this.salario = salario;
	}

	public boolean isCapitao() {
		return capitao;
	}

	public void setCapitao(boolean capitao) {
		this.capitao = capitao;
	}
}
