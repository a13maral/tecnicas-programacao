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
