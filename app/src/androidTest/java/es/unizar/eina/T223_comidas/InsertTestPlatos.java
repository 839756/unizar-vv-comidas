package es.unizar.eina.T223_comidas;

import static org.junit.Assert.*;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import es.unizar.eina.T223_comidas.database.Plato;
import es.unizar.eina.T223_comidas.database.PlatoRepository;
import es.unizar.eina.T223_comidas.ui.Platos;

@RunWith(AndroidJUnit4.class)
public class InsertTestPlatos {

    private Plato newPlato;

    @Rule
    public ActivityScenarioRule<Platos> scenarioRule = new ActivityScenarioRule<>(Platos.class);

    // ==============================
    // Clases de equivalencia válidas
    // ==============================

    @Test
    public void testPrimeroPlato() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PlatoRepository mPlatoRepository= activity.getPlatoRepository();

            newPlato = new Plato("Plato 1", "Descripción", "PRIMERO", 33.0);
            long id = mPlatoRepository.insert(newPlato);
            newPlato.setId((int)id);

            assertTrue("El resultado esperado no es el deseado", id>0);
        });
    }

    @Test
    public void testSegundoPlato() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PlatoRepository mPlatoRepository= activity.getPlatoRepository();

            newPlato = new Plato("Plato 1", "Descripción", "SEGUNDO", 33.0);
            long id = mPlatoRepository.insert(newPlato);
            newPlato.setId((int)id);

            assertTrue("El resultado esperado no es el deseado", id>0);
        });
    }

    @Test
    public void testPostrePlato() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PlatoRepository mPlatoRepository= activity.getPlatoRepository();

            newPlato = new Plato("Plato 1", "Descripción", "POSTRE", 33.0);
            long id = mPlatoRepository.insert(newPlato);
            newPlato.setId((int)id);

            assertTrue("El resultado esperado no es el deseado", id>0);
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

            newPlato = new Plato(null, "Descripción", "POSTRE", 33.0);
            long id = mPlatoRepository.insert(newPlato);
            newPlato.setId((int)id);

            assertEquals("El resultado esperado no es el deseado", -1,id);
        });
    }

    @Test
    public void testCadenaVacíaPlato() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PlatoRepository mPlatoRepository= activity.getPlatoRepository();

            newPlato = new Plato("", "Descripción", "POSTRE", 33.0);
            long id = mPlatoRepository.insert(newPlato);
            newPlato.setId((int)id);

            assertEquals("El resultado esperado no es el deseado", -1,id);
        });
    }

    @Test
    public void testOtraCategoriaPlato() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PlatoRepository mPlatoRepository= activity.getPlatoRepository();

            newPlato = new Plato("Plato 1", "Descripción", "OTRO", 33.0);
            long id = mPlatoRepository.insert(newPlato);
            newPlato.setId((int)id);

            assertEquals("El resultado esperado no es el deseado", -1,id);
        });
    }

    @Test
    public void testCategoriaNullPlato() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PlatoRepository mPlatoRepository= activity.getPlatoRepository();

            newPlato = new Plato("Plato 1", "Descripción", null, 33.0);
            long id = mPlatoRepository.insert(newPlato);
            newPlato.setId((int)id);

            assertEquals("El resultado esperado no es el deseado", -1,id);
        });
    }

    @Test
    public void testPrecioNegativoPlato() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PlatoRepository mPlatoRepository= activity.getPlatoRepository();

            newPlato = new Plato("Plato 1", "Descripción", "POSTRE", -33.0);
            long id = mPlatoRepository.insert(newPlato);
            newPlato.setId((int)id);

            assertEquals("El resultado esperado no es el deseado", -1,id);
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
}