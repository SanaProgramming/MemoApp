package sana.programming.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import sana.programming.Database.DataBase;
import sana.programming.MainActivity;
import sana.programming.R;

public class MemoList extends AppCompatActivity implements View.OnClickListener {

    public ListView listmemoview = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_list);

        ((Button)findViewById(R.id.newmemo)).setOnClickListener(this);
        ((Button)findViewById(R.id.home)).setOnClickListener(this);

        memoListDisplay();

        //ListViewに表示するリスト項目をArrayListで準備する
        ArrayList data = new ArrayList<>();
        //data.add("テスト");


        //リスト項目とListViewを対応付けるArrayAdapterを用意する
        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);

        //ListViewにArrayAdapterを設定する
        ListView listView = (ListView)findViewById(R.id.listview);
        listView.setAdapter(adapter);


        //メモがないときはTextViewを表示してメモがあるときは非表示にさせる
        TextView textView = (TextView)findViewById(R.id.no_notes);
        if (data.isEmpty()) {
            textView.setText("メモがありません");
        }else{
            textView.setText(null);
        }
        TextView textView1 = (TextView) findViewById(R.id.supplement);
        if (data.isEmpty()) {
            textView1.setText("新規をタップしてメモを作成してください");
        }else{
            textView1.setText(null);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.newmemo):
                Intent intentNewmemo = new Intent(getApplication(), MemoInput.class);
                intentNewmemo.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intentNewmemo);

                break;
            case (R.id.home):
                Intent intentHome = new Intent(getApplication(), MainActivity.class);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intentHome);
                break;
        }
    }

    void  memoListDisplay(){

        DataBase dataBase = new DataBase(MemoList.this);
        SQLiteDatabase db = dataBase.getReadableDatabase();
        try {
            String sql = "SELECT _id,name FROM notememo";
            Cursor cursor = db.rawQuery(sql,null);
            String[] from = {"name"};
            int[] to = {android.R.id.text1};
            SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1,cursor,from,to,0);
            listmemoview.setAdapter(simpleCursorAdapter);
        } finally {
            dataBase.close();
        }
    }
}