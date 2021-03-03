package com.sample.apps.is4447.gobusker.FanFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sample.apps.is4447.gobusker.Adapter.BuskerAdapter;
import com.sample.apps.is4447.gobusker.Model.Busker;
import com.sample.apps.is4447.gobusker.R;

import java.util.ArrayList;
import java.util.List;

public class FanSearchFragment extends Fragment {
    private RecyclerView recyclerView;
    private BuskerAdapter buskerAdapter;
    private List<Busker> mBuskers;

    EditText search_bar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fan_search, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        search_bar = view.findViewById(R.id.search_bar);

        mBuskers = new ArrayList<>();
        buskerAdapter = new BuskerAdapter(getContext(), mBuskers);
        recyclerView.setAdapter(buskerAdapter);

        readBuskers();
        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                searchBuskers(charSequence.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }
    private void searchBuskers(String s){
        Query query = FirebaseDatabase.getInstance().getReference("Buskers").orderByChild("username")
                .startAt(s)
                .endAt(s+"\uf0ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mBuskers.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Busker busker = snapshot.getValue(Busker.class);
                    mBuskers.add(busker);
                }
                buskerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void readBuskers(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Buskers");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(search_bar.getText().toString().equals("")){
                    mBuskers.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Busker busker = snapshot.getValue(Busker.class);
                        mBuskers.add(busker);
                    }
                    buskerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    }
