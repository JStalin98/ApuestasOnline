package com.example.jstalin.apuestasonline.lessons;


import com.example.jstalin.apuestasonline.R;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by JStalin on 07/02/2018.
 */

public class TableResult {

    private TableLayout table;
    private ArrayList<TableRow> rows;
    private Activity activity;
    private Resources rs;
    private int N_ROWS, N_COLUMNS;

    public TableResult() {

    }

    public TableResult(Activity activity, TableLayout table) {

        this.activity = activity;
        this.table = table;
        rs = this.activity.getResources();
        N_ROWS = N_COLUMNS = 0;
        rows = new ArrayList<TableRow>();

    }

    public void addHead(int resourceHead) {


        TableRow.LayoutParams layoutCell;
        TableRow row = new TableRow(activity);
        TableRow.LayoutParams layoutRow = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(layoutRow);

        String[] arrayHead = rs.getStringArray(resourceHead);
        N_COLUMNS = arrayHead.length;

        for (int i = 0; i < arrayHead.length; i++) {
            TextView texto = new TextView(activity);
            layoutCell = new TableRow.LayoutParams(getWidthPixelsText(arrayHead[i]), TableRow.LayoutParams.WRAP_CONTENT);
            texto.setText(arrayHead[i]);
            texto.setGravity(Gravity.CENTER_HORIZONTAL);
            texto.setTextAppearance(activity, R.style.style_cell);
            texto.setBackgroundResource(R.drawable.table_row_head);
            texto.setLayoutParams(layoutCell);

            row.addView(texto);
        }

        table.addView(row);
        rows.add(row);

        N_ROWS++;

    }



    public void addRowTable(ArrayList<String> elements)
    {
        TableRow.LayoutParams layoutCell;
        TableRow.LayoutParams layoutRow = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        TableRow row = new TableRow(activity);
        row.setLayoutParams(layoutRow);

        for(int i = 0; i< elements.size(); i++)
        {
            TextView text = new TextView(activity);
            text.setText(String.valueOf(elements.get(i)));
            text.setGravity(Gravity.CENTER_HORIZONTAL);
            text.setTextAppearance(activity, R.style.style_cell);
            text.setBackgroundResource(R.drawable.table_row);
            layoutCell = new TableRow.LayoutParams(getWidthPixelsText(text.getText().toString()), TableRow.LayoutParams.WRAP_CONTENT);
            text.setLayoutParams(layoutCell);

            row.addView(text);
        }

        table.addView(row);
        rows.add(row);

        N_ROWS++;
    }

    private int getWidthPixelsText(String text)
    {
        Paint p = new Paint();
        Rect bounds = new Rect();
        p.setTextSize(50);

        p.getTextBounds(text, 0, text.length(), bounds);
        return bounds.width();
    }


}
