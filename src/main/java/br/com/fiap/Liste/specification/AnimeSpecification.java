package br.com.fiap.Liste.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import br.com.fiap.Liste.controller.AnimeController.AnimeFilter;
import br.com.fiap.Liste.model.Anime;
import br.com.fiap.Liste.model.User;
import jakarta.persistence.criteria.Predicate;

public class AnimeSpecification {
    public static Specification<Anime> withFilters(AnimeFilter filter, User user) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.name() != null && !filter.name().isBlank()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("name")), "%" + filter.name().toLowerCase() + "%"));
            }

            if (filter.genero() != null && !filter.genero().isBlank()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("genero")), "%" + filter.genero().toLowerCase() + "%"));
            }

            if (filter.nota() != null && filter.nota() > 0.0 && filter.nota() <= 10.0) {
                predicates.add(cb.equal(root.get("nota"), filter.nota()));
            }

            if (filter.completo() != null) {
                predicates.add(cb.equal(root.get("completo"), filter.completo()));
            }

            predicates.add(cb.equal(root.get("user"), user));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
