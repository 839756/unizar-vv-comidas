package es.unizar.eina.T223_comidas;

import static org.junit.Assert.*;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import es.unizar.eina.T223_comidas.database.Pedido;
import es.unizar.eina.T223_comidas.database.PedidoRepository;
import es.unizar.eina.T223_comidas.ui.Pedidos;

@RunWith(AndroidJUnit4.class)
public class InsertTestPedidos {

    private Pedido newPedido;

    @Rule
    public ActivityScenarioRule<Pedidos> scenarioRule = new ActivityScenarioRule<>(Pedidos.class);

    @After
    public void tearDown(){

        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PedidoRepository mPedidoRepository = activity.getPedidoRepository();

            mPedidoRepository.delete(newPedido);
        });
    }

    // ==============================
    // Clases de equivalencia válidas
    // ==============================

    @Test
    public void testPedidoSolicitado() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PedidoRepository mPedidoRepository= activity.getPedidoRepository();

            newPedido = new Pedido("Pedido 1", 633333333, "24/04/2024/19:30", "SOLICITADO");
            long id = mPedidoRepository.insert(newPedido);
            newPedido.setId((int)id);
            
            assertTrue("El resultado esperado no es el deseado", id>0);
        });
    }

    @Test
    public void testPedidoPreparado() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PedidoRepository mPedidoRepository= activity.getPedidoRepository();

            newPedido = new Pedido("Pedido 1", 633333333, "24/04/2024/19:30", "PREPARADO");
            long id = mPedidoRepository.insert(newPedido);
            newPedido.setId((int)id);

            assertTrue("El resultado esperado no es el deseado", id>0);
        });
    }

    @Test
    public void testPedidoRecogido() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PedidoRepository mPedidoRepository= activity.getPedidoRepository();

            newPedido = new Pedido("Pedido 1", 633333333, "24/04/2024/19:30", "RECOGIDO");
            long id = mPedidoRepository.insert(newPedido);
            newPedido.setId((int)id);

            assertTrue("El resultado esperado no es el deseado", id>0);
        });
    }

    // =================================
    // Clases de equivalencia no válidas
    // =================================

    @Test
    public void testClienteNullPedido() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PedidoRepository mPedidoRepository= activity.getPedidoRepository();

            newPedido = new Pedido(null, 633333333, "24/04/2024/19:30", "SOLICITADO");
            long id = mPedidoRepository.insert(newPedido);
            newPedido.setId((int)id);

            assertEquals("El resultado esperado no es el deseado", -1, id);
        });
    }

    @Test
    public void testClienteVacioPedido() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PedidoRepository mPedidoRepository= activity.getPedidoRepository();

            newPedido = new Pedido("", 633333333, "24/04/2024/19:30", "SOLICITADO");
            long id = mPedidoRepository.insert(newPedido);
            newPedido.setId((int)id);

            assertEquals("El resultado esperado no es el deseado", -1, id);
        });
    }

    @Test
    public void testTelefonoCortoPedido() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PedidoRepository mPedidoRepository= activity.getPedidoRepository();

            newPedido = new Pedido("Pedido 1", 633, "24/04/2024/19:30", "SOLICITADO");
            long id = mPedidoRepository.insert(newPedido);
            newPedido.setId((int)id);

            assertEquals("El resultado esperado no es el deseado", -1, id);
        });
    }

    @Test
    public void testDiaInvalidoPedido() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PedidoRepository mPedidoRepository= activity.getPedidoRepository();

            newPedido = new Pedido("Pedido 1", 633333333, "22/04/2024/19:30", "SOLICITADO");
            long id = mPedidoRepository.insert(newPedido);
            newPedido.setId((int)id);

            assertEquals("El resultado esperado no es el deseado", -1, id);
        });
    }

    @Test
    public void testHoraInvalidaPedido() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PedidoRepository mPedidoRepository= activity.getPedidoRepository();

            newPedido = new Pedido("Pedido 1", 633333333, "24/04/2024/18:30", "SOLICITADO");
            long id = mPedidoRepository.insert(newPedido);
            newPedido.setId((int)id);

            assertEquals("El resultado esperado no es el deseado", -1, id);
        });
    }

    @Test
    public void testFechaPasadaPedido() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PedidoRepository mPedidoRepository= activity.getPedidoRepository();

            newPedido = new Pedido("Pedido 1", 633333333, "24/12/2023/19:30", "SOLICITADO");
            long id = mPedidoRepository.insert(newPedido);
            newPedido.setId((int)id);

            assertEquals("El resultado esperado no es el deseado", -1, id);
        });
    }

    @Test
    public void testEstadoInvalidoPedido() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PedidoRepository mPedidoRepository= activity.getPedidoRepository();

            newPedido = new Pedido("Pedido 1", 633333333, "24/04/2024/19:30", "OTRO");
            long id = mPedidoRepository.insert(newPedido);
            newPedido.setId((int)id);

            assertEquals("El resultado esperado no es el deseado", -1, id);
        });
    }
}
