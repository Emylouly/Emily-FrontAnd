package br.edu.ifba.demo.frontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifba.demo.frontend.dto.LivroDTO;
import br.edu.ifba.demo.frontend.service.LivroService;

@Controller
@RequestMapping("/livro")
public class LivroController {

    @Autowired
    private LivroService livroService;

    // Listar todos os livros
    @GetMapping("/listall")
    public ModelAndView listarLivros() {
        ModelAndView model = new ModelAndView("livro"); // Nome do template correto
        model.addObject("listaLivro", livroService.listAll());
        return model;
    }

    // Buscar livro por ID
    @GetMapping("/view/{id}")
    public ModelAndView buscarLivroPorId(@PathVariable("id") Long id) {
        LivroDTO livro = livroService.getById(id);
        ModelAndView model = new ModelAndView("livro/form");
        model.addObject("livro", livro);
        model.addObject("view", true); // Correção para condicionar o modo de visualização
        return model;
    }

    // Deletar livro e redirecionar para a lista de livros
    @GetMapping("/deletelivro/{id}")
    public String deletarLivro(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        livroService.delete(id);
        redirectAttributes.addFlashAttribute("deletelivro", "Livro excluído com sucesso!");
        return "redirect:/livro/listall";
    }

    @GetMapping("/novo")
    public ModelAndView novoLivro() {
        ModelAndView model = new ModelAndView("livro/form");
        model.addObject("livro", new LivroDTO());
        return model;
    }

    // Mapeia a URL para salvar o livro após o envio do formulário
    @PostMapping("/novo")
    public String adicionarLivro(@ModelAttribute LivroDTO livro) {
        livroService.addLivro(livro); // Chama o serviço para salvar o livro
        return "redirect:/livro/listall"; // Redireciona para a página de lista de livros
    }

}
