package ca.aequilibrium.transformers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class TransformerAdapter extends BaseAdapter {

    private Context mContext;
    private List<Transformer> mTransformers;
    private LayoutInflater mLayoutInflater;

    public TransformerAdapter(Context context, List<Transformer> transformers) {
        mTransformers = transformers;
        mContext = context;
        mLayoutInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void update(List<Transformer> transformers) {
        mTransformers = transformers;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mTransformers.size();
    }

    @Override
    public Object getItem(int position) {
        return mTransformers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView != null ?
                convertView : mLayoutInflater.inflate(R.layout.item_transformer, null);

        Transformer transformer = mTransformers.get(position);

        TextView tvName = (TextView) view.findViewById(R.id.transformer_name);
        tvName.setText(transformer.getName());

        return view;
    }
}
