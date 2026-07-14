import entidades.Usuario;
import repositorios.RepositorioEmprestimo;
import repositorios.RepositorioLivro;
import repositorios.RepositorioUsuario;
import servicos.ServicoEmprestimo;
import servicos.ServicoLivro;
import servicos.ServicoUsuario;
import servicos.excecao.RecursoNaoEncontrado;

private static final String REGEX_EMAIL = "^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$";

void main() {

    Scanner sc = new Scanner(System.in);

    ServicoLivro servicoLivro = new ServicoLivro(new RepositorioLivro());
    ServicoUsuario servicoUsuario = new ServicoUsuario(new RepositorioUsuario());
    ServicoEmprestimo servicoEmprestimo = new ServicoEmprestimo(new RepositorioEmprestimo(), servicoUsuario, servicoLivro);

    CargaTeste.popularDados(servicoLivro, servicoUsuario, servicoEmprestimo);

    boolean rodando = true;
    int opcao = -1;

    while (rodando) {

        System.out.println("----------------  Livraria Nerdola  ----------------");
        System.out.println(" Digite a opção desejada:");
        System.out.println();
        System.out.println(" 1 - Usuários");
        System.out.println(" 2 - Livros");
        System.out.println(" 3 - Empréstimos");
        System.out.println(" 4 - Relatórios");
        System.out.println(" 5 - Comprovantes");
        System.out.println(" 0 - Sair");

        try {
            opcao = sc.nextInt();
        }
        catch (InputMismatchException e){
            System.out.println("Opção inválida, digite um número.");
            continue;
        }
        finally {
            sc.nextLine();
        }

        int opcaoInterna = -1;

        switch (opcao) {
            case 1:
                boolean subMenuUsuarios = true;
                while (subMenuUsuarios) {
                    System.out.println("============================== USUÁRIOS ==============================");
                    System.out.println(" 1 - Cadastrar usuário");
                    System.out.println(" 2 - Atualizar usuário");
                    System.out.println(" 3 - Remover usuário");
                    System.out.println(" 4 - Bloquear usuário");
                    System.out.println(" 5 - Desbloquear usuário");
                    System.out.println(" 6 - Buscar usuário por nome");
                    System.out.println(" 7 - Buscar usuário por id");
                    System.out.println(" 0 - Voltar");

                    try {
                        opcaoInterna = sc.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Opção inválida, digite um número.");
                        continue;
                    } finally {
                        sc.nextLine();
                    }

                    switch (opcaoInterna) {
                        case 1 -> cadastrarUsuario(sc, servicoUsuario);
                        case 2 -> atualizarUsuario(sc, servicoUsuario);
                        case 3 -> removerUsuario(sc, servicoUsuario, servicoEmprestimo);
                        case 4 -> bloquearUsuario(sc, servicoUsuario);
                        case 5 -> desbloquearUsuario(sc, servicoUsuario);
                        case 6 -> buscarUsuarioPorNome(sc, servicoUsuario);
                        case 7 -> buscarUsuarioPorId(sc, servicoUsuario);
                        case 0 -> subMenuUsuarios = false;
                        default -> System.out.println("Opção inválida, tente novamente!");
                    }
                }
                break;

            case 2:
                boolean subMenuLivros = true;
                while (subMenuLivros) {
                    System.out.println("=============================== LIVROS ===============================");
                    System.out.println(" 1 - Cadastrar livro");
                    System.out.println(" 2 - Atualizar livro");
                    System.out.println(" 3 - Remover livro");
                    System.out.println(" 4 - Buscar livro por título");
                    System.out.println(" 5 - Buscar livro por id");
                    System.out.println(" 0 - Voltar");

                    try {
                        opcaoInterna = sc.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Opção inválida, digite um número.");
                        continue;
                    } finally {
                        sc.nextLine();
                    }

                    switch (opcaoInterna) {
                        case 1 -> cadastrarLivro(sc, servicoLivro);
                        case 2 -> atualizarLivro(sc, servicoLivro);
                        case 3 -> removerLivro(sc, servicoLivro);
                        case 4 -> buscarLivroPorTitulo(sc, servicoLivro);
                        case 5 -> buscarLivroPorId(sc, servicoLivro);
                        case 0 -> subMenuLivros = false;
                        default -> System.out.println("Opção inválida, tente novamente!");
                    }
                }
                break;

            case 3:
                System.out.println("============================= EMPRÉSTIMOS =============================");
                System.out.println(" 1 - Emprestar livros");
                System.out.println(" 2 - Devolver livro");
                System.out.println(" 0 - Voltar");
                break;
            case 4:
                System.out.println("============================= RELATÓRIOS =============================");
                System.out.println(" 1 - Listar usuários liberados");
                System.out.println(" 2 - Listar usuários bloqueados");
                System.out.println(" 3 - Listar todos os usuários");
                System.out.println();
                System.out.println(" 4 - Listar livros disponíveis");
                System.out.println(" 5 - Listar livros emprestados");
                System.out.println(" 6 - Listar todos os livros");
                System.out.println();
                System.out.println(" 7 - Listar empréstimos ativos");
                System.out.println(" 0 - Voltar");
                break;
            case 5:
                System.out.println("============================ COMPROVANTES =============================");
                System.out.println(" 1 - Buscar histórico por usuário");
                System.out.println(" 2 - Buscar histórico por livro");
                System.out.println(" 0 - Voltar");
                break;
            case 0:
                rodando = false;
                System.out.println("Finalizando o sistema...");
                break;
            default:
                System.out.println("Opção inválida, tente novamente!");
                break;
        }
    }

    sc.close();
}

private static void cadastrarUsuario(Scanner sc, ServicoUsuario servicoUsuario){
    System.out.println("Digite os dados do usuário: ");
    System.out.print("Nome: ");
    String nome = sc.nextLine();

    Optional<String> emailOpt = lerEmailComValidacao(sc);
    if(emailOpt.isEmpty()){
        System.out.println("Cadastro cancelado.");
        return;
    }

    String email = emailOpt.get();

    try {
        servicoUsuario.cadastrar(new Usuario(nome, email));
        System.out.println("Usuário registrado com sucesso.");
    }
    catch (IllegalStateException e){
        System.out.println("Erro: " + e.getMessage());
    }
}

private static void atualizarUsuario(Scanner sc, ServicoUsuario servicoUsuario) {

    int idUsuario = lerIdComValidacao(sc, "Digite o id do usuário: ");

    System.out.print("Digite o nome que deseja: ");
    String nome = sc.nextLine();

    Optional<String> emailOpt = lerEmailComValidacao(sc);
    if(emailOpt.isEmpty()){
        System.out.println("Cadastro cancelado.");
        return;
    }

    String email = emailOpt.get();

    try {
        servicoUsuario.atualizarUsuario(idUsuario, new Usuario(nome, email));
        System.out.println("Usuário atualizado com sucesso.");
    }
    catch (RecursoNaoEncontrado e){
        System.out.println("Erro: " + e.getMessage());
    }
}

private static void removerUsuario(Scanner sc, ServicoUsuario servicoUsuario, ServicoEmprestimo servicoEmprestimo){

    int idUsuario = lerIdComValidacao(sc, "Digite o id do usuário: ");

    try {
        if (servicoEmprestimo.usuarioTemEmprestimoAtivo(idUsuario)) {
            System.out.println("Não é possível remover: usuário possui empréstimo ativo.");
        } else {
            servicoUsuario.removerPorId(idUsuario);
            System.out.println("Usuário removido com sucesso!");
        }
    } catch (RecursoNaoEncontrado e) {
        System.out.println("Erro: " + e.getMessage());
    }
}

private static void bloquearUsuario(Scanner sc, ServicoUsuario servicoUsuario){

    int idUsuario = lerIdComValidacao(sc, "Digite o id do usuário: ");

    try {
        servicoUsuario.bloquearUsuarioPorId(idUsuario);
        System.out.println("Usuário id " + idUsuario + " bloqueado com sucesso");
    }
    catch (IllegalStateException | RecursoNaoEncontrado e) {
        System.out.println("Erro: " + e.getMessage());
    }
}

private static void desbloquearUsuario(Scanner sc, ServicoUsuario servicoUsuario){

    int idUsuario = lerIdComValidacao(sc, "Digite o id do usuário: ");

    try {
        servicoUsuario.desbloquearUsuarioPorId(idUsuario);
        System.out.println("Usuário id " + idUsuario + " desbloqueado com sucesso");
    }
    catch (IllegalStateException | RecursoNaoEncontrado e) {
        System.out.println("Erro: " + e.getMessage());
    }
}

private static void buscarUsuarioPorNome(Scanner sc, ServicoUsuario servicoUsuario){
    System.out.print("Digite o nome do usuário: ");
    String nome = sc.nextLine();

    try {
        List<Usuario> usuarios = servicoUsuario.listarPorNome(nome);
        servicoUsuario.imprimirLista(usuarios);
    }
    catch (RecursoNaoEncontrado e){
        System.out.println("Erro: " + e.getMessage());
    }
}

private static void buscarUsuarioPorId(Scanner sc, ServicoUsuario servicoUsuario){

    int idUsuario = lerIdComValidacao(sc, "Digite o id do usuário: ");

    try {
        Usuario usuario = servicoUsuario.buscarPorId(idUsuario);
        servicoUsuario.imprimir(usuario);
    }
    catch (RecursoNaoEncontrado e){
        System.out.println("Erro: " + e.getMessage());
    }
}

private static int lerIdComValidacao (Scanner sc, String mensagem){
    while (true){
        System.out.print(mensagem);
        try {
            int id = sc.nextInt();
            sc.nextLine();
            return id;
        }
        catch (InputMismatchException e){
            System.out.println("Digite um número válido.");
            sc.nextLine();
        }
    }
}

private static Optional<String> lerEmailComValidacao(Scanner sc) {
    while (true) {
        System.out.print("Digite o e-mail: ");
        String email = sc.nextLine();

        if (emailValido(email)) {
            return Optional.of(email);
        }

        System.out.println("E-mail inválido!");
        System.out.print("Deseja tentar novamente? (s/n): ");
        if (sc.nextLine().equalsIgnoreCase("n")) {
            return Optional.empty();
        }
    }
}

private static boolean emailValido (String email){
    return email.matches(REGEX_EMAIL);
}