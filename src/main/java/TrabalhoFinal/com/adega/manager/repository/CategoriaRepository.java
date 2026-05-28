package TrabalhoFinal.com.adega.manager.repository;

import TrabalhoFinal.com.adega.manager.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
