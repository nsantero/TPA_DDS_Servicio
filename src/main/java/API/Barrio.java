package API;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "barrio")

public class Barrio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre")
    private String nombre;
    @Transient
    private int cantPersonas;
    @OneToMany(mappedBy = "barrio") //va a tener q persistise
    private List<Persona> personas;

    public Barrio(String nombre, List<Persona> personas) {
        this.nombre = nombre;
        this.personas = personas;
        this.cantPersonas=personas.size();
    }

    public Barrio() {
        this.personas=new ArrayList<Persona>();
        this.cantPersonas=0;
    }

    public void agregarPersona(Persona persona){
        this.personas.add(persona);
        this.cantPersonas++;
    }
    public void sacarPersona(Persona persona){
        this.personas.remove(persona);
        this.cantPersonas--;
    }
    public void setCantPersonas(){
        this.cantPersonas=personas.size();
    }
}
