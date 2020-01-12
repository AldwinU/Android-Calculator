package com.example.andre.mcalcpro;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import ca.roumani.i2c.MPro;

public class MCalcPro_Activity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
        setContentView(R.layout.mcalcpro_layout);
        tts = new TextToSpeech(this, this);
    }

    public void onInit(int initStatus) {
        tts.setLanguage(Locale.US);

    }

    public void onClick(View v) {
        try {
            MPro mp = new MPro();
            mp.setPrinciple(((EditText) findViewById(R.id.pBox)).getText().toString()); //Set principle to pbox
            String amortization = ((EditText) findViewById(R.id.aBox)).getText().toString();
            mp.setAmortization(amortization); //Set amortization to abox
            mp.setInterest(((EditText) findViewById(R.id.iBox)).getText().toString()); //Set interest to ibox
            String s = "Monthly Payment = " + mp.computePayment("%.2f");
            tts.speak(s, TextToSpeech.QUEUE_FLUSH, null);
            s += "\n\n";
            s += "By making these payments monthly for " + amortization + " years, the mortgage will be paid in full. But if you terminate the mortgage on its n'th anniversary, the balance still owing depends on n as shown below:\n\n";
            s += "       n          Balance\n\n";
            s += String.format("%8d", 0) + mp.outstandingAfter(0, "%,16.0f") + "\n\n";
            s += String.format("%8d", 1) + mp.outstandingAfter(1, "%,16.0f") + "\n\n";
            s += String.format("%8d", 2) + mp.outstandingAfter(2, "%,16.0f") + "\n\n";
            s += String.format("%8d", 3) + mp.outstandingAfter(3, "%,16.0f") + "\n\n";
            s += String.format("%8d", 4) + mp.outstandingAfter(4, "%,16.0f") + "\n\n";
            s += String.format("%8d", 5) + mp.outstandingAfter(5, "%,16.0f") + "\n\n";
            s += String.format("%8d", 10) + mp.outstandingAfter(10, "%,16.0f") + "\n\n";
            s += String.format("%8d", 15) + mp.outstandingAfter(15, "%,16.0f") + "\n\n";
            s += String.format("%8d", 20) + mp.outstandingAfter(20, "%,16.0f") + "\n\n";

            ((TextView) findViewById(R.id.output)).setText(s);


        } catch (Exception e) {
            Toast label = Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT);
            label.show();
        }
    }
}