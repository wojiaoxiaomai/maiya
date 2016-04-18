package com.choncheng.maya.contants;

/**
 * 
 * @项目名称：Maya
 * @类名称：Constants
 * @类描述： 常量
 * @创建人：李波
 * @创建时间：2015-8-8 上午8:29:29
 * @版本号：v1.0
 * 
 */
public class Constants {

	// 客户电话
	public static final String CALL_NUMBER = "028-86523627";
	// 域名地址
	public static final String SERVER = "http://maiya.zgcom.cn";

	// api地址
	public static final String SERVER_API = SERVER + "/api.php/";

	// apk下载地址
	public static final String DOWNLOAD_APK_URL = Constants.SERVER_API
			+ "appversion/download_new?device_type=1&user_type=1";
	// 验证码超时时间60s
	public static final long CODE_TIME_OUT = 60000;
	/*
	 * banner图片轮训间隔时间
	 */
	public static final int BANNER_TIME = 4000;
	public static final String FILE_START = "file://";

	/**
	 * 从第几页开始
	 */
	public static final int PAGE = 1;
	/**
	 * 分页每一页好多条
	 */
	public static final int PAGESIZE = 10;
	public static final int LOADING_OVER_SLEEP = 400;
	// 派送的方式 1:定时派送 （有个时间段） 2： 及时派送 3:上门自取
	public static final int DELIVERY_TYPE_1 = 1;
	public static final int DELIVERY_TYPE_2 = 2;
	public static final int DELIVERY_TYPE_3 = 3;

	// 支付方式： 1 : 在线支付 2： 货到付款
	public static final int PAY_WAY_1 = 1;
	public static final int PAY_WAY_2 = 2;
	// 支付状态： 1： 未支付 2： 付款中 3： 已支付
	public static final int PAY_STATUS_1 = 1;
	public static final int PAY_STATUS_2 = 2;
	public static final int PAY_STATUS_3 = 3;
	// 订单是否已经完成1：完成 2： 未完成
	public static final int IS_OVER_1 = 1;
	public static final int IS_OVER_2 = 2;

	// 1: 未发货 2：发货中 3：已收货
	public static final int HAV_STATUS_1 = 1;
	public static final int HAV_STATUS_2 = 2;
	public static final int HAV_STATUS_3 = 3;
	// 订单评论状态 1评论 2：未评论
	public static final int IS_CONTENTS_1 = 1;
	public static final int IS_CONTENTS_2 = 2;
	// apply_status 1:无申请状态 2：申请退款 3:退款成功 4：退款失败
	public static final int APPLY_STATUS_1 = 1;
	public static final int APPLY_STATUS_2 = 2;
	public static final int APPLY_STATUS_3 = 3;
	public static final int APPLY_STATUS_4 = 4;
	// 我的订单的状态
	public static final int ORDER_ALL = 0;// 全部
	public static final int ORDER_NO_PAY = 1;// 待付款
	public static final int ORDER_NO_GET = 2;// 待收货
	public static final int ORDER_NO_COMMENTS = 3;// 待评价
	public static final int ORDER_IS_OVER = 4;
	public static final int ORDER_APPLY_ING = 5;
	// 发送更新购物车广播
	public static final String ACTION_UPDATE_SHOPCART_NUMBER = "action_update_shopcart_number";
	public static final String SP_NAME = "sp_name";

	// 为了区分搜索中的分类，标识 1为通过关键字搜索得到，2为分类列表中点击的2级获得，3为通过点击3级获得
	public static final int SEARCH_TYPE_KEY = 1;// 关键字搜索
	public static final int SEARCH_TYPE_SECOND = 2;// 二级目录
	public static final int SEARCH_TYPE_THIRD = 3;// 三级目录
}
