package API;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static spark.Spark.*;

public class API {
    public static void main(String[] args) {
        Gson gson = new Gson();
        port(4567);//por defecto 4567


        Repositorio repositorio=new Repositorio();


        post("/consumo", (req, res) -> {


            Persona persona = gson.fromJson(req.body(), Persona.class);
            /* Procesar el jason
            * obtenerBarrioPorNombre
            * obtenerPersonaPorNombreyApellido
            * actualizar/crear
            * pushear a base de datos
            * */


            List<Barrio> barrioLista = new ArrayList<Barrio>(repositorio.obtenerBarrioPorNombre(persona.getBarrioNombre()));
            List<Persona> personaList = new ArrayList<Persona>(repositorio.obtenerPersonaPorNombreyApellido(persona.getNombre(), persona.getApellido()));

            Barrio barrioDB=null;
            Persona personaDB=null;
            if (barrioLista.size()==1){barrioDB=barrioLista.get(0);}
            if (personaList.size()==1){personaDB=personaList.get(0);}


            if (barrioDB!=null){
                if(personaDB!=null){
                    //ambos existen
                    if(personaDB.getBarrio().getId() != barrioDB.getId()){
                        personaDB.setBarrio(barrioDB);
                        barrioDB.agregarPersona(personaDB);
                        repositorio.actualizarPersona(personaDB);
                        repositorio.actualizarBarrio(barrioDB);
                    }
                }else{
                    //solo existe el barrio
                    persona.setBarrio(barrioDB);
                    barrioDB.agregarPersona(persona);
                    repositorio.actualizarBarrio(barrioDB);
                    repositorio.agregarPersona(persona);
                }
            }else{
                Barrio barrioNuevo=new Barrio();
                barrioNuevo.setNombre(persona.getBarrioNombre());
                if(personaDB!=null){
                    //solo persona
                    personaDB.setBarrio(barrioNuevo);
                    barrioNuevo.agregarPersona(personaDB);
                    repositorio.actualizarPersona(personaDB);
                    repositorio.agregarBarrio(barrioNuevo);

                }else{
                    //ninguno

                    persona.setBarrio(barrioNuevo);
                    repositorio.agregarBarrio(barrioNuevo);
                    barrioNuevo.agregarPersona(persona);
                    repositorio.agregarPersona(persona);



                }
            }

            res.status(200);//por defecto 200
            return "Consumo Registrado";
        }, gson::toJson);


        get("/barrios", (req, res) -> {
            res.type("application/json");

            List<Barrio> lista = new ArrayList<Barrio>(repositorio.obtenerTodosLosBarrios());

            lista.forEach(a->a.setCantPersonas());

            return lista;
        }, gson::toJson);
    }



}
