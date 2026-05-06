package com.paginacao;

import java.util.List;
import java.util.stream.Collectors;

public class CatalogoBusca {
    public List<Livro> buscarComPaginacao(List<Livro> livros, String termo,
                                          int pagina, int tamanhoPagina) {
        // TODO: implemente usando filter, skip e limit
        throw new UnsupportedOperationException("Não implementado");
        return livros.stream()
            .filter(livro -> livro.getTitulo().toLowerCase().contains(termo.toLowerCase()))
            .skip(pagina * tamanhoPagina)
            .limit(tamanhoPagina)
            .collect(Collectors.toList());
    }
}
