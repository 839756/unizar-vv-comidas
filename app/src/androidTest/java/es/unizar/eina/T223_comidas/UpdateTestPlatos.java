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

            int platosIni = mPlatoRepository.getNumeroDePlatos();

            platoMod = new Plato("Plato 1 mod", "Descripción", "PRIMERO", 33.0);
            platoMod.setId((int)this.id);

            mPlatoRepository.update(newPlato);

            int platosFin = mPlatoRepository.getNumeroDePlatos();

            
            Plato platoRecuperado = mPlatoRepository.getPlatoById(id);

            assertTrue("El resultado esperado no es el deseado", id>0);
            assertNotEquals(newPlato, platoRecuperado);
            assertEquals(platosIni, platosFin);
        });
    }

    @Test
    public void testModDescripcionPlato() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PlatoRepository mPlatoRepository= activity.getPlatoRepository();

            int platosIni = mPlatoRepository.getNumeroDePlatos();

            platoMod = new Plato("Plato 1", "Descripción mod", "PRIMERO", 33.0);
            platoMod.setId((int)this.id);

            mPlatoRepository.update(newPlato);

            int platosFin = mPlatoRepository.getNumeroDePlatos();

            
            Plato platoRecuperado = mPlatoRepository.getPlatoById(id);

            assertTrue("El resultado esperado no es el deseado", id>0);
            assertNotEquals(newPlato, platoRecuperado);
            assertEquals(platosIni, platosFin);
        });
    }

    @Test
    public void testModCategoriaPlato() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PlatoRepository mPlatoRepository= activity.getPlatoRepository();

            int platosIni = mPlatoRepository.getNumeroDePlatos();

            platoMod = new Plato("Plato 1", "Descripción", "SEGUNDO", 33.0);
            platoMod.setId((int)this.id);

            mPlatoRepository.update(newPlato);

            int platosFin = mPlatoRepository.getNumeroDePlatos();

            
            Plato platoRecuperado = mPlatoRepository.getPlatoById(id);

            assertTrue("El resultado esperado no es el deseado", id>0);
            assertNotEquals(newPlato, platoRecuperado);
            assertEquals(platosIni, platosFin);
        });
    }

    @Test
    public void testModPrecioPlato() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PlatoRepository mPlatoRepository= activity.getPlatoRepository();

            int platosIni = mPlatoRepository.getNumeroDePlatos();

            platoMod = new Plato("Plato 1", "Descripción", "PRIMERO", 32.0);
            platoMod.setId((int)this.id);

            mPlatoRepository.update(newPlato);

            int platosFin = mPlatoRepository.getNumeroDePlatos();

            
            Plato platoRecuperado = mPlatoRepository.getPlatoById(id);

            assertTrue("El resultado esperado no es el deseado", id>0);
            assertNotEquals(newPlato, platoRecuperado);
            assertEquals(platosIni, platosFin);
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

            int platosIni = mPlatoRepository.getNumeroDePlatos();

            newPlato = new Plato(null, "Descripción", "POSTRE", 33.0);
            long id = mPlatoRepository.insert(newPlato);
            newPlato.setId((int)id);

            int platosFin = mPlatoRepository.getNumeroDePlatos();

            assertEquals("El resultado esperado no es el deseado", -1,id);
            assertEquals(platosIni, platosFin);
        });
    }

    @Test
    public void testCadenaVacíaPlato() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PlatoRepository mPlatoRepository= activity.getPlatoRepository();

            int platosIni = mPlatoRepository.getNumeroDePlatos();

            newPlato = new Plato("", "Descripción", "POSTRE", 33.0);
            long id = mPlatoRepository.insert(newPlato);
            newPlato.setId((int)id);

            int platosFin = mPlatoRepository.getNumeroDePlatos();

            assertEquals("El resultado esperado no es el deseado", -1,id);
            assertEquals(platosIni, platosFin);
        });
    }

    @Test
    public void testOtraCategoriaPlato() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PlatoRepository mPlatoRepository= activity.getPlatoRepository();

            int platosIni = mPlatoRepository.getNumeroDePlatos();

            newPlato = new Plato("Plato 1", "Descripción", "OTRO", 33.0);
            long id = mPlatoRepository.insert(newPlato);
            newPlato.setId((int)id);

            int platosFin = mPlatoRepository.getNumeroDePlatos();

            assertEquals("El resultado esperado no es el deseado", -1,id);
            assertEquals(platosIni, platosFin);
        });
    }

    @Test
    public void testPrecioNegativoPlato() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PlatoRepository mPlatoRepository= activity.getPlatoRepository();

            int platosIni = mPlatoRepository.getNumeroDePlatos();

            newPlato = new Plato("Plato 1", "Descripción", "POSTRE", -33.0);
            long id = mPlatoRepository.insert(newPlato);
            newPlato.setId((int)id);

            int platosFin = mPlatoRepository.getNumeroDePlatos();

            assertEquals("El resultado esperado no es el deseado", -1,id);
            assertEquals(platosIni, platosFin);
        });
    }
}