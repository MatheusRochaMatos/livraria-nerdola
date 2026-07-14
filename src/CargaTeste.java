import entidades.Emprestimo;
import entidades.Livro;
import entidades.Usuario;
import servicos.ServicoEmprestimo;
import servicos.ServicoLivro;
import servicos.ServicoUsuario;

import java.time.LocalDate;
import java.util.*;

public class CargaTeste {

    public static void popularDados(ServicoLivro servicoLivro, ServicoUsuario servicoUsuario, ServicoEmprestimo servicoEmprestimo) {

        // ===================== LIVROS =====================
        Livro livroHP1 = new Livro("Harry Potter e a Pedra Filosofal", "J.K. Rowling", (short) 1997);
        Livro livroHP2 = new Livro("Harry Potter e a Câmara Secreta", "J.K. Rowling", (short) 1998);
        Livro livroHP3 = new Livro("Harry Potter e o Prisioneiro de Azkaban", "J.K. Rowling", (short) 1999);
        Livro livroHP4 = new Livro("Harry Potter e o Cálice de Fogo", "J.K. Rowling", (short) 2000);
        Livro livroHP5 = new Livro("Harry Potter e a Ordem da Fênix", "J.K. Rowling", (short) 2003);
        Livro livroHP6 = new Livro("Harry Potter e o Enigma do Príncipe", "J.K. Rowling", (short) 2005);
        Livro livroHP7 = new Livro("Harry Potter e as Relíquias da Morte", "J.K. Rowling", (short) 2007);

        Livro livroSW1 = new Livro("Star Wars: Uma Nova Esperança", "George Lucas", (short) 1976);
        Livro livroSW2 = new Livro("Star Wars: O Império Contra-Ataca", "Donald F. Glut", (short) 1980);
        Livro livroSW3 = new Livro("Star Wars: O Retorno de Jedi", "James Kahn", (short) 1983);
        Livro livroSW4 = new Livro("Star Wars: Heir to the Empire", "Timothy Zahn", (short) 1991);
        Livro livroSW5 = new Livro("Star Wars: Dark Disciple", "Christie Golden", (short) 2015);
        Livro livroSW6 = new Livro("Star Wars: Thrawn", "Timothy Zahn", (short) 2017);

        Livro livroSrA1 = new Livro("O Senhor dos Anéis: A Sociedade do Anel", "J.R.R. Tolkien", (short) 1954);
        Livro livroSrA2 = new Livro("O Senhor dos Anéis: As Duas Torres", "J.R.R. Tolkien", (short) 1954);
        Livro livroSrA3 = new Livro("O Senhor dos Anéis: O Retorno do Rei", "J.R.R. Tolkien", (short) 1955);

        Livro livroHobbit1 = new Livro("O Hobbit", "J.R.R. Tolkien", (short) 1937);

        Livro livroDuna = new Livro("Duna", "Frank Herbert", (short) 1965);
        Livro livroFundacao = new Livro("Fundação", "Isaac Asimov", (short) 1951);
        Livro llivroNeuromancer = new Livro("Neuromancer", "William Gibson", (short) 1984);
        Livro livroGuiaMochileiro = new Livro("O Guia do Mochileiro das Galáxias", "Douglas Adams", (short) 1979);
        Livro livroEndersGame = new Livro("Ender's Game: O Jogo do Exterminador", "Orson Scott Card", (short) 1985);
        Livro livroWatchmen = new Livro("Watchmen", "Alan Moore", (short) 1986);
        Livro livroVDeVinganca = new Livro("V de Vingança", "Alan Moore", (short) 1988);
        Livro livroPiadaMortal = new Livro("Batman: A Piada Mortal", "Alan Moore", (short) 1988);
        Livro livroGuerraInfinita = new Livro("A Guerra Infinita", "Jim Starlin", (short) 1991);
        Livro livroPercyJackson = new Livro("Percy Jackson e o Ladrão de Raios", "Rick Riordan", (short) 2005);
        Livro livroHomemAranha = new Livro("Homem-Aranha: De Volta ao Lar", "Stan Lee", (short) 1962);
        Livro livroRodaDoTempo = new Livro("A Roda do Tempo: O Olho do Mundo", "Robert Jordan", (short) 1990);
        Livro livroNarnia = new Livro("As Crônicas de Nárnia: O Leão, a Feiticeira e o Guarda-Roupa", "C.S. Lewis", (short) 1950);

        servicoLivro.cadastrarVarios(List.of(
                livroHP1, livroHP2, livroHP3, livroHP4, livroHP5, livroHP6, livroHP7, livroSW1, livroSW2, livroSW3,
                livroSW4, livroSW5, livroSW6, livroSrA1, livroSrA2, livroSrA3, livroHobbit1, livroDuna, livroFundacao, llivroNeuromancer,
                livroGuiaMochileiro, livroEndersGame, livroWatchmen, livroVDeVinganca, livroPiadaMortal, livroGuerraInfinita, livroPercyJackson, livroHomemAranha, livroRodaDoTempo, livroNarnia
        ));

        // ===================== USUÁRIOS =====================
        List<Usuario> usuarios = new ArrayList<>(List.of(
                new Usuario("João Paulo", "joaopaulo@gmail.com"),
                new Usuario("Pedro Luiz", "pedrin@gmail.com"),
                new Usuario("Camila Lima", "cami@gmail.com"),
                new Usuario("Andreza Soarez", "andreza@gmail.com"),
                new Usuario("Beatriz Soarez", "bia@gmail.com"),
                new Usuario("Jaqueline Souza", "jaque@gmail.com"),
                new Usuario("Marcelo da Silva", "marcelo@gmail.com"),
                new Usuario("Bruno Rodrigues", "bruno@gmail.com"),
                new Usuario("Cassio Torres", "cassio@gmail.com"),
                new Usuario("Fernanda Alves", "fernanda@gmail.com"),
                new Usuario("Lucas Martins", "lucas@gmail.com"),
                new Usuario("Renata Costa", "renata@gmail.com"),
                new Usuario("Vinícius Pereira", "vini@gmail.com"),
                new Usuario("Larissa Fernandes", "larissa@gmail.com"),
                new Usuario("Gustavo Henrique", "guga@gmail.com"),
                new Usuario("Pedro da Silva", "pedro@gmail.com"),
                new Usuario("Rodrigo da Silva", "rodrigo@gmail.com")
        ));

        usuarios.forEach(usuario -> {
            try {
                servicoUsuario.cadastrar(usuario);
            } catch (IllegalStateException e) {
                System.out.println("Erro ao cadastrar o usuário: "
                        + usuario.getNome() + ": " + e.getMessage());
            }
        });

// ===================== EMPRÉSTIMOS =====================
        Map<Livro, LocalDate> livrosEmp1 = new HashMap<>();
        livrosEmp1.put(livroHP1, null);
        livrosEmp1.put(livroHP2, null);
        livrosEmp1.put(livroHP3, null);

        Map<Livro, LocalDate> livrosEmp2 = new HashMap<>();
        livrosEmp2.put(livroSW1, null);
        livrosEmp2.put(livroSW2, null);

        Map<Livro, LocalDate> livrosEmp3 = new HashMap<>();
        livrosEmp3.put(livroSrA1, null);
        livrosEmp3.put(livroHobbit1, null);

        Map<Livro, LocalDate> livrosEmp4 = new HashMap<>();
        livrosEmp4.put(livroHP4, LocalDate.parse("2025-11-16"));
        livrosEmp4.put(livroHP5, LocalDate.parse("2025-11-12"));
        livrosEmp4.put(livroHP6, LocalDate.parse("2025-11-07"));

        Map<Livro, LocalDate> livrosEmp5 = new HashMap<>();
        livrosEmp5.put(livroSW3, LocalDate.parse("2025-09-09"));
        livrosEmp5.put(livroSW4, LocalDate.parse("2025-09-19"));

        Emprestimo e1 = new Emprestimo(servicoUsuario.buscarPorId(9), livrosEmp5,
                LocalDate.of(2025, 9, 5),
                LocalDate.of(2025, 9, 20));

        Emprestimo e2 = new Emprestimo(servicoUsuario.buscarPorId(7), livrosEmp4,
                LocalDate.of(2025, 10, 28),
                LocalDate.of(2025, 11, 15));

        Emprestimo e3 = new Emprestimo(servicoUsuario.buscarPorId(1), livrosEmp1,
                LocalDate.of(2026, 5, 27),
                LocalDate.of(2026, 6, 7));

        Emprestimo e4 = new Emprestimo(servicoUsuario.buscarPorId(3), livrosEmp2,
                LocalDate.of(2026, 5, 27),
                LocalDate.of(2026, 6, 7));

        Emprestimo e5 = new Emprestimo(servicoUsuario.buscarPorId(5), livrosEmp3,
                LocalDate.of(2026, 6, 1),
                LocalDate.of(2026, 6, 15));


        servicoEmprestimo.salvarEmprestimo(e1);
        servicoEmprestimo.salvarEmprestimo(e2);

        servicoEmprestimo.devolverLivroId(10);
        servicoEmprestimo.devolverLivroId(11);

        servicoEmprestimo.devolverLivroId(4);
        servicoEmprestimo.devolverLivroId(5);
        servicoEmprestimo.devolverLivroId(6);

        /*
        * Como estou simulando a devolução, o metodo devolve o livro com um LocalDate.now
        *       abaixo insiro novamente as datas de devolução (simulada) no histórico.
         */

        servicoEmprestimo.atualizarDataDeDevolucaoNoHistorico(e1, livroSW3, LocalDate.parse("2025-09-09"));
        servicoEmprestimo.atualizarDataDeDevolucaoNoHistorico(e1, livroSW4, LocalDate.parse("2025-09-19"));
        servicoEmprestimo.atualizarDataDeDevolucaoNoHistorico(e2, livroHP4, LocalDate.parse("2025-11-16"));
        servicoEmprestimo.atualizarDataDeDevolucaoNoHistorico(e2, livroHP5, LocalDate.parse("2025-11-12"));
        servicoEmprestimo.atualizarDataDeDevolucaoNoHistorico(e2, livroHP6, LocalDate.parse("2025-11-07"));


        servicoEmprestimo.salvarEmprestimo(e3);
        servicoEmprestimo.salvarEmprestimo(e4);
        servicoEmprestimo.salvarEmprestimo(e5);
    }
}
