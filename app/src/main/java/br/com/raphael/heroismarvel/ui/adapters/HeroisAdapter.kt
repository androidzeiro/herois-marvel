package br.com.raphael.heroismarvel.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import br.com.raphael.heroismarvel.R
import br.com.raphael.heroismarvel.model.Personagem
import coil.api.load
import coil.size.Scale
import kotlinx.android.synthetic.main.item_heroi.view.*

class HeroisAdapter(
    var items: MutableList<Personagem>,
    private val onHeroiClicked: (Personagem) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_heroi, parent, false)
        return OfertasViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is OfertasViewHolder) {
            val item = items[position]
            if (item.thumbnail.path.isNotEmpty()) {
                holder.imagem.load("${item.thumbnail.path}.${item.thumbnail.extension}") {
                    crossfade(750)
                    placeholder(R.drawable.ic_image_placeholder)
                    error(R.drawable.ic_image_placeholder)
                    scale(Scale.FILL)
                }
            }

            holder.container.setOnClickListener {
                onHeroiClicked.invoke(item)
            }

            holder.nome.text = item.name
        }
    }

    private class OfertasViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val container: CardView by lazy { itemView.cv_item }
        val imagem: ImageView by lazy { itemView.iv_heroi }
        val nome: TextView by lazy { itemView.tv_nome }
    }
}