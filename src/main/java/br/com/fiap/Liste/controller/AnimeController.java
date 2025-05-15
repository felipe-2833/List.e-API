package br.com.fiap.Liste.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.Liste.model.Anime;
import br.com.fiap.Liste.model.User;
import br.com.fiap.Liste.repository.AnimeRepository;
import br.com.fiap.Liste.specification.AnimeSpecification;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/animes")
@Slf4j
public class AnimeController {

    public record AnimeFilter(String name, String genero, Double nota, Boolean completo) {
    }

    @Autowired
    private AnimeRepository repository;

    @GetMapping
    @Cacheable("animes")
    @Operation(description = "Listar todos os animes", tags = "animes", summary = "Lista de animes")
    public Page<Anime> index(AnimeFilter filter,
            @PageableDefault(size = 5, sort = "name", direction = Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal User user) {
        log.info("Buscando animes com filtro ", filter.name(), filter.genero(), filter.nota(), filter.completo());
        var specification = AnimeSpecification.withFilters(filter, user);
        return repository.findAll(specification, pageable);
    }

    @PostMapping
    @CacheEvict(value = "animes", allEntries = true)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(responses = {
            @ApiResponse(responseCode = "400", description = "Falha na validação")
    }, tags = "animes", summary = "Adicionar anime", description = "Adicionar animes")
    public Anime create(@RequestBody @Valid Anime anime, @AuthenticationPrincipal User user) {
        log.info("Cadastrando animes " + anime.getName());
        anime.setUser(user);
        return repository.save(anime);
    }

    @GetMapping("/{id}")
    @Operation(description = "Listar anime pelo id", tags = "animes", summary = "Listar de anime")
    public Anime get(@PathVariable Long id, @AuthenticationPrincipal User user) {
        log.info("Buscando anime " + id);
        checkPermission(id, user);
        return getAnime(id);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Deletar anime pelo id", tags = "animes", summary = "Deletar anime")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long id, @AuthenticationPrincipal User user) {
        log.info("Apagando anime " + id);
        checkPermission(id, user);
        repository.delete(getAnime(id));
    }

    @PutMapping("/{id}")
    @Operation(description = "Update anime pelo id", tags = "animes", summary = "Update anime")
    public Anime update(@PathVariable Long id, @RequestBody @Valid Anime anime, @AuthenticationPrincipal User user) {
        log.info("Atualizando ifos anime " + id + " " + anime);
        checkPermission(id, user);
        anime.setId(id);
        anime.setUser(user);
        return repository.save(anime);
    }

    private void checkPermission(Long id, User user) {
        var animeOld = getAnime(id);
        if (!animeOld.getUser().equals(user))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    private Anime getAnime(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime não encontrado na lista"));
    }

}
