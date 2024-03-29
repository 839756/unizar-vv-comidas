package es.unizar.eina.T223_comidas;

import android.util.Log;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import es.unizar.eina.T223_comidas.database.Plato;
import es.unizar.eina.T223_comidas.database.PlatoRepository;
import es.unizar.eina.T223_comidas.ui.Platos;

@RunWith(AndroidJUnit4.class)
public class SobrecargaTest {
    private Plato newPlato;
    private long id;

    @Rule
    public ActivityScenarioRule<Platos> scenarioRule = new ActivityScenarioRule<>(Platos.class);

    @Before
    public void createPlato(){
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PlatoRepository mPlatoRepository = activity.getPlatoRepository();

            this.newPlato = new Plato("PruebaSC", "Descripción", "PRIMERO", 33.0);
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
    // Prueba
    // ==============================

    @Ignore("Se ignora porque es un test para hacer que falle la aplicación poniendola al límite")
    @Test
    public void testSobrecargaPedidos() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad

            PlatoRepository mPlatoRepository = activity.getPlatoRepository();

            String descripcion = "a";
            int longitud = 10;

            try {
                while (longitud < 25000000) {
                    descripcion = extendString(descripcion, longitud);

                    Plato platoSC = new Plato("PruebaSC",
                            descripcion,
                            "PRIMERO",
                            33.0);

                    platoSC.setId((int)this.id);

                    mPlatoRepository.update(platoSC);

                    Log.d("UnitTest", "Se ha introducido una descripción de longitd de: " + longitud);

                    longitud += 100000;
                }
            } catch (Exception e){
                Log.d("UnitTest", "Prueba ha fallado en el test de sobrecarga de platos del nombre.");

            }
        });
    }

    private String extendString(String originalString, int maxLength) {
        StringBuilder result = new StringBuilder();

        for (int targetLength = 10; targetLength <= maxLength; targetLength *= 10) {
            StringBuilder extended = new StringBuilder(originalString.length() * (targetLength / originalString.length() + 1));
            while (extended.length() < targetLength) {
                extended.append(originalString);
            }
            result.append(extended.substring(0, targetLength)).append("\n");
        }

        return result.toString();
    }
}
