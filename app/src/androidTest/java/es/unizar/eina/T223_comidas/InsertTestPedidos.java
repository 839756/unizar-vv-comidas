package es.unizar.eina.T223_comidas;

import static org.junit.Assert.*;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import es.unizar.eina.T223_comidas.database.Pedido;
import es.unizar.eina.T223_comidas.database.PedidoRepository;
import es.unizar.eina.T223_comidas.ui.Pedidos;

@RunWith(AndroidJUnit4.class)
public class InsertTestPedidos {

    private Pedido newPedido;

    public static String nextValidOpeningTime() {
        return nextValidOpeningTime(new Date());
    }
    public static String nextValidOpeningTime(Date fromDate) {
        Calendar time = Calendar.getInstance();
        time.setTime(fromDate);
        time.add(Calendar.HOUR_OF_DAY, 24);
        if (time.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            time.add(Calendar.HOUR_OF_DAY, 24);
        }
        time.set(Calendar.HOUR_OF_DAY, 19);
        time.set(Calendar.MINUTE, 30);
        return new SimpleDateFormat("dd/MM/yyyy/HH:mm").format(time.getTime());
    }


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


            newPedido = new Pedido("Pedido 1", 633333333, nextValidOpeningTime(), "SOLICITADO");

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

            newPedido = new Pedido("Pedido 1", 633333333, nextValidOpeningTime(), "PREPARADO");

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

            newPedido = new Pedido("Pedido 1", 633333333, nextValidOpeningTime(), "RECOGIDO");
          
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

            newPedido = new Pedido(null, 633333333, nextValidOpeningTime(), "SOLICITADO");

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

            newPedido = new Pedido("", 633333333, nextValidOpeningTime(), "SOLICITADO");

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

            newPedido = new Pedido("Pedido 1", 633, nextValidOpeningTime(), "SOLICITADO");

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

            newPedido = new Pedido("Pedido 1", 633333333, "21/04/2025/19:30", "SOLICITADO");
            long id = mPedidoRepository.insert(newPedido);
            newPedido.setId((int)id);

            assertEquals("El resultado esperado no es el deseado", -1, id);
        });
    }


    @Test
    public void testHoraInvalidaPedidoSup() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PedidoRepository mPedidoRepository= activity.getPedidoRepository();

            newPedido = new Pedido("Pedido 1", 633333333, "24/04/2025/23:00", "SOLICITADO");
            long id = mPedidoRepository.insert(newPedido);
            newPedido.setId((int)id);

            assertEquals("El resultado esperado no es el deseado", -1, id);
        });
    }

    @Test
    public void testHoraInvalidaPedidoInf() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PedidoRepository mPedidoRepository= activity.getPedidoRepository();

            newPedido = new Pedido("Pedido 1", 633333333, "24/04/2025/18:30", "SOLICITADO");
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

            newPedido = new Pedido("Pedido 1", 633333333, nextValidOpeningTime(), "OTRO");

            long id = mPedidoRepository.insert(newPedido);
            newPedido.setId((int)id);

            assertEquals("El resultado esperado no es el deseado", -1, id);
        });
    }
}
