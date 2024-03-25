package es.unizar.eina.T223_comidas.ui;

import es.unizar.eina.T223_comidas.database.Pedido;
import es.unizar.eina.T223_comidas.database.Plato;

import android.text.TextUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

public class UnitTest {

    private PlatoViewModel mPlatoViewModel;

    private PedidoViewModel mPedidoViewModel;

    private Vector<Plato> platos;

    private Vector<Pedido> pedidos;

    public UnitTest(PlatoViewModel platoViewModel, PedidoViewModel pedidoViewModel) {
        mPlatoViewModel = platoViewModel;
        mPedidoViewModel = pedidoViewModel;
        setupPlatos();
        setupPedidos();
    }

    public void setupPlatos() {
        platos = new Vector<>();
        // Válidos
        platos.add(new Plato("Plato 1", "Descripcion", "PRIMERO", 33.0));
        platos.add(new Plato("Plato 1", "Descripcion", "SEGUNDO", 33.0));
        platos.add(new Plato("Plato 1", "Descripcion", "POSTRE", 33.0));
        // No válidos
        platos.add(new Plato(null, "Descripcoin", "PRIMERO", 33.0));
        platos.add(new Plato("", "Descripcion", "PRIMERO", 33.0));
        platos.add(new Plato("Plato 1", null, "PRIMERO", 33.0));
        platos.add(new Plato("Plato 1", "Descripcion", "NO", 33.0));
        platos.add(new Plato("Plato 1", "Descripcion", "PRIMERO", -33.0));
        // platos.add(new Plato("Plato 1", "Descripcion", "PRIMERO", null)); Error al compilar.

    }

    public void setupPedidos() {
        pedidos = new Vector<>();
        // Válidos
        pedidos.add(new Pedido("Pedido 1", 633333333, "24/01/2024/19:30","SOLICITADO"));
        pedidos.add(new Pedido("Pedido 1", 633333333, "24/01/2024/19:30","PREPARADO"));
        pedidos.add(new Pedido("Pedido 1", 633333333, "24/01/2024/19:30","RECOGIDO"));
        // No válidos
        pedidos.add(new Pedido(null, 633333333, "24/01/2024/19:30","SOLICITADO"));
        pedidos.add(new Pedido("", 633333333, "24/01/2024/19:30","SOLICITADO"));
        pedidos.add(new Pedido("Pedido 1", 633, "24/01/2024/19:30","SOLICITADO"));
        // pedidos.add(new Pedido("Pedido", null, "24/12/2023/19:30","SOLICITADO")); Error al compilar.
        pedidos.add(new Pedido("Pedido", 633333333, "22/01/2024/19:30","SOLICITADO"));
        pedidos.add(new Pedido("Pedido", 633333333, "24/01/2024/18:30","SOLICITADO"));
        pedidos.add(new Pedido("Pedido 1", 633333333, "24/12/2023/19:30","RECOGIDO"));
        pedidos.add(new Pedido("Pedido", 633333333, "24/01/2024/19:30","NO"));
    }

    public void testUnitarios(){
        testCrearPlato();
        testCrearPedido();
    }

    public void testSobrecarga() {
        testSC();
    }

    public void testVolumen() {
        testVolumenPlato();
        testVolumenPedido();
    }

    public void testCrearPlato() {
        long result;
        int iteracion = 1;
        for (Plato plato : platos) {
            try {
                // Antes de insertar se hacen las comprobaciones para ver si es correcto introducir el plato
                if(!(TextUtils.isEmpty(plato.getNombre()) ||
                        plato.getNombre().length() <= 0 ||
                        TextUtils.isEmpty(plato.getDescripcion()) ||
                        plato.getPrecio() < 0.00 ||
                        (plato.getCategoria() != "PRIMERO" && plato.getCategoria() != "SEGUNDO" && plato.getCategoria() != "POSTRE"))) {
                    // Se inserta el plato
                    result = mPlatoViewModel.insert(plato);
                }
                else { result = -1;}

                Log.d("UnitTest", "El resultado de la introduccón del plato " + iteracion + " es: " + result);


                if (result < 0) {
                    Log.d("UnitTest", "Prueba ha fallado al introducir el plato en la iteración: " + iteracion);
                }
                iteracion++;

            } catch (Exception e) {
                Log.d("UnitTest", "Prueba fallida para el plato: " + plato.getNombre());
            }
        }
    }

    public void testCrearPedido() {
        long result;
        int iteracion = 1;

        // ###################################################################################### //
        //                                 Se consigue la fecha                                   //
        // ###################################################################################### //
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy/HH:mm");
        Date fechaActual = new Date(); // Fecha y hora actual
        Date fecha = null;
        try {
            fecha = sdf.parse("00/00/0000/00:00");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        // ###################################################################################### //


        for (Pedido pedido : pedidos) {
            try {

                try {
                    if (!TextUtils.isEmpty(pedido.getFecha())) {
                        fecha = sdf.parse(pedido.getFecha().toString());
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    return;
                }

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(fecha);
                int diaDeLaSemana = calendar.get(Calendar.DAY_OF_WEEK);
                int horaDelDia = calendar.get(Calendar.HOUR_OF_DAY);
                int minutos = calendar.get(Calendar.MINUTE);


                if (!( TextUtils.isEmpty(pedido.getCliente()) ||
                        pedido.getCliente().length() <= 0 ||
                        String.valueOf(pedido.getMovil()).length() != 9 ||
                        (diaDeLaSemana == calendar.MONDAY || !(horaDelDia == 19 && minutos >= 30) && !(horaDelDia >= 20 && horaDelDia < 23)
                                &&  !(horaDelDia == 23 && minutos == 00) || fecha.compareTo(fechaActual) <= 0) ||
                        (pedido.getEstado() != "SOLICITADO" && pedido.getEstado() != "PREPARADO" && pedido.getEstado() != "RECOGIDO")
                        )) {
                    // Se inserta el plato
                    result = mPedidoViewModel.insert(pedido);
                } else { result=-1; }

                Log.d("UnitTest", "El resultado de la introduccón del pedido " + iteracion + " es: " + result);

                if (result < 0) {
                    Log.d("UnitTest", "Prueba ha fallado al introducir el pedido en la iteración: " + iteracion);
                }

                iteracion++;
            } catch (Exception e) {
                Log.d("UnitTest", "Prueba fallida para el plato: " + pedido.getCliente());
            }
        }
    }

    public void testVolumenPlato() {
        long result;
        String nombreP;
        Plato plato = platos.get(0);

        for(int i = 0; i < 100; i++) {
            try {

                Plato platoNuevo = new Plato(plato.getNombre() + " " + i,
                        plato.getDescripcion(),
                        plato.getCategoria(),
                        plato.getPrecio());

                result = mPlatoViewModel.insert(platoNuevo);

                if (result < 0) {
                    Log.d("UnitTest", "Prueba ha fallado al introducir el plato " + i);
                }

            } catch (Exception e) {
                Log.d("UnitTest", "Prueba ha fallado en el test de volumen de platos.");
            }
        }
    }

    public void testVolumenPedido() {
        long result;
        Pedido pedido = pedidos.get(0);
        for(int i = 0; i < 2000; i++) {
            try {

                Pedido pedidoNuevo = new Pedido(pedido.getCliente() + " " + i,
                        pedido.getMovil(),
                        pedido.getFecha(),
                        pedido.getEstado());

               result = mPedidoViewModel.insert(pedidoNuevo);

               if (result < 0) {
                   Log.d("UnitTest", "Prueba ha fallado al introducir el pedido " + i);
               }

            } catch (Exception e) {
                Log.d("UnitTest", "Prueba ha fallado en el test de volumen de pedidos.");
            }
        }
    }

    public void testSC() {
        long result;
        String descripcion = "a";
        Plato plato = new Plato("PruebaSC",
                descripcion,
                "PRIMERO",
                33.0);
        boolean fallo = false;
        int longitud = 10;

        try {
            while (true) {
                descripcion = extendString(descripcion, longitud);

                Plato platoSC = new Plato("PruebaSC",
                        descripcion,
                        "PRIMERO",
                        33.0);

                mPlatoViewModel.insert(platoSC);

                Log.d("UnitTest", "Se ha introducido una descripción de longitd de: " + longitud);

                longitud += 100000;
            }
        } catch (Exception e){
            Log.d("UnitTest", "Prueba ha fallado en el test de sobrecarga de platos del nombre.");

        }

    }

    private String extendString(String originalString, int maxLength) {
        StringBuilder result = new StringBuilder();

        for (int targetLength = 10; targetLength <= maxLength; targetLength *= 10) {
            StringBuilder extended = new StringBuilder(originalString.length() * (targetLength / originalString.length() + 1));
            while (extended.length() < targetLength) {
                extended.append(originalString);
            }
            result.append(extended.substring(0, targetLength)).append("\n");
        }

        return result.toString();
    }
}

