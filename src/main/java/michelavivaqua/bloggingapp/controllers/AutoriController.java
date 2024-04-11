package michelavivaqua.bloggingapp.controllers;

import michelavivaqua.bloggingapp.entities.Autore;
import michelavivaqua.bloggingapp.entities.NewAutoreDTO;
import michelavivaqua.bloggingapp.entities.NewAutoreRespDTO;
import michelavivaqua.bloggingapp.exceptions.BadRequestException;
import michelavivaqua.bloggingapp.services.AutoriService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/authors")
public class AutoriController {
    @Autowired
    private AutoriService autoriService;

//         1. GET http://localhost:3001/authors (ritorna la lista di autori)
//        @GetMapping
//        private List<Autore> getAllAutori(){
//            return this.autoriService.getAutoriList();
//        }

    @GetMapping
    public Page<Autore> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "4") int size,
                                    @RequestParam(defaultValue = "id") String sortBy) {
        return this.autoriService.getAutori(page, size, sortBy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewAutoreRespDTO saveAutore(@RequestBody @Validated NewAutoreDTO body, BindingResult validation){
        // @Validated valida il payload in base ai validatori utilizzati nella classe NewUserDTO
        // BindingResult validation ci serve per valutare il risultato di questa validazione
        if(validation.hasErrors()) { // Se ci sono stati errori di validazione dovrei triggerare un 400 Bad Request
            System.out.println(validation.getAllErrors());
            throw new BadRequestException(validation.getAllErrors()); // Inviamo la lista degli errori all'Error Handler opportuno
        }
        System.out.println(body);
        // Altrimenti se non ci sono stati errori posso salvare tranquillamente lo user
        return new NewAutoreRespDTO(this.autoriService.saveAutore(body).getId());}



    // 2. GET http://localhost:3001/authors/{{authorId}} (ritorna un singolo autore)
    @GetMapping("/{authorId}")
    private Autore findAutoreById(@PathVariable int authorId){
        return this.autoriService.findById(authorId);
    }

    // 3. POST http://localhost:3001/authors (+ body) (crea un nuovo autore)
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED) // Status Code 201
//    private Autore saveAutore(@RequestBody Autore body){
//        return this.autoriService.saveAutore(body);
//    }

    // 4. PUT http://localhost:3001/authors/{{authorId}} (+ body) (modifica lo specifico autore)
    @PutMapping("/{authorId}")
    private Autore findByIdAndUpdate(@PathVariable int authorId, @RequestBody Autore body){
        return this.autoriService.findByIdAndUpdate(authorId, body);
    }



    // 5. DELETE http://localhost:3001/authors/{authorId} (cancella lo specifico autore)
    @DeleteMapping("/{authorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Status Code 204
    public void deleteAutoreById(@PathVariable int authorId) {
        this.autoriService.findByIdAndDelete(authorId);
    }

    @PostMapping("/upload")
    public String uploadAvatar(@RequestParam("avatar") MultipartFile image) throws IOException {
        // "avatar" deve corrispondere ESATTAMENTE alla chiave del Multipart dove sarà contenuto il file
        // altrimenti il file non verrà trovato
        return this.autoriService.uploadImage(image);

    }
    @PostMapping("/upload/{authorId}")
    public Autore uploadAvatar (@RequestParam("avatar") MultipartFile image, @PathVariable int authorId) throws IOException {
        return this.autoriService.uploadAutoreImage(image,authorId);
    }
}


