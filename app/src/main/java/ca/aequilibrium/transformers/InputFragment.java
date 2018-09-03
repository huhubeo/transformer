package ca.aequilibrium.transformers;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class InputFragment extends BaseFragment {

    public interface InputFragmentListener {
        void onMemberAdded(Transformer t);
        void onMemberEdited(Transformer old, Transformer t);
    }

    private EditText mTxtName;
    private Spinner mSpinnerTeam;
    private Spinner mSpinnerStrength;
    private Spinner mSpinnerIntelligence;
    private Spinner mSpinnerSpeed;
    private Spinner mSpinnerEndurance;
    private Spinner mSpinnerRank;
    private Spinner mSpinnerCourage;
    private Spinner mSpinnerFirepower;
    private Spinner mSpinnerSkill;

    private ImageView mBackgroundImage;

    private Button mBtnOk;
    private Button mBtnCancel;

    private Transformer mTransformer;
    private Transformer mShowingTransformer;

    private InputFragmentListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.input_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mTransformer = new Transformer();
        mTxtName = (EditText) findViewById(R.id.txt_name);
        mSpinnerTeam = (Spinner) findViewById(R.id.sp_team);
        mSpinnerStrength = (Spinner) findViewById(R.id.sp_strength);
        mSpinnerIntelligence = (Spinner) findViewById(R.id.sp_intelligence);
        mSpinnerSpeed = (Spinner) findViewById(R.id.sp_speed);
        mSpinnerEndurance = (Spinner) findViewById(R.id.sp_endurance);
        mSpinnerRank = (Spinner) findViewById(R.id.sp_rank);
        mSpinnerCourage = (Spinner) findViewById(R.id.sp_courage);
        mSpinnerFirepower = (Spinner) findViewById(R.id.sp_firepower);
        mSpinnerSkill = (Spinner) findViewById(R.id.sp_skill);
        mBackgroundImage = (ImageView) findViewById(R.id.img_background);

        mBtnOk = (Button) findViewById(R.id.btn_ok);
        mBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMember();
            }
        });
        mBtnCancel = (Button) findViewById(R.id.btn_cancel);
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFragment();
            }
        });

        ArrayAdapter<CharSequence> teamAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.team, android.R.layout.simple_spinner_item);
        teamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerTeam.setAdapter(teamAdapter);
        mSpinnerTeam.setSelection(0);
        mSpinnerTeam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mTransformer.setTeam((i == 0) ? Transformer.Team.AUTOBOTS : Transformer.Team.DECEPTICONS);
                mBackgroundImage.setBackgroundResource((i == 0) ? R.drawable.autobot : R.drawable.decepticon);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.point, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerStrength.setAdapter(adapter);
        mSpinnerStrength.setSelection(0);
        mSpinnerStrength.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mTransformer.setStrength(i + 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mSpinnerIntelligence.setAdapter(adapter);
        mSpinnerIntelligence.setSelection(0);
        mSpinnerIntelligence.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mTransformer.setIntelligence(i + 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mSpinnerSpeed.setAdapter(adapter);
        mSpinnerSpeed.setSelection(0);
        mSpinnerSpeed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mTransformer.setSpeed(i + 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mSpinnerEndurance.setAdapter(adapter);
        mSpinnerEndurance.setSelection(0);
        mSpinnerEndurance.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mTransformer.setEndurance(i + 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mSpinnerRank.setAdapter(adapter);
        mSpinnerRank.setSelection(0);
        mSpinnerRank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mTransformer.setRank(i + 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mSpinnerCourage.setAdapter(adapter);
        mSpinnerCourage.setSelection(0);
        mSpinnerCourage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mTransformer.setCourage(i + 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mSpinnerFirepower.setAdapter(adapter);
        mSpinnerFirepower.setSelection(0);
        mSpinnerFirepower.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mTransformer.setFirepower(i + 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mSpinnerSkill.setAdapter(adapter);
        mSpinnerSkill.setSelection(0);
        mSpinnerSkill.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mTransformer.setSkill(i + 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if (mShowingTransformer != null) {
            mTxtName.setText(mShowingTransformer.getName());
            mSpinnerTeam.setSelection(mShowingTransformer.getTeam() == Transformer.Team.AUTOBOTS ? 0 : 1);
            mSpinnerStrength.setSelection(mShowingTransformer.getStrength() - 1);
            mSpinnerIntelligence.setSelection(mShowingTransformer.getIntelligence() - 1);
            mSpinnerSpeed.setSelection(mShowingTransformer.getSpeed() - 1);
            mSpinnerEndurance.setSelection(mShowingTransformer.getEndurance() - 1);
            mSpinnerRank.setSelection(mShowingTransformer.getRank() - 1);
            mSpinnerCourage.setSelection(mShowingTransformer.getCourage() - 1);
            mSpinnerFirepower.setSelection(mShowingTransformer.getFirePower() - 1);
            mSpinnerSkill.setSelection(mShowingTransformer.getSkill() - 1);
        }
    }

    public void setInputFragmentListener(InputFragmentListener listener) {
        mListener = listener;
    }

    public void setTransformer(Transformer t) {
        mShowingTransformer = t;
    }

    private void addMember() {
        mTransformer.setName(mTxtName.getText().toString());
        if (mListener != null) {
            if (mShowingTransformer == null) {
                mListener.onMemberAdded(mTransformer);
            } else {
                mListener.onMemberEdited(mShowingTransformer, mTransformer);
            }
        }
        closeFragment();
    }

    @Override
    protected void removeListeners() {
        mSpinnerTeam.setOnItemSelectedListener(null);
        mSpinnerStrength.setOnItemSelectedListener(null);
        mSpinnerIntelligence.setOnItemSelectedListener(null);
        mSpinnerSpeed.setOnItemSelectedListener(null);
        mSpinnerEndurance.setOnItemSelectedListener(null);
        mSpinnerRank.setOnItemSelectedListener(null);
        mSpinnerCourage.setOnItemSelectedListener(null);
        mSpinnerFirepower.setOnItemSelectedListener(null);
        mSpinnerSkill.setOnItemSelectedListener(null);
    }
}
