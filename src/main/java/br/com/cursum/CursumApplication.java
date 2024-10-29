package br.com.cursum;

import br.com.cursum.principal.Principal;
import br.com.cursum.repository.CursoRepository;
import br.com.cursum.repository.FormacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CursumApplication implements CommandLineRunner {
	@Autowired
	private CursoRepository cursoRepositorio;
	@Autowired
	private FormacaoRepository formacaoRepositorio;

	public static void main(String[] args) {
		SpringApplication.run(CursumApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(cursoRepositorio, formacaoRepositorio);
		principal.exibeMenu();
	}
}
