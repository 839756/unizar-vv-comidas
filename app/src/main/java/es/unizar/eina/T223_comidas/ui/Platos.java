package es.unizar.eina.T223_comidas.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import es.unizar.eina.T223_comidas.R;
import es.unizar.eina.T223_comidas.database.Plato;
import es.unizar.eina.T223_comidas.database.PlatoRepository;

/** Pantalla principal de platos */
public class Platos extends AppCompatActivity {
    private PlatoViewModel mPlatoViewModel;

    static final int INSERT_ID = Menu.FIRST;

    RecyclerView mRecyclerView;

    PlatoListAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_platos);
        mRecyclerView = findViewById(R.id.recyclerview);
        mPlatoViewModel = new ViewModelProvider(this).get(PlatoViewModel.class);
        mAdapter = new PlatoListAdapter(new PlatoListAdapter.PlatoDiff(), mPlatoViewModel);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mPlatoViewModel.getAllPlatosByNombre().observe(this, platos -> {
            mAdapter.submitList(platos);
        });

        FloatingActionButton mFab = findViewById(R.id.fab);
        mFab.setOnClickListener(view -> {
            createPlato();
        });

        Button crearPlato =  findViewById(R.id.buttonCrearPlato);
        crearPlato.setOnClickListener(view -> {
            createPlato();
        });

        registerForContextMenu(mRecyclerView);

        ImageButton platosButton = findViewById(R.id.botonInicio1);

        platosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Platos.this, MainActivity.class);

                startActivity(intent);
            }
        });

        Button buttonFiltrar = findViewById(R.id.buttonFiltrarPlato);
        buttonFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrdenarPlatos dialogFragment = new OrdenarPlatos(Platos.this, mPlatoViewModel, mAdapter);
                dialogFragment.show(getSupportFragmentManager(), "OrdenarPlatos");
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result = super.onCreateOptionsMenu(menu);
        menu.add(Menu.NONE, INSERT_ID, Menu.NONE, R.string.add_plato);
        return result;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case INSERT_ID:
                createPlato();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createPlato() {
        int numPlatos = mPlatoViewModel.getNumeroDePlatos();
        if(numPlatos < 100) {
            Intent intent = new Intent(this, PlatoEdit.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(getApplicationContext(), "Se ha alcanzado el máximo de platos",
                    Toast.LENGTH_LONG).show();
        }
    }

    public PlatoRepository getPlatoRepository(){
        return mPlatoViewModel.getPlatoRepository();
    }

}