package ua.kharkiv.sendey;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/*
 * ����������� ������ ���������� � ��������� ��������
 */
public class DetailsActivity extends Activity implements OnClickListener {
    public static final int CONTACT_REMOVED = 1;

    EditText etFirstNameDet;
    EditText etLastNameDet;
    EditText etEmailDet;
    EditText etPhoneDet;
    Button btnDelete;
    DataBase db;
    Cursor cursor;
    String id;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        
        etFirstNameDet = (EditText) findViewById(R.id.etFNameDet);
        etLastNameDet = (EditText) findViewById(R.id.etLNameDet);
        etEmailDet = (EditText) findViewById(R.id.etEmailDet);
        etPhoneDet = (EditText) findViewById(R.id.etPhoneDet);
        
        int blackColor = getResources().getColor(R.color.textColorBlack);
        etFirstNameDet.setEnabled(false);
        etFirstNameDet.setTextColor(blackColor);
        etLastNameDet.setEnabled(false);
        etLastNameDet.setTextColor(blackColor);
        etEmailDet.setEnabled(false);
        etEmailDet.setTextColor(blackColor);
        etPhoneDet.setEnabled(false);
        etPhoneDet.setTextColor(blackColor);
        
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(this);
        
        /* ��������� ����������� � �� */
        db = new DataBase(this);
        db.open();
        cursor = db.getSingleData(id);
        cursor.moveToFirst();
        
        int FirstNameDetIndex = cursor.getColumnIndex(DataBase.COLUMN_F_NAME);
        int LastNameDetIndex = cursor.getColumnIndex(DataBase.COLUMN_L_NAME);
        int EmailDetIndex = cursor.getColumnIndex(DataBase.COLUMN_EMAIL);
        int PhoneDetIndex = cursor.getColumnIndex(DataBase.COLUMN_PHONE);
        
        etFirstNameDet.setText(cursor.getString(FirstNameDetIndex));
        etLastNameDet.setText(cursor.getString(LastNameDetIndex));
        etEmailDet.setText(cursor.getString(EmailDetIndex));
        etPhoneDet.setText((cursor.getInt(PhoneDetIndex) == 0
                            ? "" : cursor.getString(PhoneDetIndex)));
        
        /* ��������� ����������� � �� */
        cursor.close();
        db.close();
    }
    
    @Override
    public void onClick(View v) {

        /* ������� ������ �� �� */
        db.open();
        db.delRec(Long.parseLong(id));
        db.close();
        
        etFirstNameDet.setText("");
        etLastNameDet.setText("");
        etEmailDet.setText("");
        etPhoneDet.setText("");
        
        /* ������� ����������� ��������� */
        Toast.makeText(this,
              getResources().getString(R.string.det_delete_contact),
              Toast.LENGTH_SHORT).show();
        
        /* ��������� ������ activity */
        setResult(CONTACT_REMOVED, null);
        finish();
    }
}