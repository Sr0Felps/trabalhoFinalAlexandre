package TrabalhoFinal.com.adega.manager.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Tempo de expiração do Token (Ex: 1 hora)
    private final long tempoExpiracao = 3600000;

    // Método para gerar o Token após o login com sucesso
    public String gerarToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tempoExpiracao))
                .signWith(secretKey)
                .compact();
    }

    // Método para extrair o e-mail (subject) de dentro do Token
    public String extrairEmail(String token) {
        return extrairClaims(token).getSubject();
    }

    // Método para verificar se o Token ainda é válido
    public boolean isTokenValido(String token, String email) {
        final String emailDoToken = extrairEmail(token);
        return (emailDoToken.equals(email) && !isTokenExpirado(token));
    }

    private boolean isTokenExpirado(String token) {
        return extrairClaims(token).getExpiration().before(new Date());
    }

    private Claims extrairClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
