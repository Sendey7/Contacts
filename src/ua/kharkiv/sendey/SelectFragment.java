package ua.kharkiv.sendey;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/*
 * ����������� ������ ���������, ����� ��������
 */
public class SelectFragment extends ListFragment
                            implements LoaderCallbacks<Cursor> {
    SimpleCursorAdapter scAdapter;
    DataBase db;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    
        View rootView = inflater.inflate(R.layout.fragment_select,
                        container, false);
        
        /* ��������� ����������� � �� */
        db = new DataBase(getActivity());
        db.open();
        
        /* ������������ ����� �� � ����� �������� ������ */
        String[] from = new String[] {DataBase.COLUMN_F_NAME,
                                      DataBase.COLUMN_L_NAME};
        int[] to = new int[] {R.id.tvFName, R.id.tvLName};
        
        /* �������� ������� � ����������� ������ */
        scAdapter = new SimpleCursorAdapter(getActivity(),
                                            R.layout.item, null, from, to, 0);
        setListAdapter(scAdapter);
        
        /* ������� Loader ��� ������ ������ */
        getActivity().getSupportLoaderManager().initLoader(0, null, this);
        
        return rootView;
    }
    
    /* ������������ ������ �� ��������� ������ */
    @Override
    public void onListItemClick(ListView list, View view,
                            int position, long id) {
    	super.onListItemClick(list, view, position, id);
        
        /* �������� activity ��� ����������� ������� �������� */
        Intent intent = new Intent(getActivity(), DetailsActivity.class); 
        intent.putExtra("id", Long.toString(id));
        startActivity(intent);
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
        getActivity().getSupportLoaderManager().getLoader(0).forceLoad();
        super.onResume();
    }
    
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bndl) {
        return new MyCursorLoader(getActivity(), db);
    }
    
    /* ��������� ������� ������ ��������� */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        scAdapter.swapCursor(cursor);
    }
    
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
    
    static class MyCursorLoader extends CursorLoader {
        DataBase db;
        
        public MyCursorLoader(Context context, DataBase db) {
            super(context);
            this.db = db;
        }
        
        /* �������� ������ �� �� � ���� */
        @Override
        public Cursor loadInBackground() {
            Cursor cursor = db.getAllData();
            return cursor;
        }
    }
}