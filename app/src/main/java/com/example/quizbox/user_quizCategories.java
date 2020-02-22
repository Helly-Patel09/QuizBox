package com.example.quizbox;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


public class user_quizCategories extends Fragment {

    ImageButton ib1,ib2,ib3;
    Intent intent;
    final Bundle bundle = new Bundle();
    // TODO: Rename and change types of parameters
    private String category;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public user_quizCategories() {
        // Required empty public constructor
        //
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewgroup = (ViewGroup)inflater.inflate(R.layout.fragment_user_quiz_categories, container, false);

        ib1 = viewgroup.findViewById(R.id.imageButton2);
        ib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putString("type","General Knowledge");
                intent = new Intent(getContext(),quesAndAns.class);
                intent.putExtra("quiztype",bundle);
                startActivity(intent);
            }
        });

        ib2 = viewgroup.findViewById(R.id.imageButton3);
        ib2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putString("type","Sports");
                intent = new Intent(getContext(),quesAndAns.class);
                intent.putExtra("quiztype",bundle);
                startActivity(intent);
            }
        });

        ib3 = viewgroup.findViewById(R.id.imageButton4);
        ib3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putString("type","Computer Science");
                intent = new Intent(getContext(),quesAndAns.class);
                intent.putExtra("quiztype",bundle);
                startActivity(intent);
            }
        });

        return viewgroup;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
