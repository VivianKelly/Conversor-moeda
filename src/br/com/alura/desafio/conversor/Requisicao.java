package br.com.alura.desafio.conversor;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Requisicao {
    private final String taxaDeConversao;

    public Requisicao(String taxaDeConversao) {
        this.taxaDeConversao = taxaDeConversao;
    }

    public RespostaDaConversao obterTaxas() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://v6.exchangerate-api.com/v6/YOUR-API-KEY/latest/" + taxaDeConversao))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();

        Gson gson = new Gson();
        return gson.fromJson(json, RespostaDaConversao.class);
    }
}
