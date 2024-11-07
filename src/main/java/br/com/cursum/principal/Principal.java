package br.com.cursum.principal;

import br.com.cursum.api.DadosAtividade;
import br.com.cursum.api.DadosAula;
import br.com.cursum.api.DadosCurso;
import br.com.cursum.api.DadosInstrutor;
import br.com.cursum.dto.*;
import br.com.cursum.model.Formacao;
import br.com.cursum.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.*;

public class Principal {
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://cursos-web-service.vercel.app/";

    private CursoService cursoService;
    private FormacaoService formacaoService;
    private AulaService aulaService;
    private AtividadeService atividadeService;
    private HabilidadeService habilidadeService;

    private CursoDTO cursoDTO;
    private DadosCurso dadosCurso;
    private List<CursoDTO> cursos = new ArrayList<>();

    private Scanner leitor = new Scanner(System.in);

    public Principal(
            CursoService cursoService,
            FormacaoService formacaoService,
            HabilidadeService habilidadeService,
            AulaService aulaService,
            AtividadeService atividadeService) {
        this.cursoService = cursoService;
        this.formacaoService = formacaoService;
        this.habilidadeService = habilidadeService;
        this.aulaService = aulaService;
        this.atividadeService = atividadeService;
    }

    public void exibeMenu(){
        int opcao;
        do {
            var menu = """
                    \nESCOLHA UMA OPÇÃO
                    1 - Buscar cursos na API
                    2 - Listar cursos da Plataforma
                    3 - Criar uma Formação
                    4 - Listar Cursos com aulas e atividades
                    0 - Sair
                    """;
            System.out.println(menu);
            opcao = leitor.nextInt();
            leitor.nextLine();

            switch (opcao){
                case 1 -> buscarCursoNaApi();
                case 2 -> listarCursos();
                case 3 -> criarFormacao();
                case 4 -> listarCurso();
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida");
            }
        } while (opcao != 0);
    }

    public void buscarCursoNaApi() {
        System.out.println("Digite o curso que deseja buscar da API: ");
        String nomeCurso = leitor.nextLine();
        String url = ENDERECO + "curso?nome=" + nomeCurso.replace(" ", "+");
        String json = consumo.obterDados(url);
        dadosCurso = conversor.obterDados(json, DadosCurso.class);
        System.out.println(dadosCurso);

        System.out.println("Deseja salvar o curso na plataforma ? (S/N)");
        if (leitor.nextLine().equalsIgnoreCase("s")) {
            if (salvarCursoBuscadoNaApi()) {
                System.out.println("*Curso salvo com sucesso!*");
            }
        }
    }

    private boolean salvarCursoBuscadoNaApi() {
        List<HabilidadeDTO> habilidadesDTO = new ArrayList<>();
        if (dadosCurso.habilidades() != null && ! dadosCurso.habilidades().isEmpty()) {
            String[] habilidadesArray = dadosCurso.habilidades().split(",");
            for (String habilidade : habilidadesArray) {
                habilidadesDTO.add(new HabilidadeDTO(null, habilidade.trim(), null));
            }
        }

        cursoDTO = new CursoDTO(null, dadosCurso.nome(), dadosCurso.dataCriacao(), dadosCurso.duracao(), dadosCurso.icone(), Double.parseDouble(dadosCurso.avaliacao()), dadosCurso.escola(), habilidadesDTO, null, null);
        buscaInstrutoresDaApi();
        cursoDTO = cursoService.criarCurso(cursoDTO);
        buscaAulasDaApi();

        return cursoService.buscarPorId(cursoDTO.getId()) != null;
    }

    private void buscaInstrutoresDaApi() {
        String url = ENDERECO + "curso?nome=" + cursoDTO.getNome().replace(" ", "+") + "&instrutor=all";
        String json = consumo.obterDados(url);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            DadosInstrutor[] instrutoresArray = objectMapper.readValue(json, DadosInstrutor[].class);

            for (DadosInstrutor dadosInstrutor : instrutoresArray) {
                InstrutorDTO instrutorDTO = new InstrutorDTO(null, dadosInstrutor.nome(), dadosInstrutor.descricao(), null);
               //instrutorDTO.getCursoIds().add(cursoDTO.getId());
                cursoDTO.getInstrutores().add(instrutorDTO);
                // salvar instrutor usando o serviço (implementação dependente do InstrutorService, se existir)
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void buscaAulasDaApi() {
        String url = ENDERECO + "curso?nome=" + cursoDTO.getNome().replace(" ", "+") + "&aulas=all";
        String json = consumo.obterDados(url);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            DadosAula[] dadosAulaArray = objectMapper.readValue(json, DadosAula[].class);

            for (DadosAula dadosAula : dadosAulaArray) {
                AulaDTO aulaDTO = new AulaDTO(null, dadosAula.titulo(), Integer.parseInt(dadosAula.numSequencia()), dadosAula.duracao(), cursoDTO.getId(), null);
                AulaDTO aulaSalva = aulaService.criarAula(aulaDTO);
                buscaAtividadesDaApi(dadosAula.idApi(), aulaSalva);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void buscaAtividadesDaApi(String aulaId, AulaDTO aula) {
        String url = ENDERECO + "curso?nome=" + cursoDTO.getNome().replace(" ", "+") + "&aula=" + aulaId;
        String json = consumo.obterDados(url);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            DadosAtividade[] dadosAtividadeArray = objectMapper.readValue(json, DadosAtividade[].class);

            for (DadosAtividade dadosAtividade : dadosAtividadeArray) {
                AtividadeDTO atividadeDTO = new AtividadeDTO(null, Integer.parseInt(dadosAtividade.numSequencia()), dadosAtividade.titulo(), dadosAtividade.tipo(), aula.getId());
                atividadeService.criarAtividade(atividadeDTO);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listarCursos() {
        List<CursoDTO> cursos = cursoService.buscarTodos();
        cursos.stream()
                .sorted(Comparator.comparing(CursoDTO::getNome))
                .forEach(c -> System.out.println("Curso: " + c.getNome() + ", Escola: " + c.getEscola()));
    }

    public void criarFormacao() {
        FormacaoDTO formacaoDTO = new FormacaoDTO();
        System.out.println("Qual nome você quer dar à formação?");
        formacaoDTO.setNome(leitor.nextLine());

        String continuar;
        do {
            System.out.println("Qual o ID do curso que deseja inserir?");
            Long idCurso = leitor.nextLong();
            leitor.nextLine();

            Optional<CursoDTO> cursoDTO = Optional.ofNullable(cursoService.buscarPorId(idCurso));

            if (cursoDTO.isPresent()) {
                formacaoDTO.getCursos().add(cursoDTO.get());
                System.out.println("Curso adicionado!");
            } else {
                System.out.println("Curso não encontrado!");
            }

            System.out.println("Deseja inserir outro curso? (S/N)");
            continuar = leitor.nextLine();
        } while (continuar.equalsIgnoreCase("s"));

        formacaoService.criarFormacao(formacaoDTO);
    }

    public void listarCurso() {
        System.out.println("Insira o id do curso que deseja listar:");
        Long idCurso = leitor.nextLong();
        leitor.nextLine();

        CursoDTO curso = cursoService.buscarPorId(idCurso);

        if (curso != null) {
            //System.out.println("Curso: " + curso);
            System.out.println("Curso: "+curso.toString());
        } else {
            System.out.println("Curso não encontrado.");
        }
    }
}
