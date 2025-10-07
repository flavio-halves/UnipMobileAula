package com.example.helpdeskunipassismobile.ui;



import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.helpdeskunipassismobile.model.Ticket;
import java.util.ArrayList;
import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.VH> {

    private final List<Ticket> data = new ArrayList<>();

    public void setData(List<Ticket> items) {
        data.clear();
        if (items != null) data.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        var tv = new TextView(parent.getContext());
        tv.setPadding(16, 16, 16, 16);
        return new VH(tv);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Ticket t = data.get(position);
        String usuario = (t.usuario != null && t.usuario.nome != null) ? t.usuario.nome : "—";
        ((TextView) holder.itemView).setText("#" + t.id + " • " + t.titulo + " • " + usuario);
    }

    @Override
    public int getItemCount() { return data.size(); }

    static class VH extends RecyclerView.ViewHolder {
        public VH(@NonNull TextView itemView) { super(itemView); }
    }
}

