package com.flyme.setdemo;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Build;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		SetView setView = (SetView) findViewById(R.id.content);
		
		ListView leftView = (ListView) LayoutInflater.from(MainActivity.this).inflate(R.layout.leftview, null);
		leftView.setAdapter(new MyAdapter());
		
		setView.setLeftContentView(leftView);
		
		ListView rightView = (ListView) LayoutInflater.from(MainActivity.this).inflate(R.layout.rightview, null);
		rightView.setAdapter(new MyAdapter());
		setView.setRightContentView(rightView);
		
	}
	
	
	class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return 200;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView view = new TextView(MainActivity.this);
			view.setText("×ó±ßµÄ"+position);
			return view;
		}
		
	}
	
	
	

}
