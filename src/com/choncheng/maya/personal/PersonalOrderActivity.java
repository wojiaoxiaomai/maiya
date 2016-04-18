package com.choncheng.maya.personal;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.sdk.app.PayTask;
import com.choncheng.maya.BaseActivity;
import com.choncheng.maya.MyApplication;
import com.choncheng.maya.R;
import com.choncheng.maya.SetTopView;
import com.choncheng.maya.api.API;
import com.choncheng.maya.api.ResponseHandler;
import com.choncheng.maya.contants.Constants;
import com.choncheng.maya.contants.Extra;
import com.choncheng.maya.pay.PayInfo;
import com.choncheng.maya.pay.PayUtils;
import com.choncheng.maya.pay.Result;
import com.choncheng.maya.personal.PersonalOrderAdapter.OrderCallback;
import com.choncheng.maya.utils.AnimUtil;
import com.choncheng.maya.utils.AppInfoUtil;
import com.choncheng.maya.utils.OrderUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * 
 * @��Ŀ���ƣ�Maya
 * @�����ƣ�PersonalInfoActivity
 * @���������ҵ� ����
 * @�����ˣ��
 * @����ʱ�䣺2015-8-9 ����8:15:00
 * @�汾�ţ�v1.0
 * 
 */
public class PersonalOrderActivity extends BaseActivity implements
		OrderCallback, OnRefreshListener2<ListView> {
	private RadioGroup mGroup;
	private ImageView mLine;
	private int mScreenWidth;
	private float mCurrentCheckedRadioLeft;// ��ǰ��ѡ�е�RadioButton�������ľ���

	private ListView mListView;
	private List<PersonalOrder> mList;
	private PersonalOrderAdapter mAdapter;
	private View mEmptyView;// listviewΪ�յ�ʱ����ʾ
	private int orderType = Constants.ORDER_ALL;// Ĭ��ѡ�е�ȫ��

	private PullToRefreshListView mPullToRefreshListView;
	private int page = Constants.PAGE;
	private boolean mLoadOver = false;
	private static final int LOADOVER = 3;
	private static final int SDK_PAY_FLAG = 1;

	private static final int SDK_CHECK_FLAG = 2;

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				Result resultObj = new Result((String) msg.obj);
				String resultStatus = resultObj.resultStatus;

				// �ж�resultStatus Ϊ��9000�������֧���ɹ�������״̬�������ɲο��ӿ��ĵ�
				if (TextUtils.equals(resultStatus, "9000")) {
					showToast(R.string.pay_sucess);
					Intent it = new Intent(PersonalOrderActivity.this,
							PersonalOrderActivity.class);
					it.putExtra(Extra.ORDER_TYPE, Constants.ORDER_NO_GET);
					it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
							| Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(it);
					finish();

				} else {
					// �ж�resultStatus Ϊ�ǡ�9000����������֧��ʧ��
					// ��8000��
					// ����֧�������Ϊ֧������ԭ�����ϵͳԭ���ڵȴ�֧�����ȷ�ϣ����ս����Ƿ�ɹ��Է�����첽֪ͨΪ׼��С����״̬��
					if (TextUtils.equals(resultStatus, "8000")) {
						showToast(R.string.pay_suring);
					} else {
						showToast(R.string.pay_fail);
					}
				}
				break;
			}
			case SDK_CHECK_FLAG: {
				// Toast.makeText(PersonalOrderDetailActivity.this,
				// "�����Ϊ��" + msg.obj, Toast.LENGTH_SHORT).show();
				break;
			}

			case LOADOVER: {
				showToast(R.string.loading_over);
				mAdapter.notifyDataSetChanged();
				mPullToRefreshListView.onRefreshComplete();
			}

				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personal_order_activity);
		initView();
		if (mUser != null && MyApplication.getInstance().getLostTime() == 0) {
			orderLoseTime(mUser.getUcode());
		}
	}

	@Override
	protected void initView() {
		super.initView();
		new SetTopView(this, R.string.my_order, true);
		initListView();
		initRadioGroup();
	}

	private void initListView() {
		mEmptyView = findViewById(R.id.listview_empty_view);
		mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.listview);
		mPullToRefreshListView.setMode(Mode.BOTH);
		mPullToRefreshListView.setOnRefreshListener(this);
		mListView = mPullToRefreshListView.getRefreshableView();
		mList = new ArrayList<PersonalOrder>();
		mAdapter = new PersonalOrderAdapter(mList, this, this);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent it = new Intent(PersonalOrderActivity.this,
						PersonalOrderDetailActivity.class);
				it.putExtra(Extra.ORDER_ID, mList.get(position - 1)
						.getOrder_id());
				it.putExtra(Extra.ORDER_TYPE,
						OrderUtils.getOrderType(mList.get(position - 1)));
				startActivity(it);
				AnimUtil.setFromLeftToRight(PersonalOrderActivity.this);
			}
		});
	}

	/**
	 * ��ʼ�����⵼��
	 */
	private void initRadioGroup() {
		mLine = (ImageView) findViewById(R.id.line);
		mScreenWidth = AppInfoUtil.getWith(this);
		LinearLayout.LayoutParams params = new LayoutParams(mScreenWidth / 6, 4);
		mLine.setLayoutParams(params);
		if (getIntent() != null) {
			orderType = getIntent().getIntExtra(Extra.ORDER_TYPE,
					Constants.ORDER_ALL);
		}
		updateOrderListByType(orderType);
		mGroup = (RadioGroup) findViewById(R.id.group);
		mGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				int radioButtonId = group.getCheckedRadioButtonId();
				// ����ID��ȡRadioButton��ʵ��
				RadioButton rb = (RadioButton) findViewById(radioButtonId);
				AnimationSet animationSet = new AnimationSet(true);
				TranslateAnimation translateAnimation;
				translateAnimation = new TranslateAnimation(
						mCurrentCheckedRadioLeft, rb.getLeft(), 0f, 0f);
				animationSet.addAnimation(translateAnimation);
				animationSet.setFillBefore(true);
				animationSet.setFillAfter(true);
				animationSet.setDuration(300);

				mLine.startAnimation(animationSet);// ��ʼ������ɫ����ͼƬ�Ķ����л�
				mCurrentCheckedRadioLeft = rb.getLeft();// ���µ�ǰ��ɫ����������ߵľ���
				mLine.setLayoutParams(new LinearLayout.LayoutParams(rb
						.getRight() - rb.getLeft(), 4));
				page = Constants.PAGE;
				mLoadOver = false;
				mList.clear();
				switch (checkedId) {
				case R.id.btn_1:
					orderType = Constants.ORDER_ALL;
					orderlistsAll(mUser.getUcode(), page);
					break;
				case R.id.btn_2:
					orderType = Constants.ORDER_NO_PAY;
					orderlistsNoPay(mUser.getUcode(), page);
					break;

				case R.id.btn_3:
					orderType = Constants.ORDER_NO_GET;
					orderlistsNoGet(mUser.getUcode(), page);
					break;

				case R.id.btn_4:
					orderType = Constants.ORDER_NO_COMMENTS;
					orderlistsNoContents(mUser.getUcode(), page);
					break;
				case R.id.btn_5:
					orderType = Constants.ORDER_IS_OVER;
					orderlistsIsOver(mUser.getUcode(), page);
					break;
				case R.id.btn_6:
					orderType = Constants.ORDER_APPLY_ING;
					orderlistsApplying(mUser.getUcode(), page);
					break;
				default:
					break;
				}
				mAdapter.notifyDataSetChanged();
			}
		});
	};

	/**
	 * ��֧������ʧЧʱ��ӿڣ� order/lose_time
	 */
	private void orderLoseTime(String ucode) {
		API.orderLoseTime(this, ucode, new ResponseHandler() {

			@Override
			public void onRese(String data, int state) {
				if (state == STATE_OK) {
					JSONObject jsonObject = JSON.parseObject(data);
					if (jsonObject != null) {
						MyApplication.getInstance().setLostTime(
								jsonObject.getLongValue("lose_time"));
					}
				}
			}
		});
	}

	/**
	 * ����typeˢ�¶����б�
	 * 
	 * @param type
	 */
	private void updateOrderListByType(int type) {
		if (mUser == null) {
			return;
		}
		float toXtra = 0;
		switch (type) {
		case Constants.ORDER_ALL:
			((RadioButton) findViewById(R.id.btn_1)).setChecked(true);
			orderlistsAll(mUser.getUcode(), page);
			toXtra = 0;
			break;
		case Constants.ORDER_NO_PAY:
			((RadioButton) findViewById(R.id.btn_2)).setChecked(true);
			orderlistsNoPay(mUser.getUcode(), page);
			toXtra = mScreenWidth * 1 / 6;
			break;
		case Constants.ORDER_NO_GET:
			((RadioButton) findViewById(R.id.btn_3)).setChecked(true);
			orderlistsNoGet(mUser.getUcode(), page);
			toXtra = mScreenWidth * 2 / 6;
			break;
		case Constants.ORDER_NO_COMMENTS:
			((RadioButton) findViewById(R.id.btn_4)).setChecked(true);
			orderlistsNoContents(mUser.getUcode(), page);
			toXtra = mScreenWidth * 3 / 6;
			break;
		case Constants.ORDER_IS_OVER:
			((RadioButton) findViewById(R.id.btn_5)).setChecked(true);
			orderlistsIsOver(mUser.getUcode(), page);
			toXtra = mScreenWidth * 4 / 6;
			break;
		case Constants.ORDER_APPLY_ING:
			((RadioButton) findViewById(R.id.btn_6)).setChecked(true);
			orderlistsApplying(mUser.getUcode(), page);
			toXtra = mScreenWidth * 5 / 6;
			break;
		default:
			break;
		}
		mCurrentCheckedRadioLeft = toXtra;
		AnimationSet animationSet = new AnimationSet(true);
		TranslateAnimation translateAnimation;
		translateAnimation = new TranslateAnimation(0f, toXtra, 0f, 0f);
		animationSet.addAnimation(translateAnimation);
		animationSet.setFillBefore(true);
		animationSet.setFillAfter(true);
		// animationSet.setDuration(1);
		mLine.startAnimation(animationSet);// ��
	}

	/**
	 * ��ѯ���ж���
	 * 
	 * @param ucode
	 */
	private void orderlistsAll(String ucode, int page) {
		showProgressDialog(R.string.loading);
		API.orderlistsAll(this, ucode, page, new ResponseHandler() {
			@Override
			public void onRese(String data, int state) {
				dismissDialog();
				switch (state) {
				case STATE_OK:
					if (!TextUtils.isEmpty(data)) {
						List<PersonalOrder> personalOerderList = JSON
								.parseArray(data, PersonalOrder.class);
						if (personalOerderList != null) {
							mList.addAll(personalOerderList);
						}
						if (personalOerderList.size() < Constants.PAGESIZE) {
							mLoadOver = true;
						}
					} else {
						mLoadOver = true;
					}
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
				mAdapter.notifyDataSetChanged();
				updateListView();
				mPullToRefreshListView.onRefreshComplete();
			}
		});
	}

	/**
	 * ��֧��
	 * 
	 * @param ucode
	 */
	private void orderlistsNoPay(String ucode, int page) {
		showProgressDialog(R.string.loading);
		API.orderlistsNoPay(this, ucode, page, new ResponseHandler() {
			@Override
			public void onRese(String data, int state) {
				dismissDialog();
				switch (state) {
				case STATE_OK:
					if (!TextUtils.isEmpty(data)) {
						List<PersonalOrder> personalOerderList = JSON
								.parseArray(data, PersonalOrder.class);
						if (personalOerderList != null) {
							mList.addAll(personalOerderList);
						}
						if (personalOerderList.size() < Constants.PAGESIZE) {
							mLoadOver = true;
						}
					} else {
						mLoadOver = true;
					}
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
				mAdapter.notifyDataSetChanged();
				updateListView();
				mPullToRefreshListView.onRefreshComplete();
			}
		});
	}

	/**
	 * ���ջ�
	 * 
	 * @param ucode
	 */
	private void orderlistsNoGet(String ucode, int page) {
		showProgressDialog(R.string.loading);
		API.orderlistsNoGet(this, ucode, page, new ResponseHandler() {
			@Override
			public void onRese(String data, int state) {
				dismissDialog();
				switch (state) {
				case STATE_OK:
					if (!TextUtils.isEmpty(data)) {
						List<PersonalOrder> personalOerderList = JSON
								.parseArray(data, PersonalOrder.class);
						if (personalOerderList != null) {
							mList.addAll(personalOerderList);
						}
						if (personalOerderList.size() < Constants.PAGESIZE) {
							mLoadOver = true;
						}
					} else {
						mLoadOver = true;
					}
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
				mAdapter.notifyDataSetChanged();
				updateListView();
				mPullToRefreshListView.onRefreshComplete();
			}
		});
	}

	/**
	 * ������
	 * 
	 * @param ucode
	 */
	private void orderlistsNoContents(String ucode, int page) {
		showProgressDialog(R.string.loading);
		API.orderlistsNoContents(this, ucode, page, new ResponseHandler() {
			@Override
			public void onRese(String data, int state) {
				dismissDialog();
				switch (state) {
				case STATE_OK:
					if (!TextUtils.isEmpty(data)) {
						List<PersonalOrder> personalOerderList = JSON
								.parseArray(data, PersonalOrder.class);
						if (personalOerderList != null) {
							mList.addAll(personalOerderList);
						}
						if (personalOerderList.size() < Constants.PAGESIZE) {
							mLoadOver = true;
						}
					} else {
						mLoadOver = true;
					}
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
				mAdapter.notifyDataSetChanged();
				updateListView();
				mPullToRefreshListView.onRefreshComplete();
			}
		});
	}

	/**
	 * �����
	 * 
	 * @param ucode
	 */
	private void orderlistsIsOver(String ucode, int page) {
		showProgressDialog(R.string.loading);
		API.orderlistsIsOver(this, ucode, page, new ResponseHandler() {
			@Override
			public void onRese(String data, int state) {
				dismissDialog();
				switch (state) {
				case STATE_OK:
					if (!TextUtils.isEmpty(data)) {
						List<PersonalOrder> personalOerderList = JSON
								.parseArray(data, PersonalOrder.class);
						if (personalOerderList != null) {
							mList.addAll(personalOerderList);
						}
						if (personalOerderList.size() < Constants.PAGESIZE) {
							mLoadOver = true;
						}
					} else {
						mLoadOver = true;
					}
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
				mAdapter.notifyDataSetChanged();
				updateListView();
				mPullToRefreshListView.onRefreshComplete();
			}
		});
	}

	/**
	 * �˿���
	 * 
	 * @param ucode
	 */
	private void orderlistsApplying(String ucode, int page) {
		showProgressDialog(R.string.loading);
		API.orderlistsApplying(this, ucode, page, new ResponseHandler() {
			@Override
			public void onRese(String data, int state) {
				dismissDialog();
				switch (state) {
				case STATE_OK:
					if (!TextUtils.isEmpty(data)) {
						List<PersonalOrder> personalOerderList = JSON
								.parseArray(data, PersonalOrder.class);
						if (personalOerderList != null) {
							mList.addAll(personalOerderList);
						}
						if (personalOerderList.size() < Constants.PAGESIZE) {
							mLoadOver = true;
						}
					} else {
						mLoadOver = true;
					}
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
				mAdapter.notifyDataSetChanged();
				updateListView();
				mPullToRefreshListView.onRefreshComplete();
			}
		});
	}

	/**
	 * listview���£�Ϊ����ʾ�л�
	 */
	private void updateListView() {
		if (mList == null || mList.isEmpty()) {
			mEmptyView.setVisibility(View.VISIBLE);
		} else {
			mEmptyView.setVisibility(View.GONE);
		}
	}

	@Override
	public void updateOrder(int type, PersonalOrder order) {
		// �ص�ִ��
		switch (type) {
		case Constants.ORDER_NO_PAY:
			if (System.currentTimeMillis() / 1000 > MyApplication.getInstance()
					.getLostTime() + Long.parseLong(order.getCreate_time())) {
				// ˵�������Ѿ�ʧЧ��,��ִ��ȡ������
				orderstatusOrderCancel(order.getOrder_id());
			} else {
				// ������Ч������֧��
				payalipayDoEncrypt(order.getOrder_number());
			}
			break;
		case Constants.ORDER_NO_GET:
			// ȷ���ջ���֧����֧���ģ�
			ordersureSure(order.getOrder_id());
			break;

		case Constants.ORDER_APPLY_ING:
			// �����˿��У�ȡ���˿�
			orderapplyCancelOrder(order.getOrder_id());
			break;
		case Constants.ORDER_IS_OVER:
			// ��������ɣ�ɾ���ص�
			orderstatusHide(order.getOrder_id());
			break;
		default:
			break;
		}
	}

	/**
	 * ȡ������
	 * 
	 * @param ucode
	 * @param order_id������
	 * @param response
	 */
	private void orderstatusOrderCancel(String order_id) {
		showProgressDialog(R.string.loading_cancle_order);
		API.orderstatusOrderCancel(this, mUser.getUcode(), order_id,
				new ResponseHandler() {

					@Override
					public void onRese(String data, int state) {
						dismissDialog();
						switch (state) {
						case STATE_OK:
							// ����ȡ���ɹ���ˢ��ҳ��
							showToast(R.string.order_cancle_sucess);
							page = Constants.PAGE;
							mLoadOver = false;
							mList.clear();
							updateOrderListByType(orderType);
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

	/**
	 * ȷ���ջ�
	 * 
	 * @param ucode
	 * @param order_id����id
	 * @param response
	 */
	private void ordersureSure(String order_id) {
		showProgressDialog(R.string.loading);
		API.ordersureSure(this, mUser.getUcode(), order_id,
				new ResponseHandler() {

					@Override
					public void onRese(String data, int state) {
						dismissDialog();
						switch (state) {
						case STATE_OK:
							// �ջ��ɹ���ˢ��ҳ��(��ת��������)
							Intent it = new Intent(PersonalOrderActivity.this,
									PersonalOrderActivity.class);
							it.putExtra(Extra.ORDER_TYPE,
									Constants.ORDER_NO_COMMENTS);
							it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
									| Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(it);
							finish();
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

	/**
	 * ȡ�������˿�
	 * 
	 * @param ucode
	 * @param order_id����id
	 * @param response
	 */
	private void orderapplyCancelOrder(String order_id) {
		showProgressDialog(R.string.loading);
		API.orderapplyCancelOrder(this, mUser.getUcode(), order_id,
				new ResponseHandler() {

					@Override
					public void onRese(String data, int state) {
						dismissDialog();
						switch (state) {
						case STATE_OK:
							// ȡ�������˿� ���سɹ�
							showToast(R.string.order_applay_cancle_sucess);
							Intent it = new Intent(PersonalOrderActivity.this,
									PersonalOrderActivity.class);
							it.putExtra(Extra.ORDER_TYPE,
									Constants.ORDER_NO_GET);
							it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
									| Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(it);
							finish();
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

	/**
	 * ɾ������
	 * 
	 * @param ucode
	 *            �û�У����
	 * @param order_id
	 *            ����id
	 * @param response
	 */
	private void orderstatusHide(String order_id) {
		showProgressDialog(R.string.loading);
		API.orderstatusHide(this, mUser.getUcode(), order_id,
				new ResponseHandler() {

					@Override
					public void onRese(String data, int state) {
						dismissDialog();
						switch (state) {
						case STATE_OK:
							// ɾ�����������سɹ�
							showToast(R.string.order_del_sucess);
							page = Constants.PAGE;
							mLoadOver = false;
							mList.clear();
							updateOrderListByType(orderType);
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

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		String lable = DateUtils.formatDateTime(getApplicationContext(),
				System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
						| DateUtils.FORMAT_SHOW_DATE
						| DateUtils.FORMAT_ABBREV_ALL);
		refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(lable);
		page = Constants.PAGE;
		mLoadOver = false;
		mList.clear();
		updateOrderListByType(orderType);
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		String lable = DateUtils.formatDateTime(getApplicationContext(),
				System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
						| DateUtils.FORMAT_SHOW_DATE
						| DateUtils.FORMAT_ABBREV_ALL);
		refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(lable);

		if (!mLoadOver) {
			page++;
			updateOrderListByType(orderType);
		} else {
			loadOver();
		}

	}

	/*
	 * ���ظ������ʱ����
	 */
	private void loadOver() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(Constants.LOADING_OVER_SLEEP);
					mHandler.sendEmptyMessage(LOADOVER);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * ֧��������(����֧��������Ϣ)
	 * 
	 * @param context
	 * @param ucode�û�У����
	 * @param order_number
	 *            ������
	 * @param response
	 */
	private void payalipayDoEncrypt(String order_number) {
		showProgressDialog(R.string.loading);
		API.payalipayDoEncrypt(this, mUser.getUcode(), order_number,
				new ResponseHandler() {

					@Override
					public void onRese(String data, int state) {
						dismissDialog();
						switch (state) {
						case STATE_OK:
							final PayInfo payInfo = JSON.parseObject(data,
									PayInfo.class);
							if (payInfo != null) {
								// ��ʼ����֧��sdk����֧��
								Runnable payRunnable = new Runnable() {

									@Override
									public void run() {
										// ����PayTask ����
										PayTask alipay = new PayTask(
												PersonalOrderActivity.this);
										// ����֧���ӿ�
										String result = alipay.pay(PayUtils
												.getPayInfo(payInfo));
										Message msg = new Message();
										msg.what = SDK_PAY_FLAG;
										msg.obj = result;
										mHandler.sendMessage(msg);
									}
								};

								Thread payThread = new Thread(payRunnable);
								payThread.start();
							} else {
								showToast(R.string.pay_submit_order_fail);
							}
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
