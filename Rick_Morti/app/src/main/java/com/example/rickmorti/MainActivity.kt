package com.example.rickmorti

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var btnAnterior: Button
    private lateinit var btnSiguiente: Button
    private lateinit var textPagina: TextView
    private lateinit var adapter: PersonajeAdapter

    private var paginaActual = 1
    private var tieneSiguiente = true
    private var tieneAnterior = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerPersonajes)
        progressBar = findViewById(R.id.progressBar)
        btnAnterior = findViewById(R.id.btnAnterior)
        btnSiguiente = findViewById(R.id.btnSiguiente)
        textPagina = findViewById(R.id.textPagina)

        adapter = PersonajeAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        btnAnterior.setOnClickListener {
            if (tieneAnterior) {
                paginaActual--
                cargarPersonajes(paginaActual)
            }
        }

        btnSiguiente.setOnClickListener {
            if (tieneSiguiente) {
                paginaActual++
                cargarPersonajes(paginaActual)
            }
        }

        cargarPersonajes(paginaActual)
    }

    private fun cargarPersonajes(pagina: Int) {
        progressBar.visibility = View.VISIBLE
        btnAnterior.isEnabled = false
        btnSiguiente.isEnabled = false

        lifecycleScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    RetrofitClient.apiService.getPersonajes(pagina)
                }
                progressBar.visibility = View.GONE
                adapter.actualizarLista(response.results)
                textPagina.text = "Página $pagina"

                tieneAnterior = response.info.prev != null
                tieneSiguiente = response.info.next != null
                btnAnterior.isEnabled = tieneAnterior
                btnSiguiente.isEnabled = tieneSiguiente

                animarCarga()

            } catch (e: Exception) {
                progressBar.visibility = View.GONE
                btnAnterior.isEnabled = true
                btnSiguiente.isEnabled = true
                Log.e("RM_DEBUG", "Error al consumir la API", e)
                Toast.makeText(
                    this@MainActivity, "Error al cargar: ${e.localizedMessage}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    // Dos animaciones consecutivas de 5 segundos cada una
    private fun animarCarga() {
        recyclerView.alpha = 0f
        val fadeIn = ObjectAnimator.ofFloat(recyclerView, "alpha", 0f, 1f)
        fadeIn.duration = 5000

        fadeIn.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                val escala = ObjectAnimator.ofFloat(recyclerView, "scaleX", 1f, 1.02f, 1f)
                escala.duration = 5000
                escala.start()
            }
        })
        fadeIn.start()
    }
}
