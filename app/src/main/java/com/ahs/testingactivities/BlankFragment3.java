package com.ahs.testingactivities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;

public class BlankFragment3 extends Fragment {


    DBHelper book_prot_db;
    ListView lvBooklist;

    public BlankFragment3() {
        // Required empty public constructor
    }

    public static BlankFragment3 newInstance(String param1, String param2) {
        BlankFragment3 fragment = new BlankFragment3();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank3, container, false);

        book_prot_db = new DBHelper(getActivity());

        ArrayList<HashMap<String, String>> bookList = book_prot_db.GetBooks();
        lvBooklist = (ListView) view.findViewById(R.id.lvBookList);

        ListAdapter adapter = new SimpleAdapter(getContext(), bookList, R.layout.list_rows, new String[]{"isbn", "title", "author", "qt", "price"},
                new int[]{R.id.tvIsbn, R.id.tvBookName, R.id.tvBookAuthor, R.id.lvQt, R.id.lvPrice});
        lvBooklist.setAdapter(adapter);

        return view;
    }


}
