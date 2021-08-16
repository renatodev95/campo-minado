package br.com.cod3r.cm.visao;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import br.com.cod3r.cm.excecao.ExplosaoException;
import br.com.cod3r.cm.excecao.SairException;
import br.com.cod3r.cm.modelo.Tabuleiro;

public class TabuleiroConsole {

	private Tabuleiro tabuleiro;
	private Scanner sc = new Scanner(System.in);

	public TabuleiroConsole(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;

		executarJogo();
	}

	private void executarJogo() {
		try {
			boolean continuar = true;

			while (continuar) {
				cicloDoJogo();

				System.out.println("Outra partida? (S/n) ");
				String resposta = sc.nextLine();
				if ("n".equalsIgnoreCase(resposta)) {
					continuar = false;
				} else {
					tabuleiro.reiniciar();
				}
			}
		} catch (SairException e) {
			System.out.println("Tchau!!!");
		} finally {
			sc.close();
		}
	}

	private void cicloDoJogo() {
		try {

			// enquanto o objetivo nao for alcançado execute este trecho
			while (!tabuleiro.objetivoAlcancado()) {
				System.out.println(tabuleiro); // apresente o tabuleiro

				// capture os valores das coordenadas x e y
				String digitado = capturarValorDigitado("Digite (x, y): ");
				
				// transforme a string com as coordenadas em um valor inteiro
				// e em seguida passe para um array iterator (que possui o metodo next())
				Iterator<Integer> xy = Arrays.stream(digitado.split(","))
						.map(e -> Integer.parseInt(e.trim())).iterator();

				// capture as opções para abrir ou marcar/desmarcar o tabuleiro
				// nas coordenadas informadas anteriormente
				digitado = capturarValorDigitado("1 - Abrir ou 2 - (Des)Marcar: ");
				
				// dependendo da opção informada, o tabuleiro chama o método abrir ou alternarMarcacao
				// para o campo com as coordenada de acordo com o iterator criado anteriormente
				if ("1".equals(digitado)) {
					tabuleiro.abrir(xy.next(), xy.next());
				} else if ("2".equals(digitado)) {
					tabuleiro.alternarMarcacao(xy.next(), xy.next());
				}
			}

			System.out.println("Você ganhou!!!");
		} catch (ExplosaoException e) {
			System.out.println("Você perdeu!");
		}
	}

	private String capturarValorDigitado(String texto) {
		System.out.print(texto);
		String digitado = sc.nextLine();

		if ("sair".equalsIgnoreCase(digitado)) {
			throw new SairException();
		}

		return digitado;
	}
}
