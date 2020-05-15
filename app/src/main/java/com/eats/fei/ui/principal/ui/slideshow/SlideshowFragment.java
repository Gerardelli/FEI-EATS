package com.eats.fei.ui.principal.ui.slideshow;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.eats.fei.R;
import com.eats.fei.ui.principal.ui.gallery.GalleryFragment;
import com.eats.fei.ui.registrar.EditarActivity;
import com.eats.fei.ui.registrar.RegistrarProductoActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SlideshowFragment extends Fragment {

    private FirebaseDatabase dDatabase;
    private FirebaseAuth firebaseAuth = null;
    private SlideshowViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        final Button button3 = root.findViewById(R.id.button3);

        /*Apartado para boton*/
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SlideshowFragment.this.getContext(), RegistrarProductoActivity.class));
            }

        });
        /*Fin del apartado*/











        return root;
    }
}
