package br.edu.ifba.demo.frontend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifba.demo.frontend.dto.LivroDTO;
import br.edu.ifba.demo.frontend.service.LivroService;

@Controller
public class LivroController {

    @Autowired
    private LivroService livroService;

    // Listar todos os livros
    @GetMapping("/livro/listall")
    public ModelAndView livro() {
        ModelAndView model = new ModelAndView();
        model.addObject("listaLivro", livroService.listAll());
        model.setViewName("livro");
        return model;
    }

    // Buscar livro por ID
    @GetMapping("/livro/buscarporid/{id}")
    public ModelAndView getById(@RequestParam("id") Long id) {
        ModelAndView model = new ModelAndView("livro/form");
        LivroDTO livro = livroService.getById(id);

        if (livro != null) {
            model.addObject("listaLivro", List.of(livro)); // Atualiza a lista para exibir somente o livro encontrado
        } else {
            model.addObject("errorMessage", "Livro não encontrado!");
        }
        model.setViewName("livro"); // Reutiliza a mesma view de "livro"
        return model;
    }

    // Deletar livro e redirecionar para a lista de livros
    @GetMapping("/livro/deletelivro/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        livroService.delete(id); 
        redirectAttributes.addFlashAttribute("deletelivro", "Livro excluído com sucesso!");
        return "redirect:/livro/listall";  // Redireciona para a página de listagem de livros
    }
}
