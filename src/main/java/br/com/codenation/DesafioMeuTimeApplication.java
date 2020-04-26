package br.com.codenation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.codenation.desafio.annotation.Desafio;
import br.com.codenation.desafio.app.MeuTimeInterface;

public class DesafioMeuTimeApplication implements MeuTimeInterface {
	
	private TimeRepository timeRepository = new TimeRepository();
	
	private JogadorRepository jogadorRepository = new JogadorRepository(timeRepository.buscarTodos()); 

	/**
	 * Inicio das Ações do Time
	 */
	@Desafio("buscarTimes")
	public List<Long> buscarTimes() {
		return timeRepository.buscarTimesOrdenados();
	}
	
	@Desafio("incluirTime")
	public void incluirTime(Long id, String nome, LocalDate dataCriacao, String corUniformePrincipal, String corUniformeSecundario) {
		timeRepository.inserir(new Time(id, nome, dataCriacao, corUniformePrincipal, corUniformeSecundario));
	}
	
	@Desafio("buscarNomeTime")
	public String buscarNomeTime(Long idTime) {
		return timeRepository.encontrarTimePorIdTime(idTime).getNome();
	}
	
	@Desafio("buscarCorCamisaTimeDeFora")
	public String buscarCorCamisaTimeDeFora(Long timeDaCasa, Long timeDeFora) {
		Time timeCasa = timeRepository.encontrarTimePorIdTime(timeDaCasa);
		Time timeFora = timeRepository.encontrarTimePorIdTime(timeDeFora);
		
		if(timeFora.getCorUniformePrincipal().equalsIgnoreCase(timeCasa.getCorUniformePrincipal())) return timeFora.getCorUniformeSecundario();
		
		return timeFora.getCorUniformePrincipal();
	}

	@Desafio("buscarJogadoresDoTime")
	public List<Long> buscarJogadoresDoTime(Long idTime) {
		Time time = timeRepository.encontrarTimePorIdTime(idTime);
		
		return jogadorRepository.buscarTodos(time);
	}
	
	/**
	 * Fim das Ações do Time
	 */

	
	/**
	 * Inicio das Ações do Jogador
	 */
	@Desafio("incluirJogador")
	public void incluirJogador(Long id, Long idTime, String nome, LocalDate dataNascimento, Integer nivelHabilidade, BigDecimal salario) {
		
		Time time = timeRepository.encontrarTimePorIdTime(idTime);
		
		jogadorRepository.inserirJogador(new Jogador(id, time, nome, dataNascimento, nivelHabilidade, salario));
	}

	@Desafio("definirCapitao")
	public void definirCapitao(Long idJogador) {
		Time time = timeRepository.encontrarTimePorIdJogador(idJogador);
		
		time.getJogadores().forEach((id, jogador) -> time.getJogadores().get(idJogador).setCapitao(id.equals(idJogador)));
		
		
	}

	@Desafio("buscarCapitaoDoTime")
	public Long buscarCapitaoDoTime(Long idTime) {
		Time time = timeRepository.encontrarTimePorIdTime(idTime);
		return jogadorRepository.buscarCapitao(time).getId();
		
	}

	@Desafio("buscarNomeJogador")
	public String buscarNomeJogador(Long idJogador) {
		return jogadorRepository.buscarJogador(idJogador).getNome();
	}


	@Desafio("buscarMelhorJogadorDoTime")
	public Long buscarMelhorJogadorDoTime(Long idTime) {
		Time time = timeRepository.encontrarTimePorIdTime(idTime);
		
		return jogadorRepository.buscarMelhorJogador(time);
	}

	@Desafio("buscarSalarioDoJogador")
	public BigDecimal buscarSalarioDoJogador(Long idJogador) {
		return jogadorRepository.buscarJogador(idJogador).getSalario();
	}
	
	@Desafio("buscarJogadorMaisVelho")
	public Long buscarJogadorMaisVelho(Long idTime) {
		Time time = timeRepository.encontrarTimePorIdTime(idTime);
		
		return jogadorRepository.buscarJogadorMaisVelho(time);
	}

	@Desafio("buscarJogadorMaiorSalario")
	public Long buscarJogadorMaiorSalario(Long idTime) {
		Time time = timeRepository.encontrarTimePorIdTime(idTime);
		
		return jogadorRepository.buscarMaiorSalario(time);
	}


	@Desafio("buscarTopJogadores")
	public List<Long> buscarTopJogadores(Integer top) {
        return jogadorRepository.buscarTopJogadores(top);
	}

	/**
	 * Fim das ações do jogador
	 */
}
