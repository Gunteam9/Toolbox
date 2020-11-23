package fr.univ.orleans.toolbox;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Create a spinner adapter with images and texts for each elements
 */
public class SpinnerAdapter extends ArrayAdapter<String> {

    private Context context;
    private Drawable[] imgs;
    private String[] texts;

    public SpinnerAdapter(@NonNull Context context, Drawable[] imgs, String[] texts) {
        super(context, R.layout.custom_spinner_row);
        this.imgs = imgs;
        this.texts = texts;
        this.context = context;
    }

    @Override
    public int getCount() {
        return texts.length;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder mViewHolder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.custom_spinner_row, parent, false);
            mViewHolder.img = (ImageView) convertView.findViewById(R.id.spinnerImg);
            mViewHolder.text = (TextView) convertView.findViewById(R.id.spinnerText);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.img.setImageDrawable(imgs[position]);
        mViewHolder.text.setText(texts[position]);

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    public Drawable[] getImgs() {
        return imgs;
    }

    public void setImgs(Drawable[] imgs) {
        this.imgs = imgs;
    }

    public String[] getTexts() {
        return texts;
    }

    public void setTexts(String[] texts) {
        this.texts = texts;
    }

    private static class ViewHolder {
        ImageView img;
        TextView text;
    }
}
