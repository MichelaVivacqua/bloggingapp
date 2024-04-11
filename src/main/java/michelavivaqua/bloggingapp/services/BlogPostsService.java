package michelavivaqua.bloggingapp.services;

import michelavivaqua.bloggingapp.entities.BlogPost;
import michelavivaqua.bloggingapp.exceptions.NotFoundException;
import michelavivaqua.bloggingapp.repositories.AutoriDAO;
import michelavivaqua.bloggingapp.repositories.BlogPostsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogPostsService {
    @Autowired
    private BlogPostsDAO blogPostRepository;
    @Autowired
    private AutoriDAO autoreRepository;

    public List<BlogPost> getBlogPostsList() {
        return blogPostRepository.findAll();
    }

    public BlogPost saveBlogPost(BlogPost body) {
        return blogPostRepository.save(body);
    }

    public BlogPost findById(int id) {
        return blogPostRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    public BlogPost findByIdAndUpdate(int id, BlogPost updatedBlogPost) {
        BlogPost found = findById(id);
        found.setTitolo(updatedBlogPost.getTitolo());
        found.setContenuto(updatedBlogPost.getContenuto());
        return blogPostRepository.save(found);
    }

    public void findByIdAndDelete(int authorId) {
        blogPostRepository.deleteById(authorId);
    }
}

