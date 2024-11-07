package br.com.cursum.repository;

import br.com.cursum.model.Atividade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AtividadeRepository extends JpaRepository<Atividade, Long> {
    // Método para buscar atividades por ID de aula, caso necessário
    List<Atividade> findByAulaId(Long aulaId);
}
