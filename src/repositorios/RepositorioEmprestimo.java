package repositorios;

import entidades.Emprestimo;

import java.util.Optional;

public final class RepositorioEmprestimo extends Repositorio<Emprestimo>{

    public Optional<Emprestimo> buscarEmprestimoPorLivroId(long id){
        return lista.stream().filter(emprestimo
                -> emprestimo.getLivrosEmprestado().keySet().stream()
                .anyMatch(livro -> livro.getId().equals(id)))
                .findFirst();
    }
}
