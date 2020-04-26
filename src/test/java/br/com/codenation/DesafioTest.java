package br.com.codenation;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.codenation.desafio.exceptions.CapitaoNaoInformadoException;
import br.com.codenation.desafio.exceptions.IdentificadorUtilizadoException;
import br.com.codenation.desafio.exceptions.JogadorNaoEncontradoException;
import br.com.codenation.desafio.exceptions.TimeNaoEncontradoException;

public class DesafioTest {

	private DesafioMeuTimeApplication application;
	
	@Before
	public void setUpTest() {
		application = new DesafioMeuTimeApplication();
		
		application.incluirTime(99L, "Time Default", LocalDate.now(), "azul", "verde");
		
		application.incluirJogador(999L, 99L, "Jogador Default", LocalDate.now(), 99, BigDecimal.valueOf(9999.9));
		
	}
	
	@Test
	public void deveInserirTime() {
		application.incluirTime(1L, "Teste", LocalDate.now(), "azul", "verde");
	}
	
	@Test(expected = IdentificadorUtilizadoException.class)
	public void naoDeveInserirTime() {
		application.incluirTime(99L, "Time Default", LocalDate.now(), "azul", "verde");
	}
	
	@Test
	public void deveInserirJogador() {
		application.incluirTime(2L, "Teste Jogador", LocalDate.now(), "azul", "verde");
		
		application.incluirJogador(210L, 2L, "Jogador Teste 1", LocalDate.now(), 99, BigDecimal.valueOf(9999.9));
		
	}

	@Test(expected = TimeNaoEncontradoException.class)
	public void naoDeveInserirJogadorTimeNaoCadastrado() {
		application.incluirJogador(999L, 9999L, "Jogador Time Não Existe", LocalDate.now(), 99, BigDecimal.valueOf(9999.9));
	}
	
	@Test(expected = IdentificadorUtilizadoException.class)
	public void naoDeveInserirJogadorDuplicado() {
		application.incluirJogador(999L, 99L, "Jogador Default", LocalDate.now(), 99, BigDecimal.valueOf(9999.9));
	}
	
	@Test()
	public void deveDefinirCapitao() {
		
		application.incluirTime(3L, "Teste Jogador 3", LocalDate.now(), "azul", "verde");
		
		application.incluirJogador(310L, 3L, "Jogador Teste 3", LocalDate.now(), 99, BigDecimal.valueOf(9999.9));
		
		application.definirCapitao(310L);
		
		Long idCapitao = application.buscarCapitaoDoTime(3L);
		
		assertTrue(idCapitao.equals(310L));
		
	}
	
	
	@Test(expected = JogadorNaoEncontradoException.class)
	public void naoDeveDefinirCapitaoJogadorNaoEncontrado() {
		application.incluirTime(4L, "Teste Jogador 4", LocalDate.now(), "azul", "verde");
		
		application.incluirJogador(410L, 4L, "Jogador Teste 4", LocalDate.now(), 99, BigDecimal.valueOf(9999.9));
		
		application.definirCapitao(5L);
	}
	

	@Test()
	public void deveDefinirApenasUmCapitao() {
		
		application.incluirTime(3L, "Teste Jogador 3", LocalDate.now(), "azul", "verde");
		
		application.incluirJogador(310L, 3L, "Jogador Teste 3", LocalDate.now(), 99, BigDecimal.valueOf(9999.9));
		application.incluirJogador(311L, 3L, "Jogador Teste 4", LocalDate.now(), 99, BigDecimal.valueOf(9999.9));
		application.incluirJogador(312L, 3L, "Jogador Teste 5", LocalDate.now(), 99, BigDecimal.valueOf(9999.9));
		
		application.definirCapitao(310L);
		application.definirCapitao(312L);
		
		Long idCapitao = application.buscarCapitaoDoTime(3L);
		
		assertTrue(idCapitao.equals(312L));
		
	}
	
	
	
	@Test(expected = CapitaoNaoInformadoException.class)
	public void naoDeveBuscarCapitao() {
		application.incluirTime(5L, "Teste Jogador 5", LocalDate.now(), "azul", "verde");
		
		application.incluirJogador(510L, 5L, "Jogador Teste 5", LocalDate.now(), 99, BigDecimal.valueOf(9999.9));
		
		application.buscarCapitaoDoTime(5L);
	}
	
	@Test
	public void deveBuscarNomeJogador() {
		application.incluirTime(5L, "Teste Jogador 5", LocalDate.now(), "azul", "verde");
		
		application.incluirJogador(510L, 5L, "Jogador Teste 5", LocalDate.now(), 99, BigDecimal.valueOf(9999.9));
		
		String nomeJogador = application.buscarNomeJogador(510L);
		
		assertTrue(nomeJogador.equalsIgnoreCase("Jogador Teste 5"));
	}
	
	
	@Test(expected = JogadorNaoEncontradoException.class)
	public void naoDeveBuscarNomeJogador() {
		application.incluirTime(6L, "Teste Jogador 6", LocalDate.now(), "azul", "verde");
		
		application.incluirJogador(610L, 6L, "Jogador Teste 6", LocalDate.now(), 99, BigDecimal.valueOf(9999.9));
		
		application.buscarNomeJogador(9910L);
	}
	

	@Test
	public void deveBuscarJogadoresOrdenados() {
		application.incluirTime(7L, "Teste Jogador 7", LocalDate.now(), "azul", "verde");
		
		application.incluirJogador(710L, 7L, "Jogador Teste 7", LocalDate.now(), 99, BigDecimal.valueOf(9999.9));
		application.incluirJogador(711L, 7L, "Jogador Teste 8", LocalDate.now(), 99, BigDecimal.valueOf(9999.9));
		application.incluirJogador(703L, 7L, "Jogador Teste 9", LocalDate.now(), 99, BigDecimal.valueOf(9999.9));
		
		List<Long> jogadoresDoTime = application.buscarJogadoresDoTime(7L);

		assertThat(jogadoresDoTime, is(Arrays.asList(703L, 710L, 711L)));
	}
	
	@Test(expected = TimeNaoEncontradoException.class)
	public void naoDeveBuscarJogadoresOrdenados() {
		application.incluirTime(8L, "Teste Jogador 8", LocalDate.now(), "azul", "verde");
		
		application.incluirJogador(810L, 8L, "Jogador Teste 10", LocalDate.now(), 99, BigDecimal.valueOf(9999.9));
		application.incluirJogador(811L, 8L, "Jogador Teste 11", LocalDate.now(), 99, BigDecimal.valueOf(9999.9));
		application.incluirJogador(803L, 8L, "Jogador Teste 12", LocalDate.now(), 99, BigDecimal.valueOf(9999.9));
		
		application.buscarJogadoresDoTime(875L);
	}
	
	@Test
	public void deveBuscarMelhorJogador() {
		application.incluirTime(9L, "Teste Jogador 7", LocalDate.now(), "azul", "verde");
		
		application.incluirJogador(910L, 9L, "Jogador Teste 7", LocalDate.now(), 10, BigDecimal.valueOf(9999.9));
		application.incluirJogador(911L, 9L, "Jogador Teste 8", LocalDate.now(), 20, BigDecimal.valueOf(9999.9));
		application.incluirJogador(903L, 9L, "Jogador Teste 9", LocalDate.now(), 30, BigDecimal.valueOf(9999.9));
		
		Long idMelhorJogador = application.buscarMelhorJogadorDoTime(9L);
		
		assertTrue(idMelhorJogador.equals(903L));
	}
	

	@Test
	public void deveBuscarJogadorMaisVelho() {
		application.incluirTime(10L, "Teste Jogador 7", LocalDate.now(), "azul", "verde");
		
		application.incluirJogador(1010L, 10L, "Jogador Teste 7", LocalDate.of(1994, 1, 13), 10, BigDecimal.valueOf(9999.9));
		application.incluirJogador(1011L, 10L, "Jogador Teste 8", LocalDate.of(2000, 05, 24), 20, BigDecimal.valueOf(9999.9));
		application.incluirJogador(1003L, 10L, "Jogador Teste 9", LocalDate.of(1990, 12, 20), 30, BigDecimal.valueOf(9999.9));
		
		Long idMelhorJogador = application.buscarJogadorMaisVelho(10L);
		
		assertTrue(idMelhorJogador.equals(1003L));
	}
	

	@Test
	public void deveBuscarJogadorMaiorSalario() {
		application.incluirTime(11L, "Teste Jogador 7", LocalDate.now(), "azul", "verde");
		
		application.incluirJogador(1110L, 11L, "Jogador Teste 7", LocalDate.of(1994, 1, 13), 10, BigDecimal.valueOf(1000.9));
		application.incluirJogador(1111L, 11L, "Jogador Teste 8", LocalDate.of(2000, 05, 24), 20, BigDecimal.valueOf(1001.9));
		application.incluirJogador(1103L, 11L, "Jogador Teste 9", LocalDate.of(1990, 12, 20), 30, BigDecimal.valueOf(999.9));
		
		Long idMelhorJogador = application.buscarJogadorMaiorSalario(11L);
		
		assertTrue(idMelhorJogador.equals(1111L));
	}
	

	@Test
	public void deveBuscarSalarioDoJogador() {
		application.incluirTime(12L, "Teste Jogador 7", LocalDate.now(), "azul", "verde");
		
		application.incluirJogador(1210L, 12L, "Jogador Teste 7", LocalDate.of(1994, 1, 13), 10, BigDecimal.valueOf(1000.9));
		application.incluirJogador(1211L, 12L, "Jogador Teste 8", LocalDate.of(2000, 05, 24), 20, BigDecimal.valueOf(1001.9));
		application.incluirJogador(1203L, 12L, "Jogador Teste 9", LocalDate.of(1990, 12, 20), 30, BigDecimal.valueOf(999.9));
		
		BigDecimal salarioDoJogador = application.buscarSalarioDoJogador(1211L);
		
		assertTrue(salarioDoJogador.equals(BigDecimal.valueOf(1001.9)));
	}
	
	@Test
	public void deveBuscarCorTimeDeFora() {
		application.incluirTime(13L, "Time Da Casa", LocalDate.now(), "azul", "verde");
		application.incluirTime(14L, "Time de Fora", LocalDate.now(), "azul", "amarelo");
		
		String corCamisaTimeDeFora = application.buscarCorCamisaTimeDeFora(13L, 14L);
		
		assertTrue(corCamisaTimeDeFora.equals("amarelo"));
	}
	
	@Test
	public void deveBuscarTimesOrdenados() {
		application.incluirTime(9L, "Time Da Casa", LocalDate.now(), "azul", "verde");
		application.incluirTime(10L, "Time de Fora", LocalDate.now(), "azul", "amarelo");
		application.incluirTime(11L, "Time de Fora", LocalDate.now(), "azul", "amarelo");
		application.incluirTime(15L, "Time de Fora", LocalDate.now(), "azul", "amarelo");
		application.incluirTime(14L, "Time de Fora", LocalDate.now(), "azul", "amarelo");
		application.incluirTime(80L, "Time de Fora", LocalDate.now(), "azul", "amarelo");
		
		List<Long> timesOrdenados = application.buscarTimes();
		
		assertThat(timesOrdenados, is(Arrays.asList(9L, 10L, 11L, 14L, 15L, 80L, 99L)));
	}
		
	
}
