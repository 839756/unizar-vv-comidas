package es.unizar.eina.T223_comidas.espresso;

import androidx.test.espresso.contrib.RecyclerViewActions;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;

import static androidx.test.espresso.assertion.ViewAssertions.matches;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import es.unizar.eina.T223_comidas.R;
import es.unizar.eina.T223_comidas.ui.MainActivity;

public class EspressoTest {

    private ArrayList<String[]> caminos;

    private int numeroPlatosInicial;
    private int numeroPlatos;

    private int numeroPedidosInicial;
    private int numeroPedidos;

    private final String NOMBRE_PLATOS = "Test plato";
    private final double PRECIO_PLATO = 1.0;
    private final String SUFIJO_PLATO = " - " + PRECIO_PLATO + " €";
    private final String NOMBRE_PEDIDO = "Test pedido";
    private final String FECHA_PEDIDO = "23/05/2024/20:00";
    private final String SUFIJO_PEDIDO = " - " + FECHA_PEDIDO;

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void test() {
        onView(withText("PEDIDOS")).perform(click());

        caminoCrearPedido();
        caminoConfirmarCrearPedido();
    }

    private void ejecutarCamino(String[] camino) {
        for (String accion : camino) {
            switch (accion) {
                case "1":
                    caminoPantallaPedidos();
                    break;
                case "2":
                    caminoPantallaPlatos();
                    break;
                case "3":
                    caminoOrdenarPedido();
                    break;
                case "4":
                    caminoCrearPedido();
                    break;
                case "5":
                    caminoEliminarPedido();
                    break;
                case "6":
                    caminoModificarPedido();
                    break;
                case "7":
                case "12":
                    caminoVolverHome();
                    break;
                case "8":
                    caminoNoConfirmarCrearPedido();
                    break;
                case "9":
                    caminoConfirmarCrearPedido();
                    break;
                case "10":
                    caminoNoConfirmarModificarPedido();
                    break;
                case "11":
                    caminoConfirmarModificarPedido();
                    break;
                case "13":
                    caminoCrearPlato();
                    break;
                case "14":
                    caminoEliminarPlato();
                    break;
                case "15":
                    caminoOrdenarPlato();
                    break;
                case "16":
                    caminoModificarPlato();
                    break;
                case "17":
                    caminoNoConfirmarCrearPlato();
                    break;
                case "18":
                    caminoConfirmarCrearPlato();
                    break;
                case "19":
                    caminoNoConfirmarModificarPlato();
                    break;
                case "20":
                    caminoConfirmarModificarPlato();
                    break;
                default:
                    return;
            }
        }
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
        String nombrePedido = this.NOMBRE_PEDIDO + " " + (this.numeroPedidos - 1) + this.SUFIJO_PEDIDO;
        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.scrollTo(hasDescendant(withText(nombrePedido))));
        onView(withText(nombrePedido)).perform(longClick());

        if (this.numeroPedidos < this.numeroPedidosInicial) {
            onView(withText("Eliminar Pedido")).perform(click());
            this.numeroPedidos--;
        } else {
            pressBack();
        }
    }

    private void caminoCrearPedido() {
        onView(withText("CREAR")).perform(click());

        String nombrePedido = this.NOMBRE_PEDIDO + " " + this.numeroPedidos;

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
        this.numeroPedidos++;
        onView(withId(R.id.btnConfirmOrder)).perform(click());
        onView((withText("No enviar info"))).perform(click());

        String nombrePedido = this.NOMBRE_PEDIDO + " " + (this.numeroPedidos - 1) + this.SUFIJO_PEDIDO;

        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.scrollTo(hasDescendant(withText(nombrePedido))));
        onView(withText(nombrePedido)).check(matches(isDisplayed()));
    }

    private void caminoModificarPedido() {
        String nombrePedido = this.NOMBRE_PEDIDO + " " + (this.numeroPedidos - 1) + this.SUFIJO_PEDIDO;

        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.scrollTo(hasDescendant(withText(nombrePedido))));
        onView(withText(nombrePedido)).perform(click());

        String nuevoNombrePedido = this.NOMBRE_PEDIDO + " " + (this.numeroPedidos - 1);

        onView(withId(R.id.etCustomerName)).perform(replaceText(nuevoNombrePedido), closeSoftKeyboard());
    }

    private void caminoNoConfirmarModificarPedido() {
        pressBack();
    }
    private void caminoConfirmarModificarPedido() {
        onView(withId(R.id.btnConfirmOrder)).perform(click());
        onView((withText("No enviar info"))).perform(click());

        String nombrePedido = this.NOMBRE_PEDIDO + " " + (this.numeroPedidos - 1) + this.SUFIJO_PEDIDO;

        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.scrollTo(hasDescendant(withText(nombrePedido))));
        onView(withText(nombrePedido)).check(matches(isDisplayed()));
    }

    private void caminoVolverHome() {
        onView(withId(R.id.botonInicio1)).perform(click());
    }

    private void caminoEliminarPlato() {
        String nombrePlato = this.NOMBRE_PLATOS + " " + (this.numeroPlatos - 1) + this.SUFIJO_PLATO;
        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.scrollTo(hasDescendant(withText(nombrePlato))));
        onView(withText(nombrePlato)).perform(longClick());

        if (this.numeroPlatos < this.numeroPlatosInicial) {
            onView(withText("Eliminar Plato")).perform(click());
            this.numeroPlatos--;
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

        String nombrePlato = this.NOMBRE_PLATOS + " " + this.numeroPlatos;

        onView(withId(R.id.nombrePlato)).perform(replaceText(nombrePlato), closeSoftKeyboard());

        onView(withId(R.id.precioPlato)).perform(replaceText("33"), closeSoftKeyboard());
    }



    private void caminoNoConfirmarCrearPlato() {
        pressBack();
    }

    private void caminoConfirmarCrearPlato() {
        this.numeroPlatos++;
        onView(withId(R.id.button_save)).perform(click());

        String nombrePlato = this.NOMBRE_PLATOS + " " + (this.numeroPlatos - 1) + this.SUFIJO_PLATO;

        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.scrollTo(hasDescendant(withText(nombrePlato))));
        onView(withText(nombrePlato)).check(matches(isDisplayed()));
    }

    private void caminoModificarPlato() {
        String nombrePlato = this.NOMBRE_PLATOS + " " + (this.numeroPlatos - 1) + this.SUFIJO_PLATO;

        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.scrollTo(hasDescendant(withText(nombrePlato))));
        onView(withText(nombrePlato)).perform(click());

        String descripcion = this.NOMBRE_PLATOS + " " + (this.numeroPlatos - 1);

        onView(withId(R.id.descPlato)).perform(replaceText(descripcion), closeSoftKeyboard());
    }

    private void caminoConfirmarModificarPlato() {
        onView(withId(R.id.button_save)).perform(click());

        String nombrePlato = this.NOMBRE_PLATOS + " " + (this.numeroPlatos - 1) + this.SUFIJO_PLATO;

        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.scrollTo(hasDescendant(withText(nombrePlato))));
        onView(withText(nombrePlato)).check(matches(isDisplayed()));
    }

    private void caminoNoConfirmarModificarPlato() {
        pressBack();
    }

}
