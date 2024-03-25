package es.unizar.eina.T223_comidas.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
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

        UnitTest unitTest = new UnitTest(mPlatoViewModel, mPedidoViewModel);

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

        Button testButton = findViewById(R.id.Pruebas);

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create and display a list dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Select a Test");

                // Add choices for tests
                String[] tests = {"testUnitarios", "testVolumen", "testSobrecarga"};
                builder.setItems(tests, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Call the selected test method
                        switch (which) {
                            case 0: unitTest.testUnitarios(); break;
                            case 1: unitTest.testVolumen(); break;
                            case 2: unitTest.testSobrecarga(); break;
                        }
                    }
                });

                // Create and show the dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
}
