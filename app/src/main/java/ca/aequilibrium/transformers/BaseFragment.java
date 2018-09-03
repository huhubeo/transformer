package ca.aequilibrium.transformers;

import android.app.Fragment;
import android.view.View;

public class BaseFragment extends Fragment {

    protected GameActivity getGameActivity() {
        return (GameActivity)getActivity();
    }

    protected final View findViewById(int id) {
        return getView() != null ? getView().findViewById(id) : null;
    }

    protected void closeFragment() {
        removeListeners();
        getGameActivity().getFragmentManager()
                .beginTransaction()
                .remove(this)
                .commit();
    }

    protected void removeListeners() {
        //will be override by children
    }
}
