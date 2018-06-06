package com.davidpopayan.sena.myapplication.Especializacion;

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
import com.davidpopayan.sena.myapplication.Fragmentos.FragmentTextEspe;
import com.davidpopayan.sena.myapplication.Fragmentos.FragmentViewPlay;
import com.davidpopayan.sena.myapplication.R;

public class infoespeci extends AppCompatActivity {
    Button btnVideo;
    FrameLayout contenedor;
    ImageView icono;
    TextView programa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infoespeci);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        btnVideo = findViewById(R.id.btnVideo);
        icono = findViewById(R.id.imginfoespeI);
        programa = findViewById(R.id.txtinfoespeT);
        programa.setText(MainActivity.staprogramaN);

        Fragment fragment = new FragmentTextEspe();
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor2,fragment).commit();

        btnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new FragmentViewPlay();
                getSupportFragmentManager().beginTransaction().replace(R.id.contenedor2,fragment).commit();
            }
        });
        inputImage();
    }

    public void back(View view) {
        finish();
    }

    public void inputImage(){
        switch (MainActivity.staIconoN){
            case 1:
                icono.setImageDrawable(getResources().getDrawable(R.drawable.audifoblanco));
                break;
            case 2:
                icono.setImageDrawable(getResources().getDrawable(R.drawable.psazulblan));
                break;

            case 3:
                icono.setImageDrawable(getResources().getDrawable(R.drawable.playblanco));
                break;
            case 4:
                icono.setImageDrawable(getResources().getDrawable(R.drawable.cubitoazul));
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
