package br.edu.ifba.demo.frontend.service;

import java.util.List;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import br.edu.ifba.demo.frontend.dto.GeneroDTO;

@Service
public class GeneroService {
    private final String BASE_URL = "http://localhost:8081/genero";
    private final RestTemplate restTemplate = new RestTemplate();
    private final WebClient webClient;

    public GeneroService() {
        this.webClient = WebClient.builder().baseUrl(BASE_URL).build();
    }

    public List<GeneroDTO> listAllGeneros() {
        return this.webClient.method(HttpMethod.GET).uri("/listall").retrieve().bodyToFlux(GeneroDTO.class)
                .collectList().block();
    }

    public void addGenero(GeneroDTO generoDTO) {
        this.webClient.post()
                .uri("/salvar") // Correto para gêneros
                .bodyValue(generoDTO)
                .retrieve()
                .bodyToMono(Void.class)
                .block();

    }

    public boolean salvarEatualizar(GeneroDTO generoDTO) {
        GeneroDTO salvar = this.webClient.post()
                .uri("/salvar")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(generoDTO)
                .retrieve()
                .bodyToMono(GeneroDTO.class)
                .block();
        return salvar != null;
    }
    

    public void deletarGenero(Long id) {
        this.webClient.delete()
                .uri("/deletar/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
    
}