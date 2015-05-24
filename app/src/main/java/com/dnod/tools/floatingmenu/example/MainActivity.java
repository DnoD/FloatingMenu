package com.dnod.tools.floatingmenu.example;

import android.animation.TypeEvaluator;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.dnod.tools.floatingmenu.ContextMenu;
import com.dnod.tools.floatingmenu.ContextMenuManager;
import com.dnod.tools.floatingmenu.FloatingMenuBuilder;
import com.dnod.tools.floatingmenu.FloatingMenuAlgorithm;
import com.dnod.tools.floatingmenu.animation.FloatingMenuItemLinearAnimation;
import com.dnod.tools.floatingmenu.animation.FloatingMenuItemSpiralAnimation;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    private TypeEvaluator<PointF> mSelectedAnimation = new FloatingMenuItemLinearAnimation();

    private enum CONTEXT_MENU_ITEMS {
        COPY, ATTACH, CALL, CUT
    }

    private ContextMenuManager mMenuManager;
    private ContextMenu mContextMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMenuManager = new ContextMenuManager();
        findViewById(R.id.root).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_UP:
                        mMenuManager.release();
                        List<ContextMenu.MenuItem> menuItems = new ArrayList<>();
                        menuItems.add(new ContextMenu.MenuItem().setIconResID(R.drawable.ic_action_copy).setUniqueID(CONTEXT_MENU_ITEMS.COPY.ordinal()));
                        menuItems.add(new ContextMenu.MenuItem().setIconResID(R.drawable.ic_action_attach).setUniqueID(CONTEXT_MENU_ITEMS.ATTACH.ordinal()));
                        menuItems.add(new ContextMenu.MenuItem().setIconResID(R.drawable.ic_action_cut).setUniqueID(CONTEXT_MENU_ITEMS.CUT.ordinal()));
                        menuItems.add(new ContextMenu.MenuItem().setIconResID(R.drawable.ic_action_call).setUniqueID(CONTEXT_MENU_ITEMS.CALL.ordinal()));
                        Display display = MainActivity.this.getWindowManager().getDefaultDisplay();
                        Point size = new Point();
                        display.getSize(size);
                        mMenuManager.setMenuBuilder(new FloatingMenuBuilder(MainActivity.this, event.getX(), event.getY() + getSupportActionBar().getHeight())
                                .setItemsAppearanceAlgorithm(new FloatingMenuAlgorithm(new PointF(event.getX(), event.getY() + getSupportActionBar().getHeight()), size.x, size.y, menuItems.size(), 300))
                                .addMenuItems(menuItems)
                                .setAppearanceAnimation(mSelectedAnimation)
                                .setOnItemClickListener(new ContextMenu.OnItemClickListener() {
                                    @Override
                                    public void onItemClicked(ContextMenu.MenuItem item) {
                                        switch (CONTEXT_MENU_ITEMS.values()[item.getID()]) {
                                            case ATTACH:
                                                Toast.makeText(MainActivity.this, "Hello Attach", Toast.LENGTH_LONG).show();
                                                break;
                                            case COPY:
                                                Toast.makeText(MainActivity.this, "Hello Copy", Toast.LENGTH_LONG).show();
                                                break;
                                            case CALL:
                                                Toast.makeText(MainActivity.this, "Hello Call", Toast.LENGTH_LONG).show();
                                                break;
                                            case CUT:
                                                Toast.makeText(MainActivity.this, "Hello Cut", Toast.LENGTH_LONG).show();
                                                break;
                                        }
                                    }
                                }));
                        mContextMenu = mMenuManager.buildMenu();
                        mContextMenu.show();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_linear:
                mSelectedAnimation = new FloatingMenuItemLinearAnimation();
                break;
            case R.id.action_spiral:
                mSelectedAnimation = new FloatingMenuItemSpiralAnimation(2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(mContextMenu != null && mContextMenu.isShowing()){
            mContextMenu.hide();
            return;
        }
        super.onBackPressed();
    }
}
