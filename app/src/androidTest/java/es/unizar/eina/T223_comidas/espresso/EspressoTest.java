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

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import es.unizar.eina.T223_comidas.R;
import es.unizar.eina.T223_comidas.ui.MainActivity;

public class EspressoTest {

    private static ArrayList<String[]> caminos;

    private static int numeroPlatosInicial;
    private static int numeroPlatos;

    private static int numeroPedidosInicial;
    private static int numeroPedidos;

    private static final String NOMBRE_PLATOS = "Test plato";
    private static final double PRECIO_PLATO = 33.0;
    private static final String SUFIJO_PLATO = " - " + PRECIO_PLATO + " €";
    private static final String NOMBRE_PEDIDO = "Test pedido";
    private static final String FECHA_PEDIDO = "23/05/2024/20:00";
    private static final String SUFIJO_PEDIDO = " - " + FECHA_PEDIDO;

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @BeforeClass
    public static void testSetUp() {
        caminos = new ArrayList<>();
        // Camino 1
        caminos.add(new String[]{"1","3", "3", "4", "8", "3", "4", "9", "3", "6", "10", "3", "6", "11", "3", "5", "3", "7"});
        // Camino 2
        caminos.add(new String[]{"1", "7", "1", "5", "5", "7", "1", "5", "4", "8", "5", "6", "10", "5", "7"});
        // Camino 3
        caminos.add(new String[]{"1", "4", "8", "4", "8", "6", "10", "4", "8", "7"});
        // Camino 4
        caminos.add(new String[]{"1", "6", "10", "6", "10", "7"});
        // Camino 5
        caminos.add(new String[]{"1", "4", "9", "4", "9", "5", "4", "9", "6", "10", "4", "9", "7"});
        // Camino 6
        caminos.add(new String[]{"1", "6", "11", "5", "6", "11", "4", "8", "6", "11", "6", "11", "7"});
        // Camino 7
        caminos.add(new String[]{"1", "7", "2", "15", "15", "14", "14", "15", "13", "17", "15", "16", "19", "15", "12"});
        // Camino 8
        caminos.add(new String[]{"2", "12", "2", "14", "13", "18", "14", "16", "20", "14", "12"});
        // Camino 9
        caminos.add(new String[]{"2", "13", "17", "12", "1", "7", "2", "16", "19", "15", "13", "17", "14", "12"});
        // Camino 10
        caminos.add(new String[]{"2", "13", "17", "13", "17", "14", "13", "17", "16", "19", "12"});
        // Camino 11
        caminos.add(new String[]{"2", "13", "18", "15", "13", "18", "13", "18", "16", "20", "13", "18", "12"});
        // Camino 12
        caminos.add(new String[]{"2", "16", "19", "13", "18", "16", "19", "14", "16", "19", "16", "19", "12"});
        // Camino 13
        caminos.add(new String[]{"2", "16", "20", "15", "16", "20", "16", "20", "12"});

        numeroPlatosInicial = 3;
        numeroPedidosInicial = 3;
    }

    @Before
    public void platosYPedidosSetUp() {
        String[] caminoCrearPlato = new String[]{"2", "13", "18", "12"};
        while (numeroPlatos < numeroPlatosInicial) {
            ejecutarCamino(caminoCrearPlato);
        }

        String[] caminoCrearPedido = new String[]{"1", "4", "9", "7"};
        while (numeroPedidos < numeroPedidosInicial) {
            ejecutarCamino(caminoCrearPedido);
        }
    }
    //@Ignore
    @Test
    public void primerCaminoTest() {
        ejecutarCamino(caminos.get(0));
    }

    //@Ignore
    @Test
    public void segundoCaminoTest() {
        ejecutarCamino(caminos.get(1));
    }

    //@Ignore
    @Test
    public void tercerCaminoTest() {
        ejecutarCamino(caminos.get(2));
    }

    //@Ignore
    @Test
    public void cuartoCaminoTest() {
        ejecutarCamino(caminos.get(3));
    }

    //@Ignore
    @Test
    public void quintoCaminoTest() {
        ejecutarCamino(caminos.get(4));
    }

    //@Ignore
    @Test
    public void sextoCaminoTest() {
        ejecutarCamino(caminos.get(5));
    }

    //@Ignore
    @Test
    public void septimoCaminoTest() {
        ejecutarCamino(caminos.get(6));
    }

    //@Ignore
    @Test
    public void octavoCaminoTest() {
        ejecutarCamino(caminos.get(7));
    }

    //@Ignore
    @Test
    public void novenoCaminoTest() {
        ejecutarCamino(caminos.get(8));
    }

    //@Ignore
    @Test
    public void decimoCaminoTest() {
        ejecutarCamino(caminos.get(9));
    }

    //@Ignore
    @Test
    public void undecimoCaminoTest() {
        ejecutarCamino(caminos.get(10));
    }

    //@Ignore
    @Test
    public void duodecimoCaminoTest() {
        ejecutarCamino(caminos.get(11));
    }

    //@Ignore
    @Test
    public void decimotercerCaminoTest() {
        ejecutarCamino(caminos.get(12));
    }

    //@Ignore
    @After
    public void tearDown(){
        String[] caminoBorrarPlato = new String[]{"2", "14", "12"};
        while (numeroPlatos > numeroPlatosInicial) {
            ejecutarCamino(caminoBorrarPlato);
        }

        String[] caminoBorrarPedido = new String[]{"1", "5", "7"};
        while (numeroPedidos > numeroPedidosInicial) {
            ejecutarCamino(caminoBorrarPedido);
        }
    }

    @AfterClass
    public static void cleanApp() {
        String[] caminoBorrarPlato = new String[]{"2", "14T", "12"};
        while (numeroPlatos > 0) {
            ejecutarCamino(caminoBorrarPlato);
        }

        String[] caminoBorrarPedido = new String[]{"1", "5T", "7"};
        while (numeroPedidos > 0) {
            ejecutarCamino(caminoBorrarPedido);
        }
    }

    private static void ejecutarCamino(String[] camino) {
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
                case "5T":
                    caminoEliminarPedidoT();
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
                case "14T":
                    caminoEliminarPlatoT();
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

    private static void caminoPantallaPedidos() {
        onView(withText("PEDIDOS")).perform(click());
    }
    private static void caminoPantallaPlatos() {
        onView(withText("PLATOS")).perform(click());
    }

    private static void caminoOrdenarPedido() {
        onView(withText("ORDENAR")).perform(click());
        onView(withText("ORDENAR POR CLIENTE")).perform(click());
    }

    private static void caminoEliminarPedido() {
        String nombrePedido = NOMBRE_PEDIDO + " " + (numeroPedidos - 1) + SUFIJO_PEDIDO;
        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.scrollTo(hasDescendant(withText(nombrePedido))));
        onView(withText(nombrePedido)).perform(longClick());

        if (numeroPedidos > numeroPedidosInicial) {
            onView(withText("Eliminar Pedido")).perform(click());
            numeroPedidos--;
        } else {
            pressBack();
        }
    }

    private static void caminoEliminarPedidoT() {
        String nombrePedido = NOMBRE_PEDIDO + " " + (numeroPedidos - 1) + SUFIJO_PEDIDO;
        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.scrollTo(hasDescendant(withText(nombrePedido))));
        onView(withText(nombrePedido)).perform(longClick());

        if (numeroPedidos > 0) {
            onView(withText("Eliminar Pedido")).perform(click());
            numeroPedidos--;
        } else {
            pressBack();
        }
    }

    private static void caminoCrearPedido() {
        onView(withText("CREAR")).perform(click());

        String nombrePedido = NOMBRE_PEDIDO + " " + numeroPedidos;

        onView(withId(R.id.etCustomerName)).perform(replaceText(nombrePedido), closeSoftKeyboard());

        onView(withId(R.id.etPhoneNumber)).perform(replaceText("612345678"), closeSoftKeyboard());

        String[] parts = FECHA_PEDIDO.split("/");
        String formattedDate = parts[0] + "/" + parts[1] + "/" + parts[2];
        String formattedTime = parts[3];

        onView(withId(R.id.etPickupDate)).perform(replaceText(formattedDate + "/" + formattedTime), closeSoftKeyboard());
    }

    private static void caminoNoConfirmarCrearPedido() {
        pressBack();
    }

    private static void caminoConfirmarCrearPedido() {
        numeroPedidos++;
        onView(withId(R.id.btnConfirmOrder)).perform(click());
        onView((withText("No enviar info"))).perform(click());

        String nombrePedido = NOMBRE_PEDIDO + " " + (numeroPedidos - 1) + SUFIJO_PEDIDO;

        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.scrollTo(hasDescendant(withText(nombrePedido))));
        onView(withText(nombrePedido)).check(matches(isDisplayed()));
    }

    private static void caminoModificarPedido() {
        String nombrePedido = NOMBRE_PEDIDO + " " + (numeroPedidos - 1) + SUFIJO_PEDIDO;

        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.scrollTo(hasDescendant(withText(nombrePedido))));
        onView(withText(nombrePedido)).perform(click());

        String nuevoNombrePedido = NOMBRE_PEDIDO + " " + (numeroPedidos - 1);

        onView(withId(R.id.etCustomerName)).perform(replaceText(nuevoNombrePedido), closeSoftKeyboard());
    }

    private static void caminoNoConfirmarModificarPedido() {
        pressBack();
    }
    private static void caminoConfirmarModificarPedido() {
        onView(withId(R.id.btnConfirmOrder)).perform(click());
        onView((withText("No enviar info"))).perform(click());

        String nombrePedido = NOMBRE_PEDIDO + " " + (numeroPedidos - 1) + SUFIJO_PEDIDO;

        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.scrollTo(hasDescendant(withText(nombrePedido))));
        onView(withText(nombrePedido)).check(matches(isDisplayed()));
    }

    private static void caminoVolverHome() {
        onView(withId(R.id.botonInicio1)).perform(click());
    }

    private static void caminoEliminarPlato() {
        String nombrePlato = NOMBRE_PLATOS + " " + (numeroPlatos - 1) + SUFIJO_PLATO;
        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.scrollTo(hasDescendant(withText(nombrePlato))));
        onView(withText(nombrePlato)).perform(longClick());

        if (numeroPlatos > numeroPlatosInicial) {
            onView(withText("Eliminar Plato")).perform(click());
            numeroPlatos--;
        } else {
            pressBack();
        }
    }

    private static void caminoEliminarPlatoT() {
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

    private static void caminoOrdenarPlato() {
        onView(withText("ORDENAR")).perform(click());
        onView(withText("ORDENAR POR NOMBRE")).perform(click());
    }

    private static void caminoCrearPlato() {
        onView(withText("CREAR")).perform(click());

        String nombrePlato = NOMBRE_PLATOS + " " + numeroPlatos;

        onView(withId(R.id.nombrePlato)).perform(replaceText(nombrePlato), closeSoftKeyboard());

        onView(withId(R.id.precioPlato)).perform(replaceText("33"), closeSoftKeyboard());
    }



    private static void caminoNoConfirmarCrearPlato() {
        pressBack();
    }

    private static void caminoConfirmarCrearPlato() {
        numeroPlatos++;
        onView(withId(R.id.button_save)).perform(click());

        String nombrePlato = NOMBRE_PLATOS + " " + (numeroPlatos - 1) + SUFIJO_PLATO;

        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.scrollTo(hasDescendant(withText(nombrePlato))));
        onView(withText(nombrePlato)).check(matches(isDisplayed()));
    }

    private static void caminoModificarPlato() {
        String nombrePlato = NOMBRE_PLATOS + " " + (numeroPlatos - 1) + SUFIJO_PLATO;

        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.scrollTo(hasDescendant(withText(nombrePlato))));
        onView(withText(nombrePlato)).perform(click());

        String descripcion = NOMBRE_PLATOS + " " + (numeroPlatos - 1);

        onView(withId(R.id.descPlato)).perform(replaceText(descripcion), closeSoftKeyboard());
    }

    private static void caminoConfirmarModificarPlato() {
        onView(withId(R.id.button_save)).perform(click());

        String nombrePlato = NOMBRE_PLATOS + " " + (numeroPlatos - 1) + SUFIJO_PLATO;

        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.scrollTo(hasDescendant(withText(nombrePlato))));
        onView(withText(nombrePlato)).check(matches(isDisplayed()));
    }

    private static void caminoNoConfirmarModificarPlato() {
        pressBack();
    }

}
