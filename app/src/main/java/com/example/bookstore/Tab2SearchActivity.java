package com.example.bookstore;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Tab2SearchActivity extends Fragment {

    private EditText searchText;
    private Button searchButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.tab2_search, container, false);

        searchText = (EditText)rootView.findViewById(R.id.editText2);
        searchButton = (Button)rootView.findViewById(R.id.button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = searchText.getText().toString();
                if(!text.isEmpty()){
                    new FetchBooks(text).execute(text);
                } else {
                    Toast.makeText(getActivity(), "Please enter a book name", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }
}
