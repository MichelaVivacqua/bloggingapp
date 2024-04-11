package michelavivaqua.bloggingapp.controllers;

import michelavivaqua.bloggingapp.entities.Autore;
import michelavivaqua.bloggingapp.entities.BlogPost;
import michelavivaqua.bloggingapp.entities.BlogPostPayload;
import michelavivaqua.bloggingapp.exceptions.NotFoundException;
import michelavivaqua.bloggingapp.services.AutoriService;
import michelavivaqua.bloggingapp.services.BlogPostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blogPosts")
public class BlogPostsController {
    @Autowired
    private BlogPostsService blogPostsService;

    @Autowired
    private AutoriService autoriService;

    // 1. GET http://localhost:3001/blogPosts (ritorna la lista di blog posts)
    @GetMapping
    private List<BlogPost> getAllBlogPosts(){
        return this.blogPostsService.getBlogPostsList();
    }



    // 2. GET http://localhost:3001/blogPosts/{{blogPostId}} (ritorna un blog post)
    @GetMapping("/{blogPostId}")
    private BlogPost findBlogPostById(@PathVariable int blogPostId){
        return this.blogPostsService.findById(blogPostId);
    }


//     3. POST http://localhost:3001/blogPosts (+ body) (crea un nuovo blog post)
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED) // Status Code 201
//    private BlogPost saveBlogPost(@RequestBody BlogPost body){
//        return this.blogPostsService.saveBlogPost(body);
//    }


    // POST http://localhost:3001/blogPosts (+ body) (crea un nuovo blog post)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Status Code 201
    private BlogPost saveBlogPost(@RequestBody BlogPostPayload payload){
        // Verifica autore
        Autore autore = autoriService.findById(payload.getAuthorId());
        if (autore == null) {
            throw new NotFoundException(payload.getAuthorId());
        }

        // Creo il blog post
        BlogPost blogPost = new BlogPost();
        blogPost.setCategoria(payload.getCategoria());
        blogPost.setTitolo(payload.getTitolo());
        blogPost.setCover(payload.getCover());
        blogPost.setContenuto(payload.getContenuto());
        blogPost.setTempoDiLettura(payload.getTempoDiLettura());
        blogPost.setAutore(autore);

        return blogPostsService.saveBlogPost(blogPost);
    }

    // 4. PUT http://localhost:3001/blogPosts/{{blogPostId}} (+ body) (modifica lo specifico blog post)
    @PutMapping("/{blogPostId}")
    private BlogPost findBlogPostByIdAndUpdate(@PathVariable int blogPostId, @RequestBody BlogPost body){
        return this.blogPostsService.findByIdAndUpdate(blogPostId, body);
    }

    // 5. DELETE http://localhost:3001/blogPosts/{{blogPostId}} (cancella lo specifico blog post)
    @DeleteMapping("/{blogPostId}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Status Code 204
    private void findBlogPostByIdAndDelete(@PathVariable int blogPostId){
        this.blogPostsService.findByIdAndDelete(blogPostId);
    }
}
