package TrabalhoFinal.com.adega.manager.config;

import TrabalhoFinal.com.adega.manager.model.Categoria;
import TrabalhoFinal.com.adega.manager.model.Usuario;
import TrabalhoFinal.com.adega.manager.model.Vinho;
import TrabalhoFinal.com.adega.manager.repository.CategoriaRepository;
import TrabalhoFinal.com.adega.manager.repository.UsuarioRepository;
import TrabalhoFinal.com.adega.manager.repository.VinhoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(UsuarioRepository usuarioRepository,
                                      CategoriaRepository categoriaRepository,
                                      VinhoRepository vinhoRepository,
                                      PasswordEncoder passwordEncoder) {
        return args -> {
            // 1. Popula o Usuário Administrador
            if (usuarioRepository.count() == 0) {
                Usuario admin = new Usuario();
                admin.setNome("Administrador Adega");
                admin.setEmail("admin@teste.com");
                admin.setSenha(passwordEncoder.encode("123456"));
                usuarioRepository.save(admin);
                System.out.println("Usuário administrador criado com sucesso!");
            }

            // 2. Popula as Categorias
            if (categoriaRepository.count() == 0) {
                Categoria tinto = new Categoria(); tinto.setNome("Vinho Tinto");
                Categoria branco = new Categoria(); branco.setNome("Vinho Branco");
                Categoria rose = new Categoria(); rose.setNome("Vinho Rosé");
                Categoria espumante = new Categoria(); espumante.setNome("Espumante");

                categoriaRepository.saveAll(Arrays.asList(tinto, branco, rose, espumante));
                System.out.println("Categorias iniciais criadas com sucesso!");

                // 3. Popula 15 Vinhos variados
                if (vinhoRepository.count() == 0) {

                    // --- VINHOS TINTOS ---
                    Vinho v1 = new Vinho(); v1.setNome("Casillero del Diablo Reserva"); v1.setVinicola("Concha y Toro"); v1.setSafra(2021); v1.setQuantidadeEstoque(50); v1.setCategoria(tinto);
                    Vinho v2 = new Vinho(); v2.setNome("Angelica Zapata Malbec"); v2.setVinicola("Catena Zapata"); v2.setSafra(2018); v2.setQuantidadeEstoque(24); v2.setCategoria(tinto);
                    Vinho v3 = new Vinho(); v3.setNome("Pêra-Manca"); v3.setVinicola("Fundação Eugénio de Almeida"); v3.setSafra(2015); v3.setQuantidadeEstoque(12); v3.setCategoria(tinto);
                    Vinho v4 = new Vinho(); v4.setNome("Periquita Reserva"); v4.setVinicola("José Maria da Fonseca"); v4.setSafra(2020); v4.setQuantidadeEstoque(60); v4.setCategoria(tinto);
                    Vinho v5 = new Vinho(); v5.setNome("Cartuxa Colheita"); v5.setVinicola("Adega Cartuxa"); v5.setSafra(2019); v5.setQuantidadeEstoque(30); v5.setCategoria(tinto);

                    // --- VINHOS BRANCOS ---
                    Vinho v6 = new Vinho(); v6.setNome("Casal Garcia"); v6.setVinicola("Aveleda"); v6.setSafra(2022); v6.setQuantidadeEstoque(120); v6.setCategoria(branco);
                    Vinho v7 = new Vinho(); v7.setNome("Montes Alpha Chardonnay"); v7.setVinicola("Montes"); v7.setSafra(2021); v7.setQuantidadeEstoque(40); v7.setCategoria(branco);
                    Vinho v8 = new Vinho(); v8.setNome("Esporão Reserva Branco"); v8.setVinicola("Herdade do Esporão"); v8.setSafra(2022); v8.setQuantidadeEstoque(45); v8.setCategoria(branco);
                    Vinho v9 = new Vinho(); v9.setNome("Alamos Sauvignon Blanc"); v9.setVinicola("Catena Zapata"); v9.setSafra(2023); v9.setQuantidadeEstoque(80); v9.setCategoria(branco);

                    // --- VINHOS ROSÉS ---
                    Vinho v10 = new Vinho(); v10.setNome("Mateus Original"); v10.setVinicola("Sogrape"); v10.setSafra(2023); v10.setQuantidadeEstoque(85); v10.setCategoria(rose);
                    Vinho v11 = new Vinho(); v11.setNome("Whispering Angel"); v11.setVinicola("Château d'Esclans"); v11.setSafra(2022); v11.setQuantidadeEstoque(20); v11.setCategoria(rose);
                    Vinho v12 = new Vinho(); v12.setNome("EA Rosé"); v12.setVinicola("Fundação Eugénio de Almeida"); v12.setSafra(2023); v12.setQuantidadeEstoque(50); v12.setCategoria(rose);

                    // --- ESPUMANTES ---
                    Vinho v13 = new Vinho(); v13.setNome("Chandon Réserve Brut"); v13.setVinicola("Chandon"); v13.setSafra(2020); v13.setQuantidadeEstoque(35); v13.setCategoria(espumante);
                    Vinho v14 = new Vinho(); v14.setNome("Freixenet Cordon Negro"); v14.setVinicola("Freixenet"); v14.setSafra(2021); v14.setQuantidadeEstoque(70); v14.setCategoria(espumante);
                    Vinho v15 = new Vinho(); v15.setNome("Moët & Chandon Impérial"); v15.setVinicola("Moët & Chandon"); v15.setSafra(2019); v15.setQuantidadeEstoque(15); v15.setCategoria(espumante);

                    vinhoRepository.saveAll(Arrays.asList(v1, v2, v3, v4, v5, v6, v7, v8, v9, v10, v11, v12, v13, v14, v15));
                    System.out.println("Os 15 Vinhos iniciais foram criados com sucesso!");
                }
            }
        };
    }
}