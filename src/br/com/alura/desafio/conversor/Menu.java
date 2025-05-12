package br.com.alura.desafio.conversor;

import java.io.IOException;
import java.util.Scanner;

public class Menu {
    private final Scanner entrada = new Scanner(System.in);

    public void exibirMenu() {
        int opcao = 0;

        String mensagem = """
                ******************************************************************************************
                Seja bem vindo/a ao Conversor de Moeda =]
                
                1) Dólar =>> Real brasileiro
                2) Real brasileiro =>> Dólar
                3) Euro =>> Real brasileiro
                4) Real Brasileiro =>> Euro
                5) Boliviano =>> Real brasileiro
                6) Real brasileiro =>> Boliviano
                7) Sair
                
                Escolha uma opção válida:
                *******************************************************************************************
                """;

        while (opcao != 7) {
            System.out.println(mensagem);
            opcao = entrada.nextInt();

            if (opcao >= 1 && opcao <= 6) {
                try {
                    executarConversao(opcao);
                } catch (IOException | InterruptedException e) {
                    System.out.println("Erro na requisição: " + e.getMessage());
                }
            } else if (opcao == 7) {
                System.out.println("Encerrando o programa.");
            } else {
                System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void executarConversao(int opcao) throws IOException, InterruptedException {
        String moedaBase = "";
        String moedaDestino = "";
        double taxa = 0;

        switch (opcao) {
            case 1 -> { moedaBase = "USD"; moedaDestino = "BRL"; }
            case 2 -> { moedaBase = "BRL"; moedaDestino = "USD"; }
            case 3 -> { moedaBase = "EUR"; moedaDestino = "BRL"; }
            case 4 -> { moedaBase = "BRL"; moedaDestino = "EUR"; }
            case 5 -> { moedaBase = "BOB"; moedaDestino = "BRL"; }
            case 6 -> { moedaBase = "BRL"; moedaDestino = "BOB"; }
        }

        Requisicao requisicao = new Requisicao(moedaBase);
        RespostaDaConversao resposta = requisicao.obterTaxas();
        TaxaDeConversao taxas = resposta.getConversionRates();

        switch (moedaDestino) {
            case "BRL" -> taxa = taxas.getBRL();
            case "USD" -> taxa = taxas.getUSD();
            case "EUR" -> taxa = taxas.getEUR();
            case "BOB" -> taxa = taxas.getBOB();
        }

        System.out.print("Digite o valor a ser convertido: ");
        double valor = entrada.nextDouble();

        double valorConvertido = valor * taxa;
        System.out.printf("Valor " + valor + "[" + moedaBase + "] corresponde ao valor final de =>>> " + valorConvertido + "[" + moedaDestino + "]");
    }
}
