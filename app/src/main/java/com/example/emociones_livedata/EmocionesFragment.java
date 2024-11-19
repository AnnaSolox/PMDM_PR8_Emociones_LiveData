package com.example.emociones_livedata;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.emociones_livedata.databinding.FragmentEmocionesBinding;


public class EmocionesFragment extends Fragment {

    private FragmentEmocionesBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return (binding = FragmentEmocionesBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EmocionesViewModel emocionesViewModel = new ViewModelProvider(this).get(EmocionesViewModel.class);

        binding.tituloEmocion.setVisibility(View.VISIBLE);
        binding.imagenEmocion.setVisibility(View.GONE);
        binding.textoEmocion.setVisibility(View.GONE);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            binding.tituloEmocion.setVisibility(View.GONE);

            emocionesViewModel.obtenerEmocion().observe(getViewLifecycleOwner(), new Observer<Emociones.EmocionModelo>() {
                @Override
                public void onChanged(Emociones.EmocionModelo emocionModelo) {
                    Glide.with(EmocionesFragment.this).load(emocionModelo.imagen).into(binding.imagenEmocion);
                    binding.imagenEmocion.setBackgroundColor(emocionModelo.color);
                    binding.textoEmocion.setText(emocionModelo.texto);

                    binding.imagenEmocion.setVisibility(View.VISIBLE);
                    binding.textoEmocion.setVisibility(View.VISIBLE);


                }
            });
        }, 4000);
    }
}