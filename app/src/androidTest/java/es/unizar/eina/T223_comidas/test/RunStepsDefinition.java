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
import static org.junit.Assert.assertNotNull;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;

import io.cucumber.java.After;
import io.cucumber.java.Before;
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

    ActivityTestRule rule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void launchActivity() throws Exception {
        rule.launchActivity(null);
    }

    @After
    public void finishActivity() throws Exception {
        rule.getActivity().finish();
    }

    @Given("Hay {int} platos en la aplicación")
    public void hay_platos_en_la_aplicación(Integer int1) {
        System.out.println("Hola");
    }

    @When("Añadir un plato")
    public void anadir_un_plato() {
        caminoPantallaPlatos();
        caminoCrearPlato();
        caminoConfirmarCrearPlato();
        caminoVolverHome();
    }

    @Then("Hay {int} platos nuevos en la aplicación")
    public void hay_platos_nuevos_en_la_aplicación(Integer int1) {
        System.out.println("Adios");
    }

    private void caminoPantallaPedidos() {
        onView(withText("PEDIDOS")).perform(click());
    }
    private void caminoPantallaPlatos() {
        onView(withText("PLATOS")).perform(click());
    }

    private void caminoOrdenarPedido() {
        onView(withText("ORDENAR")).perform(click());
        onView(withText("ORDENAR POR CLIENTE")).perform(click());
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

        String[] parts = FECHA_PEDIDO.split("/");
        String formattedDate = parts[0] + "/" + parts[1] + "/" + parts[2];
        String formattedTime = parts[3];

        onView(withId(R.id.etPickupDate)).perform(replaceText(formattedDate + "/" + formattedTime), closeSoftKeyboard());
    }

    private void caminoNoConfirmarCrearPedido() {
        pressBack();
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

        String nuevoNombrePedido = NOMBRE_PEDIDO + " " + (numeroPedidos - 1);

        onView(withId(R.id.etCustomerName)).perform(replaceText(nuevoNombrePedido), closeSoftKeyboard());
    }

    private void caminoNoConfirmarModificarPedido() {
        pressBack();
    }
    private void caminoConfirmarModificarPedido() {
        onView(withId(R.id.btnConfirmOrder)).perform(click());
        onView((withText("No enviar info"))).perform(click());

        String nombrePedido = NOMBRE_PEDIDO + " " + (numeroPedidos - 1) + SUFIJO_PEDIDO;

        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.scrollTo(hasDescendant(withText(nombrePedido))));
        onView(withText(nombrePedido)).check(matches(isDisplayed()));
    }

    private void caminoVolverHome() {
        onView(withId(R.id.botonInicio1)).perform(click());
    }

    private void caminoEliminarPlato() {
        String nombrePlato = NOMBRE_PLATOS + " " + (numeroPlatos - 1) + SUFIJO_PLATO;
        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.scrollTo(hasDescendant(withText(nombrePlato))));
        onView(withText(nombrePlato)).perform(longClick());

        onView(withText("Eliminar Plato")).perform(click());
        numeroPlatos--;
    }

    private void caminoEliminarPlatoT() {
        String nombrePlato = NOMBRE_PLATOS + " " + (numeroPlatos - 1) + SUFIJO_PLATO;
        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.scrollTo(hasDescendant(withText(nombrePlato))));
        onView(withText(nombrePlato)).perform(longClick());

        if (numeroPlatos > 0) {
            onView(withText("Eliminar Plato")).perform(click());
            numeroPlatos--;
        } else {
            pressBack();
        }
    }

    private void caminoOrdenarPlato() {
        onView(withText("ORDENAR")).perform(click());
        onView(withText("ORDENAR POR NOMBRE")).perform(click());
    }

    private void caminoCrearPlato() {
        onView(withText("CREAR")).perform(click());

        String nombrePlato = NOMBRE_PLATOS + " " + numeroPlatos;

        onView(withId(R.id.nombrePlato)).perform(replaceText(nombrePlato), closeSoftKeyboard());

        onView(withId(R.id.precioPlato)).perform(replaceText("33"), closeSoftKeyboard());
    }



    private void caminoNoConfirmarCrearPlato() {
        pressBack();
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

        String descripcion = NOMBRE_PLATOS + " " + (numeroPlatos - 1);

        onView(withId(R.id.descPlato)).perform(replaceText(descripcion), closeSoftKeyboard());
    }

    private void caminoConfirmarModificarPlato() {
        onView(withId(R.id.button_save)).perform(click());

        String nombrePlato = NOMBRE_PLATOS + " " + (numeroPlatos - 1) + SUFIJO_PLATO;

        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.scrollTo(hasDescendant(withText(nombrePlato))));
        onView(withText(nombrePlato)).check(matches(isDisplayed()));
    }

    private void caminoNoConfirmarModificarPlato() {
        pressBack();
    }


}
