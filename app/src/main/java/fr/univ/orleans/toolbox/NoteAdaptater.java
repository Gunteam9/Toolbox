package fr.univ.orleans.toolbox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * An adaptater that allow a good display of our note
 */
public class NoteAdaptater extends ArrayAdapter<Note> {
    private Context context;
    int ressource;

    public NoteAdaptater(Context context, int ressource, ArrayList<Note> data){
        super(context,ressource,data);
        this.context = context;
        this.ressource = ressource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String title = getItem(position).getTitle();
        String content = getItem(position).getContent();

        //Note note = new Note(title,content);

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(ressource,parent,false);

        TextView vtitle = (TextView) convertView.findViewById(R.id.titleview);
        TextView vcontent = (TextView) convertView.findViewById(R.id.contentview);

        vtitle.setText(title);
        vcontent.setText(content);

        return convertView;
    }

}
