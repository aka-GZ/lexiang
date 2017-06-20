package com.wq.mine;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.aigestudio.wheelpicker.widgets.WheelDatePicker;
import com.bilibili.boxing.Boxing;
import com.bilibili.boxing.model.config.BoxingConfig;
import com.bilibili.boxing.model.config.BoxingCropOption;
import com.bilibili.boxing.model.entity.BaseMedia;
import com.bilibili.boxing.utils.BoxingFileHelper;
import com.bilibili.boxing_impl.ui.BoxingActivity;
import com.bumptech.glide.Glide;
import com.sunrun.sunrunframwork.bean.BaseBean;
import com.sunrun.sunrunframwork.http.utils.DateUtil;
import com.sunrun.sunrunframwork.uiutils.UIUtils;
import com.sunrun.sunrunframwork.view.ItemView;
import com.wq.base.LBaseActivity;
import com.wq.common.boxing.GlideMediaLoader;
import com.wq.common.model.LoginInfo;
import com.wq.common.quest.Config;
import com.wq.common.util.Tool;
import com.wq.login.ModifyPwdActivity;
import com.wq.project01.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.sunrun.sunrunframwork.utils.EmptyDeal.size;
import static com.wq.common.model.Const.CODE_OK;
import static com.wq.common.quest.BaseQuestConfig.QUEST_EDIT_USER_AVATAR_CODE;
import static com.wq.common.quest.BaseQuestConfig.QUEST_UPDATE_USER_INFO_CODE;
import static com.wq.common.quest.BaseQuestStart.editUserAvatar;
import static com.wq.common.quest.BaseQuestStart.updateUserInfo;
import static com.wq.mine.UserInfoEditActivity.TAG_CONTENT;

/**
 * 用户个人信息
 */
public class UserInfoActivity extends LBaseActivity {
    File headFile;
    @BindView(R.id.img_head)
    CircleImageView imgHead;
    @BindView(R.id.btnPortrait)
    LinearLayout btnPortrait;
    @BindView(R.id.btnNickname)
    ItemView btnNickname;
    LoginInfo loginInfo=Config.getLoginInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        GlideMediaLoader.loadHead(this, imgHead,loginInfo.avatar_url);
        btnNickname.setRightText(loginInfo.user_name);
        initPortraitClick();
    }


    /**
     * 修改用户头像
     */
    private void initPortraitClick() {
        findViewById(R.id.btnPortrait).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                BoxingCropOption cropOption =new BoxingCropOption()
                //修改头像
                String cachePath = BoxingFileHelper.getCacheDir(that);
                Uri destUri = new Uri.Builder()
                        .scheme("file")
                        .appendPath(cachePath)
                        .appendPath(String.format(Locale.US, "%s.jpg", System.currentTimeMillis()))
                        .build();
                BoxingConfig singleCropImgConfig = new BoxingConfig(BoxingConfig.Mode.SINGLE_IMG)
                        .needCamera().withCropOption(new BoxingCropOption(destUri).withMaxResultSize(300, 300).aspectRatio(1, 1));
                Boxing.of(singleCropImgConfig).
                        withIntent(UserInfoActivity.this, BoxingActivity.class).
                        start(UserInfoActivity.this, 3);
            }
        });
        findViewById(R.id.btnPortrait).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
//                PictureShow pictureShow = new PictureShow(that);
//                pictureShow.setArgment(new String[]{info.getAvatar()});
//                pictureShow.show();
                return true;
            }
        });
    }


    @Override
    public void nofityUpdate(int requestCode, BaseBean bean) {
        switch (requestCode) {
            case QUEST_UPDATE_USER_INFO_CODE:
            case QUEST_EDIT_USER_AVATAR_CODE:
                UIUtils.shortM(bean);
                if (bean.status==CODE_OK) {
                    Config.putLoginInfo(loginInfo);
//                    finish();
//                    BaseQuestStart.getUserInfo(this);//更新数据
                }
                break;
        }
        super.nofityUpdate(requestCode, bean);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK && data != null) {
            String content = data.getStringExtra(TAG_CONTENT);
            if (requestCode == 0x11) {//修改用户昵称
                loginInfo.user_name=content;
                tmpItemView.setRightText(content);
                UIUtils.showLoadDialog(that,"提交中..");
                updateUserInfo(this,content);
              ///  editUserInfo(this, EDIT_NICKNAME_URL, "nick_name", content);
            } else {//修改头像
                ArrayList<BaseMedia> medias = Boxing.getResult(data);

                if (size(medias) > 0) {
                    BaseMedia media = medias.get(0);
                    String imgPath = media.getPath();
                    Glide.clear(imgHead);
                    GlideMediaLoader.loadHead(this, imgHead, imgPath);
                    loginInfo.avatar_url=imgPath;
                    headFile = new File(imgPath);
                    UIUtils.showLoadDialog(that,"提交中..");
                    editUserAvatar(this, Tool.image2String(headFile));
                  //  editUserInfo(this, EDIT_HEADIMG_URL, "headimg", headFile);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    ItemView tmpItemView;

    @OnClick({R.id.btnNickname,R.id.btnModifyPwd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNickname:
                tmpItemView = (ItemView) view;
                UserInfoEditActivity.launch(this, "修改用户名", 0x11, Config.getLoginInfo().user_name);
                break;
            case R.id.btnModifyPwd:
                ModifyPwdActivity.launch(this);
                break;
        }
    }

    public static  void launch(Activity activity){
        Intent intent=new Intent(activity,UserInfoActivity.class);
        activity.startActivity(intent);
    }
}
