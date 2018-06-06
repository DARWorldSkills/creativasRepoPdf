package com.davidpopayan.sena.myapplication.Fragmentos;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davidpopayan.sena.myapplication.R;
import com.github.barteksc.pdfviewer.PDFView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTextPlay extends Fragment {
    PDFView pdfView;


    public FragmentTextPlay() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragment_text_play, container, false);

        pdfView = view.findViewById(R.id.prueba);

        pdfView.fromAsset("primer.pdf").load();


        return view;
    }

}
