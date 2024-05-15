package es.unizar.eina.T223_comidas.test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.junit.Assert.assertEquals;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;


import org.junit.Rule;

import es.unizar.eina.T223_comidas.database.Pedido;
import es.unizar.eina.T223_comidas.database.PedidoRepository;
import es.unizar.eina.T223_comidas.database.Plato;
import es.unizar.eina.T223_comidas.database.PlatoRepository;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import es.unizar.eina.T223_comidas.R;

import es.unizar.eina.T223_comidas.ui.MainActivity;

public class RunStepsDefinition {

    private int numeroPlatos;

    private int numeroPedidos;

    private final String NOMBRE_PLATOS = "Test plato";
    private final double PRECIO_PLATO = 33.0;
    private final String SUFIJO_PLATO = " - " + PRECIO_PLATO + " €";
    private final String NOMBRE_PEDIDO = "Test pedido";
    private final String FECHA_PEDIDO = "23/05/2024/20:00";
    private final String SUFIJO_PEDIDO = " - " + FECHA_PEDIDO;


    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    private static ActivityScenario<MainActivity> scenario;

    @Before
    public void setUpTest() {
        scenario = ActivityScenario.launch(MainActivity.class);
        scenario.onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PlatoRepository mPlatoRepository = activity.getPlatoRepository();
            PedidoRepository mPedidoRepository = activity.getPedidoRepository();

            mPlatoRepository.deleteAll();
            mPedidoRepository.deleteAll();

            Plato newPlato = new Plato(NOMBRE_PLATOS + " " + numeroPlatos, "Descripción", "PRIMERO", PRECIO_PLATO);
            mPlatoRepository.insert(newPlato);
            numeroPlatos++;

            newPlato = new Plato(NOMBRE_PLATOS + " " + numeroPlatos, "Descripción", "PRIMERO", PRECIO_PLATO);
            mPlatoRepository.insert(newPlato);
            numeroPlatos++;

            newPlato = new Plato(NOMBRE_PLATOS + " " + numeroPlatos, "Descripción", "PRIMERO", PRECIO_PLATO);
            mPlatoRepository.insert(newPlato);
            numeroPlatos++;

            Pedido newPedido = new Pedido(NOMBRE_PEDIDO + " " + numeroPedidos, 633333333, FECHA_PEDIDO, "SOLICITADO");
            long id = mPedidoRepository.insert(newPedido);
            numeroPedidos++;

            newPedido = new Pedido(NOMBRE_PEDIDO + " " + numeroPedidos, 633333333, FECHA_PEDIDO, "SOLICITADO");
            id = mPedidoRepository.insert(newPedido);
            numeroPedidos++;

            newPedido = new Pedido(NOMBRE_PEDIDO + " " + numeroPedidos, 633333333, FECHA_PEDIDO, "SOLICITADO");
            id = mPedidoRepository.insert(newPedido);
            numeroPedidos++;
        });
    }

    @After
    public void tearDown() {
        scenario = ActivityScenario.launch(MainActivity.class);
        scenario.onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PlatoRepository mPlatoRepository = activity.getPlatoRepository();
            PedidoRepository mPedidoRepository = activity.getPedidoRepository();

            mPlatoRepository.deleteAll();
            mPedidoRepository.deleteAll();
        });
    }

    @Given("There are {int} dishes")
    public void there_are_dishes(Integer int1) {
        scenario = ActivityScenario.launch(MainActivity.class);
        scenario.onActivity(activity -> {
            PlatoRepository mPlatoRepository = activity.getPlatoRepository();

            int nPlatos = mPlatoRepository.getNumeroDePlatos();

            assertEquals("No hay el número de platos indicado", int1.intValue(),  nPlatos);
        });
    }

    @Given("There are {int} orders")
    public void there_are_orders(Integer int1 ) {
        scenario = ActivityScenario.launch(MainActivity.class);
        scenario.onActivity(activity -> {
            PedidoRepository mPedidoRepository = activity.getPedidoRepository();

            int nPedidos = mPedidoRepository.getNumeroDePedidos();

            assertEquals("No hay el número de pedidos indicado", int1.intValue(),  nPedidos);
        });
    }


    @When("Add a dish")
    public void add_a_dish() {
        caminoPantallaPlatos();
        caminoCrearPlato();
        caminoConfirmarCrearPlato();
    }

    @When("Add an order")
    public void add_an_order( ) {
        caminoPantallaPedidos();
        caminoCrearPedido();
        caminoConfirmarCrearPedido();
    }

    @When("Delete a dish")
    public void delete_a_dish( ) {
        caminoPantallaPlatos();
        caminoEliminarPlato();
    }

    @When("Delete an order")
    public void delete_an_order( ) {
        caminoPantallaPedidos();
        caminoEliminarPedido();
    }

    @When("Modify a dish")
    public void modify_a_dish( ) {
        caminoPantallaPlatos();
        caminoModificarPlato();
        caminoConfirmarModificarPlato();
    }
    @When("Modify an order")
    public void modify_an_order( ) {
        caminoPantallaPedidos();
        caminoModificarPedido();
        caminoConfirmarModificarPedido();
    }
    @When("Arrenge the orders")
    public void arrenge_the_orders( ) {
        caminoPantallaPedidos();
        caminoOrdenarPedido();
    }

    @When("Arrenge the dishes")
    public void arrenge_the_dishes( ) {
        caminoPantallaPlatos();
        caminoOrdenarPlato();
    }

    @Then("There should be {int} dishes")
    public void there_should_be_dishes(Integer int1) {
        scenario = ActivityScenario.launch(MainActivity.class);
        scenario.onActivity(activity -> {
            PlatoRepository mPlatoRepository = activity.getPlatoRepository();

            int nPlatos = mPlatoRepository.getNumeroDePlatos();

            assertEquals("No hay el número de platos indicado", int1.intValue(),  nPlatos);
        });

    }

    @Then("There should be {int} orders")
    public void there_should_be_orders(Integer int1 ) {
        scenario = ActivityScenario.launch(MainActivity.class);
        scenario.onActivity(activity -> {
            PedidoRepository mPedidoRepository = activity.getPedidoRepository();

            int nPedidos = mPedidoRepository.getNumeroDePedidos();

            assertEquals("No hay el número de pedidos indicado", int1.intValue(), nPedidos);
        });
    }

    private void caminoPantallaPedidos() {
        onView(withText("PEDIDOS")).perform(click());
    }
    private void caminoPantallaPlatos() {
        onView(withText("PLATOS")).perform(click());
    }

    private void caminoOrdenarPedido() {
        onView(withText("ORDENAR")).perform(click());
        onView(withText("ORDENAR POR ESTADO")).perform(click());
    }

    private void caminoEliminarPedido() {
        String nombrePedido = NOMBRE_PEDIDO + " " + (numeroPedidos - 1) + SUFIJO_PEDIDO;
        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.scrollTo(hasDescendant(withText(nombrePedido))));
        onView(withText(nombrePedido)).perform(longClick());

        onView(withText("Eliminar Pedido")).perform(click());
        numeroPedidos--;

    }

    private void caminoCrearPedido() {
        onView(withText("CREAR")).perform(click());

        String nombrePedido = NOMBRE_PEDIDO + " " + numeroPedidos;

        onView(withId(R.id.etCustomerName)).perform(replaceText(nombrePedido), closeSoftKeyboard());

        onView(withId(R.id.etPhoneNumber)).perform(replaceText("612345678"), closeSoftKeyboard());

        onView(withId(R.id.etPickupDate)).perform(replaceText(FECHA_PEDIDO), closeSoftKeyboard());
    }

    private void caminoConfirmarCrearPedido() {
        numeroPedidos++;
        onView(withId(R.id.btnConfirmOrder)).perform(click());
        onView((withText("No enviar info"))).perform(click());

        String nombrePedido = NOMBRE_PEDIDO + " " + (numeroPedidos - 1) + SUFIJO_PEDIDO;

        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.scrollTo(hasDescendant(withText(nombrePedido))));
        onView(withText(nombrePedido)).check(matches(isDisplayed()));
    }

    private void caminoModificarPedido() {
        String nombrePedido = NOMBRE_PEDIDO + " " + (numeroPedidos - 1) + SUFIJO_PEDIDO;

        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.scrollTo(hasDescendant(withText(nombrePedido))));
        onView(withText(nombrePedido)).perform(click());

        onView(withId(R.id.spinnerEstado)).perform(click());
        onView(withText("PREPARADO")).perform(click());
    }

    private void caminoNoConfirmarModificarPedido() {
        pressBack();
    }
    private void caminoConfirmarModificarPedido() {
        onView(withId(R.id.btnConfirmOrder)).perform(click());
        onView((withText("No enviar info"))).perform(click());

        String nombrePedido = NOMBRE_PEDIDO + " " + (numeroPedidos - 1);

        scenario = ActivityScenario.launch(MainActivity.class);
        scenario.onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PedidoRepository mPedidoRepository = activity.getPedidoRepository();

            Pedido pedido = mPedidoRepository.getPedidoByNombre(nombrePedido);

            assertEquals("El pedido no ha sido modificado", pedido.getEstado(),"PREPARADO");
        });
    }

    private void caminoEliminarPlato() {
        String nombrePlato = NOMBRE_PLATOS + " " + (numeroPlatos - 1) + SUFIJO_PLATO;
        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.scrollTo(hasDescendant(withText(nombrePlato))));
        onView(withText(nombrePlato)).perform(longClick());

        onView(withText("Eliminar Plato")).perform(click());
        numeroPlatos--;

    }

    private void caminoOrdenarPlato() {
        onView(withText("ORDENAR")).perform(click());
        onView(withText("ORDENAR POR CATEGORÍA")).perform(click());
    }

    private void caminoCrearPlato() {
        onView(withText("CREAR")).perform(click());

        String nombrePlato = NOMBRE_PLATOS + " " + numeroPlatos;

        onView(withId(R.id.nombrePlato)).perform(replaceText(nombrePlato), closeSoftKeyboard());

        onView(withId(R.id.precioPlato)).perform(replaceText("33"), closeSoftKeyboard());
    }

    private void caminoConfirmarCrearPlato() {
        numeroPlatos++;
        onView(withId(R.id.button_save)).perform(click());

        String nombrePlato = NOMBRE_PLATOS + " " + (numeroPlatos - 1) + SUFIJO_PLATO;

        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.scrollTo(hasDescendant(withText(nombrePlato))));
        onView(withText(nombrePlato)).check(matches(isDisplayed()));
    }

    private void caminoModificarPlato() {
        String nombrePlato = NOMBRE_PLATOS + " " + (numeroPlatos - 1) + SUFIJO_PLATO;

        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.scrollTo(hasDescendant(withText(nombrePlato))));
        onView(withText(nombrePlato)).perform(click());

        onView(withId(R.id.categPlato)).perform(click());
        onView(withText("SEGUNDO")).perform(click());
    }

    private void caminoConfirmarModificarPlato() {
        onView(withId(R.id.button_save)).perform(click());

        String nombrePlato = NOMBRE_PLATOS + " " + (numeroPlatos - 1);

        scenario = ActivityScenario.launch(MainActivity.class);
        scenario.onActivity(activity -> {
            // Acceso al repositorio a través de la actividad
            PlatoRepository mPlatoRepository = activity.getPlatoRepository();

            Plato plato = mPlatoRepository.getPlatoByNombre(nombrePlato);

            assertEquals("El pedido no ha sido modificado", plato.getCategoria(),"SEGUNDO");
        });
    }

}