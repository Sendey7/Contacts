package ua.kharkiv.sendey;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/*
 * ���������� ���������� � �������� � ��
 */
public class InsertFragment extends Fragment implements OnClickListener {
    EditText etFirstName;
    EditText etLastName;
    EditText etEmail;
    EditText etPhone;
    Button btnInsert;
    DataBase db;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_insert,
                                         container, false);
        
        db = new DataBase(getActivity());
        
        etFirstName = (EditText) rootView.findViewById(R.id.etFirstName);
        etLastName = (EditText) rootView.findViewById(R.id.etLastName);
        etEmail = (EditText) rootView.findViewById(R.id.etEmail);
        etPhone = (EditText) rootView.findViewById(R.id.etPhone);
        
        btnInsert = (Button) rootView.findViewById(R.id.btnInsert);
        btnInsert.setOnClickListener(this);
        
        return rootView;
    }
    
    @Override
    public void onClick(View v) {
        String firstName;
        String lastName;
        String email;
        long phone = 0;
        boolean valid = true;
    
        /* �������� ������ �� ����� ����� � ��������� ������������ �����*/
        firstName = etFirstName.getText().toString();
        if (firstName.isEmpty()) {
            etFirstName.setError(getResources()
                                .getString(R.string.err_first_name));
            valid = false;
        }
        
        lastName = etLastName.getText().toString();
        
        email = etEmail.getText().toString();
        Pattern p = Pattern
               .compile("[a-zA-Z][\\w]*@([a-z]{2,}).([a-z]{2,}).?[a-z]{2,3}");
        Matcher m = p.matcher(email);
        boolean isEmail = m.matches();
        if (!isEmail) {
        	etEmail.setError(getResources()
                                .getString(R.string.err_email));
            valid = false;
        }
        
        try { 
            phone = (etPhone.getText().toString().isEmpty()
            		?0
            		:Long.parseLong(etPhone.getText().toString()));
        } catch (NumberFormatException e) { 
            etPhone.setError(getResources()
                            .getString(R.string.err_phone));
            valid = false; 
        } 
        
        if (valid) {
            
            /* ��������� ����������� � �� */
            db.open();
            
            /* ��������� ������ � �� */
            db.addRec(firstName, lastName, email, phone);
            
            /* ��������� ����������� � �� */
            db.close();
            
            /* ��������� ������ ��������� */
            getActivity().getSupportLoaderManager().getLoader(0).forceLoad();
            
            etFirstName.setText("");
            etLastName.setText("");
            etEmail.setText("");
            etPhone.setText("");
            
            /* ������� ����������� ��������� */
            Toast.makeText(getActivity(),
                  getResources().getString(R.string.ins_contact_add),
                  Toast.LENGTH_SHORT).show();
        }
    }
}