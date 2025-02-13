package es.unizar.eina.T223_comidas;

import static es.unizar.eina.T223_comidas.InsertTestPedidos.nextValidOpeningTime;
import static org.junit.Assert.*;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import es.unizar.eina.T223_comidas.database.Pedido;
import es.unizar.eina.T223_comidas.database.PedidoRepository;
import es.unizar.eina.T223_comidas.ui.Pedidos;

@RunWith(AndroidJUnit4.class)
public class UpdateTestPedidos {
    private Pedido newPedido;
    private long id;

    @Rule
    public ActivityScenarioRule<Pedidos> scenarioRule = new ActivityScenarioRule<>(Pedidos.class);

    @Before
    public void createPedido(){
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PedidoRepository mPedidoRepository = activity.getPedidoRepository();

            this.newPedido = new Pedido("Pedido 1", 633333333, nextValidOpeningTime(), "SOLICITADO");

            this.id = mPedidoRepository.insert(newPedido);
            newPedido.setId((int)id);
        });
    }
    
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
    public void testModNombrePedido() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PedidoRepository mPedidoRepository= activity.getPedidoRepository();

            Pedido pedidoMod = new Pedido("Pedido 1 mod", 633333333, nextValidOpeningTime(), "SOLICITADO");

            pedidoMod.setId((int)this.id);

            int numFilMod = mPedidoRepository.update(pedidoMod);

            assertEquals("El resultado esperado no es el deseado: numFilMod != 1",1, numFilMod);
        });
    }

    @Test
    public void testModTelefonoPedido() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PedidoRepository mPedidoRepository= activity.getPedidoRepository();

            Pedido pedidoMod = new Pedido("Pedido 1", 633333332, nextValidOpeningTime(), "SOLICITADO");

            pedidoMod.setId((int)this.id);

            int numFilMod = mPedidoRepository.update(pedidoMod);

            assertEquals("El resultado esperado no es el deseado: numFilMod != 1",1, numFilMod);
        });
    }

    @Test
    public void testModFechaPedido() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PedidoRepository mPedidoRepository= activity.getPedidoRepository();

            Date nextWeek = new Date(new Date().getTime()+7*24*60*60*1000);
            Pedido pedidoMod = new Pedido("Pedido 1", 633333333, nextValidOpeningTime(nextWeek), "SOLICITADO");

            pedidoMod.setId((int)this.id);

            int numFilMod = mPedidoRepository.update(pedidoMod);

            assertEquals("El resultado esperado no es el deseado: numFilMod != 1",1, numFilMod);
        });
    }

    @Test
    public void testModEstadoPedidoRecogido() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PedidoRepository mPedidoRepository= activity.getPedidoRepository();

            Pedido pedidoMod = new Pedido("Pedido 1", 633333333, nextValidOpeningTime(), "RECOGIDO");

            pedidoMod.setId((int)this.id);

            int numFilMod = mPedidoRepository.update(pedidoMod);

            assertEquals("El resultado esperado no es el deseado: numFilMod != 1",1, numFilMod);
        });
    }

    // =================================
    // Clases de equivalencia no válidas
    // =================================

    @Test
    public void testModNombreNullPedido() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PedidoRepository mPedidoRepository= activity.getPedidoRepository();

            Pedido pedidoMod = new Pedido(null, 633333333, nextValidOpeningTime(), "SOLICITADO");

            pedidoMod.setId((int)this.id);

            int numFilMod = mPedidoRepository.update(pedidoMod);

            assertEquals("El resultado esperado no es el deseado: numFilMod != 0",0, numFilMod);
        });
    }

    @Test
    public void testModNombreVacioPedido() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PedidoRepository mPedidoRepository= activity.getPedidoRepository();

            Pedido pedidoMod = new Pedido("", 633333333, nextValidOpeningTime(), "SOLICITADO");

            pedidoMod.setId((int)this.id);

            int numFilMod = mPedidoRepository.update(pedidoMod);

            assertEquals("El resultado esperado no es el deseado: numFilMod != 0",0, numFilMod);
        });
    }

    @Test
    public void testModTelefonoCortoPedido() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PedidoRepository mPedidoRepository= activity.getPedidoRepository();

            Pedido pedidoMod = new Pedido("Pedido 1", 633, nextValidOpeningTime(), "SOLICITADO");

            pedidoMod.setId((int)this.id);

            int numFilMod = mPedidoRepository.update(pedidoMod);

            assertEquals("El resultado esperado no es el deseado: numFilMod != 0",0, numFilMod);
        });
    }

    @Test
    public void testModFechaIncorrectaPedido() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PedidoRepository mPedidoRepository= activity.getPedidoRepository();

            Pedido pedidoMod = new Pedido("Pedido 1", 633333333, "21/04/2025/19:30", "SOLICITADO");
            pedidoMod.setId((int)this.id);

            int numFilMod = mPedidoRepository.update(pedidoMod);

            assertEquals("El resultado esperado no es el deseado: numFilMod != 0",0, numFilMod);
        });
    }


    @Test
    public void testModHoraIncorrectaPedidoSup() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PedidoRepository mPedidoRepository= activity.getPedidoRepository();

            Pedido pedidoMod = new Pedido("Pedido 1", 633333333, "24/04/2025/23:00", "SOLICITADO");
            pedidoMod.setId((int)this.id);

            int numFilMod = mPedidoRepository.update(pedidoMod);

            assertEquals("El resultado esperado no es el deseado: numFilMod != 0",0, numFilMod);
        });
    }

    @Test
    public void testModHoraIncorrectaPedidoInf() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PedidoRepository mPedidoRepository= activity.getPedidoRepository();

            Pedido pedidoMod = new Pedido("Pedido 1", 633333333, "24/04/2025/18:30", "SOLICITADO");
            pedidoMod.setId((int)this.id);

            int numFilMod = mPedidoRepository.update(pedidoMod);

            assertEquals("El resultado esperado no es el deseado: numFilMod != 0",0, numFilMod);
        });
    }

    @Test
    public void testModFechaPasadaPedido() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PedidoRepository mPedidoRepository= activity.getPedidoRepository();

            Pedido pedidoMod = new Pedido("Pedido 1", 633333333, "24/12/2023/19:30", "SOLICITADO");
            pedidoMod.setId((int)this.id);

            int numFilMod = mPedidoRepository.update(pedidoMod);

            assertEquals("El resultado esperado no es el deseado: numFilMod != 0",0, numFilMod);
        });
    }

    @Test
    public void testModEstadoInvalidoPedido() {
        scenarioRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PedidoRepository mPedidoRepository= activity.getPedidoRepository();

            Pedido pedidoMod = new Pedido("Pedido 1", 633333333, nextValidOpeningTime(), "OTRO");

            pedidoMod.setId((int)this.id);

            int numFilMod = mPedidoRepository.update(pedidoMod);

            assertEquals("El resultado esperado no es el deseado: numFilMod != 0",0, numFilMod);
        });
    }
}
