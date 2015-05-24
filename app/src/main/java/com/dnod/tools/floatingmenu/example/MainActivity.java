package com.dnod.tools.floatingmenu.example;

import android.graphics.Point;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.dnod.tools.floatingmenu.ContextMenu;
import com.dnod.tools.floatingmenu.ContextMenuManager;
import com.dnod.tools.floatingmenu.FloatingMenuBuilder;
import com.dnod.tools.floatingmenu.FloatingMenuAlgorithm;
import com.dnod.tools.floatingmenu.FloatingMenuItemLinearAnimation;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    private enum CONTEXT_MENU_ITEMS {
        COPY, ATTACH
    }

    private ContextMenuManager mMenuManager;

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
                        Display display = MainActivity.this.getWindowManager().getDefaultDisplay();
                        Point size = new Point();
                        display.getSize(size);
                        mMenuManager.setMenuBuilder(new FloatingMenuBuilder(MainActivity.this, event.getX(), event.getY())
                                .setItemsAppearanceAlgorithm(new FloatingMenuAlgorithm(new PointF(event.getX(), event.getY()), size.x, size.y, menuItems.size(), 300))
                                .addMenuItems(menuItems)
                                .setAppearanceAnimation(new FloatingMenuItemLinearAnimation())
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
                                        }
                                    }
                                }));
                        mMenuManager.buildMenu().show();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
