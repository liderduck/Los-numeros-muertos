package jona.losnumerosmuertos;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class Main2Activity extends AppCompatActivity {
    private TextView t4,t5,t6;
    private EditText ed1;
    private int numIntentos,numAAdiv;
    private String numAdiv;
    private Button b3;
    private NotificationManager elnotificationmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        t4 = (TextView) findViewById(R.id.textView4);
      //  t5 = (TextView) findViewById(R.id.textView5);
        t6 = (TextView) findViewById(R.id.textView6);
        b3=(Button) findViewById(R.id.button3);
        CharSequence dif = getIntent().getCharSequenceExtra("cadenaDif");
        elnotificationmanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        t4.setText(dif);
        numIntentos=crearPartida(dif);

        int min = 0;
        int max = 99;

        Random r = new Random();
        numAAdiv = r.nextInt(max - min + 1) + min;
        numAdiv = String.valueOf(numAAdiv);
        Log.i("hola","numero"+numAdiv);
    }
    public void empJugar(View v){
        t5 = (TextView) findViewById(R.id.textView5);
        numIntentos--;
        t5.setText(String.valueOf(numIntentos));
        ed1 = (EditText) findViewById(R.id.eT);
        String numPos =ed1.getText().toString();
        jugar(numAdiv, numPos, numIntentos);
    }


    public void jugar(String numAAdiv,String numPos,Integer numIntentos){
        int numMuertos=0;
        int numHeridos=0;
        ArrayList<numero> arNumAdiv = new ArrayList<numero>();
        numero a1 = new numero(numAAdiv.charAt(0),false,false);
        numero a2 = new numero(numAAdiv.charAt(1),false,false);
        arNumAdiv.add(a1);
        arNumAdiv.add(a2);
        numero acAdiv;

        ArrayList<numero> arNumPos = new ArrayList<numero>();
        numero b1 = new numero(numPos.charAt(0),false,false);
        numero b2 = new numero(numPos.charAt(1),false,false);
        arNumPos.add(b1);
        arNumPos.add(b2);
        numero acPos;

        if(numIntentos!=0){
            for(int i=0;i<arNumAdiv.size();i++) {
                acAdiv=arNumAdiv.get(i);
                for (int t = 0; t < arNumPos.size(); t++) {
                    acPos=arNumPos.get(t);
                    if( acAdiv.muerto==false ) {
                        if (acAdiv.valor.equals(acPos.valor) && (i == t)) {
                            numMuertos++;
                            acAdiv.muerto = true;
                            acPos.muerto= true;
                            if(acPos.herido==true){
                                numHeridos--;
                            }
                        } else if (acAdiv.valor.equals(acPos.valor) && (i != t) && (acPos.herido == false)&& (acPos.muerto==false)) {
                            numHeridos++;
                            acPos.herido = true;
                        }
                    }
                }
            }
        }else{
            b3.setEnabled(false);
            perder().show();
        }
        if(numAAdiv.equals(numPos)){
           notif();
            b3.setEnabled(false);
        }
       t6.append("Nº heridos"+String.valueOf(numHeridos)+"Nº muertos"+String.valueOf(numMuertos)+System.getProperty("line.separator"));
    }

    public int crearPartida(CharSequence dif){
        t5 = (TextView) findViewById(R.id.textView5);
        if (dif.equals("1 FACIL")){
            t5.setText("20");
            numIntentos=20;
        }else if(dif.equals("2 MEDIO")){
            t5.setText("15");
            numIntentos=15;
        }else if(dif.equals("3 DIFICIL")){
            t5.setText("10");
            numIntentos=10;
        }
        return numIntentos;
    }

    public AlertDialog perder(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
        builder.setMessage("Ohhh has perdido")
                .setTitle("Fin del juego")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        return builder.create();
    }

    private void notif() {
        NotificationCompat.Builder elconstructor = new NotificationCompat.Builder(Main2Activity.this);
        elconstructor.setContentText("Enhorabuena!! Has ganado");
        elconstructor.setContentTitle("Juego finalizado");
        elconstructor.setSmallIcon(android.R.drawable.stat_sys_warning);
        elnotificationmanager.notify(1, elconstructor.build());

    }

}
