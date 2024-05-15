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
import es.unizar.eina.T223_comidas.database.Pedido;
import es.unizar.eina.T223_comidas.database.PedidoRepository;
import es.unizar.eina.T223_comidas.database.Plato;
import es.unizar.eina.T223_comidas.database.PlatoRepository;

/** Pantalla principal de pedidos */
public class Pedidos extends AppCompatActivity {
    private PedidoViewModel mPedidoViewModel;

    private CantidadViewModel mCantidadViewModel;

    static final int INSERT_ID = Menu.FIRST;

    static final int DELETE_ID = Menu.FIRST + 1;

    RecyclerView mRecyclerView;

    PedidoListAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);
        mRecyclerView = findViewById(R.id.recyclerview);
        mPedidoViewModel = new ViewModelProvider(this).get(PedidoViewModel.class);
        mCantidadViewModel = new ViewModelProvider(this).get(CantidadViewModel.class);
        mAdapter = new PedidoListAdapter(new PedidoListAdapter.PedidoDiff(), mPedidoViewModel, mCantidadViewModel);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mPedidoViewModel.getAllPedidosByCliente().observe(this, pedidos -> {
            mAdapter.submitList(pedidos);
        });

        FloatingActionButton mFab = findViewById(R.id.fabPedido);
        mFab.setOnClickListener(view -> {
            createPedido();
        });

        Button crearPedido =  findViewById(R.id.buttonCrearPedido);
        crearPedido.setOnClickListener(view -> {
            createPedido();
        });

        registerForContextMenu(mRecyclerView);

        ImageButton pedidosButton = findViewById(R.id.botonInicio1);

        pedidosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pedidos.this, MainActivity.class);

                startActivity(intent);
            }
        });

        Button buttonFiltrar = findViewById(R.id.buttonFiltrarPedido);
        buttonFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrdenarPedidos dialogFragment = new OrdenarPedidos(Pedidos.this, mPedidoViewModel, mAdapter);
                dialogFragment.show(getSupportFragmentManager(), "OrdenarPedidos");
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result = super.onCreateOptionsMenu(menu);
        menu.add(Menu.NONE, INSERT_ID, Menu.NONE, "Añadir pedido");
        return result;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case INSERT_ID:
                createPedido();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onContextItemSelected(MenuItem item) {
        Pedido current = mAdapter.getCurrent();
        switch (item.getItemId()) {
            case DELETE_ID:
                Toast.makeText(
                        getApplicationContext(),
                        "Borrando " + current.getCliente(),
                        Toast.LENGTH_LONG).show();
                mPedidoViewModel.delete(current);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void createPedido() {
        int numPedidos = mPedidoViewModel.getNumeroDePedidos();
        if(numPedidos < 2000) {
            Intent intent = new Intent(this, PedidoEdit.class);
            PedidoEdit.mPedidos = new MapaPedidos(mCantidadViewModel, 0);
            startActivity(intent);
        }
        else{
            Toast.makeText(getApplicationContext(), "Se ha alcanzado el máximo de pedidos",
                    Toast.LENGTH_LONG).show();
        }
    }

    public PedidoRepository getPedidoRepository() {
        return mPedidoViewModel.getPedidoRepository();
    }

}