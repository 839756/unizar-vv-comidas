package es.unizar.eina.T223_comidas;

import static org.junit.Assert.assertEquals;

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

public class DeleteTestPlatos {

    @Rule
    public ActivityScenarioRule<Platos> scenarioRule = new ActivityScenarioRule<>(Platos.class);

    // ==============================
    // Clases de equivalencia válidas
    // ==============================

    @Test
    public void testBorradoPlato() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PlatoRepository mPlatoRepository= activity.getPlatoRepository();

            Plato newPlato = new Plato("Plato 1", "Descripción", "PRIMERO", 33.0);
            long id = mPlatoRepository.insert(newPlato);
            newPlato.setId((int)id);

            int numFilDel = mPlatoRepository.delete(newPlato);

            assertEquals("El resultado esperado no es el deseado: numFilDel != 1",1, numFilDel);
        });
    }

    // =================================
    // Clases de equivalencia no válidas
    // =================================

    @Test
    public void testBorradoPlatoNull() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PlatoRepository mPlatoRepository= activity.getPlatoRepository();

            int numFilDel = mPlatoRepository.delete(null);

            assertEquals("El resultado esperado no es el deseado: numFilDel != 0",0, numFilDel);
        });
    }

    @Test
    public void testBorradoPlatoNoExistente() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PlatoRepository mPlatoRepository= activity.getPlatoRepository();

            Plato newPlato2 = new Plato("Plato 2", "Descripción", "PRIMERO", 33.0);
            int numFilDel = mPlatoRepository.update(newPlato2);

            assertEquals("El resultado esperado no es el deseado: numFilDel != 0",0, numFilDel);
        });
    }

}
