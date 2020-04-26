package br.com.codenation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.com.codenation.desafio.exceptions.CapitaoNaoInformadoException;
import br.com.codenation.desafio.exceptions.IdentificadorUtilizadoException;
import br.com.codenation.desafio.exceptions.JogadorNaoEncontradoException;

public class JogadorRepository {
	
	private Map<Long, Time> times;
	
	public JogadorRepository(Map<Long, Time> times) {
		this.times = times;
	}
	
	public void inserirJogador(Jogador jogador) {
		
		if(jogador.getTime().getJogadores().containsKey(jogador.getId())) throw new IdentificadorUtilizadoException(String.format("Jogador %s - %s. Já Cadastrado!", jogador.getId(), jogador.getNome()));
		
		jogador.getTime().addJogador(jogador);
	}
	
	public Jogador buscarCapitao(Time time) {
		return time.getJogadores().values().stream().filter(Jogador::isCapitao).findFirst().orElseThrow(() -> new CapitaoNaoInformadoException(String.format("O Time %s. Não possui um capitão!", time.getId())));
	}

	public Jogador buscarJogador(Long idJogador) {
		Time time = times.values().stream().filter(timeCadastrado -> timeCadastrado.possuiJogar(idJogador)).findFirst().orElseThrow(() -> new JogadorNaoEncontradoException(String.format("Jogador %s. Não Cadastrado!", idJogador)));
		
		return time.getJogadores().get(idJogador);
	}
	
	public List<Long> buscarTodos(Time time) {
		List<Long> jogadores = new ArrayList<>(time.getJogadores().keySet());
		Collections.sort(jogadores);
		return jogadores;
	}

	public Long buscarMaiorSalario(Time time) {
		Comparator<Jogador> filtroJogadorMaiorSalario = Comparator.comparing(Jogador::getSalario);
		
		Jogador jogador = Collections.max(time.getJogadores().values(), filtroJogadorMaiorSalario);
		
		return jogador.getId();
	}

	public Long buscarJogadorMaisVelho(Time time) {
		Comparator<Jogador> filtroJogadorMaisVelho = Comparator.comparing(Jogador::getDataNascimento);
		
		Jogador jogador = Collections.min(time.getJogadores().values(), filtroJogadorMaisVelho);
		
		return jogador.getId();
		
	}

	public Long buscarMelhorJogador(Time time) {
		
		Comparator<Jogador> filtroMelhorJogador = Comparator.comparing(Jogador::getNivelHabilidade);
		
		Jogador jogador = Collections.max(time.getJogadores().values(), filtroMelhorJogador);
		
		return jogador.getId();
	}

	public List<Long> buscarTopJogadores(Integer top) {
		List<Long> topJogadores = new ArrayList<>();
		
		List<Map<Long, Jogador>> teste = times.values().stream().map(Time::getJogadores).collect(Collectors.toList());
		List<Jogador> jogadores = new ArrayList<Jogador>();
		teste.stream().forEach( v -> v.values().forEach(j -> jogadores.add(j)));
		
        if (!jogadores.isEmpty()) {
            if (jogadores.size() < top) {
            	top = jogadores.size();
            }

            Comparator<Jogador> jogadorComparator = Comparator.comparing(Jogador::getNivelHabilidade).reversed();
            jogadores.stream()
                    .sorted(jogadorComparator)
                    .collect(Collectors.toList())
                    .subList(0, top)
                    .forEach(jogador -> topJogadores.add(jogador.getId()));
        }
        
		return topJogadores;
	}
}
