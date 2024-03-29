package es.unizar.eina.T223_comidas.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import es.unizar.eina.T223_comidas.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PlatoViewModel mPlatoViewModel = new ViewModelProvider(this).get(PlatoViewModel.class);
        PedidoViewModel mPedidoViewModel = new ViewModelProvider(this).get(PedidoViewModel.class);

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
}
