package br.com.cursum.repository;

import br.com.cursum.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CursoRepository extends JpaRepository<Curso, Long> {
    @Query(value = """
            
            """, nativeQuery = true)
    List<Curso> findAllById(Long id);

}
