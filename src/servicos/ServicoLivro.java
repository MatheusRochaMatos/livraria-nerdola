package servicos;

import entidades.Livro;
import repositorios.RepositorioLivro;
import servicos.excecao.RecursoNaoEncontrado;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ServicoLivro implements VerificarListas {

    private final RepositorioLivro repositorioLivro;

    public ServicoLivro(RepositorioLivro repositorio){
        this.repositorioLivro = repositorio;
    }

    public Livro buscarPorId (long id){
        Optional<Livro> livro = repositorioLivro.buscarPorId(id);
        return livro.orElseThrow((() -> new RecursoNaoEncontrado("Nenhum livro encontrado com ID: " + id)));
    }

    public void cadastrar(Livro livro){
        repositorioLivro.salvar(livro);
    }

    public void cadastrarVarios(List<Livro> entidades){
        repositorioLivro.salvarVarios(entidades);
    }

    public void remover(Livro livro){
        verificarLivro(livro);
        repositorioLivro.remover(livro);
    }

    public void removerPorId(long id){
         Livro livro = buscarPorId(id);
         verificarLivro(livro);
         repositorioLivro.removerPorId(id);
    }

    public void atualizarLivro(long id, Livro livro){
        Livro entidade = buscarPorId(id);
        atualizarDadosDoLivro(entidade, livro);

        /* Não precisa disso, pois atualizarDadosDoLivro já altera o próprio obj
         * mas é para treinar CRUD e o sistema ficar pronto para um possivel BD.
         */
        repositorioLivro.atualizar(entidade);
    }

    public List<Livro> listar(){
        List<Livro> lista = repositorioLivro.listar()
                .stream().sorted(Comparator.comparing(Livro::getTitulo, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());

        verificarLista(lista);

        return lista;
    }

    public List<Livro> listarPorTitulo(String titulo){
        List<Livro> lista = repositorioLivro.buscarPorTitulo(titulo);

        verificarLista(lista, titulo);

        return lista;
    }

    public List<Livro> listarDisponiveis(){

        List<Livro> lista = repositorioLivro.listar().stream().filter(Livro::isDisponivel)
                .sorted(Comparator.comparing(Livro::getTitulo, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());

        verificarLista(lista);

        return lista;
    }

    public List<Livro> listarEmprestados(){
        List<Livro> lista = repositorioLivro.listar().stream().filter(x -> !x.isDisponivel())
                .sorted(Comparator.comparing(Livro::getTitulo, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());

        verificarLista(lista);

        return lista;
    }

    private void verificarLivro(Livro livro){
        if(!livro.isDisponivel()){
            throw new IllegalStateException("Livro emprestado não pode ser removido, " +
                    "caso necessário fazer a entrada para remover.");
        }
    }

    private void atualizarDadosDoLivro(Livro entidade, Livro obj){
        entidade.setTitulo(obj.getTitulo());
        entidade.setAutor(obj.getAutor());
        entidade.setAno(obj.getAno());
    }
}
