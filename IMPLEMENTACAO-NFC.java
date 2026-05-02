package com.example.tcc;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class NfcActivity extends AppCompatActivity {

    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private IntentFilter[] intentFiltersArray;
    private String[][] techListsArray;

    private boolean faceAuthenticated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); 

        // Verifica se veio da autenticação facial
        faceAuthenticated = getIntent().getBooleanExtra("FACE_AUTHENTICATED", false);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (nfcAdapter == null) {
            Toast.makeText(this, "NFC não suportado neste dispositivo", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // Preparar a Intent para capturar tags NFC enquanto o app está aberto
        Intent intent = new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE);

        // Configurar filtros para capturar qualquer tag
        IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try {
            ndef.addDataType("*/*");
        } catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("Falha ao configurar filtro NFC", e);
        }
        intentFiltersArray = new IntentFilter[]{ndef, new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED), new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED)};
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (nfcAdapter != null) {
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, null);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        resolveIntent(intent);
    }

    private void resolveIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            
            if (!faceAuthenticated) {
                Toast.makeText(this, "ERRO: Faça o reconhecimento facial primeiro!", Toast.LENGTH_LONG).show();
                return;
            }

            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            if (tag != null) {
                byte[] id = tag.getId();
                String tagId = bytesToHexString(id);
                
                // Registro da presença
                ControlePresenca.registrarPresenca(this, tagId);
                
                Toast.makeText(this, "Presença Confirmada!\nID: " + tagId, Toast.LENGTH_LONG).show();
            }
        }
    }

    // Utilitário para converter o ID da tag (bytes) para String Legível
    private String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (byte b : src) {
            int v = b & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString().toUpperCase();
    }
}
