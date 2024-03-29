package es.unizar.eina.T223_comidas;

import static org.junit.Assert.assertEquals;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import es.unizar.eina.T223_comidas.database.Pedido;
import es.unizar.eina.T223_comidas.database.PedidoRepository;
import es.unizar.eina.T223_comidas.ui.Pedidos;

@RunWith(AndroidJUnit4.class)
public class DeleteTestPedidos {

    @Rule
    public ActivityScenarioRule<Pedidos> scenarioRule = new ActivityScenarioRule<>(Pedidos.class);

    // ==============================
    // Clases de equivalencia válidas
    // ==============================

    @Test
    public void testBorradoPedido() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PedidoRepository mPedidoRepository= activity.getPedidoRepository();

            Pedido newPedido = new Pedido("Pedido 1", 633333333, "24/04/2024/19:30", "SOLICITADO");
            long id = mPedidoRepository.insert(newPedido);
            newPedido.setId((int)id);

            int numFilDel = mPedidoRepository.delete(newPedido);

            assertEquals("El resultado esperado no es el deseado: numFilDel != 1",1, numFilDel);
        });
    }

    // =================================
    // Clases de equivalencia no válidas
    // =================================

    @Test
    public void testBorradoPedidoNull() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PedidoRepository mPedidoRepository= activity.getPedidoRepository();

            int numFilDel = mPedidoRepository.delete(null);

            assertEquals("El resultado esperado no es el deseado: numFilDel != 0",0, numFilDel);
        });
    }

    @Test
    public void testBorradoPedidoNoExistente() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PedidoRepository mPedidoRepository= activity.getPedidoRepository();

            Pedido newPedido2 = new Pedido("Pedido 2", 633333333, "24/04/2024/19:30", "SOLICITADO");
            int numFilDel = mPedidoRepository.update(newPedido2);

            assertEquals("El resultado esperado no es el deseado: numFilDel != 0",0, numFilDel);
        });
    }

}
