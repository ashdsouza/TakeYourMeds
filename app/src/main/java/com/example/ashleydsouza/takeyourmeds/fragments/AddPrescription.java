package com.example.ashleydsouza.takeyourmeds.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ashleydsouza.takeyourmeds.R;
import com.example.ashleydsouza.takeyourmeds.cruds.MedicineCrudImplementation;
import com.example.ashleydsouza.takeyourmeds.models.MedicineInformation;
import com.example.ashleydsouza.takeyourmeds.models.MedicineViewModel;
import com.example.ashleydsouza.takeyourmeds.utils.Session;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddPrescription.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddPrescription#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddPrescription extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final Integer RELEVANT_LINEAR_LAYOUTS = 3;
    private OnFragmentInteractionListener mListener;
    private LinearLayout parentLinearLayout;
    private ArrayList<MedicineInformation> medicines;
    private MedicineViewModel medViewModel;
    private Context context;
    private Session session;
    private int userId;
    private StringBuffer prescriptionBuilder;

    public AddPrescription() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddPrescription.
     */
    // TODO: Rename and change types and number of parameters
    public static AddPrescription newInstance(String param1, String param2) {
        AddPrescription fragment = new AddPrescription();
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

        medViewModel = ViewModelProviders.of(this).get(MedicineViewModel.class);
        session = new Session(getActivity());
        userId = session.getUserId();

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_add_prescription, container, false);

        parentLinearLayout = rootView.findViewById(R.id.parent_linear_layout);
        Button addButton = rootView.findViewById(R.id.add_button);
        addButton.setOnClickListener(this);

        //String for Calender event
        prescriptionBuilder = new StringBuffer();
        prescriptionBuilder.append("Medicine_Name\tDosage\tAmount\tTime\tAdditional_Notes\n");

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.save, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.save_action:
                savePrescription();
                return true;
//            default:

        }

        return super.onOptionsItemSelected(item); // important line

    }

    public void savePrescription() {
        medicines = new ArrayList<>();
        int viewCount = parentLinearLayout.getChildCount();

        //Save Medicine data in very first entry
        saveInModel(parentLinearLayout);

        //Save Medicine data if there are more rows added
        if(viewCount > RELEVANT_LINEAR_LAYOUTS) {
            for (int i = RELEVANT_LINEAR_LAYOUTS; i < viewCount; i++) {
                CardView view = (CardView) parentLinearLayout.getChildAt(i);
                LinearLayout inCard = view.findViewById(R.id.linear_layout_inside_card);
                saveInModel(inCard);
            }
        }

        //check if you have medicine data to add
        if(medicines.size() > 0) {
            try {
                medViewModel.insert(medicines);

                //check if the toggle is set to save as event in calender
                if(true) {
                    saveToCalender();
                }
                UserHome homeFragment = new UserHome();
                getFragmentManager().beginTransaction().replace(R.id.flContent, homeFragment).commit();

                Toast.makeText(getActivity(), "Medicine Data Saved", Toast.LENGTH_SHORT).show();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void saveInModel(LinearLayout layout) {
        MedicineInformation medicine = new MedicineInformation();

        for(int i = 0; i < RELEVANT_LINEAR_LAYOUTS; i++) {
            LinearLayout view = (LinearLayout) layout.getChildAt(i);
            for(int j = 0;j< view.getChildCount(); j++) {
                View elem = view.getChildAt(j);
                switch (elem.getId()) {
                    case R.id.med_name:
                        EditText name = (EditText) elem;
                        if(!name.getText().toString().equals("")) {
                            medicine.setName(name.getText().toString());
                            prescriptionBuilder.append(name.getText().toString() + "\t");
                        }
                        break;
                    case R.id.med_dosage:
                        Spinner dosage = (Spinner) elem;
                        if(dosage.getSelectedItem() != null) {
                            medicine.setDosage(dosage.getSelectedItem().toString());
                            prescriptionBuilder.append(dosage.getSelectedItem().toString() + "\t");
                        }
                        break;
                    case R.id.med_dosage_amount:
                        EditText amount = (EditText) elem;
                        if(!amount.getText().toString().equals("")) {
                            medicine.setAmount(Integer.valueOf(amount.getText().toString()));
                            prescriptionBuilder.append(amount.getText().toString() + "\t");
                        }
                        break;
                    case R.id.med_time:
                        Spinner time = (Spinner) elem;
                        if(time.getSelectedItem() != null) {
                            medicine.setTime(time.getSelectedItem().toString());
                            prescriptionBuilder.append(time.getSelectedItem().toString() + "\t");
                        }
                        break;
                    case R.id.med_notes:
                        EditText notes = (EditText) elem;
                        if(!notes.getText().toString().equals("")) {
                            medicine.setAdditionalNotes(notes.getText().toString());
                            prescriptionBuilder.append(notes.getText().toString());
                        }
                        break;
                }
            }
        }
//        medicine.printMedicine();
        if(isMedicineEntered(medicine)) {
            medicine.setUserId(userId);
            medicines.add(medicine);
            prescriptionBuilder.append("\n");
        }
    }

    public boolean isMedicineEntered(MedicineInformation medicine) {
        return medicine.getName() != null && medicine.getAmount() != null &&
                medicine.getDosage() != null && medicine.getTime() != null;
    }

    public void saveToCalender() {
        String prescription = prescriptionBuilder.toString();
//        System.out.print(prescription);

        Calendar dt = Calendar.getInstance();
        long start = dt.getTimeInMillis();

        if(getContext() != null) {
            ContentResolver cntR = getContext().getContentResolver();
            ContentValues values = new ContentValues();
            values.put(CalendarContract.Events.DTSTART, start);
            values.put(CalendarContract.Events.TITLE, "Prescription For Today");
            values.put(CalendarContract.Events.DESCRIPTION, prescription);

            TimeZone timeZone = TimeZone.getDefault();
            values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());
            values.put(CalendarContract.Events.CALENDAR_ID, 1);
            values.put(CalendarContract.Events.RRULE, "FREQ=DAILY;UNTIL=20181215");
            values.put(CalendarContract.Events.HAS_ALARM, 1);
            values.put(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_FREE);

            Uri uri = cntR.insert(CalendarContract.Events.CONTENT_URI, values);
            long eventID = Long.parseLong(uri.getLastPathSegment());

            ContentValues reminders = new ContentValues();
            reminders.put(CalendarContract.Reminders.EVENT_ID, eventID);
            reminders.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
            reminders.put(CalendarContract.Reminders.MINUTES, 10);

            uri = cntR.insert(CalendarContract.Reminders.CONTENT_URI, reminders);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.add_button:
                onAddMedicine(v);
                break;
            case R.id.delete_button:
                onDeleteMedicine(v);
                break;
        }
    }

    public void onAddMedicine(View v) {
        if(context != null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final View rowView = inflater.inflate(R.layout.prescription_row, null);
            parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount());

            Button addButton = rowView.findViewById(R.id.add_button);
            addButton.setOnClickListener(this);

            Button delButton = rowView.findViewById(R.id.delete_button);
            delButton.setOnClickListener(this);
        }
    }

    public void onDeleteMedicine(View v) {
        ViewParent par = v.getParent().getParent().getParent();
        parentLinearLayout.removeView((View) par);
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
        this.context = context;
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
