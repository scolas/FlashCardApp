package com.scottcolas.android.flashcard;

import android.view.View;

/**
 * Created by scott on 10/27/18.
 */

public interface ListItemClickListener {
    void onListItemClick(int clickedItemIndex);
    void onListItemDelete(int clickedItemIndex);
}
