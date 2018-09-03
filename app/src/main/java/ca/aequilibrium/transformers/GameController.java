package ca.aequilibrium.transformers;


import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.os.Handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ca.aequilibrium.transformers.Transformer.Team;

public class GameController {
    private String TAG = GameController.class.getName();

    public interface GameControllerListener {
        void onResultUpdated(String result);
    }

    public enum GameResult {INVALID_MEMBER_SIZE, GAME_END, END_BY_SPECIAL_RULE}

    private Context mContext;
    private List<Transformer> mTeamA;
    private List<Transformer> mTeamD;

    private GameControllerListener mListener;

    private int mNumBattles;

    public GameController() {
        mTeamA = new ArrayList<>();
        mTeamD = new ArrayList<>();

        mNumBattles = 0;
    }

    public void setGameControllerListener(GameControllerListener listener) {
        mListener = listener;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public void reset() {
        mTeamA.clear();
        mTeamD.clear();
    }

    public void addMember(Transformer t) {
        if (t.getTeam() == Team.AUTOBOTS) {
            mTeamA.add(t);
        } else {
            mTeamD.add(t);
        }
        //sort members in team by Rank
        Collections.sort(mTeamA, new RankComparator());
        Collections.sort(mTeamD, new RankComparator());
    }

    public void editMember(Transformer oldTransformer, Transformer newTransformer) {
        if (oldTransformer.getTeam() == Team.AUTOBOTS) {
            mTeamA.remove(oldTransformer);
        } else {
            mTeamD.remove(oldTransformer);
        }
        addMember(newTransformer);
    }

    public List<Transformer> getTeam(Team team) {
        return (team == Team.AUTOBOTS) ? mTeamA : mTeamD;
    }

    public GameResult battle() {
        if (mTeamA.size() == 0 || mTeamD.size() == 0) {
            return GameResult.INVALID_MEMBER_SIZE;
        }

        mNumBattles = 0;
        int minSize = Math.min(mTeamA.size(), mTeamD.size());

        String txtResult = "";
        int eliminatedMemberA = 0;
        int eliminatedMemberD = 0;

        for (int i = 0; i < minSize; i++) {
            Transformer memberA = mTeamA.get(i);
            Transformer memberD = mTeamD.get(i);

            int result = fight(memberA, memberD);
            txtResult += memberA.getName() + " vs " + memberD.getName();
            if (result != 2) {
                mNumBattles++;
                if (result < 0) {
                    memberA.setEliminated(true);
                    txtResult += ": " + memberD.getName() + " won\n";
                } else if (result > 0) {
                    memberD.setEliminated(true);
                    txtResult += ": " + memberA.getName() + " won\n";
                } else {
                    memberA.setEliminated(true);
                    memberD.setEliminated(true);
                    txtResult += ": Tie\n";
                }
            } else {
                mNumBattles++;
                memberA.setEliminated(true);
                memberD.setEliminated(true);
                txtResult += ": Game end\n";
                //print out the number of battles before ending by Special rule
                txtResult += mContext.getString(R.string.number_battle) + ":" + mNumBattles;
                Log.d(TAG, mContext.getString(R.string.number_battle) + ":" + mNumBattles);
                //all competitors that haven't fought will be eliminated
                eliminateAllCompetitors(minSize);
                txtResult += "\n";
                txtResult += compareSurvivors();
                if (mListener != null) {
                    mListener.onResultUpdated(txtResult);
                }
                return GameResult.END_BY_SPECIAL_RULE;
            }
        }

        String numBattles = mNumBattles + " " + ((mNumBattles > 1) ? mContext.getString(R.string.battles) : mContext.getString(R.string.battle));
        txtResult += numBattles + "\n";
        Log.d(TAG, numBattles);

        if (eliminatedMemberA > eliminatedMemberD) {
            txtResult += mContext.getString(R.string.winning_team) + " " + mTeamD.get(0).getTeam().name() + ":" + mTeamD.get(0).getName();
            Log.d(TAG, mContext.getString(R.string.winning_team) + " " + mTeamD.get(0).getTeam().name() + ":" + mTeamD.get(0).getName());
            txtResult += "\n" + printSurvivorsInTeam(mTeamA, mTeamA.get(0).getTeam().name(), false);
        } else if (eliminatedMemberA < eliminatedMemberD) {
            txtResult += mContext.getString(R.string.winning_team) + " " + mTeamA.get(0).getTeam().name() + ":" + mTeamA.get(0).getName();
            Log.d(TAG, mContext.getString(R.string.winning_team) + " " + mTeamA.get(0).getTeam().name() + ":" + mTeamA.get(0).getName());
            txtResult += "\n" + printSurvivorsInTeam(mTeamD, mTeamD.get(0).getTeam().name(), false);
        } else {
            //check survivors, which team have more survivors will be winner
            txtResult += compareSurvivors();
        }

        updateResult(txtResult);

        return GameResult.GAME_END;
    }

    private void updateResult(final String text) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mListener != null) {
                    mListener.onResultUpdated(text);
                }
            }
        }, 1500);
    }

    private void eliminateAllCompetitors(int size) {
        for (int k = 0; k < size; k++) {
            if (!mTeamA.get(k).isAlreadyFight()) {
                mTeamA.get(k).setAlreadyFight(true);
                mTeamA.get(k).setEliminated(true);
            }
            if (!mTeamD.get(k).isAlreadyFight()) {
                mTeamD.get(k).setAlreadyFight(true);
                mTeamD.get(k).setEliminated(true);
            }
        }
    }

    private int fight(Transformer t1, Transformer t2) {
        t1.setAlreadyFight(true);
        t2.setAlreadyFight(true);
        return GameRules.compareByGameRules(t1, t2);
    }

    private String compareSurvivors() {
        String text = "";
        int survivorsA = calSurvivorsInTeam(mTeamA);
        int survivorsD = calSurvivorsInTeam(mTeamD);
        if (survivorsA > survivorsD) {
            text += mContext.getString(R.string.winning_team) + " " + mTeamA.get(0).getTeam().name() + ":" + mTeamA.get(0).getName();
            Log.d(TAG, mContext.getString(R.string.winning_team) + " " + mTeamA.get(0).getTeam().name() + ":" + mTeamA.get(0).getName());
            text += "\n" + printSurvivorsInTeam(mTeamD, mTeamD.get(0).getTeam().name(), false);
        } else if (survivorsA < survivorsD) {
            text += mContext.getString(R.string.winning_team) + " " + mTeamD.get(0).getTeam().name() + ":" + mTeamD.get(0).getName();
            Log.d(TAG, mContext.getString(R.string.winning_team) + " " + mTeamD.get(0).getTeam().name() + ":" + mTeamD.get(0).getName());
            text += "\n" + printSurvivorsInTeam(mTeamA, mTeamA.get(0).getTeam().name(), false);
        } else {
            text += mContext.getString(R.string.tie);
            Log.d(TAG, mContext.getString(R.string.tie));
            text += "\n";
            text += printSurvivorsInTeam(mTeamA, mTeamA.get(0).getTeam().name(), true);
            text += "\n";
            text += printSurvivorsInTeam(mTeamD, mTeamD.get(0).getTeam().name(), true);
        }
        return text;
    }

    private String printSurvivorsInTeam(List<Transformer> team, String teamName, boolean isTie) {
        String text = (isTie ? mContext.getString(R.string.survivors) : (mContext.getString(R.string.survivor) + " " + teamName) ) + ":";
        int count = 0;
        for (Transformer t: team) {
            if (!t.isEliminated()) {
                if (count > 0) {
                    text += ",";
                }
                text += t.getName();
                count++;
            }
        }
        if (count == 0) {
            text += mContext.getString(R.string.no_survivor);
        }
        Log.d(TAG, text);
        return text;
    }

    private int calSurvivorsInTeam(List<Transformer> team) {
        int count = 0;
        for (Transformer t: team) {
            if (!t.isEliminated()) {
                count++;
            }
        }
        return count;
    }
}
