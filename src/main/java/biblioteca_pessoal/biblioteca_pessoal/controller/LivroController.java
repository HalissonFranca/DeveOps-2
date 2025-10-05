package biblioteca_pessoal.biblioteca_pessoal.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import biblioteca_pessoal.biblioteca_pessoal.model.Livro;
import biblioteca_pessoal.biblioteca_pessoal.repository.LivroRepository;

@RestController
@RequestMapping("/api/livros")
public class LivroController {
    private final LivroRepository repo;

    public LivroController(LivroRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Livro> listar() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livro> buscar(@PathVariable Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Livro> criar(@RequestBody Livro livro) {
        Livro salvo = repo.save(livro);
        return ResponseEntity.status(201).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Livro> atualizar(@PathVariable Long id, @RequestBody Livro dados) {
        return repo.findById(id).map(l -> {
            l.setTitulo(dados.getTitulo());
            l.setAutor(dados.getAutor());
            l.setAnoPublicacao(dados.getAnoPublicacao());
            l.setLido(dados.isLido());
            repo.save(l);
            return ResponseEntity.ok(l);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return repo.findById(id).map(l -> {
            repo.deleteById(id);
            return ResponseEntity.noContent().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }

}
