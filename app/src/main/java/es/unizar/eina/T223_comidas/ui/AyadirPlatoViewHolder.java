package es.unizar.eina.T223_comidas.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import es.unizar.eina.T223_comidas.R;

class AyadirPlatoViewHolder extends RecyclerView.ViewHolder {
    private final TextView mPlatoItemView;
    private final TextView mPlatoItemView2;
    private final TextView mPlatoItemView3;

    private AyadirPlatoViewHolder(View itemView) {
        super(itemView);
        mPlatoItemView = itemView.findViewById(R.id.textView1);
        mPlatoItemView2 = itemView.findViewById(R.id.textView2);
        mPlatoItemView3 = itemView.findViewById(R.id.cantidad);
    }

    public void bind(String text1, String text2, String text3) {
        mPlatoItemView.setText(text1);
        mPlatoItemView2.setText(text2);
        mPlatoItemView3.setText(text3);
    }

    static AyadirPlatoViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_platos, parent, false);

        return new AyadirPlatoViewHolder(view);
    }

}

