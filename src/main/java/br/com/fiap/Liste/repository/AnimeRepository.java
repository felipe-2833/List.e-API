package br.com.fiap.Liste.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.fiap.Liste.model.Anime;
import br.com.fiap.Liste.model.User;

public interface AnimeRepository extends JpaRepository<Anime, Long>, JpaSpecificationExecutor<Anime> {

    List<Anime> findByUser(User user);

}