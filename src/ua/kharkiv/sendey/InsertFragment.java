package ua.kharkiv.sendey;

import android.content.ContentValues;
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
        
        /* ��������� ����������� � �� */
        db = new DataBase(getActivity());
        db.open();
        
        etFirstName = (EditText) rootView.findViewById(R.id.etFirstName);
        etLastName = (EditText) rootView.findViewById(R.id.etLastName);
        etEmail = (EditText) rootView.findViewById(R.id.etEmail);
        etPhone = (EditText) rootView.findViewById(R.id.etPhone);
        
        btnInsert = (Button) rootView.findViewById(R.id.btnInsert);
        btnInsert.setOnClickListener(this);
        
        return rootView;
    }
    
    @Override
    public void onPause() {
        /* ��������� ����������� � �� */
        db.close();
        super.onPause();
    }
    
    @Override
    public void onResume() {
        /* ��������� ����������� � �� */
        db = new DataBase(getActivity());
        db.open();
        super.onResume();
    }
    
    @Override
    public void onClick(View v) {
    
        /* ������� ������ ��� ������ */
        ContentValues cv = new ContentValues();
        
        /* �������� ������ �� ����� ����� */
        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();
        String email = etEmail.getText().toString();
        long phone = Long.parseLong(etPhone.getText().toString());
        
        /* ���������� ������ ��� ������� */
        cv.put("flirst_name", firstName);
        cv.put("ast_name", lastName);
        cv.put("email", email);
        cv.put("phone", phone);
        
        /* ��������� ������ � �� */
        db.addRec(firstName, lastName, email, phone);
        
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