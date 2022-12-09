package tn.isetr_mpdam.weatherapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder>{
    private List<String> items;
    private Context mContext;

    public RvAdapter(List<String> items, Context mContext) {
        this.items = items;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RvAdapter.ViewHolder holder, int position) {
        final String item = items.get(position);
        holder.title.setText(item);
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetWeatherTask().execute(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView title;
        public final Button btn;


        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.gov);
            btn = itemView.findViewById(R.id.btn);
        }
    }

    private class GetWeatherTask extends AsyncTask<String,Void,String>
    {

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL("https://goweather.herokuapp.com/weather/"+strings[0]);
                InputStreamReader reader = new InputStreamReader(url.openStream());
                Scanner s = new Scanner(reader).useDelimiter("\\A");
                return s.hasNext() ? s.next() : "";
            }
            catch (Exception e)
            {
                cancel(true);
            }
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ResponseItem item = new Gson().fromJson(s,ResponseItem.class);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
            alertDialogBuilder.setMessage("Temperature : "+item.temperature);
            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    arg0.dismiss();
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }
}
