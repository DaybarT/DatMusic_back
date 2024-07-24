package com.example.music.Test;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonExample {
    public static void main(String[] args) {
        // JSON recuperado de la base de datos (simulado como una cadena)
        String jsonStringFromDB = "{\"listas\":{\"Favoritas\":[1,9,8,33,4534],\"Mood\":[]}}";

        try {
            // Convertir la cadena JSON en un objeto JSON
            JSONObject jsonObject = new JSONObject(jsonStringFromDB);

            // Obtener el objeto "listas" del JSON
            JSONObject listas = jsonObject.getJSONObject("listas");

            // Obtener el JSONArray "Favoritas" y añadir un nuevo índice
            JSONArray favoritas = listas.getJSONArray("Favoritas");
            favoritas.put(12345);

            // Actualizar el JSON con la lista Favoritas modificada
            listas.put("Favoritas", favoritas);

            // Convertir el objeto JSON actualizado a una cadena y mostrarlo
            String updatedJsonString = jsonObject.toString();
            System.out.println("Updated JSON: " + updatedJsonString);

            // Aquí podrías almacenar updatedJsonString en local storage usando JavaScript en una aplicación web
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

