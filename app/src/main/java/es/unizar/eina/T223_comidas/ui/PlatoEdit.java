package es.unizar.eina.T223_comidas.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import es.unizar.eina.T223_comidas.R;
import es.unizar.eina.T223_comidas.database.Plato;

/** Pantalla utilizada para la creación o edición de un plato */
public class PlatoEdit extends AppCompatActivity {

    public static final String PLATO_NOMBRE = "nombre";
    public static final String PLATO_DESC = "descripcion";
    public static final String PLATO_CATEG = "categoria";
    public static final String PLATO_PRECIO = "precio";
    public static final String PLATO_ID = "id";

    private EditText mNombreText;

    private EditText mDescText;

    private EditText mPrecioText;

    private Spinner mCategText;

    private Integer mRowId;

    private PlatoViewModel mPlatoViewModel;

    Button mSaveButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_platoedit);

        mNombreText = findViewById(R.id.nombrePlato);
        mDescText = findViewById(R.id.descPlato);
        mCategText = findViewById(R.id.categPlato);
        mPrecioText = findViewById(R.id.precioPlato);

        mPlatoViewModel = new ViewModelProvider(this).get(PlatoViewModel.class);

        mSaveButton = findViewById(R.id.button_save);
        mSaveButton.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(mNombreText.getText()) || TextUtils.isEmpty(mPrecioText.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
                Toast.makeText(getApplicationContext(), R.string.empty_not_saved,
                        Toast.LENGTH_LONG).show();
            } else {
                replyIntent.putExtra(PlatoEdit.PLATO_NOMBRE, mNombreText.getText().toString());
                replyIntent.putExtra(PlatoEdit.PLATO_DESC, mDescText.getText().toString());
                replyIntent.putExtra(PlatoEdit.PLATO_CATEG, mCategText.getSelectedItem().toString());
                replyIntent.putExtra(PlatoEdit.PLATO_PRECIO, Double.parseDouble(mPrecioText.getText().toString()));
                if (mRowId!=null) {
                    replyIntent.putExtra(PlatoEdit.PLATO_ID, mRowId.intValue());
                }
                setResult(RESULT_OK, replyIntent);

                //Una vez definido el resultado correcto se hacen las modificaciones o inserciones pertinentes
                Bundle extras = replyIntent.getExtras();
                int id = extras.getInt(PlatoEdit.PLATO_ID);
                //Si el id obtenido es 0 quiere decir que se esta creado un plato por lo que no se hace update
                if (id != 0) {
                    Plato updatedPlato = new Plato(extras.getString(PlatoEdit.PLATO_NOMBRE), extras.getString(PlatoEdit.PLATO_DESC),
                            extras.getString(PlatoEdit.PLATO_CATEG), extras.getDouble(PlatoEdit.PLATO_PRECIO));
                    updatedPlato.setId(id);
                    mPlatoViewModel.update(updatedPlato);
                }
                else{   //Si el id obtenido es 0 nos encontramos en el caso de crear
                    Plato newPlato = new Plato(extras.getString(PlatoEdit.PLATO_NOMBRE), extras.getString(PlatoEdit.PLATO_DESC),
                            extras.getString(PlatoEdit.PLATO_CATEG), extras.getDouble(PlatoEdit.PLATO_PRECIO));
                    mPlatoViewModel.insert(newPlato);
                }
            }
            finish();
        });

        populateFields();

    }

    private void populateFields () {
        mRowId = null;
        Bundle extras = getIntent().getExtras();
        if (extras!=null) {
            mNombreText.setText(extras.getString(PlatoEdit.PLATO_NOMBRE));
            mDescText.setText(extras.getString(PlatoEdit.PLATO_DESC));
            String categoriaSeleccionada = extras.getString(PlatoEdit.PLATO_CATEG);
            ArrayAdapter<String> adapter = (ArrayAdapter<String>) mCategText.getAdapter();
            mCategText.setSelection(adapter.getPosition(categoriaSeleccionada));
            mPrecioText.setText(String.valueOf(extras.getDouble(PlatoEdit.PLATO_PRECIO)));

            mRowId = extras.getInt(PlatoEdit.PLATO_ID);
        }
    }
}
