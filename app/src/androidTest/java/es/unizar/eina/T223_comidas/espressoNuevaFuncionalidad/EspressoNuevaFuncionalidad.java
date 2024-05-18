package es.unizar.eina.T223_comidas.espressoNuevaFuncionalidad;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import es.unizar.eina.T223_comidas.R;
import es.unizar.eina.T223_comidas.database.Pedido;
import es.unizar.eina.T223_comidas.database.PedidoRepository;
import es.unizar.eina.T223_comidas.database.Plato;
import es.unizar.eina.T223_comidas.database.PlatoRepository;
import es.unizar.eina.T223_comidas.ui.MainActivity;

public class EspressoNuevaFuncionalidad {

    private int numeroPedidos;
    private final String NOMBRE_PEDIDO = "Test pedido";
    private final String FECHA_PEDIDO_PREVISTA_1 = "14/06/2024/20:00";
    private final String FECHA_PEDIDO_PREVISTA_2 ="17/01/2025/20:00";
    // Poner la fecha deseada en vigente
    private final String FECHA_PEDIDO_VIGENTE = "18/05/2024/20:00";
    private final String SUFIJO_PEDIDO_PREVISTO_1 = " - " + FECHA_PEDIDO_PREVISTA_1;
    private final String SUFIJO_PEDIDO_PREVISTO_2 = " - " + FECHA_PEDIDO_PREVISTA_2;
    private final String SUFIJO_PEDIDO_VIGENTE = " - " + FECHA_PEDIDO_VIGENTE;


    @Rule
    public ActivityScenarioRule<MainActivity> mActivityRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void platosYPedidosSetUp() {
        mActivityRule.getScenario().onActivity(activity -> {
            PedidoRepository mPedidoRepository = activity.getPedidoRepository();

            mPedidoRepository.deleteAll();

            Pedido newPedido = new Pedido(NOMBRE_PEDIDO + " " + numeroPedidos, 612345678, FECHA_PEDIDO_VIGENTE, "SOLICITADO");
            long id = mPedidoRepository.insert(newPedido);
            numeroPedidos++;

            newPedido = new Pedido(NOMBRE_PEDIDO + " " + numeroPedidos, 612345678, FECHA_PEDIDO_PREVISTA_1, "SOLICITADO");
            id = mPedidoRepository.insert(newPedido);
            numeroPedidos++;

            newPedido = new Pedido(NOMBRE_PEDIDO + " " + numeroPedidos, 612345678, FECHA_PEDIDO_PREVISTA_2, "SOLICITADO");
            id = mPedidoRepository.insert(newPedido);
            numeroPedidos++;

        });
    }

    @Test
    public void pedidosVigentesTest() {
        caminoPantallaPedidos();
        caminoOrdenarPedidosVigentes();
        comprobarPedidosVigentes();
    }

    @Test
    public void pedidosPrevistosTest() {
        caminoPantallaPedidos();
        caminoOrdenarPedidosPrevistos();
        comprobarPedidosPrevistos();
    }

    @After
    public void tearDown(){
        mActivityRule.getScenario().onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PlatoRepository mPlatoRepository = activity.getPlatoRepository();
            PedidoRepository mPedidoRepository = activity.getPedidoRepository();

            mPlatoRepository.deleteAll();
            mPedidoRepository.deleteAll();
        });
    }

    private void caminoPantallaPedidos() {
        onView(withText("PEDIDOS")).perform(click());
    }

    private void caminoOrdenarPedidosVigentes() {
        onView(withText("ORDENAR")).perform(click());
        onView(withText("PEDIDOS VIGENTES")).perform(click());
    }

    private void caminoOrdenarPedidosPrevistos() {
        onView(withText("ORDENAR")).perform(click());
        onView(withText("PEDIDOS PREVISTOS")).perform(click());
    }

    private void comprobarPedidosVigentes() {
        String nombrePedido = NOMBRE_PEDIDO + " 0"  + SUFIJO_PEDIDO_VIGENTE;

        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.scrollTo(hasDescendant(withText(nombrePedido))));
        onView(withText(nombrePedido)).check(matches(isDisplayed()));
    }

    private void comprobarPedidosPrevistos() {
        String nombrePedido = NOMBRE_PEDIDO + " 1"  + SUFIJO_PEDIDO_PREVISTO_1;

        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.scrollTo(hasDescendant(withText(nombrePedido))));
        onView(withText(nombrePedido)).check(matches(isDisplayed()));

        nombrePedido = NOMBRE_PEDIDO + " 2"  + SUFIJO_PEDIDO_PREVISTO_2;

        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.scrollTo(hasDescendant(withText(nombrePedido))));
        onView(withText(nombrePedido)).check(matches(isDisplayed()));
    }
}
