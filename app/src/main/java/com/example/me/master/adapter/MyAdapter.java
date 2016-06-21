package com.example.me.master.adapter;

/**
 * Created by July on 2016/6/16.
 */

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.me.master.MessageActivity;
import com.example.me.master.R;
import com.example.me.master.domain.Message;

import java.util.List;



/*

 * 我的新闻ListView的adapter。

 * */

public class MyAdapter extends BaseAdapter {

    private List<Message> messages;//ListView显示的数据

    private int resource;//显示列表项的Layout

    private LayoutInflater inflater;//界面生成器

    private Context context;//


    public MyAdapter(List<Message> messages, int resource, Context context) {

        this.messages = messages;

        this.resource = resource;

        this.context = context;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override

    public int getCount() {

        return messages.size();

    }


    @Override

    public Object getItem(int arg0) {

        return messages.get(arg0);

    }


    @Override

    public long getItemId(int arg0) {

        return arg0;

    }


    @Override

    public View getView(int arg0, View arg1, ViewGroup arg2) {

        if (arg1 == null) {

            arg1 = inflater.inflate(resource, null);

        }

        final Message message = messages.get(arg0);

        ImageView img = (ImageView) arg1.findViewById(R.id.img);

        //设置ListView中的Item中的TextView

        img.setImageResource(message.getImg());

        //为TextView设置监听器

        img.setOnClickListener(new OnClickListener() {


            @Override

            public void onClick(View arg0) {

                Toast.makeText(context, message.getImg(), Toast.LENGTH_LONG).show();

            }

        });

        TextView title = (TextView) arg1.findViewById(R.id.title);

        title.setText(message.getTitle());

        title.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
//                Uri uri = Uri.parse("http://www.baidu.com");
//                Intent it = new Intent(Intent.ACTION_VIEW,uri);
//                context.startActivity(it);//打开新闻页面
                context.startActivity(new Intent(context, MessageActivity.class).putExtra("url", message.getUrl()));//putExtra("url", message.getUrl()) 将MessageActivity中url 对应替换成message中的url
//                Toast.makeText(context, message.getTitle(), Toast.LENGTH_LONG).show();

            }

        });

        TextView points = (TextView) arg1.findViewById(R.id.points);

        points.setText(message.getPoints());

        points.setOnClickListener(new OnClickListener() {


            @Override

            public void onClick(View arg0) {

                Toast.makeText(context, message.getPoints(), Toast.LENGTH_LONG).show();

            }

        });

        TextView time = (TextView) arg1.findViewById(R.id.time);

        time.setText(message.getTime());

        time.setOnClickListener(new OnClickListener() {


            @Override

            public void onClick(View arg0) {

                Toast.makeText(context, message.getTime(), Toast.LENGTH_LONG).show();

            }

        });

        TextView comments = (TextView) arg1.findViewById(R.id.comments);

        comments.setText(message.getComments());

        comments.setOnClickListener(new OnClickListener() {


            @Override

            public void onClick(View arg0) {

                Toast.makeText(context, message.getComments(), Toast.LENGTH_LONG).show();

            }

        });

        return arg1;

    }


}