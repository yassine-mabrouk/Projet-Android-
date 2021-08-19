package com.enset.healthcareapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.enset.healthcareapp.adapter.HospitalisationAdapter;
import com.enset.healthcareapp.model.Fiche;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Hospitalisation extends Fragment {

    private HospitalisationAdapter adapterhostp;
    View result;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference FicheRef;

    public Hospitalisation() {
    }


    public static Hospitalisation newInstance() {
        Hospitalisation fragment = new Hospitalisation();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        result = inflater.inflate(R.layout.fragment_hospitalisation, container, false);
        setUpRecyclerView();
        return result;
    }
    private void setUpRecyclerView() {
        String email_id = getActivity().getIntent().getExtras().getString("patient_email");
        FicheRef = db.collection("Patient").document(email_id).collection("MyMedicalFolder");
        Query query = FicheRef.whereEqualTo("type", "Hospitalisation");

        FirestoreRecyclerOptions<Fiche> options = new FirestoreRecyclerOptions.Builder<Fiche>()
                .setQuery(query, Fiche.class)
                .build();

        adapterhostp = new HospitalisationAdapter(options);

        RecyclerView recyclerView = result.findViewById(R.id.hospitalisationRecycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapterhostp);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapterhostp.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapterhostp.stopListening();
    }
}
