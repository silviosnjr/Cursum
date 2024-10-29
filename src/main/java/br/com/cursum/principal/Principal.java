package br.com.cursum.principal;

import br.com.cursum.model.*;
import br.com.cursum.repository.CursoRepository;
import br.com.cursum.repository.FormacaoRepository;
import br.com.cursum.service.ConsumoApi;
import br.com.cursum.service.ConverteDados;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.*;

public class Principal {
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://cursos-web-service.vercel.app/";

    private CursoRepository repositorioCurso;
    private FormacaoRepository repositorioFormacao;

    private Curso curso = new Curso();
    DadosCurso dadosCurso;
    private List<Curso> cursos = new ArrayList<>();

    private Scanner leitor = new Scanner(System.in);

    public Principal(
            CursoRepository repositorioCurso,
            FormacaoRepository repositorioFormacao) {
        this.repositorioCurso = repositorioCurso;
        this.repositorioFormacao = repositorioFormacao;
    }

    public void exibeMenu(){
        var opcao = -1;
        while (opcao != 0){
            var menu = """
                    \nESCOLHA UMA OPÇÃO
                    1 - Buscar cursos na API
                    2 - Listar cursos da Plataforma
                    3 - Criar uma Formação
                    4 - Listar Cursos com aulas e atividades
                    """;
            System.out.println(menu);
            opcao = leitor.nextInt();
            leitor.nextLine();

            switch (opcao){
                case 1:
                    buscarCurso();
                    break;
                case 2:
                    listarCursos();
                    break;
                case 3:
                    criarFormacao();
                    break;
                case 4:
                    listarCurso();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }



    }

    public void buscarCurso(){
        System.out.println("Digite o curso que deseja buscar da API: ");
        var nomeCurso = this.leitor.nextLine();
        String endpoint = "curso?";
        String parametro = "nome=";
        var url = ENDERECO + endpoint + parametro + nomeCurso.replace(" ", "+");
        var json = consumo.obterDados(url);
        dadosCurso = conversor.obterDados(json, DadosCurso.class);
        System.out.println(dadosCurso);
        System.out.println("Deseja salvar o curso na plataforma ? (S/N)");
        var resposta = this.leitor.nextLine();
        if(resposta.toLowerCase().equals("s")){
            if(this.salvarCurso()){
                System.out.println("*Curso salvo com sucesso!*");
            }
        }
    }

    public void buscaInstrutores(){
        String endpoint = "curso?";
        String parametro1 = "nome=";
        String nomeCurso = this.curso.getNome();
        String parametro2 = "&instrutor=all";

        var url = ENDERECO + endpoint + parametro1 + nomeCurso.replace(" ", "+")+parametro2;
        var json = consumo.obterDados(url);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            DadosInstrutor[] instrutoresArray = objectMapper.readValue(json, DadosInstrutor[].class);

            for (DadosInstrutor dadosInstrutor : instrutoresArray) {
                Instrutor instrutor = new Instrutor(dadosInstrutor);
                instrutor.getCursos().add(this.curso);
                this.curso.getInstrutores().add(instrutor);
                //repositorioInstrutor.save(instrutor);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void buscaAulas(){
        String endpoint = "curso?";
        String parametro1 = "nome=";
        String nomeCurso = this.curso.getNome();
        String parametro2 = "&aulas=all";

        var url = ENDERECO + endpoint + parametro1 + nomeCurso.replace(" ", "+")+parametro2;
        var json = consumo.obterDados(url);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            DadosAula[] dadosAulaArray = objectMapper.readValue(json, DadosAula[].class);

            for (DadosAula dadosAula : dadosAulaArray) {
                Aula aula = new Aula(dadosAula);
                aula.setCurso(this.curso);
                this.curso.getAulas().add(aula);
                buscaAtividades(aula);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void buscaAtividades(Aula aula){
        String endpoint = "curso?";
        String parametro1 = "nome=";
        String nomeCurso = this.curso.getNome();
        String parametro2 = "&aula="+aula.getIdApi();

        var url = ENDERECO + endpoint + parametro1 + nomeCurso.replace(" ", "+")+parametro2;
        var json = consumo.obterDados(url);

        try {
            ObjectMapper objectMapperAtv = new ObjectMapper();
            DadosAtividade[] dadosAtividadeArray = objectMapperAtv.readValue(json, DadosAtividade[].class);

            for (DadosAtividade dadosAtividade : dadosAtividadeArray) {
                Atividade atividade = new Atividade(dadosAtividade);
                atividade.setAula(aula);
                aula.getAtividades().add(atividade);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public boolean salvarCurso(){
        this.curso = new Curso(dadosCurso);
        buscaInstrutores();
        buscaAulas();
        this.repositorioCurso.save(this.curso);
        return this.repositorioCurso.existsById(this.curso.getId());
        //System.out.println(curso);
    }

    public void listarCursos(){
        List<Curso> cursos = new ArrayList<>();
        cursos = repositorioCurso.findAll();
        cursos.stream()
                .sorted(Comparator.comparing(Curso::getEscola))
                .forEach(Curso::resumo);
    }

    public void criarFormacao(){
        Formacao formacao = new Formacao();
        System.out.println("Qual nome você quer dar a formação ?");
        formacao.setNome(leitor.nextLine());

        var continuar = "s";

        while (continuar.toLowerCase().equals("s")) {
            var pergunta = "Qual o ID do curso que deseja inserir ?";
            System.out.println(pergunta);
            Long idCurso = leitor.nextLong();
            leitor.nextLine();

            Optional<Curso> c = this.repositorioCurso.findById(idCurso);

            if(c.isPresent()) {
                formacao.getCursos().add(c.get());
                System.out.println("Curso adicionado!");
            }else{
                System.out.println("Curso não encontrado!");
            }

            System.out.println("Deseja inserir outro curso ? (S/N) ");
            continuar = leitor.nextLine();

        }
        this.repositorioFormacao.save(formacao);
        if(this.repositorioFormacao.existsById(formacao.getId())){
            System.out.println("*Formação salvo com sucesso!*");
        }

    }

    public void listarCurso(){
        System.out.println("Insira o id da unidade que deseja listar: ");
        var idCurso = leitor.nextLong();
        leitor.nextLine();

        List<Curso> cursos = new ArrayList<>();
        cursos = repositorioCurso.findAllById(idCurso);
        cursos.stream()
                .sorted(Comparator.comparing(Curso::getEscola))
                .forEach(System.out::println);
    }
}
