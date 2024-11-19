package com.example.emociones_livedata;

import android.graphics.Color;

import androidx.lifecycle.LiveData;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Emociones {

    interface EmocionListener {
        void onNuevaEmocion(EmocionModelo emocionModelo);
    }

    public static class EmocionModelo {
        public final int color;
        public final int imagen;
        public final String texto;

        public EmocionModelo(int color, int imagen, String texto){
            this.color = color;
            this.imagen = imagen;
            this.texto = texto;
        }
    }

    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    ScheduledFuture<?> emocionFutura;

    private final EmocionModelo[] emociones = {
            new EmocionModelo(Color.parseColor("#F3D7CE"), R.drawable.ic_enamorado, "Amor"),
            new EmocionModelo(Color.parseColor("#A8D9E4"), R.drawable.ic_sonrojado, "Vergüenza"),
            new EmocionModelo(Color.parseColor("#FBD750"), R.drawable.ic_contento, "Alegría"),
            new EmocionModelo(Color.parseColor("#9D9D9C"), R.drawable.ic_triste,"Tristeza")
    };

    void iniciar(EmocionListener emocionListener){
        if (emocionFutura == null || emocionFutura.isCancelled()) {
            emocionFutura = scheduler.scheduleWithFixedDelay(new Runnable() {
                int indice = 0;

                @Override
                public void run() {
                    emocionListener.onNuevaEmocion(emociones[indice]);
                    indice = (indice+1) % emociones.length;
                }
            }, 0, 4, TimeUnit.SECONDS);
        }
    }

    void detener(){
        if (emocionFutura != null) {
            emocionFutura.cancel(true);
        }
    }

    LiveData<EmocionModelo> emocionModeloLiveData = new LiveData<EmocionModelo>() {
        @Override
        protected void onActive(){
            super.onActive();
            iniciar(this::postValue);
        }

        @Override
        protected void onInactive() {
            super.onInactive();
            detener();
        }

    };
}
