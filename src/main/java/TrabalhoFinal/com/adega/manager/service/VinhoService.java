package TrabalhoFinal.com.adega.manager.service;

import TrabalhoFinal.com.adega.manager.model.Categoria;
import TrabalhoFinal.com.adega.manager.model.Vinho;
import TrabalhoFinal.com.adega.manager.repository.CategoriaRepository;
import TrabalhoFinal.com.adega.manager.repository.VinhoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VinhoService {

    @Autowired
    private VinhoRepository vinhoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Vinho> listarTodos() {
        return vinhoRepository.findAll();
    }

    public Optional<Vinho> buscarPorId(Long id) {
        return vinhoRepository.findById(id);
    }

    public Vinho salvar(Vinho vinho) {
        if (vinho.getCategoria() != null && vinho.getCategoria().getId() != null) {
            Optional<Categoria> categoria = categoriaRepository.findById(vinho.getCategoria().getId());
            if (categoria.isPresent()) {
                vinho.setCategoria(categoria.get());
                return vinhoRepository.save(vinho);
            }
        }
        throw new IllegalArgumentException("Categoria inválida ou não encontrada.");
    }

    public Vinho atualizar(Long id, Vinho vinhoAtualizado) {
        return vinhoRepository.findById(id).map(vinho -> {
            vinho.setNome(vinhoAtualizado.getNome());
            vinho.setVinicola(vinhoAtualizado.getVinicola());
            vinho.setSafra(vinhoAtualizado.getSafra());
            vinho.setQuantidadeEstoque(vinhoAtualizado.getQuantidadeEstoque());

            if (vinhoAtualizado.getCategoria() != null && vinhoAtualizado.getCategoria().getId() != null) {
                Optional<Categoria> categoria = categoriaRepository.findById(vinhoAtualizado.getCategoria().getId());
                if (categoria.isPresent()) {
                    vinho.setCategoria(categoria.get());
                } else {
                    throw new IllegalArgumentException("Categoria inválida ou não encontrada.");
                }
            }
            return vinhoRepository.save(vinho);
        }).orElseThrow(() -> new IllegalArgumentException("Vinho não encontrado."));
    }

    public void deletar(Long id) {
        vinhoRepository.deleteById(id);
    }
}