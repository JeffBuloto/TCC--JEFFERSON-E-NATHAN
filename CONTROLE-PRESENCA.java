package com.example.tcc;

import android.content.Context;
import android.content.SharedPreferences;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class ControlePresenca {

    private static final String PREFS_NAME = "PresencaPrefs";
    private static final String KEY_LISTA_PRESENCA = "lista_presenca";

    // Salva o ID do aluno com a data e hora da presença
    public static void registrarPresenca(Context context, String studentId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Set<String> presencas = new HashSet<>(prefs.getStringSet(KEY_LISTA_PRESENCA, new HashSet<>()));

        String dataHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(new Date());
        String registro = "ID: " + studentId + " | Hora: " + dataHora;

        // Evita duplicatas de ID no mesmo dia (opcional, aqui estamos salvando cada leitura)
        presencas.add(registro);

        prefs.edit().putStringSet(KEY_LISTA_PRESENCA, presencas).apply();
    }

    // Retorna a lista de todos os alunos que marcaram presença
    public static List<String> obterListaPresenca(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Set<String> presencas = prefs.getStringSet(KEY_LISTA_PRESENCA, new HashSet<>());
        return new ArrayList<>(presencas);
    }

    // Limpa a lista de presença (para uma nova aula)
    public static void limparPresencas(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().remove(KEY_LISTA_PRESENCA).apply();
    }
}
