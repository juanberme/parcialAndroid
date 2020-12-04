package com.example.parcialandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView espacio;
    private EditText puntaje;
    private Button okBtn;
    private FirebaseDatabase db;
    private Pregunta question;
    private float resultado = 0;
    private int conteo = 1;
    private float prom;
    private String idString;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseDatabase.getInstance();

        espacio = findViewById(R.id.espacio);
        puntaje = findViewById(R.id.puntaje);
        okBtn = findViewById(R.id.okBtn);

        loadDatabase();


        okBtn.setOnClickListener(this);

    }

    private void loadDatabase() {

        DatabaseReference ref = db.getReference().child("preguntas").child("vista");
        ref.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot data) {
                        espacio.setText("");
                        for(DataSnapshot child : data.getChildren()){
                            question = child.getValue(Pregunta.class);
                            espacio.append(question.getPregunta());
                        }
                    }

                    @Override
                    public void onCancelled( DatabaseError error) {

                    }
                }
        );
    }

    public void promedio(){

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.okBtn:
                Log.e(">>>", "works");
                DatabaseReference reference = db.getReference().child("preguntas").child("vista").child(question.getId());
                idString = espacio.getText().toString();
                float suma = (float) Integer.parseInt(puntaje.getText().toString());
                resultado = resultado + suma;
                prom = resultado / conteo;
                if(idString == question.getPregunta()){
                    conteo += 1;
                }else{
                    conteo = 0;
                }

                String nuevoProm = String.valueOf(prom);

                Pregunta quest = new Pregunta(
                        question.getId(),
                        question.getPregunta(),
                        nuevoProm,
                        question.getEstado()

                );

                if (suma > 10){
                    Toast.makeText(this, "Debe ingresar una respuesta válida", Toast.LENGTH_SHORT).show();
                }else {
                    reference.setValue(quest);
                    puntaje.setText("");

                }

                Log.e("conteo" , ""+ conteo);
                Log.e("idString", idString);

                break;
        }
    }
}