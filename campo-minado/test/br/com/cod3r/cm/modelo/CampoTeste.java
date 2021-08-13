package br.com.cod3r.cm.modelo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.cod3r.cm.excecao.ExplosaoException;

public class CampoTeste {

	private Campo campo;

	@BeforeEach
	void iniciarCampo() {
		campo = new Campo(3, 3);
	}

	@Test
	void testeVizinhoDistancia1Esquerda() {
		Campo vizinho = new Campo(3, 2);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}

	@Test
	void testeVizinhoDistancia1Direita() {
		Campo vizinho = new Campo(3, 4);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}

	@Test
	void testeVizinhoDistancia1EmCima() {
		Campo vizinho = new Campo(2, 3);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}

	@Test
	void testeVizinhoDistancia1EmBaixo() {
		Campo vizinho = new Campo(4, 3);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}

	@Test
	void testeVizinhoDistancia2() {
		Campo vizinho = new Campo(2, 2);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}

	@Test
	void testeNaoVizinho() {
		Campo vizinho = new Campo(1, 1);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertFalse(resultado);
	}

	@Test
	void testeValorPadraoAtributoMarcado() {
		assertFalse(campo.isMarcado());
	}

	@Test
	void testeAlternarMarcacao() {
		campo.alternarMarcacao();
		assertTrue(campo.isMarcado());
	}

	@Test
	void testeAlternarMarcacaoDuasChamadas() {
		campo.alternarMarcacao();
		campo.alternarMarcacao();
		assertFalse(campo.isMarcado());
	}

	@Test
	void testeAbrirNaoMinadoNaoMarcado() {
		assertTrue(campo.abrir());
	}

	@Test
	void testeAbrirNaoMinadoMarcado() {
		campo.alternarMarcacao();
		assertFalse(campo.abrir());
	}

	@Test
	void testeAbrirMinadoMarcado() {
		campo.alternarMarcacao();
		campo.minar();
		assertFalse(campo.abrir());
	}

	@Test
	void testeAbrirMinadoNaoMarcado() {
		campo.minar();
		// testando se o m�todo abrir() gerou uma exce��o e
		// se a exce��o gerada foi o tipo/classe exception esperada
		assertThrows(ExplosaoException.class, () -> {
			campo.abrir();
		});
	}

	@Test
	void testeAbrirComVizinhos1() {
		// testando a abertura de vizinhos sem campo minado ao redor
		Campo campo11 = new Campo(1, 1);
		Campo campo22 = new Campo(2, 2);

		campo22.adicionarVizinho(campo11);
		campo.adicionarVizinho(campo22);

		campo.abrir();

		assertTrue(campo22.isAberto() && campo11.isAberto());
	}

	@Test
	void testeAbrirComVizinhos2() {
		// testando a abertura de vizinhos com um campo minado ao redor
		Campo campo11 = new Campo(1, 1);
		Campo campo12 = new Campo(1, 2);
		campo12.minar();
		Campo campo22 = new Campo(2, 2);

		campo22.adicionarVizinho(campo11);
		campo22.adicionarVizinho(campo12);
		campo.adicionarVizinho(campo22);

		campo.abrir();

		assertTrue(campo22.isAberto() && campo11.isFechado());
	}

	@Test
	void testeCampoFechado() {
		assertTrue(campo.isFechado());
	}

	@Test
	void testeRetornoDaLinha() {
		assertEquals(3, campo.getLinha());
	}

	@Test
	void testeRetornoDaColuna() {
		assertEquals(3, campo.getColuna());
	}

	@Test
	void testeObjetivoAlcancadoSemMarcacao() {
		campo.abrir();
		assertTrue(campo.objetivoAlcancado());
	}

	@Test
	void testeObjetivoAlcancadoComMarcacao() {
		campo.minar();
		campo.alternarMarcacao();
		assertTrue(campo.objetivoAlcancado());
	}

	@Test
	void testeObjetivoAlcancadoComESemMarcacao() {
		Campo campo22 = new Campo(2, 2);
		campo22.minar();

		campo.adicionarVizinho(campo22);
		campo.abrir();
		campo22.alternarMarcacao();

		assertTrue(campo.objetivoAlcancado() && campo22.objetivoAlcancado());
	}

	@Test
	void testeMinasNaVizinhanca() {
		Campo campo22 = new Campo(2, 2);
		Campo campo32 = new Campo(3, 2);
		campo22.minar();
		campo32.minar();

		campo.adicionarVizinho(campo22);
		campo.adicionarVizinho(campo32);

		assertEquals(2, campo.minasNaVizinhanca());
	}
	
	@Test
	void testeReiniciar() {
		campo.reiniciar();
		
		assertTrue(campo.isFechado() && !campo.isMinado() && !campo.isMarcado());
	}

	@Test
	void testeToStringMarcado() {
		campo.alternarMarcacao();
		assertEquals("x", campo.toString());
	}
	
	@Test
	void testeToStringAbertoEMinadoNaVizinhanca() {
		Campo campo22 = new Campo(2, 2);
		Campo campo32 = new Campo(3, 2);
		campo22.minar();
		campo32.minar();

		campo.adicionarVizinho(campo22);
		campo.adicionarVizinho(campo32);
		campo.abrir();
		assertEquals("2", campo.toString());
	}
	
	@Test
	void testeCampoAberto() {
		campo.abrir();
		assertEquals(" ", campo.toString());
	}
	
	@Test
	void testeCampoIncognito() {
		assertEquals("?", campo.toString());
	}
	
}