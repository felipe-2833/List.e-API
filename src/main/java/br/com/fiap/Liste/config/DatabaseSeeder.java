package br.com.fiap.Liste.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.fiap.Liste.model.Anime;
import br.com.fiap.Liste.model.User;
import br.com.fiap.Liste.model.UserRole;
import br.com.fiap.Liste.repository.AnimeRepository;
import br.com.fiap.Liste.repository.UserRepository;
import jakarta.annotation.PostConstruct;

@Component
@Profile("dev")
public class DatabaseSeeder {
    
    @Autowired
    private AnimeRepository animeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void animeSeed() {

        var felipe = User.builder()
                        .email("felipe@fiap.com.br")
                        .password(passwordEncoder.encode("12345"))
                        .role(UserRole.ADMIN)
                        .build();

        var samir = User.builder()
                        .email("samir@fiap.com.br")
                        .password(passwordEncoder.encode("12345"))
                        .role(UserRole.USER)
                        .build();

        userRepository.saveAll(List.of(felipe, samir));

        animeRepository.saveAll(
                List.of(
                        Anime.builder().name("Naruto").nota(9.0).genero("Battle Shounen").completo(true).user(felipe).build(),
                        Anime.builder().name("Bleach").nota(7.5).genero("Battle Shounen").completo(true).user(felipe).build(),
                        Anime.builder().name("One Piece").nota(9.0).genero("Battle Shounen").completo(true).user(samir).build(),

                        Anime.builder().name("Attack on Titan").nota(9.5).genero("Shounen").completo(true).user(felipe).build(),
                        Anime.builder().name("Death Note").nota(9.2).genero("Psicológico").completo(true).user(felipe).build(),
                        Anime.builder().name("Jujutsu Kaisen").nota(8.8).genero("Battle Shounen").completo(false).user(felipe).build(),
                        Anime.builder().name("My Hero Academia").nota(8.0).genero("Shounen").completo(false).user(felipe).build(),
                        Anime.builder().name("Chainsaw Man").nota(8.7).genero("Ação").completo(false).user(felipe).build(),
                        Anime.builder().name("Black Clover").nota(7.8).genero("Shounen").completo(false).user(felipe).build(),
                        Anime.builder().name("Vinland Saga").nota(8.9).genero("Seinen").completo(false).user(felipe).build(),
                        Anime.builder().name("Tokyo Ghoul").nota(7.5).genero("Terror").completo(true).user(felipe).build(),
                        Anime.builder().name("Erased").nota(8.5).genero("Mistério").completo(true).user(felipe).build(),
                        Anime.builder().name("Mob Psycho 100").nota(8.6).genero("Sobrenatural").completo(true).user(felipe).build(),

                        Anime.builder().name("Demon Slayer").nota(9.0).genero("Ação").completo(false).user(samir).build(),
                        Anime.builder().name("Fullmetal Alchemist: Brotherhood").nota(9.7).genero("Shounen").completo(true).user(samir).build(),
                        Anime.builder().name("Parasyte").nota(8.4).genero("Terror").completo(true).user(samir).build(),
                        Anime.builder().name("Code Geass").nota(9.1).genero("Mecha").completo(true).user(samir).build(),
                        Anime.builder().name("Dr. Stone").nota(8.0).genero("Sci-Fi").completo(false).user(samir).build()
                ));
    
    }

}
