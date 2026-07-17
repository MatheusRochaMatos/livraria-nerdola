package entidades;

import repositorios.Entidade;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class Emprestimo implements Entidade {

    private Long id;
    private final Usuario usuario;
    private final LocalDate dataEmprestimo;
    private final LocalDate dataPrevista;

    private final Map<Livro, LocalDate> livrosEmprestado;

    public Emprestimo(Usuario usuario, Map<Livro, LocalDate> livrosEmprestado){
        this.usuario = usuario;
        this.livrosEmprestado = livrosEmprestado;
        this.dataEmprestimo = LocalDate.now();
        this.dataPrevista = calcularDataPrevista();
    }

    // Contrutor interno para popular os dados na classe CargaTeste
    public Emprestimo(Usuario usuario, Map<Livro, LocalDate> livrosEmprestado, LocalDate dataEmprestimo, LocalDate dataPrevista) {
        this.usuario = usuario;
        this.livrosEmprestado = livrosEmprestado;
        this.dataEmprestimo = dataEmprestimo;
        this.dataPrevista = dataPrevista;
    }

    // Construtor para construção do histórico
    public Emprestimo(Long id, Usuario usuario, Map<Livro, LocalDate> livrosEmprestado, LocalDate dataEmprestimo, LocalDate dataPrevista) {
        this(usuario, livrosEmprestado, dataEmprestimo, dataPrevista);
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    // Getter protegido contra alterações externas; mutações passam pelos métodos abaixo.

    public Map<Livro, LocalDate> getLivrosEmprestado() {
        return Collections.unmodifiableMap(livrosEmprestado);
    }

    public void removerLivroDoMap(Livro livro){
        livrosEmprestado.remove(livro);
    }

    public void registrarDataDeDevolucao(Livro livro, LocalDate data){
        livrosEmprestado.replace(livro, data);
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public LocalDate getDataPrevista() {
        return dataPrevista;
    }

    private LocalDate calcularDataPrevista(){
        return this.dataEmprestimo.plusDays(15);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Emprestimo that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Emprestimo{" +
                "id=" + id +
                ", usuario=" + usuario +
                ", dataEmprestimo=" + dataEmprestimo +
                ", dataPrevista=" + dataPrevista +
                ", livrosEmprestado=" + livrosEmprestado +
                '}';
    }
}
