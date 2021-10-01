package com.example.mareu.ui.meeting;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.example.mareu.R;
import com.example.mareu.di.DI;
import com.example.mareu.events.DeleteMeetingEvent;
import com.example.mareu.model.Meeting;
import com.example.mareu.service.MeetingApiService;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class MyMeetingRecyclerViewAdapter extends RecyclerView.Adapter<MyMeetingRecyclerViewAdapter.ViewHolder> {

    private List<Meeting> mMeetings = new ArrayList<>();
    private String mFilterValue;
    FilterType mFilterType = FilterType.NONE;
    private final MeetingApiService mApiService = DI.getMeetingApiService();
    public MyMeetingRecyclerViewAdapter(List<Meeting> items) { setValue(items); }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_meeting, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Meeting meeting = mMeetings.get(position);
        holder.mMeetingName.setText(meeting.getName() + " - " + meeting.getRoom());
        holder.mMeetingDate.setText(meeting.getDay());
        holder.mMeetingParticipants.setText(meeting.getParticipants().toString().replaceAll("\\[|\\]", ""));
        Glide.with(holder.mMeetingImage.getContext())
                .load(R.drawable.ic_baseline_circle_24)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.mMeetingImage);
        holder.mMeetingImage.setColorFilter(AddMeetingActivity.randomColor());
        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new DeleteMeetingEvent(meeting));
                notifyItemRemoved(holder.getAbsoluteAdapterPosition());
                if (!mFilterType.equals(FilterType.NONE)) {
                    mMeetings.remove(meeting);
                }
            }
        });
    }

    @Override
    public int getItemCount() { return mMeetings.size(); }
    public enum FilterType {
        NONE, BY_DATE, BY_MEETING_ROOM
    }


    public void setValue(List<Meeting> meeting) {
        mMeetings.clear();
        mMeetings.addAll(meeting);
        notifyDataSetChanged();
    }

    public void refreshList(FilterType filterType, String filterValue) {
        mFilterType = filterType;
        mFilterValue = filterValue;
        switch (filterType) {
            case NONE:
                setValue(mApiService.getMeetings());
                break;
            case BY_DATE:
                mMeetings = mApiService.getMeetingsForGivenDate(filterValue);
                break;
            case BY_MEETING_ROOM:
                setValue(mApiService.getMeetingsForGivenMeetingRoom(filterValue));
                break;
        }
        notifyDataSetChanged();
    }

    public void refreshList() {
        this.refreshList(mFilterType, mFilterValue);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mMeetingImage;
        public TextView mMeetingName;
        public TextView mMeetingDate;
        public TextView mMeetingParticipants;
        public ImageButton mDeleteButton;
        public ConstraintLayout mParentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            mMeetingImage = itemView.findViewById(R.id.image_meeting);
            mMeetingName = itemView.findViewById(R.id.meeting_name);
            mMeetingDate = itemView.findViewById(R.id.meeting_date);
            mMeetingParticipants = itemView.findViewById(R.id.meeting_participants);
            mDeleteButton = itemView.findViewById(R.id.meeting_delete_button);
            mParentLayout = itemView.findViewById(R.id.parentlayout);

        }
    }
}
