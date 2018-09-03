package ca.aequilibrium.transformers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class GameFragment extends BaseFragment implements GameController.GameControllerListener {

    private ListView mAutobotListView;
    private ListView mDecepticonListView;

    private TextView mTxtResult;

    private TransformerAdapter mAutobotAdapter;
    private TransformerAdapter mDecepticonAdapter;

    private GameController mGame;

    public GameFragment() {
        mGame = new GameController();
        mGame.setGameControllerListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.game_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAutobotListView = (ListView) findViewById(R.id.autobot_listview);
        mDecepticonListView = (ListView) findViewById(R.id.decepticon_listview);

        mAutobotAdapter = new TransformerAdapter(getActivity(), mGame.getTeam(Transformer.Team.AUTOBOTS));
        mAutobotListView.setAdapter(mAutobotAdapter);
        mDecepticonAdapter = new TransformerAdapter(getActivity(), mGame.getTeam(Transformer.Team.DECEPTICONS));
        mDecepticonListView.setAdapter(mDecepticonAdapter);

        mAutobotListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Transformer transformer = (Transformer) mAutobotAdapter.getItem(i);
                getGameActivity().showTransformerInfo(transformer);
            }
        });

        mDecepticonListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Transformer transformer = (Transformer) mDecepticonAdapter.getItem(i);
                getGameActivity().showTransformerInfo(transformer);
            }
        });


        mTxtResult = (TextView) findViewById(R.id.txt_result);
        mGame.setContext(getGameActivity());
    }

    @Override
    protected void removeListeners() {

    }

    @Override
    public void onResultUpdated(String result) {
        if (mTxtResult != null) {
            mTxtResult.setText(result);
        }
    }

    public void reset() {
        if (mGame != null) {
            mGame.reset();
            mTxtResult.setText("");
            mAutobotAdapter.update(mGame.getTeam(Transformer.Team.AUTOBOTS));
            mDecepticonAdapter.update(mGame.getTeam(Transformer.Team.DECEPTICONS));
        }
    }

    public GameController.GameResult startBattle() {
        return mGame.battle();
    }

    public void addMember(Transformer t) {
        if (mGame != null) {
            mGame.addMember(t);
            mAutobotAdapter.update(mGame.getTeam(Transformer.Team.AUTOBOTS));
            mDecepticonAdapter.update(mGame.getTeam(Transformer.Team.DECEPTICONS));
        }
    }

    public void editMember(Transformer oldTransformer, Transformer newTransformer) {
        if (mGame != null) {
            mTxtResult.setText("");
            mGame.editMember(oldTransformer, newTransformer);
            mAutobotAdapter.update(mGame.getTeam(Transformer.Team.AUTOBOTS));
            mDecepticonAdapter.update(mGame.getTeam(Transformer.Team.DECEPTICONS));
        }
    }
}
