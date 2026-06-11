package TrabalhoFinal.com.adega.manager.controller;

import TrabalhoFinal.com.adega.manager.model.Vinho;
import TrabalhoFinal.com.adega.manager.service.VinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vinhos")
@CrossOrigin(origins = "*")
public class VinhoController {

    @Autowired
    private VinhoService vinhoService;

    @GetMapping
    public ResponseEntity<List<Vinho>> listarVinhos() {
        List<Vinho> vinhos = vinhoService.listarTodos();
        return ResponseEntity.ok(vinhos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vinho> buscarVinho(@PathVariable Long id) {
        Optional<Vinho> vinho = vinhoService.buscarPorId(id);
        return vinho.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Vinho> adicionarVinho(@RequestBody Vinho vinho) {
        try {
            Vinho novoVinho = vinhoService.salvar(vinho);
            return new ResponseEntity<>(novoVinho, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vinho> atualizarVinho(@PathVariable Long id, @RequestBody Vinho vinho) {
        try {
            Vinho vinhoAtualizado = vinhoService.atualizar(id, vinho);
            return ResponseEntity.ok(vinhoAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerVinho(@PathVariable Long id) {
        vinhoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}