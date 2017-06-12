package com.wq.common.quest;


/**
 * 接口参数帮助类00
 */
public class BaseQuestConfig implements NetQuestConfig {
    public static final int PAGE_COUNT=10;
	public static final String HTTP_API = "http://wxyx.hbgeek.com";
	
	
    //登录
    public static String LOGIN_URL = HTTP_API + "/api.php/Auth/login";
    public static final int QUEST_LOGIN_CODE = 0X001;

	
    //注册
    public static String REGISTER_URL = HTTP_API + "/api.php/Auth/register";
    public static final int QUEST_REGISTER_CODE = 0X002;
	
	

    //修改普通用户登录密码
    public static String UPDATE_PASSWORD_URL = HTTP_API + "/api.php/Cube/updateUserPassword";
    public static final int QUEST_UPDATE_PASSWORD_CODE = 0X003;
	
	
    //用户添加模板
    public static String ADD_TEMPLATE_URL = HTTP_API + "/api.php/Cube/addTemplate";
    public static final int QUEST_ADD_TEMPLATE_CODE = 0X004;

    //用户删除模板
    public static String DEL_TEMPLATE_URL = HTTP_API + "/api.php/Cube/delTemplate";
    public static final int QUEST_DEL_TEMPLATE_CODE = 0X005;

    //用户编辑模板
    public static String UPDATE_TEMPLATE_URL = HTTP_API + "/api.php/Cube/updateTemplate";
    public static final int QUEST_UPDATE_TEMPLATE_CODE = 0X006;

    //获取我的模板详情数据
    public static String GET_TEMPLATE_DATA_URL = HTTP_API + "/api.php/Cube/getTemplateData";
    public static final int QUEST_GET_TEMPLATE_DATA_CODE = 0X007;

    //获取我的模板列表
    public static String GET_TEMPLATE_LIST_URL = HTTP_API + "/api.php/Cube/getTemplateList";
    public static final int QUEST_GET_TEMPLATE_LIST_CODE = 0X008;

    //获取九宫格所有模板分类id
    public static String GET_CLASS_TEMPLATE_URL = HTTP_API + "/api.php/Cube/getClassTemplate";
    public static final int QUEST_GET_CLASS_TEMPLATE_CODE = 0X009;

    //获取九宫格市场模板列表
    public static String GET_SHOP_TEMPLATE_LIST_URL = HTTP_API + "/api.php/Cube/getShopTemplateList";
    public static final int QUEST_GET_SHOP_TEMPLATE_LIST_CODE = 0X010;
	
	
	
    //获取我的模板转发提醒
    public static String GET_FORWARD_TEMPLATE_REMIND_URL = HTTP_API + "/api.php/Cube/getForwardTemplateRemind";
    public static final int QUEST_GET_FORWARD_TEMPLATE_REMIND_CODE = 0X011;

    //设置转发提醒为已读
    public static String SET_RECORD_READ_URL = HTTP_API + "/api.php/Cube/setRecordRead";
    public static final int QUEST_SET_RECORD_READ_CODE = 0X012;

    //创建九宫格团队
    public static String ADD_TEAM_URL = HTTP_API + "/api.php/Cube/addTeam";
    public static final int QUEST_ADD_TEAM_CODE = 0X013;

    //获取我的团队成员列表
    public static String GET_TEAM_MEMBER_LIST_URL = HTTP_API + "/api.php/Cube/getTeamMemberList";
    public static final int QUEST_GET_TEAM_MEMBER_LIST_CODE = 0X014;

    //获取我所在团队的九宫格模板列表 
    public static String GET_TEAM_TEMPLATE_LIST_URL = HTTP_API + "/api.php/Cube/getTeamTemplateList";
    public static final int QUEST_GET_TEAM_TEMPLATE_LIST_CODE = 0X015;

    //获取我所在团队的九宫格模板详细数据 
    public static String GET_TEAM_TEMPLATE_VIEW_URL = HTTP_API + "/api.php/Cube/getTeamTemplateView";
    public static final int QUEST_GET_TEAM_TEMPLATE_VIEW_CODE = 0X016;

    //获取我所加入的团队列表 
    public static String GET_GROUP_LIST_URL = HTTP_API + "/api.php/Cube/getGroupList";
    public static final int QUEST_GET_GROUP_LIST_CODE = 0X017;
	
    //团队创建人删除团队成员
    public static String DEL_TEAM_MEMBER_URL = HTTP_API + "/api.php/Cube/delTeamMember";
    public static final int QUEST_DEL_TEAM_MEMBER_CODE = 0X018;

    //获取充值会员选项
    public static String GET_RECHARGE_OPTION_URL = HTTP_API + "/api.php/Cube/getRechargeOption";
    public static final int QUEST_GET_RECHARGE_OPTION_CODE = 0X019;
	
    //团队成员退出当前所在团队
    public static String DEL_GROUP_URL = HTTP_API + "/api.php/Cube/delGroup";
    public static final int QUEST_DEL_GROUP_CODE = 0X020;
	
    //删除我创建的团队
    public static String SIGNOUT_TEAM_URL = HTTP_API + "/api.php/Cube/signOutTeam";
    public static final int QUEST_SIGNOUT_TEAM_CODE = 0X021;
	
    //加入九宫格团队 
    public static String JOIN_TEAM_URL = HTTP_API + "/api.php/Cube/joinTeam";
    public static final int QUEST_JOIN_TEAM_CODE = 0X022;
	
    //支付宝app客户端支付成功通知服务端
    public static String ALIPAY_APP_RETURN_DATA_URL = HTTP_API + "/api.php/Pay/aliPayAppReturnData";
    public static final int QUEST_ALIPAY_APP_RETURN_DATA_CODE = 0X023;
	
    //充值会员(支付宝支付方式)
    public static String RECHARGE_MEMBER_URL = HTTP_API + "/api.php/Cube/rechargeMember";
    public static final int QUEST_RECHARGE_MEMBER_CODE = 0X024;
	
    //获取市场模板详情数据
    public static String GET_SHOP_TEMPLATE_DATA_URL = HTTP_API + "/api.php/Cube/getShopTemplateData";
    public static final int QUEST_GET_SHOP_TEMPLATE_DATA_CODE = 0X025;



    //获取九宫格市场推荐模板列表
    public static String GET_GETSHOPRECOMMENDTEMPLATELIST_URL = HTTP_API + "/api.php/Cube/getShopRecommendTemplateList";
    public static final int QUEST_GETSHOPRECOMMENDTEMPLATELIST_CODE = 0X026;


    //获取九宫格市场热门模板列表
    public static String GET_SHOP_HOT_TEMPLATE_LIST_URL = HTTP_API + "/api.php/Cube/getShopHotTemplateList";
    public static final int QUEST_SHOP_HOT_TEMPLATE_LIST_CODE = 0X027;



    //获取九宫格市场热门模板列表
    public static String GET_MY_USE_TEMPLATE_URL = HTTP_API + "/api.php/Cube/getMyUseTemplate";
    public static final int QUEST_MY_USE_TEMPLATE_CODE = 0X028;

    //注册验证码
    public static String GET_GETVERIFICATIONCODE_URL = HTTP_API + "/api.php/Auth/getVerificationCode";
    public static final int QUEST_GETVERIFICATIONCODE_CODE = 0X029;

}



