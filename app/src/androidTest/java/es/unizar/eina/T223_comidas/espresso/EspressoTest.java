package es.unizar.eina.T223_comidas.espresso;


import androidx.test.espresso.contrib.RecyclerViewActions;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;

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

    private String NOMBRE_PLATOS = "Test plato";
    private double PRECIO_PLATO = 1.0;
    private String SUFIJO_NOMRE_PLATO = " - " + PRECIO_PLATO + " €";
    private String NOMBRE_PEDIDO = "Test pedido";

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void test() {
        onView(withText("PLATOS")).perform(click());

        String nombrePlato = "Helado" + SUFIJO_NOMRE_PLATO;
        // Scroll to the RecyclerView item with the specified text
        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.scrollTo(hasDescendant(withText(nombrePlato))));
        onView(withText(nombrePlato)).perform(longClick());
        onView(withText("Eliminar Plato")).perform(click());
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
                    break;
                case "5":
                    caminoEliminarPedido();
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

        if (this.numeroPlatos < this.numeroPlatosInicial) {
            String nombrePlato = this.NOMBRE_PLATOS + " " + this.numeroPlatos + this.SUFIJO_NOMRE_PLATO;
            onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.scrollTo(hasDescendant(withText(nombrePlato))));
            onView(withText(nombrePlato)).perform(longClick());
            onView(withText("Eliminar Plato")).perform(click());

            this.numeroPlatos--;
        }

    }



}
