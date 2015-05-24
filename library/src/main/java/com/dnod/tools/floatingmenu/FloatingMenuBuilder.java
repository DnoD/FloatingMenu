package com.dnod.tools.floatingmenu;

import android.content.Context;

public class FloatingMenuBuilder extends ContextMenuBuilder {

    public FloatingMenuBuilder(Context context, float appearanceX, float appearanceY){
        menu = new FloatingMenu(context, appearanceX, appearanceY);
    }

    @Override
    public ContextMenu buildMenu() {
        menu.initialize();
        return menu;
    }
}
