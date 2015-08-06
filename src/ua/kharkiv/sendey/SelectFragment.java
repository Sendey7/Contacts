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
 * Отображение списка контактов, выбор контакта
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
        
        /* Открываем подключение к БД */
        db = new DataBase(getActivity());
        db.open();
        
        /* Соответствие полей БД и полей элемента списка */
        String[] from = new String[] {DataBase.COLUMN_F_NAME,
                                      DataBase.COLUMN_L_NAME};
        int[] to = new int[] {R.id.tvFName, R.id.tvLName};
        
        /* Создааем адаптер и настраиваем список */
        scAdapter = new SimpleCursorAdapter(getActivity(),
                                            R.layout.item, null, from, to, 0);
        setListAdapter(scAdapter);
        
        /* Создаем Loader для чтения данных */
        getActivity().getSupportLoaderManager().initLoader(0, null, this);
        
        return rootView;
    }
    
    /* Обрабатываем щелчки по элементам списка */
    @Override
    public void onListItemClick(ListView list, View view,
                            int position, long id) {
    	super.onListItemClick(list, view, position, id);
        
        /* Вызываем activity для отображение деталей контакта */
        Intent intent = new Intent(getActivity(), DetailsActivity.class); 
        intent.putExtra("id", Long.toString(id));
        startActivity(intent);
    }
    
    @Override
    public void onPause() {
        /* Закрываем подключение к БД */
        db.close();
        super.onPause();
    }
    
    @Override
    public void onResume() {
        /* Открываем подключение к БД */
        db = new DataBase(getActivity());
        db.open();
        getActivity().getSupportLoaderManager().getLoader(0).forceLoad();
        super.onResume();
    }
    
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bndl) {
        return new MyCursorLoader(getActivity(), db);
    }
    
    /* Обновляем адаптер списка контактов */
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
        
        /* Получаем данные из БД в фоне */
        @Override
        public Cursor loadInBackground() {
            Cursor cursor = db.getAllData();
            return cursor;
        }
    }
}