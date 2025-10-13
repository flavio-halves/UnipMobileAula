package com.example.helpdeskunipassismobile.ui;

import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.helpdeskunipassismobile.model.Usuario;
import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.VH> {

    public interface OnUserClick { void onUserClick(Usuario u); }

    private final List<Usuario> data = new ArrayList<>();
    private final OnUserClick listener;

    public UserAdapter(OnUserClick listener) { this.listener = listener; }

    public void setData(List<Usuario> items) {
        data.clear();
        if (items != null) data.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView tv = new TextView(parent.getContext());
        int pad = (int) (16 * parent.getResources().getDisplayMetrics().density);
        tv.setPadding(pad, pad, pad, pad);
        return new VH(tv);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Usuario u = data.get(position);
        TextView tv = (TextView) holder.itemView;
        tv.setText("#" + u.id + " • " + u.nome + " • " + u.email);
        tv.setOnClickListener(v -> {
            if (listener != null) listener.onUserClick(u);
        });
    }

    @Override public int getItemCount() { return data.size(); }

    static class VH extends RecyclerView.ViewHolder {
        VH(@NonNull TextView itemView) { super(itemView); }
    }
}
