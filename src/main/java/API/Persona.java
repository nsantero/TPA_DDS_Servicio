package API;
import com.google.gson.annotations.Expose;
import org.threeten.bp.LocalDate;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter

@Entity
@Table(name="Persona")

public class Persona {
    @Id
    @GeneratedValue
    private int id;

    @Transient
    private String barrioNombre;
    @ManyToMany
    @JoinTable(name = "barrio_persona", joinColumns = @JoinColumn(name = "personaId",referencedColumnName = "id"),
                                        inverseJoinColumns = @JoinColumn(name = "barrioId",referencedColumnName = "id"))
    private List<Barrio> barrios;
    //creo q esta mal deberia ser muchos a muchos

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;
   // @Column(name = "ultimoConsumo")
    //private LocalDate ultimoComsumo;

    public Persona(String localidadNombre, String nombre, String apellido) {
        this.barrioNombre = localidadNombre;
        this.nombre = nombre;
        this.apellido = apellido;
        this.barrios=new ArrayList<Barrio>();
        //this.ultimoComsumo = ultimoComsumo;
    }

    public Persona() {

    }


    public void agregarBarrio(Barrio barrio){
        this.barrios.add(barrio);
    }
    public void sacarBarrio(Barrio barrio){
        this.barrios.remove(barrio);
    }
}
