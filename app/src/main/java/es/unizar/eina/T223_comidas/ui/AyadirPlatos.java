package es.unizar.eina.T223_comidas.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import es.unizar.eina.T223_comidas.R;
import es.unizar.eina.T223_comidas.database.Pedido;

public class AyadirPlatos extends DialogFragment {

    AyadirPlatoListAdapter mAdapter;
    RecyclerView mRecyclerView;
    private PlatoViewModel mPlatoViewModel;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_ayadir_platos, null);

        mPlatoViewModel = new ViewModelProvider(this).get(PlatoViewModel.class);
        mAdapter = new AyadirPlatoListAdapter(new AyadirPlatoListAdapter.AyadirPlatoDiff(), mPlatoViewModel);
        mRecyclerView = view.findViewById(R.id.recyclerviewAyadir);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        mPlatoViewModel.getAllPlatosByCategory().observe(this, platos -> {
            mAdapter.submitList(platos);
        });

        registerForContextMenu(mRecyclerView);


        Button closeButton = view.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        builder.setView(view);

        return builder.create();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);

        // Llamamos al método en la actividad principal para actualizar el precio del pedido
        if (getActivity() instanceof PedidoEdit) {
            ((PedidoEdit) getActivity()).actualizarPrecioTotalPedido();
        }
    }

}
