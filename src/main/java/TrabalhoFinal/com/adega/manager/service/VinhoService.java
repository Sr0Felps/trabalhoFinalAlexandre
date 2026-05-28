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

    // Retorna todos os vinhos registados na base de dados
    public List<Vinho> listarTodos() {
        return vinhoRepository.findAll();
    }

    // Procura um vinho específico pelo ID
    public Optional<Vinho> buscarPorId(Long id) {
        return vinhoRepository.findById(id);
    }

    // Guarda um novo vinho, validando a categoria
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

    // Apaga um vinho pelo ID
    public void deletar(Long id) {
        vinhoRepository.deleteById(id);
    }
}
