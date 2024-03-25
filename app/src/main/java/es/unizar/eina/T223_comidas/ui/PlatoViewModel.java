package es.unizar.eina.T223_comidas.ui;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import es.unizar.eina.T223_comidas.database.Plato;
import es.unizar.eina.T223_comidas.database.PlatoRepository;

public class PlatoViewModel extends AndroidViewModel {

    private PlatoRepository mRepository;

    private final LiveData<List<Plato>> mAllPlatos;
    private final LiveData<List<Plato>> mCategoryAllPlatos;

    public PlatoViewModel(Application application) {
        super(application);
        mRepository = new PlatoRepository(application);
        mAllPlatos = mRepository.getAllPlatosByNombre();
        mCategoryAllPlatos = mRepository.getAllPlatosByCategoria();
    }

    LiveData<List<Plato>> getAllPlatosByNombre() { return mAllPlatos; }
    LiveData<List<Plato>> getAllPlatosByCategory() { return mCategoryAllPlatos; }

    public long insert(Plato plato) { return mRepository.insert(plato); }

    public int update(Plato plato) { return mRepository.update(plato); }
    public int delete(Plato plato) { return mRepository.delete(plato); }

    public int getNumeroDePlatos() { return mRepository.getNumeroDePlatos(); }

    public PlatoRepository getPlatoRepository() {
        return mRepository;
    }
}
