# Exercício: Paginação com Java Streams

## Contexto

Você foi contratado para desenvolver o backend de um sistema de catálogo de livros. O sistema já possui um repositório que retorna listas de livros, mas a interface precisa exibir os resultados em **páginas** — como acontece em qualquer e-commerce ou buscador.

Sua tarefa é implementar a classe `Paginador<T>`, que encapsula a lógica de paginação usando **Java Streams**, com ênfase nos operadores `skip` e `limit`.

---

## Estrutura fornecida

```java
package com.paginacao;

public class Livro {
    private int id;
    private String titulo;
    private String autor;
    private boolean disponivel;

    public Livro(int id, String titulo, String autor, boolean disponivel) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.disponivel = disponivel;
    }

    public int getId()           { return id; }
    public String getTitulo()    { return titulo; }
    public String getAutor()     { return autor; }
    public boolean isDisponivel(){ return disponivel; }

    @Override
    public String toString() {
        return "[" + id + "] " + titulo + " - " + autor;
    }
}
```

```java
package com.paginacao;

import java.util.List;
import java.util.stream.Collectors;

public class Paginador<T> {
    public List<T> paginar(List<T> lista, int pagina, int tamanhoPagina) {
        throw new UnsupportedOperationException("Não implementado");
        return lista.stream()
            .skip(pagina * tamanhoPagina)
            .limit(tamanhoPagina)
            .collect(Collectors.toList());
    }

    public List<T> primeirosN(List<T> lista, int n) {
        throw new UnsupportedOperationException("Não implementado");
        return lista.stream()
            .limit(n)
            .collect(Collectors.toList());
    }

    public List<T> ignorarN(List<T> lista, int n) {
        throw new UnsupportedOperationException("Não implementado");
        return lista.stream()
            .skip(n)
            .collect(Collectors.toList());
    }

    public int totalPaginas(List<T> lista, int tamanhoPagina) {
        throw new UnsupportedOperationException("Não implementado");
        return (lista.size() + tamanhoPagina - 1) / tamanhoPagina;
    }
}
```

```java
package com.paginacao;

import java.util.List;
import java.util.stream.Collectors;

public class CatalogoBusca {
    public List<Livro> buscarComPaginacao(List<Livro> livros, String termo,
                                          int pagina, int tamanhoPagina) {
        throw new UnsupportedOperationException("Não implementado");
        return livros.stream()
            .filter(livro -> livro.getTitulo().toLowerCase().contains(termo.toLowerCase()))
            .skip(pagina * tamanhoPagina)
            .limit(tamanhoPagina)
            .collect(Collectors.toList());
    }
}
```

---

## Dicas conceituais

| Operação | O que faz | Analogia |
|---|---|---|
| `stream.limit(n)` | Mantém no máximo os primeiros **n** elementos | "Me dê apenas os primeiros n itens" |
| `stream.skip(n)` | Descarta os primeiros **n** elementos | "Pule os primeiros n itens" |
| `skip` + `limit` | Combinados, selecionam uma **janela** da lista | Paginação |

**Fórmula mental para paginação:**
> Para exibir a página `P` com `T` itens por página, você deve pular `P × T` elementos e então pegar `T`.

---

## Validador

Use as tabelas abaixo para conferir suas implementações. Considere sempre a seguinte lista base de 10 livros (índices 0 a 9):

```
[0] Algoritmos - Cormen
[1] Clean Code - Martin
[2] Java Efetivo - Bloch
[3] O Programador Pragmático - Hunt
[4] Design Patterns - GoF
[5] Java: como programar - Deitel
[6] Entendendo Algoritmos - Bhargava
[7] Código Limpo em Java - Silva
[8] Estrutura de Dados - Preiss
[9] Introdução à Computação - Brookshear
```

### Exercício 1 — `paginar`

| pagina | tamanhoPagina | Resultado esperado |
|--------|--------------|-------------------|
| 0 | 3 | [0] Algoritmos, [1] Clean Code, [2] Java Efetivo |
| 1 | 3 | [3] O Programador Pragmático, [4] Design Patterns, [5] Java: como programar |
| 2 | 3 | [6] Entendendo Algoritmos, [7] Código Limpo em Java, [8] Estrutura de Dados |
| 3 | 3 | [9] Introdução à Computação |
| 4 | 3 | *(lista vazia)* |
| 0 | 5 | [0] ao [4] |
| 1 | 5 | [5] ao [9] |

### Exercício 2 — `primeirosN`

| n | Resultado esperado |
|---|---|
| 3 | [0] Algoritmos, [1] Clean Code, [2] Java Efetivo |
| 0 | *(lista vazia)* |
| 20 | todos os 10 livros |

### Exercício 3 — `ignorarN`

| n | Resultado esperado |
|---|---|
| 7 | [7] Código Limpo em Java, [8] Estrutura de Dados, [9] Introdução à Computação |
| 0 | todos os 10 livros |
| 10 | *(lista vazia)* |
| 15 | *(lista vazia)* |

### Exercício 4 — `totalPaginas`

| tamanho da lista | tamanhoPagina | Resultado esperado |
|---|---|---|
| 10 | 3 | 4 |
| 9 | 3 | 3 |
| 6 | 3 | 2 |
| 1 | 5 | 1 |
| 0 | 5 | 0 |

### Exercício 5 — `buscarComPaginacao`

Considere que os livros com "Java" no título são (em ordem):
- [2] Java Efetivo
- [5] Java: como programar
- [7] Código Limpo em Java

| termo | pagina | tamanhoPagina | Resultado esperado |
|---|---|---|---|
| "Java" | 0 | 2 | [2] Java Efetivo, [5] Java: como programar |
| "Java" | 1 | 2 | [7] Código Limpo em Java |
| "java" | 0 | 5 | todos os 3 livros com "Java" |
| "Python" | 0 | 5 | *(lista vazia)* |
