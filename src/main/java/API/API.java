package API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import static spark.Spark.*;

public class API {
    public static void main(String[] args) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();;
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
                        //aca falta sacar a la persona del barrio anterior TODO
                        Barrio barrioViejo =personaDB.getBarrio();
                        barrioViejo.sacarPersona(personaDB);
                        personaDB.setBarrio(barrioDB);
                        barrioDB.agregarPersona(personaDB);
                        repositorio.actualizarPersona(personaDB);
                        repositorio.actualizarBarrio(barrioDB);
                        repositorio.actualizarBarrio(barrioViejo);
                        //es necesario? por q esta mappeado en el la persona asi q si cambias la persona esto tmb? no se


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

        post("/consumosPrueba", (req, res) -> {
            Persona persona = gson.fromJson(req.body(), Persona.class);

            res.status(200);//por defecto 200
            return persona;
        }, gson::toJson);


        get("/barrios", (req, res) -> {
            res.type("application/json");

            List<Barrio> lista = new ArrayList<Barrio>(repositorio.obtenerTodosLosBarrios());

            lista.forEach(a->a.setCantPersonas());
            lista.forEach(a->a.setPersonasParaDevolverJson());

            return lista;
        }, gson::toJson);

        get("/barriosPrueba", (req, res) -> {
            res.type("application/json");

            List<Barrio> lista = new ArrayList<>();

            Barrio barrio1 =new Barrio();
            barrio1.setNombre("Lanus");
            Barrio barrio2 =new Barrio();
            barrio2.setNombre("La boca");
            Barrio barrio3 =new Barrio();
            barrio3.setNombre("Constitucion");

            Persona persona1=new Persona();
            persona1.setBarrio(barrio1);
            persona1.setApellido("alberto");
            persona1.setNombre("muñoz");
            barrio1.getPersonas().add(persona1);

            Persona persona2=new Persona();
            persona2.setBarrio(barrio2);
            persona2.setApellido("julian");
            persona2.setNombre("alvarez");
            barrio2.getPersonas().add(persona2);

            lista.add(barrio1);
            lista.add(barrio2);
            lista.add(barrio3);

            lista.forEach(a->a.setCantPersonas());
            lista.forEach(a->a.setPersonasParaDevolverJson());



            return lista;
        }, gson::toJson);

    }



}
