package com.example.firebasedatabaseexample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.UUID;

public class AddMovieFragment extends Fragment {
    private DatabaseReference databaseReference;
    private TextInputEditText movieName;
    private TextInputEditText movieLogo;
    private RatingBar ratingBar;
    private Button bSubmit;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.add_movie_fragment,container,false);
        movieName = (TextInputEditText)view.findViewById(R.id.tiet_movie_name);
        movieLogo = (TextInputEditText)view.findViewById(R.id.tiet_movie_logo);
        ratingBar = (RatingBar)view.findViewById(R.id.rating_bar);
        bSubmit = (Button)view.findViewById(R.id.b_submit);

        //iniciar la BBDD
        databaseReference = FirebaseDatabase.getInstance().getReference();
        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (movieName.getText().toString() != null && movieLogo.getText().toString() != null){
                    newMovie("53",movieName.getText().toString().trim(),movieLogo.getText().toString().trim(),ratingBar.getRating());
                } else {
                    if (movieName.getText().toString().isEmpty()){
                        Toast.makeText(getContext(), "Please enter a Movie Name!", Toast.LENGTH_SHORT).show();
                    } else if (movieLogo.getText().toString().isEmpty()){
                        Toast.makeText(getContext(), "Please specify an URL for the Movie Logo", Toast.LENGTH_SHORT).show();
                    }
                }
                getActivity().onBackPressed();
            }
        });
        return view;
    }

    private void newMovie(String userId, String movieName, String moviePoster, float rating){
        Movie movie = new Movie(UUID.randomUUID().toString(), movieName,moviePoster,rating);
        databaseReference.child("users").child(userId).child("movies").child(movie.getId()).setValue(movie);
    }
}
