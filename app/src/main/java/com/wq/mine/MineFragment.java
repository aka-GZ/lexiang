package com.wq.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sunrun.sunrunframwork.bean.BaseBean;
import com.sunrun.sunrunframwork.uiutils.ToastUtils;
import com.wq.base.LBaseActivity;
import com.wq.base.LBaseFragment;
import com.wq.common.boxing.GlideMediaLoader;
import com.wq.common.quest.BaseQuestStart;
import com.wq.common.quest.Config;
import com.wq.common.util.AlertDialogUtil;
import com.wq.common.util.IntentUtil;
import com.wq.common.util.LoginUtil;
import com.wq.common.util.popwindow.MenuWindow;
import com.wq.common.widget.TitleBar;
import com.wq.mine.mode.VipInfo;
import com.wq.project01.R;
import com.wq.template.MyTemplateListActivity;
import com.wq.template.TeamTemplateListActivity;
import com.wq.template.UseHistoryListActivity;
import com.wq.vip.BuyVipActivity_2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.wq.common.model.Const.CODE_OK;
import static com.wq.common.quest.BaseQuestConfig.QUEST_GET_MEMBER_CODE;

/**
 * Created by weiquan on 2017/6/2.
 */

public class MineFragment extends LBaseFragment {
    @BindView(R.id.titleBar)
    TitleBar titleBar;
    @BindView(R.id.img_head)
    CircleImageView imgHead;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.tv_open_qiye)
    TextView tvOpenQiye;
    @BindView(R.id.tv_attention)
    TextView tvAttention;
    @BindView(R.id.tv_attention_number)
    TextView tvAttentionNumber;
    @BindView(R.id.lay_attention)
    LinearLayout layAttention;
    @BindView(R.id.tv_fens)
    TextView tvFens;
    @BindView(R.id.tv_fens_number)
    TextView tvFensNumber;
    @BindView(R.id.lay_fens)
    LinearLayout layFens;
    @BindView(R.id.item_mine_template)
    TextView itemMineTemplate;
    @BindView(R.id.item_team_template)
    TextView itemTeamTemplate;
    @BindView(R.id.item_use_history)
    TextView itemUseHistory;
    @BindView(R.id.tv_vipInfo)
    TextView tv_vipInfo;

    @BindView(R.id.activity_main)
    LinearLayout activityMain;
    Unbinder unbinder;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_mine;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        titleBar.setRightAction(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MenuWindow.showHomePopDialog((LBaseActivity) that, view);
            }
        });
    }

    @Override
    public void onResume() {
        setData2Page();
        BaseQuestStart.getMember(this);
        super.onResume();
    }

    private void setData2Page() {
        tvNickname.setText(Config.getLoginInfo().user_name);
        GlideMediaLoader.loadHead(this, imgHead, Config.getLoginInfo().avatar_url);
    }

    @Override
    public void nofityUpdate(int requestCode, BaseBean bean) {
        switch (requestCode) {
            case QUEST_GET_MEMBER_CODE:
                if (bean.status == CODE_OK) {
                    VipInfo vipInfo = bean.Data();
                    tv_vipInfo.setText(String.format("%s人团队\n有效期: %s", vipInfo.group_num, vipInfo.expiration_time));
                    tv_vipInfo.setVisibility(View.VISIBLE);
                }
                break;
        }
        super.nofityUpdate(requestCode, bean);
    }

    @OnClick({R.id.lay_attention, R.id.tv_nickname, R.id.img_head, R.id.lay_fens, R.id.tv_open_qiye, R.id.item_mine_template, R.id.item_team_template, R.id.item_use_forwarded, R.id.item_use_history, R.id.item_use_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_head:
            case R.id.tv_nickname:
                UserInfoActivity.launch(that);
                break;
            case R.id.lay_attention:
                break;
            case R.id.lay_fens:
                break;
            case R.id.tv_open_qiye:
                IntentUtil.startActivity(that, BuyVipActivity_2.class);
                break;
            case R.id.item_mine_template:
                IntentUtil.startActivity(that, MyTemplateListActivity.class);
                break;
            case R.id.item_team_template:
                IntentUtil.startActivity(that, TeamTemplateListActivity.class);
                break;
            case R.id.item_use_history:
                IntentUtil.startActivity(that, UseHistoryListActivity.class);
                break;
            case R.id.item_use_forwarded:
                //IntentUtil.startActivity(that, ToBeForwardedActivity.class);
                ToastUtils.shortToast("待转发模板");
                break;
            case R.id.item_use_exit:
                AlertDialogUtil.showConfimDialog(that, "是否退出登录?", R.drawable.ic_dialog_tuichu, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LoginUtil.exitLogin(that);
                    }
                }, null);

                break;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick()
    public void onViewClicked() {
    }
}
