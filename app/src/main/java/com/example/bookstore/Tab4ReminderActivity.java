/*
This file is for the tab 4
which is for the reminder tab
if the user needs a reminder to read a book,
this activity tab will memorize it and remind on time
 */

package com.example.bookstore;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Tab4ReminderActivity extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab4_reminder, container, false);
        return rootView;
    }

}
