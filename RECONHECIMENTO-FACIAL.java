package com.example.tcc;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class FaceRecognitionActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private boolean isFaceAuthenticated = false;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_recognition); // Você precisará criar este layout

        imageView = findViewById(R.id.faceImageView);
        Button btnCapture = findViewById(R.id.btnCaptureFace);

        btnCapture.setOnClickListener(v -> {
            dispatchTakePictureIntent();
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);

            // SIMULAÇÃO DE RECONHECIMENTO FACIAL
            // Em um cenário real, aqui você usaria o Google ML Kit ou OpenCV 
            // para comparar a 'imageBitmap' com a foto salva no cadastro do aluno.
            validarFace(imageBitmap);
        }
    }

    private void validarFace(Bitmap capturedFace) {
        // Lógica de validação simplificada para o TCC
        boolean match = simulateFaceMatch(capturedFace);

        if (match) {
            isFaceAuthenticated = true;
            Toast.makeText(this, "Rosto reconhecido! Agora aproxime sua Tag NFC.", Toast.LENGTH_LONG).show();
            
            // Inicia a lógica de NFC apenas após o rosto ser aprovado
            iniciarFluxoNFC();
        } else {
            isFaceAuthenticated = false;
            Toast.makeText(this, "Rosto não reconhecido. Tente novamente.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean simulateFaceMatch(Bitmap face) {
        // Aqui entraria a lógica de comparação de biometria facial
        return face != null; // Simulando que qualquer rosto detectado é válido
    }

    private void iniciarFluxoNFC() {
        // Redireciona para a Activity de NFC ou ativa o sensor
        Intent intent = new Intent(this, NfcActivity.class);
        intent.putExtra("FACE_AUTHENTICATED", true);
        startActivity(intent);
    }
}
