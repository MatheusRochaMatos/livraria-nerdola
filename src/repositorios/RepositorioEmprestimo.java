package repositorios;

import entidades.Emprestimo;
import entidades.Livro;

import java.util.Optional;

public final class RepositorioEmprestimo extends Repositorio<Emprestimo>{

    public Optional<Emprestimo> buscarEmprestimoPorLivro(Livro livro){
        return lista.stream().filter(emprestimo
                -> emprestimo.getLivrosEmprestado().containsKey(livro)).findFirst();
    }
}
