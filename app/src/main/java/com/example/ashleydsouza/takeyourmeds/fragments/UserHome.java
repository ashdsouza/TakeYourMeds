package com.example.ashleydsouza.takeyourmeds.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ashleydsouza.takeyourmeds.R;
import com.example.ashleydsouza.takeyourmeds.adapter.GenericAdaptor;
import com.example.ashleydsouza.takeyourmeds.models.MedicineInformation;
import com.example.ashleydsouza.takeyourmeds.models.MedicineViewModel;
import com.example.ashleydsouza.takeyourmeds.models.MedicineViewModelRep;
import com.example.ashleydsouza.takeyourmeds.utils.CalendarEvent;
import com.example.ashleydsouza.takeyourmeds.utils.Session;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserHome.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserHome extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
//    private MedicineViewModel medViewModel;
    private MedicineViewModelRep medVM;
    private Session session;
    private int userId;

    public UserHome() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserHome.
     */
    // TODO: Rename and change types and number of parameters
    public static UserHome newInstance(String param1, String param2) {
        UserHome fragment = new UserHome();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_user_home, container, false);

        TextView home = rootView.findViewById(R.id.home_view);

        session = new Session(getActivity());
        String name = session.getName();
        userId = session.getUserId();

        String welcomeString = "Hi " + name + " !";
        home.setText(welcomeString);

        RecyclerView recyclerView = rootView.findViewById(R.id.prescriptions);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        final GenericAdaptor adapter = new GenericAdaptor();
        recyclerView.setAdapter(adapter);

//        medViewModel = ViewModelProviders.of(this).get(MedicineViewModel.class);
//        medViewModel.getMedsForUser(userId).observe(this, new Observer<List<MedicineInformation>>() {
//            @Override
//            public void onChanged(@Nullable List<MedicineInformation> medicineInformations) {
//                adapter.setMedicines(medicineInformations);
//            }
//        });

        medVM = ViewModelProviders.of(this).get(MedicineViewModelRep.class);
        medVM.getMedsForUser(userId).observe(this, new Observer<List<MedicineInformation>>() {
            @Override
            public void onChanged(@Nullable List<MedicineInformation> medicineInformations) {
                adapter.setMedicines(medicineInformations);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                MedicineInformation med = adapter.getMedsAt(viewHolder.getAdapterPosition());
                deleteEventsForMed(med.getMedId());
//                medViewModel.delete(med);
                medVM.delete(med);
                Toast.makeText(getActivity(), "Medicine Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        return rootView;
    }

    public void deleteEventsForMed(final int medId) {
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("events");
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("user-events");
        Query q = dbRef.child(String.valueOf(userId));
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnap: dataSnapshot.getChildren()) {
                    String key = dataSnap.getKey();
                    CalendarEvent calEvent = dataSnap.getValue(CalendarEvent.class);
                    if(calEvent != null) {
                        if(calEvent.getMedId() == medId) {
                            dataSnap.getRef().setValue(null);
                            if(key != null) {
                                ref.child(key).setValue(null);
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.home_page);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
