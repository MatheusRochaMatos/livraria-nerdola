package servicos;

import entidades.Emprestimo;
import entidades.Livro;
import entidades.Usuario;
import repositorios.RepositorioUsuario;
import servicos.excecao.RecursoNaoEncontrado;

import java.util.*;
import java.util.stream.Collectors;

public class ServicoUsuario implements VerificarListas {

    private final RepositorioUsuario repositorioUsuario;

    public ServicoUsuario(RepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }

    public Usuario buscarPorId (long id){
        Optional<Usuario> usuario = repositorioUsuario.buscarPorId(id);
        return usuario.orElseThrow(() -> new RecursoNaoEncontrado("Nenhum usuario encontrado com ID: " + id));
    }

    public void cadastrar(Usuario usuario){
        verificarUsuario(usuario);
        repositorioUsuario.salvar(usuario);
    }

    public void remover(Usuario usuario){
        buscarPorId(usuario.getId()); // verifica se o usuário existe
        repositorioUsuario.remover(usuario);
    }

    public void removerPorId(long idUsuario){
        buscarPorId(idUsuario); // verifica se o usuário existe
        repositorioUsuario.removerPorId(idUsuario);
    }

    public void bloquearUsuarioPorId(long idUsuario){
        Usuario usuario = buscarPorId(idUsuario);

        if(!usuario.isLiberado()){
            throw new IllegalStateException("Usuário id " + idUsuario + " já está bloqueado.");
        }

        usuario.setLiberado(false);
    }

    public void desbloquearUsuarioPorId(long idUsuario){
        Usuario usuario = buscarPorId(idUsuario);

        if(usuario.isLiberado()){
            throw new IllegalStateException("Usuário id " + idUsuario + " já está liberado.");
        }

        usuario.setLiberado(true);
    }

    public void atualizarUsuario(long id, Usuario usuario){
        Usuario entidade = buscarPorId(id);
        atualizarDadosDoUsuario(entidade, usuario);

        /* Não precisa disso, pois atualizarDadosDoUsuario já altera o próprio obj
         * mas é para treinar CRUD e o sistema ficar pronto para um possivel BD.
         */
        repositorioUsuario.atualizar(entidade);
    }

    public List<Usuario> listar(){
        List<Usuario> lista = repositorioUsuario.listar()
                .stream().sorted(Comparator.comparing(Usuario::getNome, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());

        verificarLista(lista);

        return lista;
    }

    public List<Usuario> listarPorNome(String nome){
        List<Usuario> lista = repositorioUsuario.buscarPorNome(nome);

        verificarLista(lista, nome);

        return lista;
    }

    public List<Usuario> listarLiberados(){
        List<Usuario> lista = repositorioUsuario.listar().stream().filter(Usuario::isLiberado)
                .sorted(Comparator.comparing(Usuario::getNome, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());

        verificarLista(lista);

        return lista;
    }

    public List<Usuario> listarBloqueados(){
        List<Usuario> lista = repositorioUsuario.listar().stream().filter(x -> !x.isLiberado())
                .sorted(Comparator.comparing(Usuario::getNome, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());

        verificarLista(lista);

        return lista;
    }

    private void atualizarDadosDoUsuario(Usuario entidade, Usuario obj){
        entidade.setNome(obj.getNome());
        entidade.setEmail(obj.getEmail());
    }

    private void verificarUsuario(Usuario usuario){
        if(repositorioUsuario.existePorEmail(usuario.getEmail())){
            throw new IllegalStateException("Já existe um usuário cadastrado com o e-mail: " + usuario.getEmail());
        }
    }

    public void imprimir(Usuario usuario){
        System.out.println("-----------------------------------------------Usuário-----------------------------------------------");
        imprimirLinha(usuario);
        System.out.println("-----------------------------------------------------------------------------------------------------");
    }

    public void imprimirLista(List<Usuario> usuarios){
        System.out.println("-----------------------------------------------Usuários-----------------------------------------------");
        usuarios.forEach(this::imprimirLinha);
        System.out.println("------------------------------------------------------------------------------------------------------");
    }

    private void imprimirLinha(Usuario usuario){
        System.out.printf("Usuário ID: %-5d Nome: %-25s E-mail: %-25s Status: %s%n",
                usuario.getId(), usuario.getNome(), usuario.getEmail(),
                usuario.isLiberado() ? "Liberado" : "Bloqueado");
    }
}
