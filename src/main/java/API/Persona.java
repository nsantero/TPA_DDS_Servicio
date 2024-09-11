package API;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter

@Entity
@Table(name="persona")

public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient
    private String barrioNombre;
    @ManyToOne
    @JoinColumn(name="barrio_id", referencedColumnName = "id", nullable = false)
    private Barrio barrio;
    //creo q esta mal deberia ser muchos a muchos

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;
   // @Column(name = "ultimoConsumo")
    //private LocalDate ultimoComsumo;

    public Persona(String localidadNombre, String nombre, String apellido, Barrio barrio) {
        this.barrioNombre = localidadNombre;
        this.nombre = nombre;
        this.apellido = apellido;
        this.barrio = barrio;
        //this.ultimoComsumo = ultimoComsumo;
    }

    public Persona() {

    }


    /*public void agregarBarrio(Barrio barrio){
        this.barrio.add(barrio);
    }
    public void sacarBarrio(Barrio barrio){
        this.barrio.remove(barrio);
    }*/
}
