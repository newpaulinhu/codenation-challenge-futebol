package br.com.codenation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import br.com.codenation.desafio.exceptions.IdentificadorUtilizadoException;
import br.com.codenation.desafio.exceptions.JogadorNaoEncontradoException;
import br.com.codenation.desafio.exceptions.TimeNaoEncontradoException;

public class TimeRepository {
	
	private Map<Long, Time> times = new LinkedHashMap<>();
	
	public Map<Long, Time> buscarTodos(){
		return times; 
	}
	
	public List<Long> buscarTimesOrdenados(){
		List<Long> idTimes = new ArrayList<>(times.keySet());
		Collections.sort(idTimes);
		return idTimes;
	}
	
	public boolean timeCadastrado(Long idTime) {
		return times.containsKey(idTime);
	}
	
	public Time encontrarTimePorIdTime(Long idTime) {
		if (!times.containsKey(idTime)) throw new TimeNaoEncontradoException(String.format("Time %s. Não Cadastrado!", idTime));
		
		return times.get(idTime);
	}
	
	public Time encontrarTimePorIdJogador(Long idJogador) {
		Time time = times.values()
				 		 .stream()
				 		 .filter(timeCadastrado -> timeCadastrado.possuiJogar(idJogador))
				 		 .findFirst()
				 		 .orElseThrow(() -> new JogadorNaoEncontradoException(String.format("Jogador %s. Não Cadastrado!", idJogador)));
		
		return time;
	}
	
	public void inserir(Time time) {
		if (this.timeCadastrado(time.getId())) throw new IdentificadorUtilizadoException(String.format("Time. Já Cadastrado!", time.getId(), time.getNome()));
		
		times.put(time.getId(),time);
	}
}
