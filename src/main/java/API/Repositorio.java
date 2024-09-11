package API;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;

public class Repositorio implements WithSimplePersistenceUnit {

    public void agregarBarrio(Barrio barrio){
        withTransaction(()->entityManager().persist(barrio));;
    }
    public void actualizarBarrio(Barrio barrio){
        withTransaction(()->entityManager().merge(barrio));
    }
    @SuppressWarnings("unchecked")
    public List<Barrio> obtenerBarrioPorNombre(String nombre){
        return entityManager()
                .createQuery("from " + Barrio.class.getName() + " where nombre =:name")
                .setParameter("name",nombre).getResultList();
    }
    @SuppressWarnings("unchecked")

    public List<Barrio> obtenerTodosLosBarrios(){
        return entityManager()
                .createQuery("from " + Barrio.class.getName())
                .getResultList();
    }
    //________________________________________________________________________________________
    public void agregarPersona(Persona persona){
        withTransaction(()->entityManager().persist(persona));
    }
    public void actualizarPersona(Persona persona){
        withTransaction(()->entityManager().merge(persona));
    }
    @SuppressWarnings("unchecked")
    public List<Persona> obtenerPersonaPorNombreyApellido(String nombre, String apellido){
        return entityManager()
                .createQuery("from " + Persona.class.getName() + " where nombre =:name and apellido =:apellido")
                .setParameter("name",nombre).setParameter("apellido",apellido).getResultList();

    }
    @SuppressWarnings("unchecked")
    public List<Persona> obtenerPersonasConConsumoEnElUltimoDia(){
        return entityManager()
                .createQuery("from " + Persona.class.getName() + " where ultimoConsumo").getResultList();
    }


    public Repositorio() {
    }
}
