package com.neusoft.contactsapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.neusoft.contactsapp.db.utils.SQLHandler;
import com.neusoft.contactsapp.entities.Contact;

public class ContactFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.add_contact_fragment,container,false);
        final EditText mFirstName=v.findViewById(R.id.first_name);
        final EditText mLastName=v.findViewById(R.id.last_name);
        final EditText mMobNo=v.findViewById(R.id.phone_num);
        v.findViewById(R.id.ok_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLHandler handler=new SQLHandler(getContext());
                //make the contact object
                //values from the edit texts
                Contact con=new Contact();
                con.setFirstName(mFirstName.getText().toString());
                con.setLastName(mLastName.getText().toString());
                con.setPhoneNumber(mMobNo.getText().toString());
                handler.addContact(con);
                Toast.makeText(getActivity(),"Contact Added.",Toast.LENGTH_SHORT).show();
                mFirstName.setText("");
                mLastName.setText("");
                mMobNo.setText("");
                mFirstName.findFocus();
            }
        });
        v.findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirstName.setText("");
                mLastName.setText("");
                mMobNo.setText("");
            }
        });
        return v;
    }
}
