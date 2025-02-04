package br.edu.ifba.demo.frontend.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import br.edu.ifba.demo.frontend.dto.LivroDTO;
import reactor.core.publisher.Mono;

@Service
public class LivroService {

    private final String BASE_URL = "http://localhost:8081/livro";
    private final RestTemplate restTemplate = new RestTemplate();
    private final WebClient webClient;

    public LivroService() {
        this.webClient = WebClient.builder().baseUrl(BASE_URL).build();
    }

    public List<LivroDTO> listAll() {
        LivroDTO[] livros = restTemplate.getForObject(BASE_URL + "/listall", LivroDTO[].class);
        return Arrays.asList(livros);
    }

    public LivroDTO getById(Long idLivro) {
        Mono<LivroDTO> monoObj = this.webClient
                .get()
                .uri("/buscarporid/{id}", idLivro) // Corrigido a URI
                .retrieve()
                .bodyToMono(LivroDTO.class);

        return monoObj.block(); // Bloqueia para obter o resultado síncrono
    }

    public void delete(Long id) {
        restTemplate.delete(BASE_URL + "/deletelivro/{id}", id);
    }

    public void addLivro(LivroDTO livroDTO) {
        this.webClient.post()
        .uri("http://localhost:8081/livro/add")  // Adicionar o domínio completo
        .bodyValue(livroDTO)
        .retrieve()
        .bodyToMono(Void.class)
        .block();

    }
    
    
}
