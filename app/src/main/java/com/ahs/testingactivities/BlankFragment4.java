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

public class BlankFragment4 extends Fragment {

    DBHelper book_prot_db;
    ListView lvOrderlist;
    ListView lvBooklist;


    public BlankFragment4() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static BlankFragment4 newInstance(String param1, String param2) {
        BlankFragment4 fragment = new BlankFragment4();
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
        View view = inflater.inflate(R.layout.fragment_blank4, container, false);

        book_prot_db = new DBHelper(getActivity());


        ArrayList<HashMap<String, String>> orderList = book_prot_db.GetOrders();
        lvOrderlist = (ListView) view.findViewById(R.id.lvOrderList);

        ListAdapter OrdersAdapter = new SimpleAdapter(getContext(), orderList, R.layout.list_rows_orders, new String[]{"order_id", "isbn", "customer_name", "location", "phone", "qt", "price", "date", "title", "author"},
                new int[]{R.id.tvOrderNumber, R.id.tvIsbn, R.id.tvCustomerName, R.id.tvCustomerLocation, R.id.tvCustomerPhoneNumber, R.id.lvQt, R.id.lvPrice, R.id.lvDate, R.id.tvBookName, R.id.tvBookAuthor});
        lvOrderlist.setAdapter(OrdersAdapter);
        System.out.println("\n\n\n\n\nasad\n\n\n\n" + OrdersAdapter.toString());

        //-------------------------------------------------------------------

      /*  ArrayList<HashMap<String, String>> bookList = book_prot_db.GetBooks();
        lvBooklist = (ListView) view.findViewById(R.id.lvBookList);

        ListAdapter BooksAdapter = new SimpleAdapter(getContext(), bookList, R.layout.list_rows, new String[]{"title", "author"},
                new int[]{ R.id.tvBookName, R.id.tvBookAuthor});
        lvOrderlist.setAdapter(BooksAdapter);*/

        return view;
    }
}