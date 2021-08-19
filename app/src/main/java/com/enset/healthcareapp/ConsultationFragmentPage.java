package com.enset.healthcareapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.enset.healthcareapp.adapter.ConsultationAdapter;
import com.enset.healthcareapp.model.Fiche;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ConsultationFragmentPage extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference FicheRef;
    private ConsultationAdapter adapterConsult;
    View result;

    public ConsultationFragmentPage() {
    }

    public static ConsultationFragmentPage newInstance() {
        ConsultationFragmentPage fragment = new ConsultationFragmentPage();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Gonfle la mise en page pour ce fragment
        result = inflater.inflate(R.layout.fragment_consultation_page, container, false);
        setUpRecyclerView();
        return result;
    }

    private void setUpRecyclerView() {

        String email_id = getActivity().getIntent().getExtras().getString("patient_email");
        FicheRef = db.collection("Patient").document(email_id).collection("MyMedicalFolder");
        Query query = FicheRef.whereEqualTo("type", "Consultation");

        FirestoreRecyclerOptions<Fiche> options = new FirestoreRecyclerOptions.Builder<Fiche>()
                .setQuery(query, Fiche.class)
                .build();

        adapterConsult = new ConsultationAdapter(options);

        RecyclerView recyclerView = result.findViewById(R.id.conslutationRecycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapterConsult);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapterConsult.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapterConsult.stopListening();
    }
}