package com.example.dicenotation;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.provider.BaseColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dicenotation.dummy.DummyContent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;


/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity extends AppCompatActivity  implements AddNotationDialogFragment.AddNotationDialogListener{

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private Menu menu;
    private Menu mMenu;
    TextView input;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "NotationPersistence.db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        input = findViewById(R.id.editTextNotation);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab =  findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Pick a Notation", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (findViewById(R.id.item_detail_container) != null) {
            mTwoPane = true;
        }

        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mMenu = menu;
        getMenuInflater().inflate(R.menu.app_menu, menu);
        //menuItem = menu.findItem(R.if.add);
        return true;
    }
//    public class NotationPersistenceDbHelper extends SQLiteOpenHelper {
//        // If you change the database schema, you must increment the database version.
//        public static final int DATABASE_VERSION = 1;
//        public static final String DATABASE_NAME = "NotationPersistence.db";
//
//
//        /* Inner class that defines the table contents */
//        public class FeedEntry implements BaseColumns {
//            public static final String TABLE_NAME = "entry";
//            public static final String COLUMN_NAME_TITLE = "title";
//            public static final String COLUMN_NAME_SUBTITLE = "subtitle";
//        }
//        private static final String SQL_CREATE_ENTRIES =
//                "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
//                        FeedEntry._ID + " INTEGER PRIMARY KEY," +
//                        FeedEntry.COLUMN_NAME_TITLE + " TEXT," +
//                        FeedEntry.COLUMN_NAME_SUBTITLE + " TEXT)";
//
//        private final String SQL_DELETE_ENTRIES =
//                "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;
//
//        public NotationPersistenceDbHelper(Context context) {
//            super(context, DATABASE_NAME, null, DATABASE_VERSION);
//        }
//        public void onCreate(SQLiteDatabase db) {
//            db.execSQL(SQL_CREATE_ENTRIES);
//        }
//        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//            // This database is only a cache for online data, so its upgrade policy is
//            // to simply to discard the data and start over
//            db.execSQL(SQL_DELETE_ENTRIES);
//            onCreate(db);
//        }
//        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//            onUpgrade(db, oldVersion, newVersion);
//        }
//    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.v("pause", "pausing ...");
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String s = input.getText().toString();
        editor.putString("input", s);
        editor.apply();
//        try {
//            FileOutputStream outputStream = openFileOutput("data.txt", MODE_PRIVATE);
//            PrintWriter writer = new PrintWriter(outputStream);
//            writer.println(s);
//            writer.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.v("pause", "resuming ...");
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        String s = prefs.getString("input", "");
        input.setText(s);
//        try {
//            FileInputStream inputStream = openFileInput("data.txt");
//            Scanner scan = new Scanner(inputStream);
//            StringBuilder sb = new StringBuilder();
//            while (scan.hasNext())
//                sb.append(scan.nextLine());
//            scan.close();
//            input.setText(sb.toString());
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();


        switch (item.getItemId()) {
            case R.id.action_about:
                Intent intent_about = new Intent(ItemListActivity.this, AboutActivity.class);
                startActivity(intent_about);
                return true;

        }
        if(id == R.id.action_add)
        {
            FragmentManager manager = getFragmentManager();
            AddNotationDialogFragment dialog = new AddNotationDialogFragment();
            dialog.show(manager,"addNotationDialog");
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new ItemListActivity.SimpleItemRecyclerViewAdapter(this, DummyContent.ITEMS, mTwoPane));
    }



    public void onFinishAddNotationDialog (String id){
        Log.v("DialogTester","Finished");
        DummyContent.addItem(new DummyContent.DummyItem(id, "", id));

    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final ItemListActivity mParentActivity;
        private final List<DummyContent.DummyItem> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DummyContent.DummyItem item = (DummyContent.DummyItem) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(ItemDetailFragment.ARG_ITEM_ID, item.id);
                    ItemDetailFragment fragment = new ItemDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, ItemDetailActivity.class);
                    intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, item.id);

                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(ItemListActivity parent,
                                      List<DummyContent.DummyItem> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText(mValues.get(position).id);
            holder.mContentView.setText(mValues.get(position).content);

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mIdView =  view.findViewById(R.id.id_text);
                mContentView =  view.findViewById(R.id.content);
            }
        }
    }
}
