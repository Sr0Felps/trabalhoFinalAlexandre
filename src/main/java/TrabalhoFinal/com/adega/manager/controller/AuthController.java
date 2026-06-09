package TrabalhoFinal.com.adega.manager.controller;

import TrabalhoFinal.com.adega.manager.dto.LoginDTO;
import TrabalhoFinal.com.adega.manager.model.Usuario;
import TrabalhoFinal.com.adega.manager.repository.UsuarioRepository;
import TrabalhoFinal.com.adega.manager.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/registar")
    public ResponseEntity<Usuario> registar(@RequestBody Usuario novoUsuario) {
        if (usuarioRepository.findByEmail(novoUsuario.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().build(); // E-mail já existe
        }
        // Encriptar a palavra-passe antes de guardar na base de dados!
        novoUsuario.setSenha(passwordEncoder.encode(novoUsuario.getSenha()));
        Usuario utilizadorSalvo = usuarioRepository.save(novoUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(utilizadorSalvo);
    }

    // Rota pública para fazer login e obter o Token
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(loginDTO.getEmail());

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            // Compara a palavra-passe digitada com a que está encriptada no banco
            if (passwordEncoder.matches(loginDTO.getSenha(), usuario.getSenha())) {
                String token = jwtUtil.gerarToken(usuario.getEmail());
                // Retorna o token formatado em JSON
                return ResponseEntity.ok("{\"token\": \"" + token + "\"}");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
    }
}
