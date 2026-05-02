package com.example.tcc;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Botão para iniciar o fluxo de presença (Face -> NFC)
        Button btnPresenca = findViewById(R.id.btnIniciarChamada);
        btnPresenca.setOnClickListener(v -> {
            Intent intent = new Intent(this, FaceRecognitionActivity.class);
            startActivity(intent);
        });

        // Botão para o Scanner Bluetooth do Professor
        Button btnBluetooth = findViewById(R.id.btnBluetooth);
        btnBluetooth.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        // Botão para ver o histórico de presenças
        Button btnHistorico = findViewById(R.id.btnVerPresencas);
        btnHistorico.setOnClickListener(v -> {
            // Implementação futura de uma lista
            android.widget.Toast.makeText(this, "Histórico em desenvolvimento...", android.widget.Toast.LENGTH_SHORT).show();
        });
    }
}
