package com.amir.batmanmovies.ui.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amir.batmanmovies.data.model.Movies
import com.amir.batmanmovies.databinding.MoviesListRowBinding
import com.amir.batmanmovies.ui.utils.AdapterOnItemClickListener
import com.bumptech.glide.Glide

class MoviesListAdapter(
    private val moviesList: List<Movies>,
    private val adapterOnItemClickListener: AdapterOnItemClickListener
) :
    RecyclerView.Adapter<MoviesListAdapter.MoviesViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return MoviesViewHolder.create(parent)
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movie = moviesList[position]
        holder.setupBindings(movie)
        holder.itemView.setOnClickListener {
            adapterOnItemClickListener.setOnItemListener(movie)
        }
    }

    class MoviesViewHolder(private val binding: MoviesListRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setupBindings(movie: Movies) {
            binding.movieTitle.text = movie.Title
            binding.movieReleaseDate.text = movie.Year
            binding.movieType.text = movie.Type
            Glide.with(binding.moviePoster.context).load(movie.Poster).fitCenter()
                .into(binding.moviePoster)
        }

        companion object {
            fun create(parent: ViewGroup) = MoviesViewHolder(
                MoviesListRowBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }

    }

}