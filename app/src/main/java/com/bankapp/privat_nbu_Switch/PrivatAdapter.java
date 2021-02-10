package com.bankapp.privat_nbu_Switch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PrivatAdapter extends RecyclerView.Adapter<PrivatAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private List<PrivatBankItem> privatBankItems;
    private OnItemClickListener mClickListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemsClickListener(OnItemClickListener listener){
        mClickListener=listener;
    }

    public PrivatAdapter(Context context, List<PrivatBankItem> privatBankItems){
        this.layoutInflater=LayoutInflater.from(context);
        this.privatBankItems=privatBankItems;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=layoutInflater.inflate(R.layout.bank_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.curNamePrivate.setText(privatBankItems.get(position).getCurrency());
        holder.curBuyPrivate.setText(String.valueOf(privatBankItems.get(position).getPurchaseRate()));
        holder.curSellPrivate.setText(String.valueOf(privatBankItems.get(position).getSaleRate()));
    }

    @Override
    public int getItemCount() {
        return privatBankItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView curNamePrivate,
                curBuyPrivate,curSellPrivate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            curNamePrivate=itemView.findViewById(R.id.currency_name_private);
            curBuyPrivate=itemView.findViewById(R.id.currency_buy_private);
            curSellPrivate=itemView.findViewById(R.id.currency_sell_private);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mClickListener!=null){
                        int position=getAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION){
                            mClickListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
