package com.dnod.tools.floatingmenu;

public class ContextMenuManager {
    private ContextMenuBuilder mMenuBuilder;

    public void setMenuBuilder(ContextMenuBuilder menuBuilder){
        mMenuBuilder = menuBuilder;
    }

    public ContextMenu buildMenu(){
        return mMenuBuilder.buildMenu();
    }

    public void release(){
        mMenuBuilder = null;
    }
}
