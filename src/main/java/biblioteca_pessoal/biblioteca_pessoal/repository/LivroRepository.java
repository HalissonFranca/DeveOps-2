package biblioteca_pessoal.biblioteca_pessoal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import biblioteca_pessoal.biblioteca_pessoal.model.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long> {
}
