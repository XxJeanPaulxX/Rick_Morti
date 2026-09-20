package com.example.rickmorti

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PersonajeAdapter(private var lista: List<Personaje>) :
    RecyclerView.Adapter<PersonajeAdapter.PersonajeViewHolder>() {

    class PersonajeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val img: ImageView = view.findViewById(R.id.imgPersonaje)
        val nombre: TextView = view.findViewById(R.id.textNombre)
        val estado: TextView = view.findViewById(R.id.textEstado)
        val especie: TextView = view.findViewById(R.id.textEspecie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonajeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_personaje, parent, false)
        return PersonajeViewHolder(view)
    }

    override fun onBindViewHolder(holder: PersonajeViewHolder, position: Int) {
        val personaje = lista[position]
        holder.nombre.text = personaje.name
        holder.estado.text = "Estado: ${personaje.status}"
        holder.especie.text = "Especie: ${personaje.species}"
        Glide.with(holder.itemView.context)
            .load(personaje.image)
            .into(holder.img)
    }

    override fun getItemCount(): Int = lista.size

    fun actualizarLista(nuevaLista: List<Personaje>) {
        lista = nuevaLista
        notifyDataSetChanged()
    }
}
