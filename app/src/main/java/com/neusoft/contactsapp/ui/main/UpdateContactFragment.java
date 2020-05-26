package com.neusoft.contactsapp.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.neusoft.contactsapp.R;
import com.neusoft.contactsapp.db.utils.SQLHandler;
import com.neusoft.contactsapp.entities.Contact;

import java.util.List;

public class UpdateContactFragment extends Fragment {
    public List<Contact> getList(){
        return new SQLHandler(getContext()).getAllContacts();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.update_contact_fragment,container,false);
        final Spinner spinner=v.findViewById(R.id.contacts_id);
        final EditText fName=v.findViewById(R.id.update_first_name);
        final EditText lName=v.findViewById(R.id.update_last_name);
        final EditText mobNum=v.findViewById(R.id.update_mob_num);
        //get the contacts from DB and populate the Spinner
        final List<Contact> contacts=getList();
        String ids[]=new String[contacts.size()];
        int i=0;
        for(Contact con:contacts){
            ids[i++]=""+con.getID();//""-> creates an empty string ; + concatenates
        }
        //add this data to the spinner
        ArrayAdapter<String> contact_adapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item,ids);
        //how to show the dropdown list
        contact_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(contact_adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fName.setText(contacts.get(position).getFirstName());
                lName.setText(contacts.get(position).getLastName());
                mobNum.setText(contacts.get(position).getPhoneNumber());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinner.setSelection(0);//select the first item when nothing is selected
            }
        });
        v.findViewById(R.id.update_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create a contact
                Contact contact=new Contact();
                contact.setFirstName(fName.getText().toString());
                contact.setLastName(lName.getText().toString());
                contact.setPhoneNumber(mobNum.getText().toString());
                contact.setID(Integer.parseInt(spinner.getSelectedItem().toString()));
                //pass the contact for updation
                new SQLHandler(getContext()).updateContact(contact);
                Toast.makeText(getContext(),"Contact has been updated",Toast.LENGTH_SHORT).show();
                //reload the list
                updateList();
            }
        });
        v.findViewById(R.id.update_cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setSelection(0);
            }
        });
        return v;
    }

    private void updateList() {

    }
}
