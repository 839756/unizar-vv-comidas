package es.unizar.eina.T223_comidas.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.Dialog;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;

import es.unizar.eina.T223_comidas.R;

public class OrdenarPlatos extends DialogFragment {

    PlatoListAdapter mAdapter;
    private PlatoViewModel mPlatoViewModel;

    private Platos platosContext;



    public OrdenarPlatos(Platos platosContext, PlatoViewModel platoViewModel, PlatoListAdapter platoListAdapter) {
        this.platosContext = platosContext;
        this.mPlatoViewModel = platoViewModel;
        this.mAdapter = platoListAdapter;


    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.ordenar_platos_pop, null);
        PlatoViewModel platoViewModel = new ViewModelProvider(this).get(PlatoViewModel.class);

        Button buttonFilterByName = view.findViewById(R.id.buttonFilterByName);
        Button buttonFilterByCategory = view.findViewById(R.id.buttonFilterByCategory);

        buttonFilterByName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlatoViewModel.getAllPlatosByNombre().observe(platosContext, platos -> {
                    mAdapter.submitList(platos);
                });
                dismiss();
            }
        });

        buttonFilterByCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlatoViewModel.getAllPlatosByCategory().observe(platosContext, platos -> {
                    mAdapter.submitList(platos);
                });
                dismiss();
            }
        });


        builder.setView(view);

        return builder.create();
    }
}
