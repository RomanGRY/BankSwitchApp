package com.bankapp.privat_nbu_Switch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NBUAdapter extends RecyclerView.Adapter<NBUAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private List<NBUBankItem> nbuBankItems;

    public NBUAdapter(Context context, List<NBUBankItem> nbuBankItems){
        this.layoutInflater=LayoutInflater.from(context);
        this.nbuBankItems=nbuBankItems;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=layoutInflater.inflate(R.layout.nbu_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.curTextNbu.setText(nbuBankItems.get(position).getTxt());
        holder.curCcNbu.setText("1 "+nbuBankItems.get(position).getCc());
        holder.curRateNbu.setText(String.valueOf(nbuBankItems.get(position).getRate())+" UAH");
    }

    @Override
    public int getItemCount() {
        return nbuBankItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView curTextNbu,
                curRateNbu,curCcNbu;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            curTextNbu=itemView.findViewById(R.id.currency_text_nbu);
            curRateNbu=itemView.findViewById(R.id.currency_rate_nbu);
            curCcNbu=itemView.findViewById(R.id.currency_cc_nbu);
        }
    }
}
