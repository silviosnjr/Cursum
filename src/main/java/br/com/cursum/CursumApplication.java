package br.com.cursum;

import br.com.cursum.principal.Principal;
import br.com.cursum.repository.*;
import br.com.cursum.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CursumApplication implements CommandLineRunner {
	@Autowired
	private CursoService cursoService;
	@Autowired
	private FormacaoService formacaoService;
	@Autowired
	private HabilidadeService habilidadeService;
	@Autowired
	private AulaService aulaService;
	@Autowired
	private AtividadeService atividadeService;

	public static void main(String[] args) {
		SpringApplication.run(CursumApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(
				cursoService,
				formacaoService,
				habilidadeService,
				aulaService,
				atividadeService);
		principal.exibeMenu();
	}
}
