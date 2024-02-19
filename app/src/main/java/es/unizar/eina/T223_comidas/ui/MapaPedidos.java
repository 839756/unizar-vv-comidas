package es.unizar.eina.T223_comidas.ui;

import android.util.Log;

import androidx.lifecycle.ViewModelProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import es.unizar.eina.T223_comidas.database.Cantidad;

public class MapaPedidos {


    // Clase para almacenar información sobre el plato
    protected static class PlatoInfo {
        private int cantidad;
        private double precio;

        public PlatoInfo(int cantidad, double precio) {
            this.cantidad = cantidad;
            this.precio = precio;
        }

        public int getCantidad() {
            return cantidad;
        }

        public double getPrecio() {
            return precio;
        }

        public void incrementarCantidad() {
            cantidad++;
        }

        public void decrementarCantidad() {
            cantidad--;
        }

        public double getPrecioTotal() {
            return cantidad * precio;
        }
    }

    private static HashMap<Integer, PlatoInfo> MapaPedidos = new HashMap<>();

    public MapaPedidos(CantidadViewModel CantidadViewModel, int idPedido){
        Log.i("etiqueta", idPedido + " " );
        if(idPedido != 0) {
            List<Cantidad> listaCantidades = CantidadViewModel.getPlatosByPedido(idPedido);

            MapaPedidos.clear(); // Limpiar el mapa antes de inicializarlo
            for (Cantidad cantidad : listaCantidades) {
                int idPlato = cantidad.getIdPlato();
                int raciones = cantidad.getRaciones();
                double precio = cantidad.getPrecio();
                Log.i("etiqueta", idPlato + " " + idPedido + " " + cantidad + " " + raciones);
                PlatoInfo platoInfo = new PlatoInfo(raciones, precio);
                MapaPedidos.put(idPlato, platoInfo);
            }
        }
        else{
            MapaPedidos.clear();
        }
    }

    public static void ayadirPlato(Integer idPlato, double precio) {
        if (MapaPedidos.containsKey(idPlato) && MapaPedidos.get(idPlato).getCantidad() < 99) {
            // Incrementa la cantidad si ya existe el plato en el pedido
            MapaPedidos.get(idPlato).incrementarCantidad();
        } else {
            // Agrega un nuevo plato al pedido
            MapaPedidos.put(idPlato, new PlatoInfo(1, precio));
        }
    }

    public static void quitarPlato(Integer idPlato) {
        if (MapaPedidos.containsKey(idPlato)) {
            // Decrementa la cantidad si existe el plato en el pedido
            MapaPedidos.get(idPlato).decrementarCantidad();
            if (MapaPedidos.get(idPlato).getCantidad() == 0) {
                // Si la cantidad es cero, elimina el plato del pedido
                MapaPedidos.remove(idPlato);
            }
        }
    }

    public static int obtenerCantidadPlato(Integer idPlato) {
        PlatoInfo platoInfo = MapaPedidos.get(idPlato);
        return (platoInfo != null) ? platoInfo.getCantidad() : 0;
    }

    public static double calcularPrecioTotalPedido() {
        double precioTotalPedido = 0.0;

        for (PlatoInfo platoInfo : MapaPedidos.values()) {
            precioTotalPedido += platoInfo.getPrecioTotal();
        }

        return precioTotalPedido;
    }

    public static Set<Map.Entry<Integer, PlatoInfo>> obtenerEntradas() {
        return MapaPedidos.entrySet();
    }


}
