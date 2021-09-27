package com.example.mareu.ui.meeting;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.mareu.R;
import com.example.mareu.di.DI;
import com.example.mareu.model.Meeting;
import com.example.mareu.service.MeetingApiService;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddMeetingActivity extends AppCompatActivity {

    private EditText nameInput;
    private TextView dateInput;
    private TextView timeInput;
    private TextView roomInput;
    private EditText emailInput;
    private EditText timerInput;
    private ChipGroup mChipGroup;

    Spinner mSpinner;
    MaterialButton mSaveButton;

    int rYear, rMonth, rDay, rHour, rMinute;

    private MeetingApiService mApiService;
    private List<String> mMeetingParticipantList;
    private int chipId;

    private List<Meeting> meetingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);
        ButterKnife.bind(this);
        init();

        nameInput = findViewById(R.id.nameInput);
        dateInput = findViewById(R.id.dateInput);
        timeInput = findViewById(R.id.timeInput);
        roomInput = findViewById(R.id.roomInput);
        emailInput = findViewById(R.id.emailInput);
        timerInput = findViewById(R.id.timerInput);
        mChipGroup = findViewById(R.id.chip_group);

        Calendar calendar = Calendar.getInstance();
        rYear = calendar.get(Calendar.YEAR);
        rMonth = calendar.get(Calendar.MONTH);
        rDay = calendar.get(Calendar.DAY_OF_MONTH);
        mSaveButton = findViewById(R.id.save);
        mSaveButton.setOnClickListener(v -> addMeeting());


        nameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                couldEnableScheduleButton();
            }
        });

        emailInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(" ") || s.toString().contains(";")) {
                    emailInput.onEditorAction(EditorInfo.IME_ACTION_DONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });



        timerInput.setOnClickListener(v -> {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(AddMeetingActivity.this);
            v = getLayoutInflater().inflate(R.layout.dialog_spinner, null);
            mSpinner = v.findViewById(R.id.spinner);
            ArrayAdapter<String> timerAdapter = new ArrayAdapter<>(AddMeetingActivity.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.Duration));
            timerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinner.setAdapter(timerAdapter);
            timerInput.setText(mSpinner.getSelectedItem().toString());
            mBuilder.setView(v);
            AlertDialog dialog = mBuilder.create();
            dialog.show();

        });

        roomInput.setOnClickListener(v -> {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(AddMeetingActivity.this);
            v = getLayoutInflater().inflate(R.layout.dialog_spinner, null);
            mBuilder.setTitle("Choisissez une salle");
            mSpinner = v.findViewById(R.id.spinner);

            //Spinner roomChoice = new Spinner(getApplicationContext(), Spinner.MODE_DIALOG);
            ArrayAdapter<String> roomAdapter = new ArrayAdapter<>(AddMeetingActivity.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.Rooms));
            roomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinner.setAdapter(roomAdapter);

            mBuilder.setPositiveButton("Ok", (dialog, which) -> {
                if(!mSpinner.getSelectedItem().toString().equalsIgnoreCase("Sélectionnez une salle")){
                    Toast.makeText(AddMeetingActivity.this, "Votre réunion se déroulera en salle " + mSpinner.getSelectedItem().toString() +  "!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    roomInput.setText(mSpinner.getSelectedItem().toString());
                }
            });

            mBuilder.setNegativeButton("Annuler", (dialog, which) -> dialog.dismiss());
            mBuilder.setView(v);
            AlertDialog dialog = mBuilder.create();
            dialog.show();
        });

        dateInput.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(AddMeetingActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, (view, year, month, dayOfMonth) -> {
                month = month+1;
                String date = dayOfMonth+"/"+month+"/"+year;
                dateInput.setText(date);
            },rYear, rMonth, rDay);
            datePickerDialog.show();
        });

        timeInput.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(AddMeetingActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    rHour = hourOfDay;
                    rMinute = minute;
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.set(0, 0,0, rHour, rMinute);
                    timeInput.setText(DateFormat.format("HH:mm", calendar1));
                }

            }, 12,0, false);
            timePickerDialog.updateTime(rHour,rMinute);
            timePickerDialog.show();
        });

        emailInput.setOnEditorActionListener((v, actionId, event) -> {
            String inputText = v.getText().toString();
            inputText = inputText.replace(" ", "").replace(";", "");
            if (inputText.contains("@") && !inputText.endsWith("@") && inputText.contains(".") && !inputText.endsWith(".")) {
                mMeetingParticipantList.add(inputText);
                Chip chip = new Chip(AddMeetingActivity.this);
                chip.setText(inputText);
                chip.setChipBackgroundColorResource(R.color.purple_700);
                chip.setCloseIconVisible(true);
                chip.setTextColor(getResources().getColor(R.color.white));
                chip.setId(chipId++);
                chip.setOnCloseIconClickListener(v1 -> {
                    mChipGroup.removeView(v1);
                    mMeetingParticipantList.remove(chip.getText().toString());
                    couldEnableScheduleButton();
                });
                mChipGroup.addView(chip);
            } else {
                Snackbar.make(v, "Email non valide, pas de nouveau participant ajouté.", Snackbar.LENGTH_LONG).setBackgroundTint(getResources().getColor(R.color.purple_700)).show();
            }
            couldEnableScheduleButton();
            emailInput.setText("");
            return false;
        });
    }

    // Barre de retour sur la page précédente
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home : {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        randomColor();
        mApiService = DI.getMeetingApiService();
        mMeetingParticipantList = new ArrayList<>();

    }

    @OnClick(R.id.save)
    void addMeeting() {
        /*List<Meeting> meetings = mApiService.getMeetings();
        String time = timeInput.getText().toString();
        String day = dateInput.getText().toString();
        for(Meeting n : meetings) {
            String dayInList = n.getDay();
            String duration = n.getDuration();
            String timeInList = n.getHour();
            if (dayInList.equals(day)) {

            }
        }*/
        Meeting meeting = new Meeting(
                System.currentTimeMillis(),
                nameInput.getText().toString(),
                dateInput.getText().toString(),
                timeInput.getText().toString(),
                roomInput.getText().toString(),
                mMeetingParticipantList,
                timerInput.getText().toString()
        );
        //getEndMeetingTime(meeting);
        mApiService.createMeeting(meeting);
        finish();
    }

    public static int randomColor() {
        Random random = new Random(System.currentTimeMillis());
        // This is the base color which will be mixed with the generated one
        final int baseColor = Color.WHITE;

        final int baseRed = Color.red(baseColor);
        final int baseGreen = Color.green(baseColor);
        final int baseBlue = Color.blue(baseColor);

        final int red = (baseRed + random.nextInt(256)) / 2;
        final int green = (baseGreen + random.nextInt(256)) / 2;
        final int blue = (baseBlue + random.nextInt(256)) / 2;

        return Color.rgb(red, green, blue);
    }

    /**
     * Used to navigate to this activity
     * @param activity
     */
    public static void navigate(FragmentActivity activity) {
        Intent intent = new Intent(activity, AddMeetingActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }

    /**
     * Enable schedule button if all is good (no empty field)
     */
    private void couldEnableScheduleButton() {
        mSaveButton.setEnabled(!nameInput.getText().toString().equals("")
                && !dateInput.getText().toString().equals("") && !timeInput.getText().toString().equals("")
                && !roomInput.getText().toString().equals("") && !mMeetingParticipantList.isEmpty());
    }

    private void getEndMeetingTime(Meeting meeting) {
        SimpleDateFormat formattedTime = new SimpleDateFormat("HH:mm");
        String endTime = meeting.getHour();

        String duration = meeting.getDuration();
        String meetingDuration = formattedTime.format(duration);


    }
}