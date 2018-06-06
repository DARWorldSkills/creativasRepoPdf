package com.davidpopayan.sena.myapplication.Videojuegos;

import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.davidpopayan.sena.myapplication.Controlador.MainActivity;
import com.davidpopayan.sena.myapplication.Fragmentos.FragmentTextPlay;
import com.davidpopayan.sena.myapplication.Fragmentos.FragmentViewPlay;
import com.davidpopayan.sena.myapplication.R;
import com.github.barteksc.pdfviewer.PDFView;

public class infoplay extends AppCompatActivity {
    Button btnVideo;
    FrameLayout contenedor;
    PDFView pdfView;

    ImageView icono;
    TextView programa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infoplay);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        btnVideo = findViewById(R.id.btnVideo);
        icono = findViewById(R.id.imginfoPlayI);
        programa = findViewById(R.id.txtinfoPlayT);
        programa.setText(MainActivity.staprogramaM);


        Fragment fragment = new FragmentTextPlay();
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,fragment).commit();

        btnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new FragmentViewPlay();
                getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,fragment).commit();
            }
        });
        inputImage();
    }

    public void back(View view) {
        finish();
    }

    public void inputImage(){
        switch (MainActivity.staIconoM){
            case 1:
                icono.setImageDrawable(getResources().getDrawable(R.drawable.audifoblanco));
                break;
            case 2:
                icono.setImageDrawable(getResources().getDrawable(R.drawable.psverdeblanco));
                break;

            case 3:
                icono.setImageDrawable(getResources().getDrawable(R.drawable.playblanco));
                break;
            case 4:
                icono.setImageDrawable(getResources().getDrawable(R.drawable.cubitover));
                break;
            case 5:
                icono.setImageDrawable(getResources().getDrawable(R.drawable.peopleblanco));
                break;
            case 6:
                icono.setImageDrawable(getResources().getDrawable(R.drawable.especyblanco));
                break;
            case 7:
                icono.setImageDrawable(getResources().getDrawable(R.drawable.lapiceroblanc));
                break;
            case 8:
                icono.setImageDrawable(getResources().getDrawable(R.drawable.videoblanco));
                break;


        }
    }
}
