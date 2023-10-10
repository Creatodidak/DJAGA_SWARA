package id.creatodidak.djaga_swara.API.NEWADAPTER;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;

public class NonScrollableLayoutManager extends LinearLayoutManager {

    public NonScrollableLayoutManager(Context context) {
        super(context);
    }

    @Override
    public boolean canScrollVertically() {
        return false;
    }
}