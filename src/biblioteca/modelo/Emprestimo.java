package biblioteca.modelo;

import simplodb.Persistivel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Emprestimo implements Persistivel {

    private static final long serialVersionUID = 1L;

    public static final int PRAZO_DIAS = 14;
    public static final BigDecimal MULTA_POR_DIA = BigDecimal.ONE;

    private Long id;
    private Long usuarioId;
    private Long livroId;
    private LocalDateTime dataEmprestimo;
    private LocalDateTime dataDevolucaoPrevista;
    private LocalDateTime dataDevolvido;

    public Emprestimo() {}

    public Emprestimo(Long usuarioId, Long livroId) {
        this.usuarioId = usuarioId;
        this.livroId = livroId;
        this.dataEmprestimo = LocalDateTime.now();
        this.dataDevolucaoPrevista = this.dataEmprestimo.plusDays(PRAZO_DIAS);
    }

    @Override public Long getId() { return id; }
    @Override public void setId(Long id) { this.id = id; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public Long getLivroId() { return livroId; }
    public void setLivroId(Long livroId) { this.livroId = livroId; }

    public LocalDateTime getDataEmprestimo() { return dataEmprestimo; }
    public void setDataEmprestimo(LocalDateTime dataEmprestimo) { this.dataEmprestimo = dataEmprestimo; }

    public LocalDateTime getDataDevolucaoPrevista() { return dataDevolucaoPrevista; }
    public void setDataDevolucaoPrevista(LocalDateTime dataDevolucaoPrevista) {
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
    }

    public LocalDateTime getDataDevolvido() { return dataDevolvido; }
    public void setDataDevolvido(LocalDateTime dataDevolvido) { this.dataDevolvido = dataDevolvido; }

    public boolean isDevolvido() {
        return dataDevolvido != null;
    }
    
    public boolean estaAtrasado() {
        boolean passouDataEntrega = LocalDateTime.now().isAfter(dataDevolucaoPrevista);
        boolean livroNaoDevolvido = dataDevolvido == null;

        return livroNaoDevolvido && passouDataEntrega;
    }


    public BigDecimal calcularMulta() {

        if (!estaAtrasado()) {
            return BigDecimal.ZERO;
        }

        // verificar se empréstimos devolvidos atrasados podem retornar ZERO.

        LocalDateTime dataReferencia = LocalDateTime.now();
        if (isDevolvido()){
            dataReferencia = dataDevolvido;
        }

        long dias = ChronoUnit.DAYS.between(dataDevolucaoPrevista, dataReferencia);

        return MULTA_POR_DIA.multiply(BigDecimal.valueOf(dias));
    }

    @Override
    public String toString() {
        DateTimeFormatter padraoDeFormatacao = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        String dataDevolucaoPrevistaFormatada = dataDevolucaoPrevista.format(padraoDeFormatacao);
        String dataDevolvidoFormatado = dataDevolvido.format(padraoDeFormatacao);
        String multaFormatada = String.format("%.2f", calcularMulta());

        String resumoEmprestimo = "Empréstimo #" + id + " | Livro: " + livroId + " | Usuário: " + usuarioId + " | Vence: " + dataDevolucaoPrevistaFormatada;

        if (isDevolvido()){
            resumoEmprestimo = resumoEmprestimo + " | Devolvido: " + dataDevolvidoFormatado;
        }
        else if (estaAtrasado()){
            resumoEmprestimo = resumoEmprestimo + " | ATRASADO | Multa: R$ " + multaFormatada;
        }

        return resumoEmprestimo;
            
    }
}
