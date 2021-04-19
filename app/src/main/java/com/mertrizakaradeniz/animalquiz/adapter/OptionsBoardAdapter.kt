package com.mertrizakaradeniz.animalquiz.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mertrizakaradeniz.animalquiz.R
import com.mertrizakaradeniz.animalquiz.models.Animal
import com.mertrizakaradeniz.animalquiz.models.BoardSize
import com.mertrizakaradeniz.animalquiz.utils.CardClickListener
import kotlin.math.min

class OptionsBoardAdapter(
    private val context: Context,
    private val boardSize: BoardSize,
    private val cardClickListener: CardClickListener
) :
    RecyclerView.Adapter<OptionsBoardAdapter.ViewHolder>() {

    companion object {
        private const val TAG = "OptionsBoardAdapter"
        private const val MARGIN_SIZE = 8
    }

    private lateinit var question: ArrayList<Animal>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cardWidth = parent.width / boardSize.getWidth() - (2 * MARGIN_SIZE)
        val cardHeight = parent.height / boardSize.getHeight() - (2 * MARGIN_SIZE)
        val cardSideLength = min(cardWidth, cardHeight)
        val view = LayoutInflater.from(context).inflate(R.layout.question_card, parent, false)
        val layoutParams =
            view.findViewById<CardView>(R.id.cardView).layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.width = cardSideLength
        layoutParams.height = cardSideLength
        layoutParams.setMargins(MARGIN_SIZE, MARGIN_SIZE, MARGIN_SIZE, MARGIN_SIZE)
        return ViewHolder(view)
    }

    override fun getItemCount() = boardSize.numCards

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageButton = itemView.findViewById<ImageButton>(R.id.imageButton)

        fun bind(position: Int) {
            imageButton.setImageResource(question[position].image)

            imageButton.setOnClickListener {
                Log.i(TAG, "Clicked on position $position")
                cardClickListener.onCardClicked(position)
            }
        }
    }

    fun updateData(list: ArrayList<Animal>) {
        question = list
    }
}