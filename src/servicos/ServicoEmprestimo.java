package servicos;

import entidades.Emprestimo;
import entidades.Livro;
import entidades.Usuario;
import repositorios.RepositorioEmprestimo;
import servicos.excecao.RecursoNaoEncontrado;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class ServicoEmprestimo implements VerificarListas{
    private final static DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final RepositorioEmprestimo repositorioEmprestimo;
    private final ServicoUsuario servicoUsuario;
    private final ServicoLivro servicoLivro;

    public ServicoEmprestimo(RepositorioEmprestimo repositorioEmprestimo, ServicoUsuario servicoUsuario, ServicoLivro servicoLivro) {
        this.repositorioEmprestimo = repositorioEmprestimo;
        this.servicoUsuario = servicoUsuario;
        this.servicoLivro = servicoLivro;
    }

    public void salvarEmprestimo(Emprestimo emprestimo){
        verificarSituacao(emprestimo.getUsuario(), emprestimo.getLivrosEmprestado());

        for (Livro livro : emprestimo.getLivrosEmprestado().keySet()) {
            livro.setDisponivel(false);
        }

        repositorioEmprestimo.salvar(emprestimo);
        registrarNoHistorico(emprestimo);
    }

    private void registrarNoHistorico(Emprestimo emprestimo){

        Map<Livro, LocalDate> livrosRelatorio = new HashMap<>();
        Map<Livro, LocalDate> livrosEmprestados = emprestimo.getLivrosEmprestado();

        livrosEmprestados.keySet().forEach(livro -> livrosRelatorio.put(livro, null));

        Emprestimo relatorio = new Emprestimo(emprestimo.getId(), emprestimo.getUsuario(), livrosRelatorio,
                emprestimo.getDataEmprestimo(), emprestimo.getDataPrevista());

        repositorioEmprestimo.salvarNoHistorico(relatorio);
    }

    public void devolverLivroId(long livroId){
        Emprestimo emprestimo = repositorioEmprestimo.buscarEmprestimoPorLivroId(livroId)
                .orElseThrow(() -> new RecursoNaoEncontrado("Nenhum empréstimo ativo encontrado para o livro: " + livroId));

        Livro livro = servicoLivro.buscarPorId(livroId);
        livro.setDisponivel(true);
        emprestimo.removerLivroDoMap(livro);
        atualizarDataDeDevolucaoNoHistorico(emprestimo, livro, LocalDate.now());
        finalizarEmprestimoSeVazio(emprestimo);
    }

    public void atualizarDataDeDevolucaoNoHistorico(Emprestimo emprestimo, Livro livro, LocalDate dataDevolucao){
        Emprestimo relatorio = repositorioEmprestimo.buscarHistoricoPorId(emprestimo.getId())
                .orElseThrow(() -> new RecursoNaoEncontrado("Empréstimo id " + emprestimo.getId() + " não existe!"));

        relatorio.registrarDataDeDevolucao(livro, dataDevolucao);

        /* Redundante aqui, pois "registrarDataDeDevolucao" já altera o objeto real do histórico.
         * Mantido para simular a camada de persistência, como se houvesse um BD por trás.
         */
        repositorioEmprestimo.atualizarHistorico(relatorio);
    }

    public Emprestimo buscarPorId (long id){
        Optional<Emprestimo> emprestimo = repositorioEmprestimo.buscarPorId(id);
        return emprestimo.orElseThrow((() -> new RecursoNaoEncontrado("Nenhum empréstimo foi encontrado com ID: " + id)));
    }

    public List<Emprestimo> listar(){
        List<Emprestimo> lista = repositorioEmprestimo.listar();
        verificarLista(lista);
        return lista;
    }

    public List<Emprestimo> historicoUsuario (long id){
        // valida que o usuário existe
        servicoUsuario.buscarPorId(id);

        return repositorioEmprestimo.listarHistorico().stream().filter(x -> x.getUsuario().getId().equals(id))
                .collect(Collectors.toList());
    }

    public List<Emprestimo> historicoLivro (long id){
        // valida que o livro existe
        servicoLivro.buscarPorId(id);

        return repositorioEmprestimo.listarHistorico().stream()
                .filter(emp -> emp.getLivrosEmprestado().keySet().stream().anyMatch(livro -> livro.getId().equals(id)))
                .collect(Collectors.toList());
    }

    private void finalizarEmprestimoSeVazio(Emprestimo emprestimo){
        Map<Livro, LocalDate> livros = emprestimo.getLivrosEmprestado();

        if (livros.isEmpty()){
            repositorioEmprestimo.remover(emprestimo);
        }
    }

    public Map<Livro, LocalDate> criarMapDeLivros(List<Livro> livros){
        validarLimiteLivros(livros);

        Map<Livro, LocalDate> map = new HashMap<>();
        for (Livro livro : livros) {
            map.put(livro, null);
        }
        return map;
    }

    public void validarLimiteLivros(List<Livro> livrosEscolhidos){
        if (livrosEscolhidos.size() > 3) {
            throw new IllegalArgumentException("Limite de 3 livros por empréstimo atingido!");
        }
    }

    private void verificarSituacao(Usuario usuario, Map<Livro, LocalDate> livros){
        servicoUsuario.buscarPorId(usuario.getId());
        if(!usuario.isLiberado()){
            throw new IllegalStateException("Usuário de ID: " + usuario.getId() + ", está bloqueado!");
        }

        for (Livro livro : livros.keySet()) {
            servicoLivro.buscarPorId(livro.getId());

            if(!livro.isDisponivel()){
                throw new IllegalStateException("Livro de ID: " + livro.getId() + ", não está disponível!");
            }
        }
    }

    public boolean usuarioTemEmprestimoAtivo(long idUsuario){
        Usuario usuario = servicoUsuario.buscarPorId(idUsuario); // verifica se o usuário existe
        return repositorioEmprestimo.listar().stream()
                .anyMatch(emp -> emp.getUsuario().equals(usuario));
    }

    public boolean livroTemEmprestimoAtivo(long idLivro){
        Livro livro = servicoLivro.buscarPorId(idLivro); // verifica se o livro existe
        return repositorioEmprestimo.listar().stream()
                .anyMatch(emp -> emp.getLivrosEmprestado().containsKey(livro));
    }

    public void imprimir(Emprestimo emprestimo){
        System.out.println("----------Emprestimo----------");
        System.out.printf("Emprestimo ID: %-5d Usuário ID: %-5d Nome: %s%n",
                emprestimo.getId(), emprestimo.getUsuario().getId(), emprestimo.getUsuario().getNome());

        System.out.printf("Data do empréstimo: %-12s-  Previsão de devolução: %s%n",
                fmt.format(emprestimo.getDataEmprestimo()), fmt.format(emprestimo.getDataPrevista()));
        System.out.println();

        for (Livro livro : emprestimo.getLivrosEmprestado().keySet()) {
            System.out.printf("ID: %-5d Título: %s%n", livro.getId(), livro.getTitulo());
        }

        System.out.println("------------------------------");
    }
}
