package com.graduation.schedule.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.graduation.schedule.R;
import com.graduation.schedule.bean.Note;
import com.graduation.schedule.util.DataBaseUtil;
import com.graduation.schedule.util.GsonUtil;
import com.graduation.schedule.util.ToastUtil;

public class NoteInputActivity extends AbstractBaseActivity {

	private EditText et_title;
	private EditText et_content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note_input);
		super.setup();
		Intent intent = getIntent();
		vSave.setEnabled(false);
		et_content = (EditText) findViewById(R.id.content);
		et_title = (EditText) findViewById(R.id.title);

		if (intent.getStringExtra("note") != null) {
			Note note = GsonUtil.gson.fromJson(intent.getStringExtra("note"),
					Note.class);
			et_content.setText(note.getContent());
			et_title.setText(note.getTitle());
			vSave.setVisibility(View.GONE);

		} else {
			et_title.addTextChangedListener(mTextWatch);
			et_content.addTextChangedListener(mTextWatch);
		}
	}

	@Override
	protected void save() {
		// TODO Auto-generated method stub
		Note note = new Note();
		note.setContent(et_content.getText().toString().trim());
		note.setTitle(et_title.getText().toString().trim());
		if (DataBaseUtil.updateNote(note)) {
			DataBaseUtil.notifyNotes();
			finish();
		} else
			ToastUtil.show(getBaseContext(), "系统异常");
	}

	@Override
	protected void back() {
		// TODO Auto-generated method stub
		finish();
	}

	TextWatcher mTextWatch = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			if (et_content.getText().toString().length() > 0
					&& et_title.getText().toString().length() > 0) {
				vSave.setEnabled(true);
			} else
				vSave.setEnabled(false);
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub

		}
	};
}
