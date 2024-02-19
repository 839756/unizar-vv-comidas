package es.unizar.eina.T223_comidas.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import es.unizar.eina.T223_comidas.R;

class PlatoViewHolder extends RecyclerView.ViewHolder {
    private final TextView mPlatoItemView;
    private final TextView mPlatoItemView2;

    private PlatoViewHolder(View itemView) {
        super(itemView);
        mPlatoItemView = itemView.findViewById(R.id.textView);
        mPlatoItemView2 = itemView.findViewById(R.id.textView2);
    }

    public void bind(String text1, String text2) {
        mPlatoItemView.setText(text1);
        mPlatoItemView2.setText(text2);
    }

    static PlatoViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);

        return new PlatoViewHolder(view);
    }

}

