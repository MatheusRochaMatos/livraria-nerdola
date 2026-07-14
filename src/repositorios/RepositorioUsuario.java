package repositorios;

import entidades.Usuario;

import java.util.List;
import java.util.stream.Collectors;

public final class RepositorioUsuario extends Repositorio<Usuario>{

    public boolean existePorEmail(String email){
        return lista.stream().anyMatch(x -> x.getEmail().equalsIgnoreCase(email));
    }

    public List<Usuario> buscarPorNome(String nome){
        String nomeNormalizado = removerAcentos(nome.toLowerCase());
        return lista.stream().filter(x -> removerAcentos(x.getNome().toLowerCase()).contains(nomeNormalizado))
                .collect(Collectors.toList());
    }
}
