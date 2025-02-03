package br.edu.ifba.demo.frontend.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.edu.ifba.demo.frontend.dto.LivroDTO;

@Service
public class LivroService {

    private final String BASE_URL = "http://localhost:8081/livro";
    private final RestTemplate Temp = new RestTemplate();

    public List<LivroDTO> listAll() {
        LivroDTO[] livros = Temp.getForObject(BASE_URL + "/listall", LivroDTO[].class);
        return Arrays.asList(livros);
    }

    public LivroDTO getById(Long id) {
        return Temp.getForObject(BASE_URL + "/buscarporid/{id}", LivroDTO.class, id);
    }

    public LivroDTO getByIsbn(String isbn) {
        return Temp.getForObject(BASE_URL + "/buscarporisbn/{isbn}", LivroDTO.class, isbn);
    }

    public LivroDTO getByTitulo(String titulo) {
        return Temp.getForObject(BASE_URL + "/buscarportitulo/{titulo}", LivroDTO.class, titulo);
    }
    
    public void delete(Long id) {
        Temp.delete(BASE_URL + "/deletelivro/{id}", id);
    }
}
