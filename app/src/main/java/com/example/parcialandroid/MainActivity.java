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
        //para cargar la seccion que debe recibir de la base de datos
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
                //para llamar la seccion que la base de datos debe leer
                DatabaseReference reference = db.getReference().child("preguntas").child("vista").child(question.getId());
                //para que recoja el valor que tiene el TextView "espacio"
                idString = espacio.getText().toString();
                //para que me convierta en flotante lo que se agrega al EditText "puntaje"
                float suma = (float) Integer.parseInt(puntaje.getText().toString());
                //para asignarle un nuevo valor a resultado
                resultado = resultado + suma;
                //para sacar el promedio
                prom = resultado / conteo;
                //la intencion era sacar asignarle =0 a "conteo" para que no se dañe el promedio
                if(idString == question.getPregunta()){
                    conteo += 1;
                }else{
                    conteo = 0;
                }

                //para convertir en String el resultado de "prom"
                String nuevoProm = String.valueOf(prom);

                Pregunta quest = new Pregunta(
                        question.getId(),
                        question.getPregunta(),
                        nuevoProm,
                        question.getEstado()

                );

                //las condiciones para que se modifiquen los valores en la base de datos
                if (suma > 10 /*|| puntaje.getText().toString() == ""*/){
                    Toast.makeText(this, "Debe ingresar una respuesta válida", Toast.LENGTH_SHORT).show();
                }else {
                    reference.setValue(quest);
                    puntaje.setText("");

                }

                break;
        }
    }
}