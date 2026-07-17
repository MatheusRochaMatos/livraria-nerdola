import entidades.Emprestimo;
import entidades.Livro;
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
    int opcao;

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
        } catch (InputMismatchException e) {
            System.out.println("Opção inválida, digite um número.");
            continue;
        } finally {
            sc.nextLine();
        }

        int opcaoInterna;

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
                        case 3 -> removerLivro(sc, servicoLivro, servicoEmprestimo);
                        case 4 -> buscarLivroPorTitulo(sc, servicoLivro);
                        case 5 -> buscarLivroPorId(sc, servicoLivro);
                        case 0 -> subMenuLivros = false;
                        default -> System.out.println("Opção inválida, tente novamente!");
                    }
                }
                break;

            case 3:
                boolean subMenuEmprestimo = true;
                while (subMenuEmprestimo) {
                    System.out.println("============================= EMPRÉSTIMOS =============================");
                    System.out.println(" 1 - Emprestar livros");
                    System.out.println(" 2 - Devolver livro");
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
                        case 1 -> emprestarLivro(sc, servicoEmprestimo, servicoLivro, servicoUsuario);
                        case 2 -> devolverLivro(sc, servicoEmprestimo);
                        case 0 -> subMenuEmprestimo = false;
                        default -> System.out.println("Opção inválida, tente novamente!");
                    }
                }
                break;
            case 4:
                boolean subMenuRelatorio = true;
                while (subMenuRelatorio) {
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

                    try {
                        opcaoInterna = sc.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Opção inválida, digite um número.");
                        continue;
                    } finally {
                        sc.nextLine();
                    }

                    switch (opcaoInterna){
                        case 1 -> usuariosLiberados(servicoUsuario);
                        case 2 -> usuariosBloqueados(servicoUsuario);
                        case 3 -> todosOsUsuarios(servicoUsuario);
                        case 4 -> livrosDisponiveis(servicoLivro);
                        case 5 -> livrosEmprestados(servicoLivro);
                        case 6 -> todosOsLivros(servicoLivro);
                        case 7 -> emprestimosAtivos(servicoEmprestimo);
                        case 0 -> subMenuRelatorio = false;
                        default -> System.out.println("Opção inválida, tente novamente!");
                    }
                }
                break;
            case 5:
                boolean subMenuComprovantes = true;
                while (subMenuComprovantes) {
                    System.out.println("============================ COMPROVANTES =============================");
                    System.out.println(" 1 - Buscar histórico por usuário");
                    System.out.println(" 2 - Buscar histórico por livro");
                    System.out.println(" 0 - Voltar");

                    try {
                        opcaoInterna = sc.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Opção inválida, digite um número.");
                        continue;
                    } finally {
                        sc.nextLine();
                    }

                    switch (opcaoInterna){
                        case 1 -> historicoPorUsuario(sc, servicoEmprestimo);
                        case 2 -> historicoPorLivro(sc, servicoEmprestimo);
                        case 0 -> subMenuComprovantes = false;
                        default -> System.out.println("Opção inválida, tente novamente!");
                    }
                }
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

private static void cadastrarUsuario(Scanner sc, ServicoUsuario servicoUsuario) {
    System.out.println("Digite os dados do usuário: ");
    System.out.print("Nome: ");
    String nome = sc.nextLine();

    Optional<String> emailOpt = lerEmailComValidacao(sc);
    if (emailOpt.isEmpty()) {
        System.out.println("Cadastro cancelado.");
        return;
    }

    String email = emailOpt.get();

    try {
        servicoUsuario.cadastrar(new Usuario(nome, email));
        System.out.println("Usuário registrado com sucesso.");
    } catch (IllegalStateException e) {
        System.out.println("Erro: " + e.getMessage());
    }
}

private static void atualizarUsuario(Scanner sc, ServicoUsuario servicoUsuario) {

    long idUsuario = lerNumerosComValidacao(sc, "Digite o id do usuário (0 para cancelar): ");

    if(cancelarOperacao(idUsuario)) return;

    System.out.println("Usuário a ser atualizado:");

    try {
        Usuario usuarioAtual = servicoUsuario.buscarPorId(idUsuario);
        servicoUsuario.imprimir(usuarioAtual);
    } catch (RecursoNaoEncontrado e) {
        System.out.println("Erro: " + e.getMessage());
        return;
    }

    System.out.print("Digite o nome que deseja: ");
    String nome = sc.nextLine();

    Optional<String> emailOpt = lerEmailComValidacao(sc);
    if (emailOpt.isEmpty()) {
        System.out.println("Atualização cancelada.");
        return;
    }

    String email = emailOpt.get();

    try {
        servicoUsuario.atualizarUsuario(idUsuario, new Usuario(nome, email));
        System.out.println("Usuário atualizado com sucesso.");
    } catch (RecursoNaoEncontrado e) {
        System.out.println("Erro: " + e.getMessage());
    }
}

private static void removerUsuario(Scanner sc, ServicoUsuario servicoUsuario, ServicoEmprestimo servicoEmprestimo) {

    long idUsuario = lerNumerosComValidacao(sc, "Digite o id do usuário (0 para cancelar): ");

    if(cancelarOperacao(idUsuario)) return;

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

private static void bloquearUsuario(Scanner sc, ServicoUsuario servicoUsuario) {

    long idUsuario = lerNumerosComValidacao(sc, "Digite o id do usuário (0 para cancelar): ");

    if(cancelarOperacao(idUsuario)) return;

    try {
        servicoUsuario.bloquearUsuarioPorId(idUsuario);
        System.out.println("Usuário id " + idUsuario + " bloqueado com sucesso");
    } catch (IllegalStateException | RecursoNaoEncontrado e) {
        System.out.println("Erro: " + e.getMessage());
    }
}

private static void desbloquearUsuario(Scanner sc, ServicoUsuario servicoUsuario) {

    long idUsuario = lerNumerosComValidacao(sc, "Digite o id do usuário (0 para cancelar): ");

    if(cancelarOperacao(idUsuario)) return;

    try {
        servicoUsuario.desbloquearUsuarioPorId(idUsuario);
        System.out.println("Usuário id " + idUsuario + " desbloqueado com sucesso");
    } catch (IllegalStateException | RecursoNaoEncontrado e) {
        System.out.println("Erro: " + e.getMessage());
    }
}

private static void buscarUsuarioPorNome(Scanner sc, ServicoUsuario servicoUsuario) {
    System.out.print("Digite o nome do usuário: ");
    String nome = sc.nextLine();

    try {
        List<Usuario> usuarios = servicoUsuario.listarPorNome(nome);
        servicoUsuario.imprimirLista(usuarios);
    } catch (RecursoNaoEncontrado e) {
        System.out.println("Erro: " + e.getMessage());
    }
}

private static void buscarUsuarioPorId(Scanner sc, ServicoUsuario servicoUsuario) {
    long idUsuario = lerNumerosComValidacao(sc, "Digite o id do usuário (0 para cancelar): ");
    if(cancelarOperacao(idUsuario)) return;

    try {
        Usuario usuario = servicoUsuario.buscarPorId(idUsuario);
        servicoUsuario.imprimir(usuario);
    } catch (RecursoNaoEncontrado e) {
        System.out.println("Erro: " + e.getMessage());
    }
}

private static void cadastrarLivro(Scanner sc, ServicoLivro servicoLivro) {
    System.out.println("Digite os dados do livro: ");
    System.out.print("Título: ");
    String titulo = sc.nextLine();
    System.out.print("Autor: ");
    String autor = sc.nextLine();

    int anoAtual = LocalDate.now().getYear();
    long anoValidado = lerNumerosComValidacao(sc, "Ano (yyyy): ");

    while (anoValidado < 1000 || anoValidado > anoAtual) {
        System.out.println("Ano digitado está incorreto!");
        anoValidado = lerNumerosComValidacao(sc, "Ano (yyyy): ");
    }

    short ano = (short) anoValidado;

    servicoLivro.cadastrar(new Livro(titulo, autor, ano));
}

private static void atualizarLivro(Scanner sc, ServicoLivro servicoLivro) {
    long idLivro = lerNumerosComValidacao(sc, "Digite o id do livro (0 para cancelar): ");
    if(cancelarOperacao(idLivro)) return;

    System.out.println("Livro a ser atualizado:");

    try {
        Livro livroAtual = servicoLivro.buscarPorId(idLivro);
        servicoLivro.imprimir(livroAtual);
    } catch (RecursoNaoEncontrado e) {
        System.out.println("Erro: " + e.getMessage());
        return;
    }

    System.out.print("Digite o título desejado: ");
    String titulo = sc.nextLine();
    System.out.print("Digite o autor do livro: ");
    String autor = sc.nextLine();

    int anoAtual = LocalDate.now().getYear();
    long anoValidado = lerNumerosComValidacao(sc, "Digite o ano do livro (yyyy): ");

    while (anoValidado < 1000 || anoValidado > anoAtual) {
        System.out.println("Ano digitado está incorreto!");
        anoValidado = lerNumerosComValidacao(sc, "Digite o ano do livro (yyyy): ");
    }

    short ano = (short) anoValidado;

    try {
        servicoLivro.atualizarLivro(idLivro, new Livro(titulo, autor, ano));
        System.out.println("Livro atualizado com sucesso.");
    } catch (RecursoNaoEncontrado e) {
        System.out.println("Erro: " + e.getMessage());
    }
}

private static void removerLivro(Scanner sc, ServicoLivro servicoLivro, ServicoEmprestimo servicoEmprestimo) {
    long idLivro = lerNumerosComValidacao(sc, "Digite o id do livro (0 para cancelar): ");
    if(cancelarOperacao(idLivro)) return;

    try {
        if (servicoEmprestimo.livroTemEmprestimoAtivo(idLivro)) {
            System.out.println("Não é possível remover: livro está emprestado.");
        } else {
            servicoLivro.removerPorId(idLivro);
            System.out.println("Livro removido com sucesso!");
        }
    } catch (RecursoNaoEncontrado e) {
        System.out.println("Erro: " + e.getMessage());
    }
}

private static void buscarLivroPorTitulo(Scanner sc, ServicoLivro servicoLivro) {
    System.out.print("Digite o título do livro: ");
    String titulo = sc.nextLine();

    try {
        List<Livro> livros = servicoLivro.listarPorTitulo(titulo);
        servicoLivro.imprimirLista(livros);
    } catch (RecursoNaoEncontrado e) {
        System.out.println("Erro: " + e.getMessage());
    }
}

private static void buscarLivroPorId(Scanner sc, ServicoLivro servicoLivro) {
    long idLivro = lerNumerosComValidacao(sc, "Digite o id do livro (0 para cancelar): ");
    if(cancelarOperacao(idLivro)) return;

    try {
        Livro livro = servicoLivro.buscarPorId(idLivro);
        servicoLivro.imprimir(livro);
    } catch (RecursoNaoEncontrado e) {
        System.out.println("Erro: " + e.getMessage());
    }
}

private static void emprestarLivro(Scanner sc, ServicoEmprestimo servicoEmprestimo, ServicoLivro servicoLivro, ServicoUsuario servicoUsuario) {
    long idUsuario = lerNumerosComValidacao(sc, "Digite o id do usuário (0 para cancelar): ");
    if(cancelarOperacao(idUsuario)) return;

    Usuario usuario;
    try {
        usuario = servicoUsuario.buscarPorId(idUsuario);
    } catch (RecursoNaoEncontrado e) {
        System.out.println("Erro: " + e.getMessage());
        return;
    }

    boolean usuarioLiberado = servicoUsuario.verificarSituacaoDoUsuario(usuario);
    if (!usuarioLiberado){
        System.out.println("Usuário bloqueado! Empréstimo cancelado.");
        return;
    }

    boolean usuarioTemEmprestimo = servicoEmprestimo.usuarioTemEmprestimoAtivo(idUsuario);
    if (usuarioTemEmprestimo){
        System.out.println("Usuário possui empréstimos pendentes! Operação cancelada.");
        return;
    }

    Set<Livro> livros = new HashSet<>();
    while (true) {
        long idLivro = lerNumerosComValidacao(sc, "Digite o id do livro (0 para cancelar o empréstimo inteiro): ");
        if(cancelarOperacao(idLivro)) return;

        Livro livro;
        try {
            livro = servicoLivro.buscarPorId(idLivro);
        } catch (RecursoNaoEncontrado e) {
            System.out.println("Erro: " + e.getMessage());
            continue;
        }

        boolean livroDisponivel = servicoLivro.verificarSituacaoDoLivro(livro);
        if (!livroDisponivel){
            System.out.println("Livro indisponível! Escolha outro livro.");
            continue;
        }

        if(livros.contains(livro)){
            System.out.println("Livro com id repetido! Não contabilizado.");
            continue;
        }
        livros.add(livro);

        if (livros.size() < 3) {
            System.out.print("Deseja registrar + 1 livro ao emprestimo? (s/n) ");
            if (sc.nextLine().equalsIgnoreCase("n")) {
                break;
            }
        } else {
            System.out.println("Limite máximo de 3 livros atingido! Prosseguindo com o emprestimo...");
            break;
        }
    }

    try {
        Map<Livro, LocalDate> livrosEmprestados = servicoEmprestimo.criarMapDeLivros(livros);
        servicoEmprestimo.salvarEmprestimo(new Emprestimo(usuario, livrosEmprestados));
    } catch (IllegalArgumentException | IllegalStateException e) {
        System.out.println("Erro: " + e.getMessage());
    }
}

private static void devolverLivro(Scanner sc, ServicoEmprestimo servicoEmprestimo) {
    while (true) {
        long idLivro = lerNumerosComValidacao(sc, "Digite o id do livro (0 para cancelar): ");
        if(cancelarOperacao(idLivro)) return;

        try {
            servicoEmprestimo.devolverLivroId(idLivro);
            System.out.println("Livro devolvido com sucesso!");

            System.out.println();

            System.out.print("Deseja devolver outro livro? (s/n) ");
            String op = sc.nextLine();

            while (!op.equalsIgnoreCase("n") && !op.equalsIgnoreCase("s")) {
                System.out.print("Opção inválida! Escolha uma opção válida (s/n): ");
                op = sc.nextLine();
            }

            if (op.equalsIgnoreCase("n")) {
                break;
            }

        } catch (RecursoNaoEncontrado e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}

private static void usuariosLiberados(ServicoUsuario servicoUsuario){
    try {
        List<Usuario> usuariosLiberados = servicoUsuario.listarLiberados();
        servicoUsuario.imprimirLista(usuariosLiberados);
    }
    catch (RecursoNaoEncontrado e){
        System.out.println("Erro: " + e.getMessage());
    }
}

private static void usuariosBloqueados(ServicoUsuario servicoUsuario){
    try {
        List<Usuario> usuariosBloqueados = servicoUsuario.listarBloqueados();
        servicoUsuario.imprimirLista(usuariosBloqueados);
    }
    catch (RecursoNaoEncontrado e){
        System.out.println("Erro: " + e.getMessage());
    }
}

private static void todosOsUsuarios(ServicoUsuario servicoUsuario){
    try {
        List<Usuario> todosOsUsuarios = servicoUsuario.listar();
        servicoUsuario.imprimirLista(todosOsUsuarios);
    }
    catch (RecursoNaoEncontrado e){
        System.out.println("Erro: " + e.getMessage());
    }
}

private static void livrosDisponiveis(ServicoLivro servicoLivro){
    try {
        List<Livro> livrosDisponiveis = servicoLivro.listarDisponiveis();
        servicoLivro.imprimirLista(livrosDisponiveis);
    }
    catch (RecursoNaoEncontrado e){
        System.out.println("Erro: " + e.getMessage());
    }
}

private static void livrosEmprestados (ServicoLivro servicoLivro) {
    try {
        List<Livro> livrosEmprestado = servicoLivro.listarEmprestados();
        servicoLivro.imprimirLista(livrosEmprestado);
    } catch (RecursoNaoEncontrado e) {
        System.out.println("Erro: " + e.getMessage());
    }
}

private static void todosOsLivros (ServicoLivro servicoLivro) {
    try {
        List<Livro> todosOsLivros = servicoLivro.listar();
        servicoLivro.imprimirLista(todosOsLivros);
    } catch (RecursoNaoEncontrado e) {
        System.out.println("Erro: " + e.getMessage());
    }
}

private static void emprestimosAtivos (ServicoEmprestimo servicoEmprestimo) {
    try {
        List<Emprestimo> emprestimosAtivos = servicoEmprestimo.listar();
        servicoEmprestimo.imprimirLista(emprestimosAtivos);
    } catch (RecursoNaoEncontrado e) {
        System.out.println("Erro: " + e.getMessage());
    }
}

private static void historicoPorUsuario (Scanner sc, ServicoEmprestimo servicoEmprestimo) {
    long idUsuario = lerNumerosComValidacao(sc, "Digite o id do usuário (0 para cancelar): ");
    if(cancelarOperacao(idUsuario)) return;

    try {
        List<Emprestimo> emprestimosUsuario = servicoEmprestimo.historicoUsuario(idUsuario);
        servicoEmprestimo.imprimirLista(emprestimosUsuario);
    } catch (RecursoNaoEncontrado e) {
        System.out.println("Erro: " + e.getMessage());
    }
}

private static void historicoPorLivro (Scanner sc, ServicoEmprestimo servicoEmprestimo) {
    long idLivro = lerNumerosComValidacao(sc, "Digite o id do livro (0 para cancelar): ");
    if(cancelarOperacao(idLivro)) return;

    try {
        List<Emprestimo> emprestimosLivro = servicoEmprestimo.historicoLivro(idLivro);
        servicoEmprestimo.imprimirLista(emprestimosLivro);
    } catch (RecursoNaoEncontrado e) {
        System.out.println("Erro: " + e.getMessage());
    }
}

private static long lerNumerosComValidacao(Scanner sc, String mensagem) {
    while (true) {
        System.out.print(mensagem);
        try {
            long id = sc.nextLong();
            sc.nextLine();
            return id;
        } catch (InputMismatchException e) {
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

private static boolean cancelarOperacao (long numero){
    if (numero == 0) {
        System.out.println("Operação cancelada.");
        return true;
    }
    else {
        return false;
    }
}

private static boolean emailValido(String email) {
    return email.matches(REGEX_EMAIL);
}