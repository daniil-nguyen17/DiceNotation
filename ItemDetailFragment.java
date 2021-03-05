package com.example.dicenotation;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dicenotation.dummy.DummyContent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";

    private DiceNotation mDiceNotation;
    private DummyContent.DummyItem mItem;
    private TextView tvNotation, tvResult, tvFormula;


    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {

            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout =  activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.content);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_detail, container, false);

//        tvNotation= view.findViewById(R.id.editTextNotation);
//        tvResult= view.findViewById(R.id.editTextResult);
//        tvFormula= view.findViewById(R.id.editTextFormula);

        if (mItem != null) {
            ((TextView) view.findViewById(R.id.editTextNotation)).setText(mItem.details);
        }
        return view;

        //        tvNotation.setText(mDiceNotation.getNotation());

//        FloatingActionButton fab = findViewById(R.id.fabRoll);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (tvNotation.getText().toString().isEmpty()== true){
//                    Snackbar.make(view, "Pick a Notation", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
//                }else{
//                    tvResult.setText(Integer.toString(mDiceNotation.roll()));
//                  tvFormula.setText(mDiceNotation.getLastFormula());
//                }
//            }
//        });
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(tvNotation.getText().toString().isEmpty()== true){
//                    toastMessage("Pick a notation");
//                }else{
//                    tvResult.setText(Integer.toString(mDiceNotation.roll()));
//                  tvFormula.setText(mDiceNotation.getLastFormula());
//                }
//            }
//        });
    }
//    private void toastMessage(String msg) {
//        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
//    }
}
