package es.unizar.eina.T223_comidas;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import es.unizar.eina.T223_comidas.database.Plato;
import es.unizar.eina.T223_comidas.database.PlatoRepository;
import es.unizar.eina.T223_comidas.ui.MainActivity;
import es.unizar.eina.T223_comidas.ui.PlatoViewModel;
import es.unizar.eina.T223_comidas.ui.Platos;

@RunWith(AndroidJUnit4.class)
public class SmokeTestPlatos {
    private Plato newPlato;

    @Rule
    public ActivityScenarioRule<Platos> scenarioRule = new ActivityScenarioRule<>(Platos.class);

    @After
    public void tearDown(){

        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PlatoRepository mPlatoViewModel = activity.getPlatoRepository();

            mPlatoViewModel.delete(newPlato);
        });
    }

    @Test
    public void testCreationIncreasesNumberOfPlatos() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PlatoRepository mPlatoRepository= activity.getPlatoRepository();

            int platosIni = mPlatoRepository.getNumeroDePlatos();

            newPlato = new Plato("prueba", "desc_prueba", "PRIMERO", 1.0);
            long id = mPlatoRepository.insert(newPlato);
            newPlato.setId((int)id);

            int platosFin = mPlatoRepository.getNumeroDePlatos();

            assertEquals(platosIni + 1, platosFin);
        });

    }


    @Test
    public void testCreationAndVerifyPlatos() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PlatoRepository mPlatoRepository = activity.getPlatoRepository();

            newPlato = new Plato("prueba", "desc_prueba", "PRIMERO", 1.0);
            long id = mPlatoRepository.insert(newPlato);
            newPlato.setId((int)id);

            Plato platoRecuperado = mPlatoRepository.getPlatoById(id);

            assertEquals(newPlato, platoRecuperado);
        });

    }

}