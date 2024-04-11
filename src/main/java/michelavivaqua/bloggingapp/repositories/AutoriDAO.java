package michelavivaqua.bloggingapp.repositories;

import michelavivaqua.bloggingapp.entities.Autore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AutoriDAO extends JpaRepository<Autore, Integer> {
    boolean existsByEmail(String email);
    Optional<Autore> findByEmail(String nome);

}
