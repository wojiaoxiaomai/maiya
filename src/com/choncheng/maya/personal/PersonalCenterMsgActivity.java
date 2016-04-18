package com.choncheng.maya.personal;

import java.util.ArrayList;
import java.util.List;

import org.simple.eventbus.Subscriber;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.choncheng.maya.BaseActivity;
import com.choncheng.maya.R;
import com.choncheng.maya.SetTopView;
import com.choncheng.maya.api.API;
import com.choncheng.maya.api.ResponseHandler;
import com.choncheng.maya.db.MessageDBHelper;
import com.choncheng.maya.utils.LocalSettings;
import com.choncheng.maya.utils.RandomNumber;

/**
 * 
 * @��Ŀ���ƣ�Maya
 * @�����ƣ�PersonalCenterMsgActivity
 * @����������Ϣ����
 * @�����ˣ��
 * @����ʱ�䣺2015-8-7 ����10:38:31
 * @�汾�ţ�v1.0
 * 
 */
public class PersonalCenterMsgActivity extends BaseActivity {
	private Button mSendBtn;// ���Ͱ�ť
	private EditText mContentEdit;
	private ListView mListView;
	private PersonalCenterMsgAdapter mAdapter;
	private List<PersonalCenterMsg> mList;
	private TextView mClearTxt;// ��������

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personal_msg_activity);
		initView();
		if (mUser != null) {
			leavemsgMsg(mUser.getUcode(), "");
		}
	}

	@Override
	protected void initView() {
		super.initView();
		new SetTopView(this, R.string.person_center_msg, true);
		mClearTxt = (TextView) findViewById(R.id.clear_txt);
		mClearTxt.setOnClickListener(this);
		mSendBtn = (Button) findViewById(R.id.send_btn);
		mSendBtn.setOnClickListener(this);
		mContentEdit = (EditText) findViewById(R.id.conent_edit);
		mListView = (ListView) findViewById(R.id.listview);
		mList = new ArrayList<PersonalCenterMsg>();
		mAdapter = new PersonalCenterMsgAdapter(mList, this);
		mListView.setAdapter(mAdapter);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		if (v.getId() == R.id.send_btn) {
			String content = mContentEdit.getText().toString().trim();
			if (TextUtils.isEmpty(content)) {
				showToast(R.string.send_msg_empty);
				return;
			}
			if (mUser != null) {
				leavemsgSendMsg(mUser.getUcode(), content);
			}
		} else if (v.getId() == R.id.clear_txt) {
			// ��������
			MessageDBHelper dbHelper = new MessageDBHelper(
					PersonalCenterMsgActivity.this);
			dbHelper.deleteMessageList();
			mList.clear();
			mAdapter.notifyDataSetChanged();
		}
	}

	/**
	 * ��Ϣ�б�
	 * 
	 * @param ucode
	 *            �û�У����
	 * @param shop_id�̼�id
	 *            (�Ǳش�)
	 * @param response
	 */
	private void leavemsgMsg(String ucode, String shop_id) {
		API.leavemsgMsg(this, ucode, shop_id, new ResponseHandler() {

			@Override
			public void onRese(String data, int state) {
				if (state == STATE_OK) {
					// ��ȡ����Ϣ�б�
					List<PersonalCenterMsg> msgList = JSON.parseArray(data,
							PersonalCenterMsg.class);

					if (msgList != null) {
						for (int i = 0; i < msgList.size(); i++) {
							PersonalCenterMsg msg = msgList.get(i);
							// 1���û������̼ҵ� 2���̼ҷ����û���
							msg.setU_to_s("2");
						}

						if (!msgList.isEmpty()) {
							LocalSettings.putBoolean(LocalSettings.KEY_NEW_MSG,
									false);
							MessageDBHelper dbHelper = new MessageDBHelper(
									PersonalCenterMsgActivity.this);
							dbHelper.saveMessage(msgList);
						}
					}
				}

				// �����ݿ��л�ȡ�����¼����ʾ
				MessageDBHelper dbHelper = new MessageDBHelper(
						PersonalCenterMsgActivity.this);
				List<PersonalCenterMsg> msgList = dbHelper.getMessageList();
				if (msgList != null) {
					mList.addAll(msgList);
				}

				for (int i = 0; i < mList.size(); i++) {
					PersonalCenterMsg msg = mList.get(i);
				}
				mAdapter.notifyDataSetChanged();
			}
		});
	}

	/**
	 * ������Ϣ
	 * 
	 * @param ucode
	 * @param content
	 *            ����
	 * @param response
	 */
	private void leavemsgSendMsg(String ucode, final String content) {
		showProgressDialog(R.string.loading_send_msg);
		API.leavemsgSendMsg(this, ucode, content, new ResponseHandler() {

			@Override
			public void onRese(String data, int state) {
				dismissDialog();
				switch (state) {
				case STATE_OK:
					// ���ͳɹ����������ݵ�����

					PersonalCenterMsg msg = new PersonalCenterMsg();
					msg.setContent(content);
					msg.setId(RandomNumber.get6RandomNumber() + "");
					msg.setCreate_time(System.currentTimeMillis() / 1000 + "");
					msg.setU_to_s("1");
					msg.setHead_img(mUser.getHead_img());
					MessageDBHelper dbHelper = new MessageDBHelper(
							PersonalCenterMsgActivity.this);
					dbHelper.saveMessage(msg);
					mList.add(msg);
					mAdapter.notifyDataSetChanged();
					mContentEdit.setText("");
					mListView.setSelection(mListView.getCount() - 1);
					break;
				case STATE_FAIL:
					showToast(data);
					break;

				case STATE_ERROR:
					showToast(R.string.http_respone_error);
					break;
				default:
					break;
				}
			}
		});
	}
	 
}
