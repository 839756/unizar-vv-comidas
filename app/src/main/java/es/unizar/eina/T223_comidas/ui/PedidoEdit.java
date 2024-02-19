package es.unizar.eina.T223_comidas.ui;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import es.unizar.eina.T223_comidas.R;
import es.unizar.eina.T223_comidas.database.Cantidad;
import es.unizar.eina.T223_comidas.database.Pedido;
import es.unizar.eina.send.SMSImplementor;
import es.unizar.eina.send.WhatsAppImplementor;

/** Pantalla utilizada para la creación o edición de un pedido */
public class PedidoEdit extends AppCompatActivity {

    public static final String PEDIDO_CLIENTE = "cliente";
    public static final String PEDIDO_MOVIL = "movil";
    public static final String PEDIDO_FECHA = "fecha";
    public static final String PEDIDO_ESTADO = "estado";
    public static final String PEDIDO_ID = "id";

    private EditText mClienteText;

    private EditText mMovilText;

    private TextView mFechaText;

    private Spinner mEstadoText;

    private Integer mRowId;

    private PedidoViewModel mPedidoViewModel;

    private CantidadViewModel mCantidadViewModel;

    Button mSaveButton;

    static MapaPedidos mPedidos;
    private Calendar calendar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidoedit);

        SMSImplementor sms = new SMSImplementor(this);
        WhatsAppImplementor whatsapp = new WhatsAppImplementor(this);

        mClienteText = findViewById(R.id.etCustomerName);
        mMovilText = findViewById(R.id.etPhoneNumber);
        mFechaText = findViewById(R.id.etPickupDate);
        mEstadoText = findViewById(R.id.spinnerEstado);

        mPedidoViewModel = new ViewModelProvider(this).get(PedidoViewModel.class);
        mCantidadViewModel = new ViewModelProvider(this).get(CantidadViewModel.class);

        mSaveButton = findViewById(R.id.btnConfirmOrder);
        mSaveButton.setOnClickListener(view -> {
            Intent replyIntent = new Intent();

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy/HH:mm");
            Date fechaActual = new Date(); // Fecha y hora actual
            Date fecha = null;
            try {
                fecha = sdf.parse("00/00/0000/00:00");
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            try {
                if (!TextUtils.isEmpty(mFechaText.getText())) {
                    fecha = sdf.parse(mFechaText.getText().toString());
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

            int movil = 0;
            if (!TextUtils.isEmpty(mMovilText.getText())){
                movil = Integer.parseInt(mMovilText.getText().toString());
            }
            String movilStr = Integer.toString(movil);
            int numeroDeDigitos = movilStr.length();

            if (TextUtils.isEmpty(mClienteText.getText()) || TextUtils.isEmpty(mFechaText.getText()) ||
                    TextUtils.isEmpty(mMovilText.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
                Toast.makeText(getApplicationContext(), "Pedido no guardado, faltan campos obligatorios",
                        Toast.LENGTH_LONG).show();
                finish();
            } else if (diaDeLaSemana == Calendar.MONDAY ||
                    !(horaDelDia == 19 && minutos >= 30) && !(horaDelDia >= 20 && horaDelDia < 23)
                            &&  !(horaDelDia == 23 && minutos == 00) || fecha.compareTo(fechaActual) <= 0) {
                setResult(RESULT_CANCELED, replyIntent);
                Toast.makeText(getApplicationContext(), "Pedido no guardado, fecha no valida",
                        Toast.LENGTH_LONG).show();
                finish();
            } else if (numeroDeDigitos != 9) {
                setResult(RESULT_CANCELED, replyIntent);
                Toast.makeText(getApplicationContext(), "Pedido no guardado, telefono no valida",
                        Toast.LENGTH_LONG).show();
                finish();
            } else {
                replyIntent.putExtra(PedidoEdit.PEDIDO_CLIENTE, mClienteText.getText().toString());
                replyIntent.putExtra(PedidoEdit.PEDIDO_MOVIL, Integer.parseInt(mMovilText.getText().toString()));
                replyIntent.putExtra(PedidoEdit.PEDIDO_FECHA, mFechaText.getText().toString());
                replyIntent.putExtra(PedidoEdit.PEDIDO_ESTADO, mEstadoText.getSelectedItem().toString());
                if (mRowId!=null) {
                    replyIntent.putExtra(PedidoEdit.PEDIDO_ID, mRowId.intValue());
                }
                setResult(RESULT_OK, replyIntent);

                //Una vez definido el resultado correcto se hacen las modificaciones o inserciones pertinentes
                Bundle extras = replyIntent.getExtras();
                int id = extras.getInt(PedidoEdit.PEDIDO_ID);
                //Si el id obtenido es 0 quiere decir que se esta creado un pedido por lo que no se hace update
                if (id != 0) {
                    Pedido updatedPedido = new Pedido(extras.getString(PedidoEdit.PEDIDO_CLIENTE),
                            extras.getInt(PedidoEdit.PEDIDO_MOVIL),
                            extras.getString(PedidoEdit.PEDIDO_FECHA),
                            extras.getString(PedidoEdit.PEDIDO_ESTADO));
                    updatedPedido.setId(id);
                    mPedidoViewModel.update(updatedPedido);
                    //Se eliminan los platos de la relacion para volver a incluir los nuevos
                    mCantidadViewModel.deletePlatosByPedido(id);

                    for (Map.Entry<Integer, MapaPedidos.PlatoInfo> entry : MapaPedidos.obtenerEntradas()) {
                        int idPlato = entry.getKey();
                        MapaPedidos.PlatoInfo platoInfo = entry.getValue();
                        int cantidad = platoInfo.getCantidad();
                        double precio = platoInfo.getPrecio();

                        Cantidad newCatidad = new Cantidad(idPlato, id, cantidad, precio);
                        mCantidadViewModel.insert(newCatidad);
                    }

                    //Envio de info
                    String[] options = {"SMS", "WhatsApp", "No enviar info"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Seleccione el método de envío de info")
                            .setItems(options, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    String mensaje = "Se ha realizado un pedido a nombre de " + extras.getString(PedidoEdit.PEDIDO_CLIENTE) +
                                            " con un coste de " + String.valueOf(MapaPedidos.calcularPrecioTotalPedido()) +
                                            " € y con la siguiente fecha de recogida: " + extras.getString(PedidoEdit.PEDIDO_FECHA) +
                                            ". El estado del pedido esta en" + extras.getString(PedidoEdit.PEDIDO_ESTADO);
                                    if (which == 0) {
                                        sms.send(String.valueOf(extras.getInt(PedidoEdit.PEDIDO_MOVIL)), mensaje);
                                    } else if (which == 1) {
                                        whatsapp.send(String.valueOf(extras.getInt(PedidoEdit.PEDIDO_MOVIL)), mensaje);
                                    }else if (which == 2) {
                                        finish();
                                    }
                                    finish();
                                }
                            });
                    builder.create().show();
                }
                else{   //Si el id obtenido es 0 nos encontramos en el caso de crear
                    Pedido newPedido = new Pedido(extras.getString(PedidoEdit.PEDIDO_CLIENTE),
                            extras.getInt(PedidoEdit.PEDIDO_MOVIL),
                            extras.getString(PedidoEdit.PEDIDO_FECHA),
                            extras.getString(PedidoEdit.PEDIDO_ESTADO));
                    int idPedido = (int)mPedidoViewModel.insert(newPedido);

                    for (Map.Entry<Integer, MapaPedidos.PlatoInfo> entry : MapaPedidos.obtenerEntradas()) {
                        int idPlato = entry.getKey();
                        MapaPedidos.PlatoInfo platoInfo = entry.getValue();
                        int cantidad = platoInfo.getCantidad();
                        double precio = platoInfo.getPrecio();

                        //Log.i("etiqueta", idPlato + " " + idPedido + " " + cantidad + " " + precioTotal);

                        Cantidad newCatidad = new Cantidad(idPlato, idPedido, cantidad, precio);
                        mCantidadViewModel.insert(newCatidad);
                    }

                    //Envio de info
                    String[] options = {"SMS", "WhatsApp", "No enviar info"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Seleccione el método de envío de info")
                            .setItems(options, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    String mensaje = "Se ha realizado un pedido a nombre de " + extras.getString(PedidoEdit.PEDIDO_CLIENTE) +
                                            " con un coste de " + String.valueOf(MapaPedidos.calcularPrecioTotalPedido()) +
                                            " € y con la siguiente fecha de recogida: " + extras.getString(PedidoEdit.PEDIDO_FECHA) +
                                            ". El estado del pedido esta en " + extras.getString(PedidoEdit.PEDIDO_ESTADO);
                                    if (which == 0) {
                                        sms.send(String.valueOf(extras.getInt(PedidoEdit.PEDIDO_MOVIL)), mensaje);
                                    } else if (which == 1) {
                                        whatsapp.send(String.valueOf(extras.getInt(PedidoEdit.PEDIDO_MOVIL)), mensaje);
                                    } else if (which == 2) {
                                        finish();
                                    }
                                    finish();
                                }
                            });
                    builder.create().show();
                }
            }
        });

        populateFields();

        Button buttonAyadir = findViewById(R.id.btnAddDish);
        buttonAyadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AyadirPlatos dialogFragment = new AyadirPlatos();
                dialogFragment.show(getSupportFragmentManager(), "Añadir platos");
                actualizarPrecioTotalPedido();
            }
        });

        calendar = Calendar.getInstance();

        mFechaText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimePicker();
            }
        });

        TextView tvTotalPrice = findViewById(R.id.tvTotalPrice);

        String precio = "El precio del pedido es: " + String.valueOf(MapaPedidos.calcularPrecioTotalPedido()) + " €";
        tvTotalPrice.setText(precio);

    }

    private void showDateTimePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                showTimePicker();
            }
        };

        new DatePickerDialog(
                this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    private void showTimePicker() {
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);

                updateEditText();
            }
        };

        new TimePickerDialog(
                this,
                timeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true // is24HourView
        ).show();
    }

    private void updateEditText() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy/HH:mm", Locale.getDefault());
        mFechaText.setText(sdf.format(calendar.getTime()));
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Actualizar el precio cada vez que la actividad se reanuda
        actualizarPrecioTotalPedido();
    }

    // Método para actualizar el precio total del pedido
    protected void actualizarPrecioTotalPedido() {
        TextView tvTotalPrice = findViewById(R.id.tvTotalPrice);
        String precio = "El precio del pedido es: " + String.valueOf(MapaPedidos.calcularPrecioTotalPedido()) + " €";
        tvTotalPrice.setText(precio);
    }


    private void populateFields () {
        mRowId = null;
        Bundle extras = getIntent().getExtras();
        if (extras!=null) {
            mClienteText.setText(extras.getString(PedidoEdit.PEDIDO_CLIENTE));
            mMovilText.setText(String.valueOf(extras.getInt(PedidoEdit.PEDIDO_MOVIL)));
            mFechaText.setText(extras.getString(PedidoEdit.PEDIDO_FECHA));
            String estadoSeleccionado = extras.getString(PedidoEdit.PEDIDO_ESTADO);
            ArrayAdapter<String> adapter = (ArrayAdapter<String>) mEstadoText.getAdapter();
            mEstadoText.setSelection(adapter.getPosition(estadoSeleccionado));

            mRowId = extras.getInt(PedidoEdit.PEDIDO_ID);
        }
    }

}
