package com.davidpopayan.sena.myapplication.Animacion;

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

public class Infocaja extends AppCompatActivity {

    Button btnVideo;
    FrameLayout contenedor;
    ImageView icono;
    TextView programa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infocaja);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Fragment fragment = new FragmentTextPlay();
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor1,fragment).commit();

        btnVideo = findViewById(R.id.btnVideo);
        icono = findViewById(R.id.imginfoCajaI);
        programa = findViewById(R.id.txtinfocajaT);
        programa.setText(MainActivity.staprogramaT);

        btnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new FragmentViewPlay();
                getSupportFragmentManager().beginTransaction().replace(R.id.contenedor1,fragment).commit();
            }
        });
        inputImage();
    }

    public void back(View view) {
        finish();
    }

    public void inputImage(){
        switch (MainActivity.staIconoT){
            case 1:
                icono.setImageDrawable(getResources().getDrawable(R.drawable.audifoblanco));
                break;
            case 2:
                icono.setImageDrawable(getResources().getDrawable(R.drawable.psnaranjita));
                break;

            case 3:
                icono.setImageDrawable(getResources().getDrawable(R.drawable.playblanco));
                break;
            case 4:
                icono.setImageDrawable(getResources().getDrawable(R.drawable.cubitonara));
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
