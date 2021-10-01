package com.example.mareu.ui.meeting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.example.mareu.R;
import com.example.mareu.di.DI;
import com.example.mareu.events.DeleteMeetingEvent;
import com.example.mareu.model.Meeting;
import com.example.mareu.service.MeetingApiService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private MeetingApiService mApiService;
    private List<Meeting> mMeetings;
    private RecyclerView mRecyclerView;
    private MyMeetingRecyclerViewAdapter mAdapter;


    @BindView(R.id.container)
    ViewPager mViewPager;

    private FloatingActionButton mAddButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getMeetingApiService();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mRecyclerView = findViewById(R.id.list_meeting);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mAddButton = findViewById(R.id.add_meeting);

        mAddButton.setOnClickListener(v -> {
            Intent AddMeetingActivity = new Intent(MainActivity.this, AddMeetingActivity.class);
            startActivity(AddMeetingActivity);
        });
    }

    private void initList() {
        mMeetings = mApiService.getMeetings();
        mAdapter = new MyMeetingRecyclerViewAdapter(mMeetings);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mRecyclerView.setAdapter(new MyMeetingRecyclerViewAdapter(mMeetings));

    }

    @Override
    public void onResume() {
        super.onResume();
        initList();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onDeleteNeighbour(DeleteMeetingEvent event) {
        mApiService.deleteMeeting(event.meeting);
        initList();
    }

    @OnClick(R.id.add_meeting)
    void addMeeting() { AddMeetingActivity.navigate(this);}

    /**
     * Initialize the contents of the Activity's standard options menu.  You
     * should place your menu items in to <var>menu</var>.
     *
     * <p>This is only called once, the first time the options menu is
     * displayed.  To update the menu every time it is displayed, see
     * {@link #onPrepareOptionsMenu}.
     *
     * <p>The default implementation populates the menu with standard system
     * menu items.  These are placed in the {@link Menu#CATEGORY_SYSTEM} group so that
     * they will be correctly ordered with application-defined menu items.
     * Deriving classes should always call through to the base implementation.
     *
     * <p>You can safely hold on to <var>menu</var> (and any items created
     * from it), making modifications to it as desired, until the next
     * time onCreateOptionsMenu() is called.
     *
     * <p>When you add items to the menu, you can implement the Activity's
     * {@link #onOptionsItemSelected} method to handle them there.
     *
     * @param menu The options menu in which you place your items.
     * @return You must return true for the menu to be displayed;
     * if you return false it will not be shown.
     * @see #onPrepareOptionsMenu
     * @see #onOptionsItemSelected
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.meeting_list_menu, menu);
        return true;
    }

    /**
     * This hook is called whenever an item in your options menu is selected.
     * The default implementation simply returns false to have the normal
     * processing happen (calling the item's Runnable or sending a message to
     * its Handler as appropriate).  You can use this method for any items
     * for which you would like to do processing without those other
     * facilities.
     *
     * <p>Derived classes should call through to the base class for it to
     * perform the default menu handling.</p>
     *
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to
     * proceed, true to consume it here.
     * @see #onCreateOptionsMenu
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getOrder()) {
            case 1:
                Calendar calendar = Calendar.getInstance();
                int rYear = calendar.get(Calendar.YEAR);
                int rMonth = calendar.get(Calendar.MONTH);
                int rDay = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, (view, year, month, dayOfMonth) -> {
                    month = month+1;
                    String date = dayOfMonth+"/"+month+"/"+year;
                    mAdapter.refreshList(MyMeetingRecyclerViewAdapter.FilterType.BY_DATE, date);
                },rYear, rMonth, rDay);
                datePickerDialog.show();
                mRecyclerView.setAdapter(mAdapter);
                break;
            case 2:
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_spinner, null);
                mBuilder.setTitle("Choisissez une salle");
                final Spinner mSpinner = mView.findViewById(R.id.spinner);

                ArrayAdapter<String> roomAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.Rooms));
                roomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinner.setAdapter(roomAdapter);

                mBuilder.setPositiveButton("Ok", (dialog, which) -> {
                    if(!mSpinner.getSelectedItem().toString().equalsIgnoreCase("-")){
                        dialog.dismiss();
                        mAdapter.refreshList(MyMeetingRecyclerViewAdapter.FilterType.BY_MEETING_ROOM, mSpinner.getSelectedItem().toString());
                        mRecyclerView.setAdapter(mAdapter);
                    }
                });
                mBuilder.setNegativeButton("Annuler", (dialog, which) -> dialog.dismiss());

                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
                break;
            case 3:
                mAdapter.refreshList(MyMeetingRecyclerViewAdapter.FilterType.NONE, "");
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }
}


