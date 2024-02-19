package es.unizar.eina.T223_comidas.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import es.unizar.eina.T223_comidas.R;

public class OrdenarPedidos extends DialogFragment {

    PedidoListAdapter mAdapter;
    private PedidoViewModel mPedidoViewModel;
    private Pedidos pedidosContext;

    public OrdenarPedidos(Pedidos pedidosContext, PedidoViewModel pedidoViewModel, PedidoListAdapter pedidoListAdapter) {
        this.pedidosContext = pedidosContext;
        this.mPedidoViewModel = pedidoViewModel;
        this.mAdapter = pedidoListAdapter;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.ordenar_pedidos_pop, null);
        PedidoViewModel pedidoViewModel = new ViewModelProvider(this).get(PedidoViewModel.class);

        Button buttonFilterByCliente = view.findViewById(R.id.buttonFilterByCliente);
        Button buttonFilterByMovil = view.findViewById(R.id.buttonFilterByTelefono);
        Button buttonFilterByFecha = view.findViewById(R.id.buttonFilterByFecha);
        Button buttonFilterByEstado = view.findViewById(R.id.buttonFilterByEstado);

        buttonFilterByCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPedidoViewModel.getAllPedidosByCliente().observe(pedidosContext, pedidos -> {
                    mAdapter.submitList(pedidos);
                });
                dismiss();
            }
        });

        buttonFilterByMovil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPedidoViewModel.getAllPedidosByMovil().observe(pedidosContext, pedidos -> {
                    mAdapter.submitList(pedidos);
                });
                dismiss();
            }
        });

        buttonFilterByFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPedidoViewModel.getAllPedidosByFecha().observe(pedidosContext, pedidos -> {
                    mAdapter.submitList(pedidos);
                });
                dismiss();
            }
        });

        buttonFilterByEstado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPedidoViewModel.getAllPedidosByEstado().observe(pedidosContext, pedidos -> {
                    mAdapter.submitList(pedidos);
                });
                dismiss();
            }
        });


        builder.setView(view);

        return builder.create();
    }
}
