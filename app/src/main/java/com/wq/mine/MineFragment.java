package com.wq.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wq.base.LBaseFragment;
import com.wq.common.util.IntentUtil;
import com.wq.common.widget.TitleBar;
import com.wq.project01.R;
import com.wq.template.TeamTemplateListActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

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

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.lay_attention, R.id.lay_fens, R.id.item_mine_template, R.id.item_team_template, R.id.item_use_history})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lay_attention:
                break;
            case R.id.lay_fens:
                break;
            case R.id.item_mine_template:
                break;
            case R.id.item_team_template:
                IntentUtil.startActivity(that, TeamTemplateListActivity.class);
                break;
            case R.id.item_use_history:
                break;
        }
    }
}
