package br.com.cursum.repository;

import br.com.cursum.model.Aula;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AulaRepository extends JpaRepository<Aula, Long> {
    // Métodos de consulta personalizados podem ser adicionados aqui, se necessário
}
