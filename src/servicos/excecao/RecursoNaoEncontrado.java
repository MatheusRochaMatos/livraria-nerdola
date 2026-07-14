package servicos.excecao;

public class RecursoNaoEncontrado extends RuntimeException{

    public RecursoNaoEncontrado (String msg){
        super(msg);
    }
}
