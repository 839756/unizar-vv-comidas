package es.unizar.eina.T223_comidas;

import static org.junit.Assert.*;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import es.unizar.eina.T223_comidas.database.Plato;
import es.unizar.eina.T223_comidas.database.PlatoRepository;
import es.unizar.eina.T223_comidas.ui.Platos;

@RunWith(AndroidJUnit4.class)
public class UpdateTestPlatos {
    private Plato newPlato;
    private long id;

    @Rule
    public ActivityScenarioRule<Platos> scenarioRule = new ActivityScenarioRule<>(Platos.class);

    @Before
    public void createPlato(){
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PlatoRepository mPlatoRepository = activity.getPlatoRepository();

            this.newPlato = new Plato("Plato 1", "Descripción", "PRIMERO", 33.0);
            this.id = mPlatoRepository.insert(newPlato);
            newPlato.setId((int)id);
        });
    }
    
    @After
    public void tearDown(){
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PlatoRepository mPlatoRepository = activity.getPlatoRepository();

            mPlatoRepository.delete(newPlato);
        });
    }

    // ==============================
    // Clases de equivalencia válidas
    // ==============================

    @Test
    public void testModNombrePlato() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PlatoRepository mPlatoRepository= activity.getPlatoRepository();

            Plato platoMod = new Plato("Plato 1 mod", "Descripción", "PRIMERO", 33.0);
            platoMod.setId((int)this.id);

            int numFilMod = mPlatoRepository.update(platoMod);

            assertEquals("El resultado esperado no es el deseado: numFilMod != 1",1, numFilMod);
        });
    }

    @Test
    public void testModDescripcionPlato() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PlatoRepository mPlatoRepository= activity.getPlatoRepository();


            Plato platoMod = new Plato("Plato 1", "Descripción mod", "PRIMERO", 33.0);
            platoMod.setId((int)this.id);

            int numFilMod = mPlatoRepository.update(platoMod);

            assertEquals("El resultado esperado no es el deseado: numFilMod != 1",1, numFilMod);
        });
    }

    @Test
    public void testModCategoriaPlato() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PlatoRepository mPlatoRepository= activity.getPlatoRepository();

            Plato platoMod = new Plato("Plato 1", "Descripción", "SEGUNDO", 33.0);
            platoMod.setId((int)this.id);

            int numFilMod = mPlatoRepository.update(platoMod);

            assertEquals("El resultado esperado no es el deseado: numFilMod != 1",1, numFilMod);
        });
    }

    @Test
    public void testModCategoriaPlatoPostre() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PlatoRepository mPlatoRepository= activity.getPlatoRepository();

            Plato platoMod = new Plato("Plato 1", "Descripción", "POSTRE", 33.0);
            platoMod.setId((int)this.id);

            int numFilMod = mPlatoRepository.update(platoMod);

            assertEquals("El resultado esperado no es el deseado: numFilMod != 1",1, numFilMod);
        });
    }

    @Test
    public void testModPrecioPlato() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PlatoRepository mPlatoRepository= activity.getPlatoRepository();

            Plato platoMod = new Plato("Plato 1", "Descripción", "PRIMERO", 32.0);
            platoMod.setId((int)this.id);

            int numFilMod = mPlatoRepository.update(platoMod);

            assertEquals("El resultado esperado no es el deseado: numFilMod != 1",1, numFilMod);
        });
    }

    
    // =================================
    // Clases de equivalencia no válidas
    // =================================

    @Test
    public void testNombreNullPlato() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PlatoRepository mPlatoRepository= activity.getPlatoRepository();

            Plato platoMod = new Plato(null, "Descripción", "PRIMERO", 33.0);
            platoMod.setId((int)this.id);

            int numFilMod = mPlatoRepository.update(platoMod);

            assertEquals("El resultado esperado no es el deseado: numFilMod != 0",0, numFilMod);
        });
    }

    @Test
    public void testCadenaVacíaPlato() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PlatoRepository mPlatoRepository= activity.getPlatoRepository();

            Plato platoMod = new Plato("", "Descripción", "PRIMERO", 33.0);
            platoMod.setId((int)this.id);

            int numFilMod = mPlatoRepository.update(platoMod);

            assertEquals("El resultado esperado no es el deseado: numFilMod != 0",0, numFilMod);
        });
    }

    @Test
    public void testOtraCategoriaPlato() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PlatoRepository mPlatoRepository= activity.getPlatoRepository();

            Plato platoMod = new Plato("Plato 1", "Descripción", "OTRO", 33.0);
            platoMod.setId((int)this.id);

            int numFilMod = mPlatoRepository.update(platoMod);

            assertEquals("El resultado esperado no es el deseado: numFilMod != 0",0, numFilMod);
        });
    }

    @Test
    public void testCategoriaNullPlato() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PlatoRepository mPlatoRepository= activity.getPlatoRepository();

            Plato platoMod = new Plato("Plato 1", "Descripción", null, 33.0);
            platoMod.setId((int)this.id);

            int numFilMod = mPlatoRepository.update(platoMod);

            assertEquals("El resultado esperado no es el deseado: numFilMod != 0",0, numFilMod);
        });
    }

    @Test
    public void testPrecioNegativoPlato() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PlatoRepository mPlatoRepository= activity.getPlatoRepository();

            Plato platoMod = new Plato("Plato 1", "Descripción", "PRIMERO", -33.0);
            platoMod.setId((int)this.id);

            int numFilMod = mPlatoRepository.update(platoMod);

            assertEquals("El resultado esperado no es el deseado: numFilMod != 0",0, numFilMod);
        });
    }

}