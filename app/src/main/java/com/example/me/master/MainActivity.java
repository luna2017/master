package com.example.me.master;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.me.master.adapter.MyAdapter;
import com.example.me.master.domain.Message;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//jsoup


public class MainActivity extends Activity {
    private static final String[] strs2 = new String[30];
    private static final String[] link = new String[30];
    //定义一个String数组用来显示ListView的内容private ListView lv;/** Called when the activity is first created. */
    private ListView listView = null;
    private static final int DIALOG_KEY = 0;
    private ProgressAsyncTask myTask;
    private List<Map<String, String>> messageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //进入页面 加载新闻信息
        myTask = new ProgressAsyncTask();
        myTask.execute();
        //点击“更新”按钮时 重新加载新闻信息
        Button sync = (Button) findViewById(R.id.id_btn_sync);
        sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myTask = new ProgressAsyncTask();
                myTask.execute();
            }
        });
    }

    // 弹出"查看"对话框
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_KEY: {
                ProgressDialog dialog = new ProgressDialog(this);
                dialog.setMessage("获取数据中  请稍候...");
                dialog.setIndeterminate(true);
                dialog.setCancelable(true);
                return dialog;
            }
        }
        return null;
    }


    class ProgressAsyncTask extends AsyncTask<Integer, Integer, String> {

        ProgressDialog bar;

        public ProgressAsyncTask() {
            super();

        }

        /**
         * 这里的Integer参数对应AsyncTask中的第一个参数 这里的String返回值对应AsyncTask的第三个参数
         * 该方法并不运行在UI线程当中，主要用于异步操作，所有在该方法中不能对UI当中的空间进行设置和修改
         * 但是可以调用publish Progress方法触发onProgressUpdate对UI进行操作
         */
        @Override
        protected String doInBackground(Integer... params) {
            try {
                Document doc = Jsoup.connect("https://news.ycombinator.com/newest").get();
                Elements athings = doc.select("tr.athing");
                Elements subtexts = doc.select("td.subtext");
                int index = 0;
                for (Element athing : athings) {
                    Map<String, String> messageMap = new HashMap<>();
                    messageMap.put("rank", athing.getElementsByClass("rank").text());//序号
                    Element title = athing.select("td.title a").first();
                    messageMap.put("title", title.text());//新闻标题
                    messageMap.put("title_href", title.attr("abs:href"));//新闻链接
                    if (athing.select("td.title a").size()>=2) {
                        Element sitestr = athing.select("td.title a").get(1);
                        messageMap.put("sitestr", sitestr.text());//发表网站
                        messageMap.put("sitestr_href", sitestr.attr("abs:href")); //发表网站链接
                    }

                    Element subtext = subtexts.get(index);
                    if (null != subtext) {
                        Element score = subtext.select("span.score").first();
                        messageMap.put("points", score.text());//point数
                        Element user = subtext.select("a").first();
                        messageMap.put("user", user.text());//用户
                        messageMap.put("user_href", user.attr("abs:href"));//用户链接
                        Element times = subtext.select("a").get(1);
                        messageMap.put("times", times.text());//发表时间
                        Element comments = subtext.select("a").last();
                        messageMap.put("comments", comments.text());//评论数
                    }
                    index++;
                    messageList.add(messageMap);
                }

            } catch (IOException e) {
                Toast.makeText(MainActivity.this, "" + "检查网络", Toast.LENGTH_SHORT).show();
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        /**
         * 这里的String参数对应AsyncTask中的第三个参数（也就是接收doInBackground的返回值）
         * 在doInBackground方法执行结束之后在运行，并且运行在UI线程当中 可以对UI空间进行设置
         */
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            bar.dismiss();
            // 获得ListView句柄
            listView = (ListView) findViewById(R.id.listView);
            final MyAdapter mgs = new MyAdapter(getListData(), R.layout.item, MainActivity.this);

            listView.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    //int position =Integer.parseInt(arg1.getTag().toString());
                    mgs.getView(arg2, arg1, listView);
                }

            });

            listView.setAdapter(mgs);
        }

        /*ListView数据*/
        private List<Message> getListData() {
            List<Message> list = new ArrayList<Message>();
            for (Map<String, String> messageMap : messageList) {
                Message message = new Message(R.drawable.icon, messageMap.get("rank")+messageMap.get("title"),messageMap.get("points") +" by "+messageMap.get("user"),messageMap.get("times"), messageMap.get("comments"), messageMap.get("title_href"));
                list.add(message);
            }

            return list;
        }

        // 该方法运行在UI线程当中,并且运行在UI线程当中 可以对UI空间进行设置
        @Override
        protected void onPreExecute() {
            bar = new ProgressDialog(MainActivity.this);
            bar.setMessage("正在加载数据····");
            bar.setIndeterminate(false);
            bar.setCancelable(false);
            bar.show();
        }

        /**
         * 这里的Intege参数对应AsyncTask中的第二个参数
         * 在doInBackground方法当中，，每次调用publishProgress方法都会触发onProgressUpdate执行
         * onProgressUpdate是在UI线程中执行，所有可以对UI空间进行操作
         */
        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.d("onProgressUpdate", "abc");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //menu操作控制
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (item.getItemId()) {
            case R.id.action_about:
                Toast.makeText(MainActivity.this, "" + "关于", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_settings:

                Toast.makeText(MainActivity.this, "" + "设置", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_quit:

                Toast.makeText(MainActivity.this, "" + "退出", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //格式化输出  当标题长度超过width时 截取前width个字
    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width - 1) + "..";
        else
            return s;
    }
}
