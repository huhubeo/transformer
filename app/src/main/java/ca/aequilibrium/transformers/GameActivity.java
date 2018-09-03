package ca.aequilibrium.transformers;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity implements InputFragment.InputFragmentListener {

    private GameFragment mGameFragment;
    private Button mBtnAdd;
    private Button mBtnFight;
    private Button mBtnRematch;
    private Button mBtnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mGameFragment = new GameFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.game_container, mGameFragment).commit();

        mBtnAdd = (Button) findViewById(R.id.btn_add);
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputFragment fragment = new InputFragment();
                fragment.setInputFragmentListener(GameActivity.this);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.top_container, fragment).commit();
            }
        });

        mBtnFight = (Button) findViewById(R.id.btn_fight);
        mBtnFight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mGameFragment != null) {
                    if (mGameFragment.startBattle() == GameController.GameResult.INVALID_MEMBER_SIZE) {
                        Toast.makeText(GameActivity.this, "Please add more members to battle", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        mBtnRematch = (Button) findViewById(R.id.btn_rematch);
        mBtnRematch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mGameFragment != null) {
                    mGameFragment.reset();
                }
            }
        });

        mBtnExit = (Button) findViewById(R.id.btn_exit);
        mBtnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onMemberEdited(Transformer oldTransformer, Transformer newTransformer) {
        if (mGameFragment != null) {
            mGameFragment.editMember(oldTransformer, newTransformer);
        }
    }

    @Override
    public void onMemberAdded(Transformer t) {
        if (mGameFragment != null) {
            mGameFragment.addMember(t);
        }
    }

    public void showTransformerInfo(Transformer t) {
        InputFragment fragment = new InputFragment();
        fragment.setTransformer(t);
        fragment.setInputFragmentListener(GameActivity.this);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.top_container, fragment).commit();
    }
}
