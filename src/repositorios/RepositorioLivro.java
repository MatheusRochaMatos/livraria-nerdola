package repositorios;

import entidades.Livro;

import java.util.List;
import java.util.stream.Collectors;

public final class RepositorioLivro extends Repositorio<Livro>{

    public List<Livro> buscarPorTitulo(String titulo){
        String nomeNormalizado = removerAcentos(titulo.toLowerCase());
        return lista.stream().filter(x -> removerAcentos(x.getTitulo().toLowerCase()).contains(nomeNormalizado))
                .collect(Collectors.toList());
    }

}