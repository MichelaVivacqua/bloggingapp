package michelavivaqua.bloggingapp.repositories;

import michelavivaqua.bloggingapp.entities.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlogPostsDAO extends JpaRepository<BlogPost, Integer> {
    boolean existsByTitolo(String titolo);
    Optional<BlogPost> findBytitolo(String titolo);

}
