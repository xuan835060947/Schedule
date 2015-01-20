package com.graduation.schedule.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ExpandableListView;

import com.graduation.schedule.R;
import com.graduation.schedule.adapter.MyExpandableListAdapter;
import com.graduation.schedule.bean.Contact;
import com.graduation.schedule.scroll_component.BaseFragment;
import com.graduation.schedule.util.DataBaseUtil;

@SuppressLint("JavascriptInterface")
public class WebFragment extends BaseFragment {

	private View view;
	private WebView webView;
	private ContactService contactService;
	private String TAG = "WebFragment";

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.index_fragment_web, null);
		contactService = new ContactService();
		webView = (WebView) view.findViewById(R.id.webview);
		webView.getSettings().setJavaScriptEnabled(true); // 允许使用javascript脚本语言
		webView.getSettings().setBuiltInZoomControls(false); // 设置可以缩放
		// 设置javaScript可用于操作MainActivity类
		webView.addJavascriptInterface(this, TAG);
		webView.loadUrl("file:///android_asset/3dchart.html");
		return view;

	}

	public String getContacts() {
		List<Contact> contacts = contactService.getContacts();
		String json = null;
		try {
			JSONArray array = new JSONArray();
			for (Contact contact : contacts) {

				JSONObject item = new JSONObject();
				item.put("name", contact.getName());
				item.put("value", contact.getValue());
				item.put("color", contact.getColor());
				array.put(item);
			}
			json = array.toString();
			Log.i(TAG, json);
			// webView.loadUrl("javascript:show('" + json + "')");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}

	class ContactService {

		public List<Contact> getContacts() {
			List<Contact> contacts = new ArrayList<Contact>();
			contacts.add(new Contact(">=90", 32.85, "#0085f2"));
			contacts.add(new Contact(">=80", 33.59, "#cbab4f"));
			contacts.add(new Contact(">=70", 22.85, "#76a871"));
			contacts.add(new Contact(">=60", 7.39, "#9f7961"));
			contacts.add(new Contact("<60", 1.63, "#a56f8f"));
			return contacts;
		}
	}
}
