package br.com.cursum.repository;

import br.com.cursum.model.Instrutor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstrutorRepository extends JpaRepository<Instrutor, Long> {
    // Métodos customizados podem ser adicionados aqui, se necessário
}
