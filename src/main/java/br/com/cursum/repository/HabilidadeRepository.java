package br.com.cursum.repository;

import br.com.cursum.model.Habilidade;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HabilidadeRepository extends JpaRepository<Habilidade, Long> {
    List<Habilidade> findByCursoId(Long cursoId);
}
