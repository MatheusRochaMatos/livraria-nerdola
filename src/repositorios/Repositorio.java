package repositorios;

import java.text.Normalizer;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public abstract class Repositorio<T extends Entidade> {

    protected List<T> lista = new ArrayList<>();
    protected List<T> historico = new ArrayList<>();

    private final AtomicLong geradorId = new AtomicLong(1);

    public void salvar(T entidade){
        if (entidade.getId() == null){
            entidade.setId(geradorId.getAndIncrement());
        }
        lista.add(entidade);
    }

    public void salvarVarios(List<T> entidades){
        for(T entidade : entidades){
            salvar(entidade);
        }
    }

    public void remover(T entidade){
        lista.remove(entidade);
    }

    public void atualizar(T entidade){
        int index = lista.indexOf(entidade);
        lista.set(index, entidade);
    }

    public void atualizarHistorico(T entidade){
        int index = historico.indexOf(entidade);
        historico.set(index, entidade);
    }

    public List<T> listar(){
        return List.copyOf(lista);
    }

    public Optional<T> buscarPorId(long id){
        return lista.stream().filter(x -> x.getId().equals(id)).findFirst();
    }

    public Optional<T> buscarHistoricoPorId(long id){
        return historico.stream().filter(x -> x.getId().equals(id)).findFirst();
    }

    public void removerPorId(long id){
        lista.removeIf(x -> x.getId().equals(id));
    }

    public void salvarNoHistorico(T relatorio){
        historico.add(relatorio);
    }

    public List<T> listarHistorico(){
        return List.copyOf(historico);
    }

    protected String removerAcentos(String texto){
        String textoNormalizado = Normalizer.normalize(texto, Normalizer.Form.NFD);
        return textoNormalizado.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }
}
