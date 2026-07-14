package servicos;

import servicos.excecao.RecursoNaoEncontrado;

import java.util.List;

public interface VerificarListas<T> {

    default void verificarLista(List<T> lista) {
        if (lista.isEmpty()) {
            throw new RecursoNaoEncontrado("A lista está vazia");
        }
    }

    default void verificarLista(List<T> lista, String nome){
        if (lista.isEmpty()) {
            throw new RecursoNaoEncontrado(nome + ", não foi encontrado!");
        }
    }
}
