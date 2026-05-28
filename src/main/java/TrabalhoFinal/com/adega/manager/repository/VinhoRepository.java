package TrabalhoFinal.com.adega.manager.repository;

import TrabalhoFinal.com.adega.manager.model.Vinho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VinhoRepository extends JpaRepository<Vinho, Long> {
}
