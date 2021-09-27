package com.example.mareu.utils;

import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import com.example.mareu.R;

import org.hamcrest.Matcher;

public class DeleteViewAction implements ViewAction {
    @Override
    public Matcher<View> getConstraints() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Click on specific button";
    }

    /**
     * Performs this action on the given view.
     *
     * @param uiController the controller to use to interact with the UI.
     * @param view         the view to act upon. never null.
     */
    @Override
    public void perform(UiController uiController, View view) {
        View button = view.findViewById(R.id.meeting_delete_button);
        // Maybe check for null
        button.performClick();
    }

}