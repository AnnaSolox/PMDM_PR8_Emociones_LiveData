package com.example.emociones_livedata;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class EmocionesViewModel extends AndroidViewModel{
    Emociones emociones;

    public EmocionesViewModel(@NonNull Application application) {
        super(application);
        emociones = new Emociones();
    }

    public LiveData<Emociones.EmocionModelo> obtenerEmocion() {
        return emociones.emocionModeloLiveData;
    }
}
