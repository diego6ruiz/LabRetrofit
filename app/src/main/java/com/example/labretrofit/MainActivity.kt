package com.example.labretrofit

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.labretrofit.retrofit.Retrofit2
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnBuscar: TextView = findViewById(R.id.btn_buscar)
        val editPokemon: EditText = findViewById(R.id.edit_pokemon)
        val mostrar: TextView = findViewById(R.id.mostrar)

        val numeros = "0123456789"
        val espacio = " "

        btnBuscar.setOnClickListener {

            //------------------------- RETROFIT 2 -----------------------------------
            val retrofit2 = Retrofit2()
            var busqueda = editPokemon.text.toString().toLowerCase()
            if (editPokemon!!.text.any {it in numeros}) {
                val toast = Toast.makeText(applicationContext, "La busqueda no puede contener numeros", Toast.LENGTH_SHORT)
                toast.show()
                mostrar.text = ""
            }
            if (busqueda.length > 50) {
                val toast = Toast.makeText(applicationContext, "La busqueda no puede exceder los 50 caracteres", Toast.LENGTH_SHORT)
                toast.show()
                mostrar.text = ""
            }
            if (editPokemon!!.text.any {it in espacio}) {
                val toast = Toast.makeText(applicationContext, "La busqueda no puede contener espacios", Toast.LENGTH_SHORT)
                toast.show()
                mostrar.text = ""
            }
            val call: Call<JsonObject> = retrofit2.getService().getPokemonById(busqueda)


            call.enqueue(object : Callback<JsonObject?> {
                override fun onResponse(call: Call<JsonObject?>, response: Response<JsonObject?>) {
                    if (response.isSuccessful) {

                        //Recuerden que la data puede venir dentro de un jsonArray o un jsonObject de primeras.
                        //JSONArray data = new JSONArray(s);
                        val data: JsonObject?
                        data = response.body()

                        val nombre = data!!["name"].asString.capitalize()


                        assert(data != null)
                        val imprimirInfo = "\nNombre del Pokemon: "+nombre+
                                "\nExperiencia Base: ${data!!["base_experience"]}"+
                                "\nAltura: ${data!!["height"]}"+
                                "\nPeso: ${data!!["weight"]}"
                        mostrar.text = imprimirInfo
                        Log.i("detalle", data.toString())

                    } else {
                        Log.e("error", "Hubo un error inesperado!")

                    }
                }

                override fun onFailure(call: Call<JsonObject?>, t: Throwable) {
                    Log.e("error", t.toString())
                }
            })
        }

    }
}