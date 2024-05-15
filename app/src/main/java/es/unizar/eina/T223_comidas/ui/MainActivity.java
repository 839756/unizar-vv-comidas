package es.unizar.eina.T223_comidas.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import es.unizar.eina.T223_comidas.R;
import es.unizar.eina.T223_comidas.database.PedidoRepository;
import es.unizar.eina.T223_comidas.database.PlatoRepository;

public class MainActivity extends AppCompatActivity {

    private PlatoViewModel mPlatoViewModel;
    private  PedidoViewModel mPedidoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPlatoViewModel = new ViewModelProvider(this).get(PlatoViewModel.class);
        mPedidoViewModel = new ViewModelProvider(this).get(PedidoViewModel.class);

        Button platosButton = findViewById(R.id.button1);

        platosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Platos.class);

                startActivity(intent);
            }
        });

        Button pedidosButton = findViewById(R.id.button2);

        pedidosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Pedidos.class);

                startActivity(intent);
            }
        });
    }

    public PedidoRepository getPedidoRepository() {
        return mPedidoViewModel.getPedidoRepository();
    }

    public PlatoRepository getPlatoRepository() {
        return mPlatoViewModel.getPlatoRepository();
    }

}
