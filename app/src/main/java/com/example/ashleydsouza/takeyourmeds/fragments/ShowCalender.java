package com.example.ashleydsouza.takeyourmeds.fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ashleydsouza.takeyourmeds.R;
import com.example.ashleydsouza.takeyourmeds.adapter.CalendarEventAdaptor;
import com.example.ashleydsouza.takeyourmeds.utils.CalendarEvent;
import com.example.ashleydsouza.takeyourmeds.utils.CalendarEventManager;
import com.example.ashleydsouza.takeyourmeds.utils.Session;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShowCalender.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShowCalender#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowCalender extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private CompactCalendarView calender;
    private Session session;
    private int userId;

    public ShowCalender() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShowCalender.
     */
    // TODO: Rename and change types and number of parameters
    public static ShowCalender newInstance(String param1, String param2) {
        ShowCalender fragment = new ShowCalender();
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

        session = new Session(getActivity());
        userId = session.getUserId();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_show_calender, container, false);

        calender = rootView.findViewById(R.id.calenderView);
        createCalendarEvent();

        RecyclerView recyclerView = rootView.findViewById(R.id.calendar_events);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        final CalendarEventAdaptor adapter = new CalendarEventAdaptor();
        recyclerView.setAdapter(adapter);

        calender.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
//                Toast.makeText(getActivity(), "Event = " + calender.getEvents(dateClicked), Toast.LENGTH_SHORT).show();
                adapter.setEvents(calender.getEvents(dateClicked));
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {

            }
        });

        return rootView;
    }

    /**
     * Function to fetch events from Firebase and set as Calendar Events
     */
    public void createCalendarEvent() {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("user-events");
        Query q = dbRef.child(String.valueOf(userId));
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnap: dataSnapshot.getChildren()) {
                    CalendarEvent calEvent = dataSnap.getValue(CalendarEvent.class);
                    if(calEvent != null) {
                        Event event = new Event(calEvent.getEventColor(),
                                calEvent.getTimeEventAdded(), calEvent.getDescription());
                        calender.addEvent(event);
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
