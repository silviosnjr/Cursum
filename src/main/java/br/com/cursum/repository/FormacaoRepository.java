package br.com.cursum.repository;

import br.com.cursum.model.Formacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormacaoRepository extends JpaRepository<Formacao, Long> {
}
