package TrabalhoFinal.com.adega.manager.model;

import jakarta.persistence.*;

@Entity
@Table(name = "vinhos")
public class Vinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String vinicola;

    private Integer safra;

    @Column(name = "quantidade_estoque", nullable = false)
    private Integer quantidadeEstoque;

    // Relacionamento: Muitos vinhos podem pertencer a uma categoria
    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    public Vinho() {}

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getVinicola() { return vinicola; }
    public void setVinicola(String vinicola) { this.vinicola = vinicola; }

    public Integer getSafra() { return safra; }
    public void setSafra(Integer safra) { this.safra = safra; }

    public Integer getQuantidadeEstoque() { return quantidadeEstoque; }
    public void setQuantidadeEstoque(Integer quantidadeEstoque) { this.quantidadeEstoque = quantidadeEstoque; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
}
