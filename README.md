# 📚 Livraria Nerdola

Sistema de gerenciamento de biblioteca desenvolvido em Java, com foco em aplicar boas práticas de Programação Orientada a Objetos, arquitetura em camadas e tratamento robusto de erros — construído para consolidar aprendizado prático, não como exercício de curso.

## 🎯 Sobre o projeto

O sistema permite gerenciar usuários, livros e empréstimos através de um menu interativo via terminal. Ele nasceu como um exercício pessoal de aprofundamento em Java, e ao longo do desenvolvimento passou por diversas refatorações conscientes — a mais significativa foi a migração da estrutura de armazenamento de livros emprestados de um array fixo para um `Map<Livro, LocalDate>`, permitindo rastrear a data de devolução de cada livro individualmente dentro de um mesmo empréstimo.

## ⚙️ Funcionalidades

- **Usuários**: cadastro, atualização, remoção, bloqueio/desbloqueio, busca por nome ou id
- **Livros**: cadastro, atualização, remoção, busca por título ou id
- **Empréstimos**: empréstimo de até 3 livros por vez, devolução individual por livro, validação de disponibilidade e situação do usuário
- **Relatórios**: listagem de usuários (liberados/bloqueados), livros (disponíveis/emprestados), empréstimos ativos
- **Comprovantes**: histórico de empréstimos por usuário ou por livro

## 🏗️ Arquitetura

O projeto segue uma arquitetura em camadas:

- **`entidades`**: `Usuario`, `Livro`, `Emprestimo` — modelos de domínio com encapsulamento (coleções internas expostas apenas como somente-leitura; mutação controlada por métodos próprios)
- **`repositorios`**: `Repositorio<T>` genérico (CRUD base) + repositórios específicos com buscas customizadas
- **`servicos`**: regras de negócio, validações e tratamento de exceções, isolados da camada de dados
- **`Main`**: orquestração do menu, sem lógica de negócio

## 🧠 Decisões técnicas que valem destaque

- Uso de `Optional<T>` para representar ausência de valor em buscas, evitando `null` espalhado pelo código
- Exceção customizada (`RecursoNaoEncontrado`) para erros de domínio, separada de exceções técnicas do Java
- Coleções devolvidas por getters são protegidas contra alteração externa (`Collections.unmodifiableMap`, `List.copyOf`), com mutação controlada por métodos dedicados nas próprias entidades
- Encadeamento de construtores (`this(...)`) para evitar duplicação de lógica entre diferentes formas de criar um `Emprestimo`
- Uso de Streams API para filtros, buscas e transformações de coleções

## 🚧 Aprendizados

Durante o desenvolvimento, contei com apoio de uma IA (Claude, da Anthropic) para tirar dúvidas conceituais, revisar decisões de design e identificar bugs sutis (como um problema de leitura no `Scanner` que causava loop infinito, e uma inconsistência na atualização de valores em um `Map`). Isso me ajudou a entender profundamente o "porquê" por trás de boas práticas — não só copiar soluções, mas entender o raciocínio.

## 🛠️ Tecnologias

- Java 21+ (uso de recursos modernos como pattern matching em `instanceof`, `switch` com setas)
- Streams API
- Sem dependências externas — projeto 100% Java puro

## ▶️ Como executar

```bash
git clone https://github.com/MatheusRochaMatos/livraria-nerdola.git
```

Abra o projeto no IntelliJ IDEA (ou sua IDE de preferência) e execute a classe `Main`. O sistema já vem populado com dados de teste (`CargaTeste`) para facilitar a exploração das funcionalidades.
