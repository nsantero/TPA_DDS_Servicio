package API;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PersonaParaDevolverJson {
    @Expose
    private String nombreYApellido;

    public PersonaParaDevolverJson(String nombre, String apellido) {
        this.nombreYApellido = nombre + ", " + apellido;
    }
    public PersonaParaDevolverJson() {

    }
}
