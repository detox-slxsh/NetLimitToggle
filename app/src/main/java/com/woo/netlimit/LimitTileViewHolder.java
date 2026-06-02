package com.woo.netlimit;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class LimitTileViewHolder extends RecyclerView.ViewHolder {

    private final CardView card;
    private final TextView labelText;
    private final TextView subText;
    private final View activeIndicator;

    public LimitTileViewHolder(@NonNull View itemView) {
        super(itemView);
        card = itemView.findViewById(R.id.card);
        labelText = itemView.findViewById(R.id.labelText);
        subText = itemView.findViewById(R.id.subText);
        activeIndicator = itemView.findViewById(R.id.activeIndicator);
    }

    public void bind(String label, String sub, boolean isActive, Runnable onClick) {
        labelText.setText(label);
        subText.setText(sub);

        if (isActive) {
            card.setCardBackgroundColor(0xFF1A73E8); // Google blue
            labelText.setTextColor(0xFFFFFFFF);
            subText.setTextColor(0xCCFFFFFF);
            activeIndicator.setVisibility(View.VISIBLE);
        } else {
            card.setCardBackgroundColor(0xFF2C2C2E);
            labelText.setTextColor(0xFFEEEEEE);
            subText.setTextColor(0xFF888888);
            activeIndicator.setVisibility(View.GONE);
        }

        // Animate scale on click
        card.setOnClickListener(v -> {
            v.animate().scaleX(0.92f).scaleY(0.92f).setDuration(80).withEndAction(() ->
                v.animate().scaleX(1f).scaleY(1f).setDuration(80).start()
            ).start();
            onClick.run();
        });
    }
}
