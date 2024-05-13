package es.unizar.eina.T223_comidas;

import static es.unizar.eina.T223_comidas.InsertTestPedidos.nextValidOpeningTime;
import static org.junit.Assert.assertEquals;

import android.util.Log;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import es.unizar.eina.T223_comidas.database.Pedido;
import es.unizar.eina.T223_comidas.database.PedidoRepository;
import es.unizar.eina.T223_comidas.ui.Pedidos;

@RunWith(AndroidJUnit4.class)
public class VolumenTest {
    private Pedido newPedido;
    private long id;

    @Rule
    public ActivityScenarioRule<Pedidos> scenarioRule = new ActivityScenarioRule<>(Pedidos.class);

    @Before
    public void createPedido(){
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PedidoRepository mPedidoRepository = activity.getPedidoRepository();

            mPedidoRepository.deleteAll();

            this.newPedido = new Pedido("Pedido 1", 633333333,  nextValidOpeningTime(), "SOLICITADO");
            this.id = mPedidoRepository.insert(newPedido);
            newPedido.setId((int)id);
        });
    }
    
    @After
    public void tearDown(){
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PedidoRepository mPedidoRepository = activity.getPedidoRepository();

            mPedidoRepository.deleteAll();
        });
    }

    // ==============================
    // Prueba
    // ==============================

    @Test
    public void testVolumenPedidos() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PedidoRepository mPedidoRepository= activity.getPedidoRepository();
            long result;

            for(int i = 0; i < 2000; i++) {
                try {

                    Pedido pedidoMod = new Pedido(newPedido.getCliente() + " " + i,
                            633333333, "22/04/2024/19:30", "SOLICITADO");

                    result = mPedidoRepository.insert(pedidoMod);

                    if (i == 1999) {
                        assertEquals("Se ha sobrepasado el límite", -1, result);
                    }

                } catch (Exception e) {
                    Log.d("UnitTest", "Prueba ha fallado en el test de volumen de pedidos.");
                }
            }

        });
    }
    
}
