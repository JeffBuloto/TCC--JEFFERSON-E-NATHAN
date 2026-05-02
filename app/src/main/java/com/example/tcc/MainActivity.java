package com.example.tcc;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 🔥 BOTÃO
        Button btn = findViewById(R.id.btnConectar);
        btn.setOnClickListener(v -> {

            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            if (bluetoothAdapter == null) {
                Toast.makeText(this, "Bluetooth não suportado", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!bluetoothAdapter.isEnabled()) {
                Toast.makeText(this, "Bluetooth está desligado", Toast.LENGTH_SHORT).show();
                return;
            }

            BluetoothLeScanner scanner = bluetoothAdapter.getBluetoothLeScanner();

            if (scanner == null) {
                Toast.makeText(this, "Erro ao iniciar scanner", Toast.LENGTH_SHORT).show();
                return;
            }

            // 🔥 Permissão de localização
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                return;
            }

            Toast.makeText(this, "Procurando dispositivos...", Toast.LENGTH_SHORT).show();

            scanner.startScan(new ScanCallback() {
                @Override
                public void onScanResult(int callbackType, ScanResult result) {
                    super.onScanResult(callbackType, result);

                    String nome = null;

                    // 🔥 CORREÇÃO DO ERRO AQUI
                    if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
                        nome = result.getDevice().getName();
                    }

                    if (nome != null) {
                        Toast.makeText(MainActivity.this, "Encontrado: " + nome, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
    }
}
