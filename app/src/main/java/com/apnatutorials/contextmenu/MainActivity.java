package com.apnatutorials.contextmenu;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ActionMode.Callback {
    private static int student_list_position = -1;
    String[] days = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    String[] students = new String[]{"John doe", "Vivek mishra", "Rakesh tripathi"};
    ArrayAdapter<String> studentAdapter = null;
    ArrayAdapter<String> dayAdapter = null;
    ListView lvDays, lvStudents;
    ActionMode mActionMode;
    Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, days);

        lvDays = (ListView) findViewById(R.id.lvDays);
        lvDays.setAdapter(dayAdapter);
        registerForContextMenu(lvDays);

        lvStudents = (ListView) findViewById(R.id.lvStudents);
        studentAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, students);
        lvStudents.setAdapter(studentAdapter);
        lvStudents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (mActionMode != null) {
                    return;
                }
                student_list_position = position;
                // Start the CAB
                mActionMode = startActionMode((ActionMode.Callback) ctx);
                lvStudents.setSelected(true);
            }
        });


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.cmnuDelete:
                Toast.makeText(MainActivity.this, "Delete action called on item : " + dayAdapter.getItem(info.position), Toast.LENGTH_SHORT).show();
                break;
            case R.id.cmnuEdit:
                Toast.makeText(MainActivity.this, "Edit action called on item : " + dayAdapter.getItem(info.position), Toast.LENGTH_SHORT).show();
                break;
            case R.id.cmnuShare:
                Toast.makeText(MainActivity.this, "Share action called on item :" + dayAdapter.getItem(info.position), Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onContextItemSelected(item);
    }


    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        // Inflate a menu resource providing context menu items
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        boolean success = true;
        switch (item.getItemId()) {
            case R.id.cmnuDelete:
                Toast.makeText(MainActivity.this, "Delete action called for student : " + studentAdapter.getItem(student_list_position), Toast.LENGTH_SHORT).show();
                mode.finish(); // Action picked, so close the CAB
                return true;
            case R.id.cmnuEdit:
                Toast.makeText(MainActivity.this, "Edit action called for student : " + studentAdapter.getItem(student_list_position), Toast.LENGTH_SHORT).show();
                mode.finish(); // Action picked, so close the CAB
                break;
            case R.id.cmnuShare:
                Toast.makeText(MainActivity.this, "Share action called for student :" + studentAdapter.getItem(student_list_position), Toast.LENGTH_SHORT).show();
                mode.finish(); // Action picked, so close the CAB
                break;
            default:
                success = false;
        }
        return success;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        mActionMode = null;
    }
}
