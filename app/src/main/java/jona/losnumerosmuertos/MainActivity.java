package jona.losnumerosmuertos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public AlertDialog selDif(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Elige la dificultad del juego");
        final CharSequence[] opciones ={"1 FACIL","2 MEDIO","3 DIFICIL"};
        builder.setSingleChoiceItems(opciones, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), opciones[which], Toast.LENGTH_SHORT).show();
                Log.i("dialogopulsado", "opcion" + opciones[which]);
                nxAct(opciones[which]);
            }
        });
        return builder.create();
    }

    public void inJuego(View v){
        selDif().show();
    }
    public AlertDialog disInfo(){
        AlertDialog.Builder builder =
                new AlertDialog.Builder(MainActivity.this);

        builder.setMessage("Se trata de adivinar un numero de 4 cifras. Se da otro numero si una de las cifras esta en el numero ha adivinar se clasificara como numero herido,si ademas se encuentra en la misma posicion el numero estara muerto")
                .setTitle("Información de juego")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        return builder.create();
    }
    public void info(View v){
        disInfo().show();
    }

    public void nxAct(CharSequence opciones){
        finish();
        Intent i= new Intent(this,Main2Activity.class);
        i.putExtra("cadenaDif",opciones);
        startActivity(i);
    }
}
