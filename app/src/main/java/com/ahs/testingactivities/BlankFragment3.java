package com.ahs.testingactivities;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BlankFragment3 extends Fragment {
    TextView tvIsbn;
    ImageView ivbookPic;
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
        System.out.println(bookList);
        ListAdapter adapter = new SimpleAdapter( getContext(), bookList, R.layout.list_rows,new String[]{"isbn","title","author","qt","price"},
                new int[]{R.id.tvIsbn, R.id.tvBookName, R.id.tvBookAuthor,R.id.lvQt,R.id.lvPrice});
        lvBooklist.setAdapter(adapter);





        return view;
    }



}
