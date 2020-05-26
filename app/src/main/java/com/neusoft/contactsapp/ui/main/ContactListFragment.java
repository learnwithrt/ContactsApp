package com.neusoft.contactsapp.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.neusoft.contactsapp.R;
import com.neusoft.contactsapp.db.utils.SQLHandler;
import com.neusoft.contactsapp.entities.Contact;

import org.w3c.dom.Text;

import java.util.List;

public class ContactListFragment extends Fragment {
    private RecyclerView mContactRecycler;
    private ContactAdapter mAdapter;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //check if this fragment is visible to user
        if(isVisibleToUser){
            //reload the data
            mAdapter.notifyDataSetChanged();//tell adapter that the data has/ might have changed
            updateData();//Show the updated data
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.contact_list_fragment,container,false);
        mContactRecycler=v.findViewById(R.id.contact_recycler);
        mContactRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        //call updateData
        updateData();
        return v;
    }
    public void updateData(){
        //this is where I get the data from DB
        SQLHandler handler=new SQLHandler(getContext());
        mAdapter=new ContactAdapter(handler.getAllContacts());
        //Set the adapter for this recycler view
        mContactRecycler.setAdapter(mAdapter);
    }
    private class ContactHolder extends RecyclerView.ViewHolder{
        private TextView mName;
        private TextView mMob;
        private Contact mContact;
        ImageButton mDelete;
        //The values in the OneContact Layout
        public ContactHolder(LayoutInflater inflater,ViewGroup group){
            super(inflater.inflate(R.layout.one_contact_fragment,group,false));
            mName=itemView.findViewById(R.id.name);
            mMob=itemView.findViewById(R.id.phone);
            mDelete=itemView.findViewById(R.id.delete_contact_button);
        }
        public void bind(Contact con){
            mContact=con;
            mName.setText(con.getFirstName()+" "+con.getLastName());
            mMob.setText(con.getPhoneNumber());
        }
    }
    private class ContactAdapter extends RecyclerView.Adapter<ContactHolder>{
        private List<Contact> mContactList;

        public ContactAdapter(List<Contact> contactList) {
            mContactList = contactList;
        }

        @NonNull
        @Override
        public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater=LayoutInflater.from(getActivity());
            return new ContactHolder(inflater,parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ContactHolder holder, int position) {
            final int pos=position;
            //how do you want to bind the data
            holder.bind(mContactList.get(position));
            //what to do when the button is clicked
            holder.mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //delete the contact at this position
                    mContactList.remove(pos);
                    //delete the contact from the database
                    new SQLHandler(getContext()).deleteContact(mContactList.get(pos));
                    //tell to delete this item from recyclerview
                    notifyItemRemoved(pos);//delete the item from recyclerview
                    //update the recyclerview
                    Toast.makeText(getContext(),"Deleted "+mContactList.get(pos).getFirstName()
                            +" "+mContactList.get(pos).getLastName(),Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mContactList.size();
        }
    }
}
