package es.unizar.eina.T223_comidas.ui;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import es.unizar.eina.T223_comidas.database.Cantidad;
import es.unizar.eina.T223_comidas.database.CantidadRepository;

public class CantidadViewModel extends AndroidViewModel {

    private CantidadRepository mRepository;

    public CantidadViewModel(Application application) {
        super(application);
        mRepository = new CantidadRepository(application);
    }

    public void insert(Cantidad cantidad) { mRepository.insert(cantidad); }

    public void update(Cantidad cantidad) { mRepository.update(cantidad); }
    public void delete(Cantidad cantidad) { mRepository.delete(cantidad); }
    public List<Cantidad> getPlatosByPedido(int idPedido){ return mRepository.getPlatosByPedido(idPedido); }

    public void deletePlatosByPedido(int idPedido){ mRepository.deletePlatosByPedido(idPedido); }
}
