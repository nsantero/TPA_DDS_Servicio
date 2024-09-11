package API;
import com.google.gson.annotations.Expose;
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
    @Expose
    @Column(name = "nombre")
    private String nombre;
    @Expose
    @Transient
    private int cantPersonas;

    @OneToMany(mappedBy = "barrio") //va a tener q persistise
    private List<Persona> personas;
    @Expose
    @Transient
    private List<PersonaParaDevolverJson> personasParaDevolverJson;

    public Barrio(String nombre, List<Persona> personas) {
        this.nombre = nombre;
        this.personas = personas;
        this.cantPersonas=personas.size();
        this.personasParaDevolverJson=new ArrayList<PersonaParaDevolverJson>();
    }

    public Barrio() {
        this.personas=new ArrayList<Persona>();
        this.personasParaDevolverJson=new ArrayList<PersonaParaDevolverJson>();
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
    public void setPersonasParaDevolverJson(){
        for(int i=0;i<this.getPersonas().size();i++){
            this.personasParaDevolverJson.add(new PersonaParaDevolverJson(
                                                    this.personas.get(i).getNombre(),
                                                    this.personas.get(i).getApellido()));
        }
    }
}
