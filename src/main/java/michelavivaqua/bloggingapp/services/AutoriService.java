package michelavivaqua.bloggingapp.services;

import michelavivaqua.bloggingapp.entities.Autore;
import michelavivaqua.bloggingapp.entities.NewAutoreDTO;
import michelavivaqua.bloggingapp.exceptions.BadRequestException;
import michelavivaqua.bloggingapp.exceptions.NotFoundException;
import michelavivaqua.bloggingapp.repositories.AutoriDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutoriService {
    @Autowired
    private AutoriDAO autoreRepository;

    public AutoriService(AutoriDAO autoriRepository) {
        this.autoreRepository = autoriRepository;
    }

    public List<Autore> getAutoriList() {
        return autoreRepository.findAll();
    }

    public Page<Autore> getAutori(int page, int size, String sortBy){
        if(size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.autoreRepository.findAll(pageable);
    }

//    public Autore saveAutore(NewAutoreDTO body) {
        // Controlla se l'email è già in uso
//        if (autoreRepository.findByEmail(body.email()).isPresent()) {
//            throw new BadRequestException("L'email " + body.email() + " è già in uso!");
//        }

//        Autore newAutore = new Autore(body.name(), body.surname(), body.email(), body.dataDiNascita(), body.avatar());

//        return autoreRepository.save(body);
//    }


    public Autore saveAutore(NewAutoreDTO newAutoreDTO) {

        if (autoreRepository.existsByEmail(newAutoreDTO.email())) {
            throw new BadRequestException("L'email " + newAutoreDTO.email() + " è già in uso!");
        }

        Autore autore = new Autore(newAutoreDTO.name(), newAutoreDTO.surname(),
                newAutoreDTO.email(), newAutoreDTO.dataDiNascita(), newAutoreDTO.avatar());
        System.out.println(autore);
        return autoreRepository.save(autore);
    }

//    public Autore save(Autore newAutore){
//        // 1. Verifico se l'email è già in uso
//        this.autoreRepository.findByEmail(newAutore.getEmail()).ifPresent(
//                // 2. Se lo è triggero un errore
//                user -> {
//                    throw new BadRequestException("L'email " + user.getEmail() + " è già in uso!");
//                }
//        );
//
////       Salvo lo user
//        return autoreRepository.save(newAutore);
//    }

    public Autore findById(int id) {
        return autoreRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    public Autore findByIdAndUpdate(int id, Autore updatedAutore) {
        Autore found = findById(id);
        found.setNome(updatedAutore.getNome());
        found.setCognome(updatedAutore.getCognome());
        return autoreRepository.save(found);
    }

    public void findByIdAndDelete(int authorId) {
        autoreRepository.deleteById(authorId);
    }
}
