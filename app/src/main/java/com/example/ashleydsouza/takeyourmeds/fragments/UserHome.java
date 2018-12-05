package com.example.ashleydsouza.takeyourmeds.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ashleydsouza.takeyourmeds.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


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

        //Get email from Activity
        Bundle bundle = getArguments();
        String email = "";
        if(bundle != null) { email = bundle.getString("email");}

        String welcomeString = "Hi Ashley! " + email;

        Calendar nowCal = Calendar.getInstance();
        String prescriptionForToday = getPrescriptionsForToday(nowCal.getTime(), null);

        welcomeString = welcomeString + "\n" + prescriptionForToday;
        home.setText(welcomeString);

        return rootView;
    }

    public String getPrescriptionsForToday(Date date, String userEmail) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy");
        String loginDate = dateFormat.format(date);

        /**
         Get list of prescription based on date and userEmail
         set this to true when call is made to DB and there are results
         **/
        boolean hasPrescription = false;
        String listOfPrescription = "For " + loginDate + "\n";
        if(hasPrescription) {
            listOfPrescription += "";
        } else {
            listOfPrescription += getString(R.string.no_user_prescription);
        }
        return listOfPrescription;
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
