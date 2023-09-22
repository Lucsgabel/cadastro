package br.edu.iftm.tspi.cadastro;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AlunoController {
    @Autowired
    private AlunoRepository pessoaRepository;

    @GetMapping("/pessoas")
    public String listarPessoas(Model model) {
        List<Aluno> pessoas = pessoaRepository.findAll();
        model.addAttribute("pessoas", pessoas);
        return "listar_pessoas";
    }

    @GetMapping("/pessoas/novo")
    public String mostrarFormularioNovaPessoa(Model model) {
        model.addAttribute("pessoa", new Aluno());
        return "formulario_pessoa";
    }

    @PostMapping("/pessoas/salvar")
    public String salvarPessoa(@ModelAttribute("pessoa") Aluno pessoa) {
        pessoaRepository.save(pessoa);
        return "redirect:/pessoas";
    }

    @GetMapping("/pessoas/editar/{id}")
    public String mostrarFormularioEditarPessoa(@PathVariable Long id, Model model) {
        Optional<Aluno> pessoaOptional = pessoaRepository.findById(id);

        if (pessoaOptional.isPresent()) {
            Aluno pessoa = pessoaOptional.get();
            model.addAttribute("pessoa", pessoa);
            return "formulario_pessoa";
        } else {
            throw new IllegalArgumentException("ID de pessoa inválido: " + id);
        }
    }

    @GetMapping("/pessoas/excluir/{id}")
    public String excluirPessoa(@PathVariable Long id) {
        pessoaRepository.deleteById(id);
        return "redirect:/pessoas";
    }
}
