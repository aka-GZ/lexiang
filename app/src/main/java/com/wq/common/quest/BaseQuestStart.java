package com.wq.common.quest;

import android.widget.EditText;

import com.google.gson.reflect.TypeToken;
import com.sunrun.sunrunframwork.http.NAction;
import com.sunrun.sunrunframwork.http.NetRequestHandler;
import com.sunrun.sunrunframwork.utils.Utils;
import com.wq.common.model.ClassTemplateBean;
import com.wq.common.model.GroupListObj;
import com.wq.common.model.HomeTemplateBean;
import com.wq.common.model.LoginInfo;
import com.wq.common.model.MyTemplateBean;
import com.wq.common.model.ShopHotTemplateBean;
import com.wq.common.model.TeamTemplateListObj;
import com.wq.common.model.TemplateDataObj;
import com.wq.common.model.TemplateStatisticsObj;
import com.wq.common.model.ToBeForwardedObj;
import com.wq.common.model.UseHistoryListBean;
import com.wq.login.RegisterActivity;
import com.wq.mine.mode.VipInfo;
import com.wq.template.mode.PeopleEntity;
import com.wq.vip.mode.RechargeOption;

import java.io.File;
import java.util.List;

/**
 * 接口请求调用帮助类
 */
public class BaseQuestStart extends BaseQuestConfig {


    /**
     * 前台用户登录
     * API参数传入方式: POST
     * 传入JSON格式: {"phone_no":"15916035572","password":"25d55ad283aa400af464c76d713c07ad"}
     * 返回JSON格式: {"meta":{"code":200,"message":"u6210u529f"},"body":{"user_money":"0.00","phone_no":"15916035572","email":null,"login_time":"2017-05-25 15:57:51","token":"be1c5c3fdedd1d3b0f692db5555f4dd5"}}
     *
     * @param phone_no 用户手机号码(账号)
     * @param password 用户登录密码md5值
     * @return code 200成功 3001账号或密码错误
     * 18316315572  12345678
     */
    public static void login(NetRequestHandler netRequestHandler, String phone_no, String password,String registration_id) {

        netRequestHandler.requestAsynPost(new NAction()
                .setUrl(BaseQuestConfig.LOGIN_URL)
                .put("phone_no", phone_no)
                .put("password", Utils.getMD5(password) )
                .put("registration_id", (registration_id) )
                .setTypeToken(LoginInfo.class)//指定解析类型,该程序里面对应body 里面的json内容
                .setRequestCode(BaseQuestConfig.QUEST_LOGIN_CODE));
    }

    /**
     * 用户添加模板
     * API参数传入方式: POST
     * 传入JSON格式: {"template_name":"测试","template_content":"测试内容","template_cover_img":"base64code...","img_list":["base64code...","base64code..."],"remind_id_list":["uid1","uid2"],"is_open":"-1"}
     * 返回JSON格式: {"meta":{"code":200,"message":"success"}}
     *
     * @param template_name->九宫格模板名称标题
     * @param template_content->模板内容
     * @param template_cover_img->模板封面图片base64格式
     * @param img_list->图片list                   最少1张 最多9张 数据为base64格式
     * @param forward_expiration_date 转发截止时间 (有指定提醒人时此参数必传)
     * @param group_id 团队id (可选传有指定提醒团队成员的时候必传)
     * @param remind_id_list->提醒指定团队成员id         list
     * @param is_open->模板是否公开                    -1不公开 1公开 (可选传)
     * @return code 200->成功 3001->template_name参数为空 3002->template_content参数为空 3005->template_cover_img参数为空  3003->img_list图片至少1张最多9张 3004->img_list包含不合法文件 3006->template_cover_img包含不合法文件 3007->数据插入失败
     */
    public static void addTemplate(NetRequestHandler netRequestHandler, Object template_name, Object template_content, String template_cover_img, List<String> img_list,String forward_expiration_date,String group_id, List<String> remind_id_list, String is_open) {

        netRequestHandler.requestAsynPost(new NAction()
                .setUrl(BaseQuestConfig.ADD_TEMPLATE_URL)
//                        .setUrl("http://192.168.1.109/naoke/Api/User/index")
                .put("template_name", template_name)
                .put("template_content", template_content)
                .put("template_cover_img", template_cover_img)
//                .put("template_cover_img", "11")
                .put("img_list", img_list)
                .put("forward_expiration_date", forward_expiration_date)
                .put("group_id", group_id)
                .put("remind_id_list", remind_id_list)
                .put("is_open", is_open)
                // .setTypeToken(LoginInfo.class)//指定解析类型,该程序里面对应body 里面的json内容
                .setRequestCode(BaseQuestConfig.QUEST_ADD_TEMPLATE_CODE));
    }

    /**
     * 用户删除模板
     * API参数传入方式: POST
     * 传入JSON格式: {"template_list":["1","2","3"]}
     * 返回JSON格式: {"meta":{"code":200,"message":"success"}}
     *
     * @param uid->用户uid
     * @param template_list->要删除的模板id list
     * @return code 200->成功 3001->template_list参数为空
     */
    public static void delTemplate(NetRequestHandler netRequestHandler, String uid, List<String> template_list) {

        netRequestHandler.requestAsynPost(new NAction()
                .setUrl(BaseQuestConfig.DEL_TEMPLATE_URL)
                .put("uid", uid)
                .put("template_list", template_list)
                // .setTypeToken(LoginInfo.class)//指定解析类型,该程序里面对应body 里面的json内容
                .setRequestCode(BaseQuestConfig.QUEST_DEL_TEMPLATE_CODE));
    }

    /**
     * 用户编辑模板
     * API参数传入方式: POST
     * 传入JSON格式: {"template_id":"1","template_name":"测试","template_content":"测试内容","template_cover_img":"base64code...","img_list":{"imgid1":"base64code...","imgid2":"base64code..."},"remind_id_list":["uid1","uid2"],"is_open":"-1"}
     * 返回JSON格式: {"meta":{"code":200,"message":"success"}}
     *
     * @param template_id->模板id
     * @param template_name->九宫格模板名称标题           (可选)
     * @param template_content->模板内容             (可选)
     * @param is_open->模板是否公开                    -1不公开 1公开 (可选)
     * @param template_cover_img->模板封面图片base64格式 (可选)
     * @param img_list->图片list                   最少1张 最多9张 数据为imgid:base64格式 (可选)
     * @param remind_id_list->提醒指定团队成员id         list (可选)
     * @return code 200->成功 3001->template_id参数为空 3002->模板数据不存在 3003->img_list图片至少1张最多9张 3004->template_cover_img包含不合法文件 3005->数据更新失败
     */
    public static void updateTemplate(NetRequestHandler netRequestHandler, String template_id, String template_name, String template_content, String is_open,
                                      String template_cover_img, List<String> img_list, List<String> remind_id_list) {

        netRequestHandler.requestAsynPost(new NAction()
                .setUrl(BaseQuestConfig.UPDATE_TEMPLATE_URL)
                .put("template_id", template_id)
                .put("template_name", template_name)
                .put("template_content", template_content)
                .put("is_open", is_open)
                .put("template_cover_img", template_cover_img)
                .put("img_list", img_list)
                .put("remind_id_list", remind_id_list)
                // .setTypeToken(LoginInfo.class)//指定解析类型,该程序里面对应body 里面的json内容
                .setRequestCode(BaseQuestConfig.QUEST_UPDATE_TEMPLATE_CODE));
    }

    /**
     * 获取我的模板详情数据
     * API参数传入方式: POST
     * 传入JSON格式: {"template_id":"1"}
     * 返回JSON格式: {"meta":{"code":200,"message":"success"},"body":{"template_name":"u6d4bu8bd5777","template_content":"u6d4bu8bd5u518522u5bb9","template_add_time":"2017-05-26 05:38:51","is_member_free":"-1","template_cover_img_url":"http://localhost:81/Public/Uploads/Model/Cube/2017-05-26/7sjhu0tyhGYkFjnM.jpeg","is_open":"-1","template_id":"3","imgList":[{"template_img_id":"5","img_url":"http://localhost:81/Public/Uploads/Model/Cube/2017-05-26/MugJv16lz0U87zGD.jpeg","img_order":"0"},{"template_img_id":"6","img_url":"http://localhost:81/Public/Uploads/Model/Cube/2017-05-26/pcSC0o140M773qa3.jpeg","img_order":"1"}],"remindPeopleList":["42","45"]}}
     *
     * @param template_id 模板id
     * @return remindPeopleList->九宫格提醒人uid list
     */
    public static void getTemplateData(NetRequestHandler netRequestHandler, String template_id) {

        netRequestHandler.requestAsynPost(new NAction()
                .setUrl(BaseQuestConfig.GET_TEMPLATE_DATA_URL)
                .put("template_id", template_id)
                 .setTypeToken(TemplateDataObj.class)//指定解析类型,该程序里面对应body 里面的json内容
                .setRequestCode(BaseQuestConfig.QUEST_GET_TEMPLATE_DATA_CODE));
    }

    /**
     * 获取我的模板列表
     * API参数传入方式: POST
     * 传入JSON格式: {"firstRow":"0","listRows":"20","searchText":"模板名称","class_id":"12"}
     * 返回JSON格式: {"meta":{"code":200,"message":"success"},"body":[{"template_id":"3","template_name":"u6d4bu8bd5777","use_num":"0","template_add_time":"2017-05-26 05:38:51","template_cover_img_url":"http://localhost:81/Public/Uploads/Model/Cube/2017-05-26/7sjhu0tyhGYkFjnM.jpeg","forwardingtimes":"0"},{"template_id":"4","template_name":"u6d4bu8bd5","use_num":"0","template_add_time":"2017-05-27 09:15:23","template_cover_img_url":"http://localhost:81/Public/Uploads/Model/Cube/2017-05-27/DCb7yuxnR0di42Ol.jpeg","forwardingtimes":"0","class_id":"1"}]}
     *
     * @param firstRow->开始记录数
     * @param searchText->模板名称 (可选传)
     * @param class_id->模板分类id (可选传)
     * @return class_id->模板分类ID
     */
    public static void getTemplateList(NetRequestHandler netRequestHandler, int firstRow, String searchText, String class_id) {

        netRequestHandler.requestAsynPost(new NAction()
                .setUrl(BaseQuestConfig.GET_TEMPLATE_LIST_URL)
                .put("firstRow", firstRow)
                .put("listRows", PAGE_COUNT)
                .put("searchText", searchText)
                .put("class_id", class_id)
                 .setTypeToken(new TypeToken<List<MyTemplateBean>>(){})//指定解析类型,该程序里面对应body 里面的json内容
                .setRequestCode(BaseQuestConfig.QUEST_GET_TEMPLATE_LIST_CODE));
    }

    /**
     * 获取九宫格所有模板分类id
     *
     * @return class_name 分类名称
     */
    public static void getClassTemplate(NetRequestHandler netRequestHandler) {

        netRequestHandler.requestAsynGet(new NAction()
                .setUrl(BaseQuestConfig.GET_CLASS_TEMPLATE_URL)
                 .setTypeToken(new TypeToken<List<ClassTemplateBean>>(){})//指定解析类型,该程序里面对应body 里面的json内容
                .setRequestCode(BaseQuestConfig.QUEST_GET_CLASS_TEMPLATE_CODE));
    }




    /**
     * 获取我的模板转发提醒
     * API参数传入方式: GET
     * 传入JSON格式: 无
     * 返回JSON格式: {"meta":{"code":200,"message":"success"}}
     * API_URL_本地: http://localhost:81/api.php/Cube/getForwardTemplateRemind
     * API_URL_服务器: http://wxyx.lyfz.net/api.php/Cube/getForwardTemplateRemind
     * @return code 200->成功
     * @return remind_id->消息提醒ID
     * @return template_id->模板ID
     * @return add_time->消息发出时间
     * @return template_name->模板名称
     */
    public static void getForwardTemplateRemind(NetRequestHandler netRequestHandler) {

        netRequestHandler.requestAsynPost(new NAction()
                .setUrl(BaseQuestConfig.GET_FORWARD_TEMPLATE_REMIND_URL)
                .setTypeToken(new TypeToken<List<ToBeForwardedObj>>(){})//指定解析类型,该程序里面对应body 里面的json内容
                .setRequestCode(BaseQuestConfig.QUEST_GET_FORWARD_TEMPLATE_REMIND_CODE));
    }


    /**
     * 设置转发提醒为已读
     * API参数传入方式: POST
     * 传入JSON格式: {"remind_id":"1"}
     * 返回JSON格式: {"meta":{"code":200,"message":"success"}}
     *
     * @param uid->用户uid
     * @param remind_id->提醒记录id
     * @return code 200->成功 3001更新数据失败
     */
    public static void setRecordRead(NetRequestHandler netRequestHandler, String uid, String remind_id) {

        netRequestHandler.requestAsynPost(new NAction()
                .setUrl(BaseQuestConfig.SET_RECORD_READ_URL)
                .put("uid", uid)
                .put("remind_id", remind_id)
                // .setTypeToken(LoginInfo.class)//指定解析类型,该程序里面对应body 里面的json内容
                .setRequestCode(BaseQuestConfig.QUEST_SET_RECORD_READ_CODE));
    }

    /**
     * 创建九宫格团队
     * API参数传入方式: POST
     * 传入JSON格式: {"group_name":"团队名称"}
     * 返回JSON格式: {"meta":{"code":200,"message":"success"},"body":{"group_number":"53377833","group_id":"3"}}
     *
     * @param group_name 团队名称
     * @return group_id->团队群ID
     */
    public static void addTeam(NetRequestHandler netRequestHandler, String group_name) {

        netRequestHandler.requestAsynPost(new NAction()
                .setUrl(BaseQuestConfig.ADD_TEAM_URL)
                .put("group_name", group_name)
                // .setTypeToken(LoginInfo.class)//指定解析类型,该程序里面对应body 里面的json内容
                .setRequestCode(BaseQuestConfig.QUEST_ADD_TEAM_CODE));
    }

    /**
     * 获取我的团队成员列表
     * API参数传入方式: POST
     * 传入JSON格式: {"group_id":"1","firstRow":"0","listRows":"10"}
     * 返回JSON格式: {"meta":{"code":200,"message":"success"},"body":[{"phone_no":"15916035572","user_name":"张三","uid":"43"}]}
     *
     * @param group_id 团队ID
     * @param firstRow 开始记录数
     * @return user_name 成员姓名
     */
    public static void getTeamMemberList(NetRequestHandler netRequestHandler, String group_id, int firstRow) {

        netRequestHandler.requestAsynPost(new NAction()
                .setUrl(BaseQuestConfig.GET_TEAM_MEMBER_LIST_URL)
                .put("group_id", group_id)
                .put("firstRow", firstRow)
                .put("listRows", 1000)
                 .setTypeToken(new TypeToken<List<PeopleEntity>>(){})//指定解析类型,该程序里面对应body 里面的json内容
                .setRequestCode(BaseQuestConfig.QUEST_GET_TEAM_MEMBER_LIST_CODE));
    }


    /**
     * 获取我所在团队的九宫格模板列表
     * API参数传入方式: POST
     * 传入JSON格式: {"group_id":"1","template_id":"5"}
     * 返回JSON格式: {"meta":{"code":200,"message":"success"},"body":[{"template_id":"3","template_name":"u6d4bu8bd5777","class_id":"-1","template_cover_img_url":"http://localhost:81/Public/Uploads/Model/Cube/2017-05-26/7sjhu0tyhGYkFjnM.jpeg","template_add_time":"2017-05-26 05:38:51","uid":"43"}]}
     *
     * @param group_id 团队ID
     * @param firstRow 开始记录数
     * @param listRows 每页显示数量
     * @return founder_forwarding_times 模板创建人转发次数
     */
    public static void getTeamTemplateList(NetRequestHandler netRequestHandler, String group_id, String firstRow, String listRows) {

        netRequestHandler.requestAsynPost(new NAction()
                .setUrl(BaseQuestConfig.GET_TEAM_TEMPLATE_LIST_URL)
                .put("group_id", group_id)
                .put("firstRow", firstRow)
                .put("listRows", listRows)
                .setTypeToken(new TypeToken<List<TeamTemplateListObj>>() {
                })//指定解析类型,该程序里面对应body 里面的json内容
                .setRequestCode(BaseQuestConfig.QUEST_GET_TEAM_TEMPLATE_LIST_CODE));
    }


    /**
     * 获取我所在团队的九宫格模板详细数据
     * API参数传入方式: POST
     * 传入JSON格式: {"group_id":"1","template_id":"5"}
     * 返回JSON格式: {"meta":{"code":200,"message":"success"},"body":[{"template_id":"3","template_name":"u6d4bu8bd5777","class_id":"-1","template_cover_img_url":"http://localhost:81/Public/Uploads/Model/Cube/2017-05-26/7sjhu0tyhGYkFjnM.jpeg","template_add_time":"2017-05-26 05:38:51","uid":"43"}]}
     * API_URL_本地: http://localhost:81/api.php/Cube/getTeamTemplateView
     * API_URL_服务器: http://fx.lyfz.net/wxyx/api.php/Cube/getTeamTemplateView
     *
     * @param group_id    团队ID
     * @param template_id 模板id
     * @return imgList->九宫格模板图片路径list
     */
    public static void getTeamTemplateView(NetRequestHandler netRequestHandler, String group_id, String template_id) {

        netRequestHandler.requestAsynPost(new NAction()
                .setUrl(BaseQuestConfig.GET_TEAM_TEMPLATE_VIEW_URL)
                .put("group_id", group_id)
                .put("template_id", template_id)
                 .setTypeToken(TemplateDataObj.class)//指定解析类型,该程序里面对应body 里面的json内容
                .setRequestCode(BaseQuestConfig.QUEST_GET_TEAM_TEMPLATE_VIEW_CODE));
    }


    /**
     * 获取我所加入的团队列表
     * API参数传入方式: POST
     * 传入JSON格式: {"firstRow":"0","listRows":"10"}
     * 返回JSON格式: {"meta":{"code":200,"message":"success"},"body":{"group_number":"53377833","group_id":"3"}}
     *
     * @param firstRow 开始记录数
     * @param listRows 每页显示数量
     * @return group_id 团队ID
     */
    public static void getGroupList(NetRequestHandler netRequestHandler, String firstRow, String listRows) {

        netRequestHandler.requestAsynPost(new NAction()
                .setUrl(BaseQuestConfig.GET_GROUP_LIST_URL)
                .put("firstRow", firstRow)
                .put("listRows", listRows)
                .setTypeToken(new TypeToken<List<GroupListObj>>() {
                })//指定解析类型,该程序里面对应body 里面的json内容
                .setRequestCode(BaseQuestConfig.QUEST_GET_GROUP_LIST_CODE));
    }


    /**
     * 团队创建人删除团队成员
     * API参数传入方式: POST
     * 传入JSON格式: {"group_id":"1","remove_uid_list":["5","3"]}
     * 返回JSON格式: {"meta":{"code":200,"message":"success"}}
     *
     * @param group_id        要删除成员的所在团队ID
     * @param remove_uid_list 要删除成员的uid list
     * @return delIdList 已删除的ID
     */
    public static void delTeamMember(NetRequestHandler netRequestHandler, String group_id, List<String> remove_uid_list) {

        netRequestHandler.requestAsynPost(new NAction()
                .setUrl(BaseQuestConfig.DEL_TEAM_MEMBER_URL)
                .put("group_id", group_id)
                .put("remove_uid_list", remove_uid_list)
                // .setTypeToken(LoginInfo.class)//指定解析类型,该程序里面对应body 里面的json内容
                .setRequestCode(BaseQuestConfig.QUEST_DEL_TEAM_MEMBER_CODE));
    }


    /**
     * 前台用户注册
     * API参数传入方式: POST
     * 传入JSON格式: {"phone_no":"15916035572","password":"c8837b23ff8aaa8a2dde915473ce0991"}
     * 返回JSON格式: {"meta":{"code":200,"message":"u6210u529f"}}
     *
     * @param phone_no 用户手机号
     * @param password 用户密码md5值
     * @param verification_code 短信验证码
     * @param desc 公司名称
     * @param user_name 用户姓名 昵称 称呼
     * @return code code 200 成功 3001 phone_no参数未传入 3002 password参数未传入  3003 账号已存在 3004写入用户数据失败 3005 手机号码不合法
     */
    public static void register(NetRequestHandler netRequestHandler, Object phone_no, String password, Object verification_code,Object user_name,Object desc) {

        netRequestHandler.requestAsynPost(new NAction()
                .setUrl(BaseQuestConfig.REGISTER_URL)
                .put("phone_no", phone_no)
                .put("password", Utils.getMD5(password))
                .put("verification_code", verification_code)
                .put("user_name", user_name)
                .put("desc", desc)
                // .setTypeToken(LoginInfo.class)//指定解析类型,该程序里面对应body 里面的json内容
                .setRequestCode(BaseQuestConfig.QUEST_REGISTER_CODE));
    }


    /**
     * 获取充值会员选项
     * API参数传入方式: GET
     * 传入JSON格式: 无
     * 返回JSON格式: {"meta":{"code":200,"message":"success"},"body":[{"option_id":"1","option_name":"20u6210u5458u4f1au5458","day":"365","price":"30","group_num":"500"},{"option_id":"2","option_name":"30u6210u5458u4f1au5458","day":"365","price":"30","group_num":"500"}]}
     * API_URL_本地: http://localhost:81/api.php/Cube/getRechargeOption
     * API_URL_服务器: http://fx.lyfz.net/wxyx/api.php/Cube/getRechargeOption
     *
     * @return group_num 允许添加团队人数 -1不限制
     */
    public static void getRechargeOption(NetRequestHandler netRequestHandler) {

        netRequestHandler.requestAsynPost(new NAction()
                .setUrl(BaseQuestConfig.GET_RECHARGE_OPTION_URL)
                 .setTypeToken(new TypeToken<List<RechargeOption>>(){})//指定解析类型,该程序里面对应body 里面的json内容
                .setRequestCode(BaseQuestConfig.QUEST_GET_RECHARGE_OPTION_CODE));
    }


    /**
     * 团队成员退出当前所在团队
     * API参数传入方式: POST
     * 传入JSON格式: {"group_id":"3"}
     * 返回JSON格式: {"meta":{"code":200,"message":"success"}}
     *
     * @param uid      用户uid
     * @param group_id 要退出所在团队ID
     * @return code 200->成功 3001->当前账号未在此团队中 3002->团队创建人无法退出团队 3003->删除失败
     */
    public static void SignOutTeam(NetRequestHandler netRequestHandler, String uid, String group_id) {

        netRequestHandler.requestAsynPost(new NAction()
                .setUrl(BaseQuestConfig.SIGNOUT_TEAM_URL)
                .put("uid", uid)
                .put("group_id", group_id)
                // .setTypeToken(LoginInfo.class)//指定解析类型,该程序里面对应body 里面的json内容
                .setRequestCode(BaseQuestConfig.QUEST_SIGNOUT_TEAM_CODE));
    }

    /**
     * 删除我创建的团队
     * API参数传入方式: POST
     * 传入JSON格式: {"group_id":"2"}
     * 返回JSON格式: {"meta":{"code":200,"message":"success"}}
     * API_URL_本地: http://localhost:81/api.php/Cube/delGroup
     * API_URL_服务器: http://fx.lyfz.net/wxyx/api.php/Cube/delGroup
     *
     * @param group_id 要删除的团队ID
     * @return code 200->成功  3001->非团队创建人无法删除团队 3002->删除失败
     */
    public static void delGroup(NetRequestHandler netRequestHandler, String group_id) {

        netRequestHandler.requestAsynPost(new NAction()
                .setUrl(BaseQuestConfig.DEL_GROUP_URL)
                .put("group_id", group_id)
                .setRequestCode(BaseQuestConfig.QUEST_DEL_GROUP_CODE));
    }


    /**
     * 加入九宫格团队
     * API参数传入方式: POST
     * 传入JSON格式: {"group_number":"3"}
     * 返回JSON格式: {"meta":{"code":200,"message":"success"}}
     * API_URL_本地: http://localhost:81/api.php/Cube/joinTeam
     * API_URL_服务器: http://fx.lyfz.net/wxyx/api.php/Cube/joinTeam
     *
     * @param group_number 要加入团队的相关编码
     * @return code 200->成功 3001->团队不存在 3002->团队人数超过最大限制 3003->插入数据失败 3004->无法加入超过2个团队
     */
    public static void joinTeam(NetRequestHandler netRequestHandler, String group_number) {

        netRequestHandler.requestAsynPost(new NAction()
                .setUrl(BaseQuestConfig.JOIN_TEAM_URL)
                .put("group_number", group_number)
                .setRequestCode(BaseQuestConfig.QUEST_JOIN_TEAM_CODE));
    }


/**
 * 请将支付宝客户端sdk支付成功返回的json数据原样返回到此接口 通知服务端支付成功
 *{"meta":{"code":200,"message":"success"}}
 */
       public static void aliPayAppReturnData(NetRequestHandler netRequestHandler,String payinfo) {

          netRequestHandler.requestAsynPost(new NAction()
                  .setUrl(BaseQuestConfig.ALIPAY_APP_RETURN_DATA_URL)
                  .put("", payinfo)
                  .setRequestCode(BaseQuestConfig.QUEST_ALIPAY_APP_RETURN_DATA_CODE));
      }


    /**
     * 充值会员
     * API参数传入方式: POST
     * 传入JSON格式: {"option_id":"3"}
     * 返回JSON格式: {"meta":{"code":200,"message":"success"},"body":"alipay_sdk=alipay-sdk-php-20161101&app_id=200561869457&biz_content=%7B%22body%22%3A%22%5Cu4e50%5Cu4eab%5Cu4f1a%5Cu5458%5Cu5145%5Cu503c-60%5Cu4eba%5Cu56e2%5Cu961f%5Cu4f1a%5Cu5458%5Cu534a%5Cu5e74%22%2C%22subject%22%3A%22%5Cu4e50%5Cu4eab%5Cu4f1a%5Cu5458%5Cu5145%5Cu503c-60%5Cu4eba%5Cu56e2%5Cu961f%5Cu4f1a%5Cu5458%5Cu534a%5Cu5e74%22%2C%22out_trade_no%22%3A%22OiTy19i1vBhh32CMwEwCS02ZAa65Zj36%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%2266.00%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=localhost%3A81%2Fapi.php%2FPay%2FaliPayCallback&sign_type=RSA2&timestamp=2017-05-31+16%3A56%3A22&version=1.0&sign=q6pC8TUBFftaF0WtReOxuTe4dTHhZJeL0X0Zb8EB17LkwQQa9aV5M2dDSM1L2ASq9ki%2B31qzl9Dnc0zj5CWMFC1vaexqH%2BARkWdNVGDmmK3cm9g9tJPu7GkmRKOym%2BpwrV2FbKSCSa%2BOtbB69fdrM1dTmnbgCqCgDln8ioXfbWGHa8SyTGQcUDC2vq7QUaXu%2BisrJFR8wcdnPveYyWUl0mhlme6Wdc0Ghr5A2iHWNgACMi9taIO6FlaUfLKSBd5%2FE1970MvL6lHqS0J1Fpo6cGLBEfqBE8cmdlp0BJFye%2FmOSEiF0fdx0HlKlfe0FRLiwQGbqE%2FvZG831SN9mvITGg%3D%3D"}
     * API_URL_本地: http://localhost:81/api.php/Cube/rechargeMember
     * API_URL_服务器: http://fx.lyfz.net/wxyx/api.php/Cube/rechargeMember
     *
     * @param option_id 充值选项ID
     */
    public static void rechargeMember(NetRequestHandler netRequestHandler, String option_id) {

        netRequestHandler.requestAsynPost(new NAction()
                .setUrl(BaseQuestConfig.RECHARGE_MEMBER_URL)
                .put("option_id", option_id)
                .setRequestCode(BaseQuestConfig.QUEST_RECHARGE_MEMBER_CODE));
    }


    /**
     * 获取市场模板详情数据
     * API参数传入方式: POST
     * 传入JSON格式: {"template_id":"13"}
     * 返回JSON格式: {"meta":{"code":200,"message":"success"}}
     * API_URL_本地: http://localhost:81/api.php/Cube/getShopTemplateData
     * API_URL_服务器: http://fx.lyfz.net/wxyx/api.php/Cube/getShopTemplateData
     *
     * @param uid->用户uid
     * @param template_id->模板ID
     * @return remindPeopleList->九宫格提醒人uid list
     */
    public static void getShopTemplateData(NetRequestHandler netRequestHandler, String template_id) {

        netRequestHandler.requestAsynPost(new NAction()
                .setUrl(BaseQuestConfig.GET_SHOP_TEMPLATE_DATA_URL)
               // .put("uid", uid)
                .put("template_id", template_id)
                .setTypeToken(TemplateDataObj.class)//指定解析类型,该程序里面对应body 里面的json内容
                .setRequestCode(BaseQuestConfig.QUEST_GET_SHOP_TEMPLATE_DATA_CODE));
    }


    /**
     * 修改普通用户登录密码
     * API参数传入方式: POST
     * 传入JSON格式: {"password":"md5Code....","new_password":"md5Code...."}
     * 返回JSON格式: {"meta":{"code":200,"message":"success"}}
     * API_URL_本地: http://localhost:81/api.php/Cube/updateUserPassword
     * API_URL_服务器: http://fx.lyfz.net/wxyx/api.php/Cube/updateUserPassword
     *
     * @param password     原密码 md5
     * @param new_password 修改后的新密码 md5
     * @return code 200->成功 3001->原密码错误 3002->更新数据失败
     */
    public static void updateUserPassword(NetRequestHandler netRequestHandler, EditText password, EditText new_password, EditText editConfimPwd) {

        netRequestHandler.requestAsynPost(new NAction()
                .setUrl(BaseQuestConfig.UPDATE_PASSWORD_URL)
                .put("password", Utils.getMD5(password.getText().toString()))
                .put("new_password", Utils.getMD5(new_password.getText().toString()))
                .setRequestCode(BaseQuestConfig.QUEST_UPDATE_PASSWORD_CODE));
    }


    /**
     * 获取九宫格市场推荐模板列表
     * API参数传入方式: POST
     * 传入JSON格式: {"firstRow":"0","listRows":"20","searchText":"模板名称","class_id":"12"}
     * 返回JSON格式: {"meta":{"code":200,"message":"success"},"body":[{"template_id":"3","template_name":"u6d4bu8bd5777","use_num":"0","template_add_time":"2017-05-26 05:38:51","template_cover_img_url":"http://localhost:81/Public/Uploads/Model/Cube/2017-05-26/7sjhu0tyhGYkFjnM.jpeg","forwardingtimes":"0"},{"template_id":"4","template_name":"u6d4bu8bd5","use_num":"0","template_add_time":"2017-05-27 09:15:23","template_cover_img_url":"http://localhost:81/Public/Uploads/Model/Cube/2017-05-27/DCb7yuxnR0di42Ol.jpeg","forwardingtimes":"0","class_id":"1"}]}
     * API_URL_本地: http://localhost:81/api.php/Cube/getShopTemplateList
     * API_URL_服务器: http://fx.lyfz.net/wxyx/api.php/Cube/getShopTemplateList
     *
     * @param firstRow->开始记录数
     * @param searchText->模板名称 (可选传)
     * @param class_id->模板分类id (可选传)
     * @return class_id->模板分类ID -1未分配
     */
    public static void getShopRecommendTemplateList(NetRequestHandler netRequestHandler, String firstRow, String searchText, String class_id) {

        netRequestHandler.requestAsynPost(new NAction()
                .setUrl(BaseQuestConfig.GET_GETSHOPRECOMMENDTEMPLATELIST_URL)
                .put("firstRow", firstRow)
                .put("listRows", 10)
                .put("searchText", searchText)
                .put("class_id", class_id)
                 .setTypeToken(new TypeToken<List<HomeTemplateBean>>(){})//指定解析类型,该程序里面对应body 里面的json内容
                .setRequestCode(BaseQuestConfig.QUEST_GETSHOPRECOMMENDTEMPLATELIST_CODE));
    }



    /**
     * 获取九宫格市场热门模板列表
     * API参数传入方式: POST
     * 传入JSON格式: {"firstRow":"0","listRows":"20","searchText":"模板名称","class_id":"12"}
     * 返回JSON格式: {"meta":{"code":200,"message":"success"},"body":[{"template_id":"3","template_name":"u6d4bu8bd5777","use_num":"0","template_add_time":"2017-05-26 05:38:51","template_cover_img_url":"http://localhost:81/Public/Uploads/Model/Cube/2017-05-26/7sjhu0tyhGYkFjnM.jpeg","forwarding_times":"0"},{"template_id":"4","template_name":"u6d4bu8bd5","use_num":"0","template_add_time":"2017-05-27 09:15:23","template_cover_img_url":"http://localhost:81/Public/Uploads/Model/Cube/2017-05-27/DCb7yuxnR0di42Ol.jpeg","forwarding_times":"0","class_id":"1"}]}
     * API_URL_本地: http://localhost:81/api.php/Cube/getShopHotTemplateList
     * API_URL_服务器: http://fx.lyfz.net/wxyx/api.php/Cube/getShopHotTemplateList
     * @param firstRow->开始记录数
     * @param listRows->每页显示数量
     * @param searchText->模板名称 (可选传)
     * @param class_id->模板分类id (可选传)
     * @return code 200->成功
     * @return template_id->模板ID
     * @return template_name->模板名称
     * @return use_num->模板使用次数
     * @return template_add_time->模板添加时间
     * @return template_cover_img_url->模板封面
     * @return forwarding_times->模板转发次数
     * @return class_id->模板分类ID -1未分配
     */
    public static void getShopHotTemplateList(NetRequestHandler netRequestHandler, String firstRow, String searchText, String class_id) {

        netRequestHandler.requestAsynPost(new NAction()
                .setUrl(BaseQuestConfig.GET_SHOP_HOT_TEMPLATE_LIST_URL)
                .put("firstRow", firstRow)
                .put("listRows", PAGE_COUNT)
                .put("searchText", searchText)
                .put("class_id", class_id)
                .setTypeToken(new TypeToken<List<HomeTemplateBean>>(){})//指定解析类型,该程序里面对应body 里面的json内容
                .setRequestCode(BaseQuestConfig.QUEST_SHOP_HOT_TEMPLATE_LIST_CODE));
    }
    /**
     * 获取九宫格市场模板列表
     * API参数传入方式: POST
     * 传入JSON格式: {"firstRow":"0","listRows":"20","searchText":"模板名称","class_id":"12"}
     * 返回JSON格式: {"meta":{"code":200,"message":"success"},"body":[{"template_id":"3","template_name":"u6d4bu8bd5777","use_num":"0","template_add_time":"2017-05-26 05:38:51","template_cover_img_url":"http://localhost:81/Public/Uploads/Model/Cube/2017-05-26/7sjhu0tyhGYkFjnM.jpeg","forwardingtimes":"0"},{"template_id":"4","template_name":"u6d4bu8bd5","use_num":"0","template_add_time":"2017-05-27 09:15:23","template_cover_img_url":"http://localhost:81/Public/Uploads/Model/Cube/2017-05-27/DCb7yuxnR0di42Ol.jpeg","forwardingtimes":"0","class_id":"1"}]}
     * API_URL_本地: http://localhost:81/api.php/Cube/getShopTemplateList
     * API_URL_服务器: http://fx.lyfz.net/wxyx/api.php/Cube/getShopTemplateList
     *
     * @param firstRow->开始记录数
     * @param listRows->每页显示数量
     * @param searchText->模板名称 (可选传)
     * @param class_id->模板分类id (可选传)
     * @return class_id->模板分类ID -1未分配
     */
    public static void getShopTemplateList(NetRequestHandler netRequestHandler,String requestUrl, int firstRow, String searchText, String class_id) {

        netRequestHandler.requestAsynPost(new NAction()
                .setUrl(requestUrl)
                .put("firstRow", firstRow)
                .put("listRows", PAGE_COUNT)
                .put("searchText", searchText)
                .put("class_id", class_id)
                .setTypeToken(new TypeToken<List<HomeTemplateBean>>(){})//指定解析类型,该程序里面对应body 里面的json内容
                .setRequestCode(BaseQuestConfig.QUEST_GET_SHOP_TEMPLATE_LIST_CODE));
    }

    /**
     * 获取我的使用记录
     * API参数传入方式: POST
     * 传入JSON格式: {"firstRow":"1","listRows":"10"}
     * 返回JSON格式: {"meta":{"code":200,"message":"success"},"body":[{"template_name":"u6d4bu8bd5","template_content":"u6d4bu8bd5u5185u5bb9","template_id":"13","use_time":"2017-06-30 16:02:28","use_num":"1"},{"template_name":"u6d4bu8bd5","template_content":"u6d4bu8bd5u5185u5bb9","template_id":"15","use_time":"2017-06-29 16:02:15","use_num":"1"},{"template_name":"u6d4bu8bd5","template_content":"u6d4bu8bd5u5185u5bb9","template_id":"17","use_time":"2017-06-11 16:04:20","use_num":"2"},{"template_name":"u6d4bu8bd5","template_content":"u6d4bu8bd5u5185u5bb9","template_id":"18","use_time":"2017-06-30 16:02:21","use_num":"1"}]}
     * API_URL_本地: http://localhost:81/api.php/Cube/getMyUseTemplate
     * API_URL_服务器: http://fx.lyfz.net/wxyx/api.php/Cube/getMyUseTemplate
     * @param uid 用户uid
     * @param firstRow 开始记录数
     * @param listRows 每页显示数量
     * @param startTime 查询发布开始时间 (可选传)
     * @param endTime 查询发布结束时间 (可选传)
     * @return code 200->成功
     * @return template_name 模板名称
     * @return template_content 模板内容
     * @return template_id 模板id
     * @return use_time 模板使用时间
     * @return use_num 模板使用次数
     */
    public static void getMyUseTemplate(NetRequestHandler netRequestHandler,  int firstRow, String startTime, String endTime) {

        netRequestHandler.requestAsynPost(new NAction()
                .setUrl(BaseQuestConfig.GET_MY_USE_TEMPLATE_URL)
                .put("firstRow", firstRow)
                .put("listRows", PAGE_COUNT)
                .put("startTime", startTime)
                .put("endTime", endTime)
                .setTypeToken(new TypeToken<List<UseHistoryListBean>>(){})//指定解析类型,该程序里面对应body 里面的json内容
                .setRequestCode(BaseQuestConfig.QUEST_MY_USE_TEMPLATE_CODE));
    }


    /**
     * 获取验证码
     * @param netRequestHandler
     * @param phone_no
     */
    public static void captcha(NetRequestHandler netRequestHandler, EditText phone_no) {
        netRequestHandler.requestAsynPost(new NAction()
                .setUrl(BaseQuestConfig.GET_GETVERIFICATIONCODE_URL)
                .put("phone_no", phone_no)
//                .setTypeToken(new TypeToken<List<UseHistoryListBean>>(){})//指定解析类型,该程序里面对应body 里面的json内容
                .setRequestCode(BaseQuestConfig.QUEST_GETVERIFICATIONCODE_CODE));
    }

    /**
     * 获取会员信息
     * API参数传入方式: GET
     * 传入JSON格式: 无
     * 返回JSON格式: {"meta":{"code":200,"message":"success"},"body":{"expiration_time":"2017-08-19 15:32:16","group_num":"100"}}
     * API_URL_本地: http://localhost:81/api.php/Cube/getMember
     * API_URL_服务器: http://wxyx.lyfz.net/api.php/Cube/getMember
     * @param uid 用户uid
     * @return code->200 成功 3001->暂无会员信息
     * @return expiration_time 会员到期时间 group_num 所购买套餐的团队允许加入人数
     */
    public static void getMember(NetRequestHandler netRequestHandler) {

        netRequestHandler.requestAsynGet(new NAction()
                .setUrl(BaseQuestConfig.GET_MEMBER_URL)
                .setTypeToken(VipInfo.class)//指定解析类型,该程序里面对应body 里面的json内容
                .cachePriority(true)
                .setRequestCode(BaseQuestConfig.QUEST_GET_MEMBER_CODE));
    }



    /**
     * 普通用户找回密码
     * API参数传入方式: POST
     * 传入JSON格式: {"phone_no":"15916035572","new_password":"md5psw","verification_code":"355156"}
     * 返回JSON格式: {"meta":{"code":200,"message":"u6210u529f"}}
     * API_URL_本地: http://localhost:81/api.php/Auth/userBackPassword
     * API_URL_服务器: http://fx.lyfz.net/wxyx/api.php/Auth/userBackPassword
     * @param phone_no 用户手机
     * @param new_password 修改的新密码
     * @param verification_code 手机验证码
     * @return code->200 成功 3001->原密码错误 3002->phone_no参数缺少 3003->new_password参数缺少 3004->verification_code参数缺少 3005->短信验证码错误
     */
    public static void userBackPassword(NetRequestHandler netRequestHandler, EditText phone_no , EditText new_password, EditText verification_code ) {
        netRequestHandler.requestAsynPost(new NAction()
                .setUrl(BaseQuestConfig.GET_USER_BACK_PASSWORD_URL)
                .put("phone_no", phone_no)
                .put("new_password", Utils.getMD5(new_password.getText().toString()))
                .put("verification_code", verification_code)
                .setRequestCode(BaseQuestConfig.QUEST_USER_BACK_PASSWORD_CODE));
    }



    /**
     * 修改用户资料
     * API参数传入方式: POST
     * 传入JSON格式: {"user_name":"张三"}
     * 返回JSON格式: {"meta":{"code":200,"message":"success"}}
     * API_URL_本地: http://localhost:81/api.php/Cube/updateUserInfo
     * API_URL_服务器: http://wxyx.lyfz.net/api.php/Cube/updateUserInfo
     * @param uid 用户uid
     * @param user_name 用户姓名 昵称 称呼 (可选)
     * @return code->200 成功 3001->更新数据失败
     */
    public static void updateUserInfo(NetRequestHandler netRequestHandler, String user_name) {
        netRequestHandler.requestAsynPost(new NAction()
                .setUrl(BaseQuestConfig.UPDATE_USER_INFO_URL)
                .put("user_name", user_name)
                .setRequestCode(BaseQuestConfig.QUEST_UPDATE_USER_INFO_CODE));
    }


    /**
     * 修改用户头像
     * API参数传入方式: POST
     * 传入JSON格式: {"avatar_base_code":"base64code..."}
     * 返回JSON格式: {"meta":{"code":200,"message":"success"},"body":{"avatar_url":"base64code"}}
     * API_URL_本地: http://localhost:81/api.php/Cube/editUserAvatar
     * API_URL_服务器: http://wxyx.lyfz.net/api.php/Cube/editUserAvatar
     * @param uid 用户uid
     * @param avatar_base_code 用户头像base64code值
     * @return code 200->成功 3001->avatar_base_code参数为空 3002->更新数据失败 3003->删除原头像失败
     * @return avatar_url 用户新头像地址
     */
    public static void editUserAvatar(NetRequestHandler netRequestHandler, String avatar_base_code) {
        netRequestHandler.requestAsynPost(new NAction()
                .setUrl(BaseQuestConfig.EDIT_USER_AVATAR_URL)
                .put("avatar_base_code", avatar_base_code)
                .setRequestCode(BaseQuestConfig.QUEST_EDIT_USER_AVATAR_CODE));
    }






    /**
     * 模板市场设置模板定时闹钟
     * API参数传入方式: POST
     * 传入JSON格式: {"template_id":"1","remind_time":"2017-06-11 12:00:00"}
     * 返回JSON格式: {"meta":{"code":200,"message":"success"}}
     * API_URL_本地: http://localhost:81/api.php/Cube/setTemplateRemind
     * API_URL_服务器: http://wxyx.lyfz.net/api.php/Cube/setTemplateRemind
     * @param template_id 模板ID
     * @param remind_time 提醒时间
     * @return code->200 成功 3001->模板不存在 3002->插入数据失败 3003->用户未设置设备id
     */
    public static void setTemplateRemind(NetRequestHandler netRequestHandler, String template_id,String remind_time) {
        netRequestHandler.requestAsynPost(new NAction()
                .setUrl(BaseQuestConfig.SET_TEMPLATE_REMIND_URL)
                .put("template_id", template_id)
                .put("remind_time", remind_time)
                .setRequestCode(BaseQuestConfig.QUEST_SET_TEMPLATE_REMIND_CODE));
    }




    public static void getTemplateStatistics(NetRequestHandler netRequestHandler, String template_id) {
        netRequestHandler.requestAsynPost(new NAction()
                .setUrl(BaseQuestConfig.GET_TEMPLATE_STATISTICS_URL)
                .put("template_id", template_id)
                .setTypeToken(TemplateStatisticsObj.class)//指定解析类型,该程序里面对应body 里面的json内容
                .setRequestCode(BaseQuestConfig.QUEST_GET_TEMPLATE_STATISTICS_CODE));
    }


/**
 * 模板转发次数记录
 * API参数传入方式: POSt
 * 传入JSON格式: {"template_id":"3"}
 * 返回JSON格式: {"meta":{"code":200,"message":"success"}}
 * API_URL_本地: http://localhost:81/api.php/Cube/templateForwardRecord
 * API_URL_服务器: http://fx.lyfz.net/wxyx/api.php/Cube/templateForwardRecord
 * @param template_id->模板ID
 * @return code 200->成功
 */
    public static void templateForwardRecord(NetRequestHandler netRequestHandler,String template_id) {
        netRequestHandler.requestAsynPost(new NAction()
                .setUrl(BaseQuestConfig.TEMPLATE_FORWARD_RECORD_URL)
                .put("template_id", template_id)
                .setRequestCode(BaseQuestConfig.QUEST_TEMPLATE_FORWARD_RECORD_CODE));
    }




}
