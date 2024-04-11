package michelavivaqua.bloggingapp.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Entity
public class Autore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private String cognome;
    private String email;
    private LocalDate dataDiNascita;
    private String avatar;
    @OneToMany(mappedBy = "autore")
    private List<BlogPost> blogPosts;
}
//public void setAvatar() {
//    this.avatar = "https://ui-avatars.com/api/?name=" + this.name + "+" + this.surname;
//}