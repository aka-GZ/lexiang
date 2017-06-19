package com.wq.template;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.bilibili.boxing.Boxing;
import com.bilibili.boxing.model.config.BoxingConfig;
import com.bilibili.boxing.model.entity.BaseMedia;
import com.bilibili.boxing_impl.ui.BoxingActivity;
import com.google.gson.reflect.TypeToken;
import com.sunrun.sunrunframwork.bean.BaseBean;
import com.sunrun.sunrunframwork.pay.alipay.AlipayUtils;
import com.sunrun.sunrunframwork.uiutils.BitmapUtils;
import com.sunrun.sunrunframwork.uiutils.PictureShow;
import com.sunrun.sunrunframwork.uiutils.ToastUtils;
import com.sunrun.sunrunframwork.uiutils.UIUtils;
import com.sunrun.sunrunframwork.utils.formVerify.FormatException;
import com.sunrun.sunrunframwork.utils.formVerify.VerifyerSet;
import com.sunrun.sunrunframwork.view.ItemView;
import com.sunrun.sunrunframwork.weight.MultiImageUploadView;
import com.sunrun.sunrunframwork.weight.switchbtn.SlideSwitch;
import com.wq.base.LBaseActivity;
import com.wq.common.quest.BaseQuestConfig;
import com.wq.common.quest.BaseQuestStart;
import com.wq.common.util.IntentUtil;
import com.wq.common.util.Tool;
import com.wq.common.widget.TitleBar;
import com.wq.project01.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.sunrun.sunrunframwork.utils.formVerify.VerifyUtil.verify;

/**
 * Created by weiquan on 2017/6/2.
 */

public class AddTemplateActivity extends LBaseActivity {
    @BindView(R.id.titleBar)
    TitleBar titleBar;
    @BindView(R.id.tv_title)
    EditText tvTitle;
    @BindView(R.id.edit_content)
    EditText editContent;
    @BindView(R.id.imgUpload)
    MultiImageUploadView multiImage;
    @BindView(R.id.cb_gongkai)
    SlideSwitch cbGongkai;
    @BindView(R.id.item_gongkai)
    ItemView itemGongkai;
    @BindView(R.id.item_remind)
    ItemView itemRemind;
    @BindView(R.id.activity_main)
    LinearLayout activityMain;
    int IMG_REQUEST_CODE = 0x010;


    private List<String> list;
    private String name;
    private String is_open = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_template);
        new ToastUtils();
        cbGongkai.setState(true);

//        imgUpload.
        multiImage.setNumCol(3);//设置列数
//        multiImage.setImgMargin(DisplayUtil.dp2px(this, 10));
        //设置宽高尺寸
        //设置条目点击事件
        multiImage.setOnItemClickListener(new MultiImageUploadView.OnItemClickListener() {
            @Override
            public void onItemClick(MultiImageUploadView parent, int position) {
                PictureShow pictureShow = new PictureShow(that, multiImage.getFiles(), position);
                pictureShow.show();
            }
        });
        //加号点击事件
        multiImage.setAddClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BoxingConfig mulitImgConfig = new BoxingConfig(BoxingConfig.Mode.MULTI_IMG).
                        needCamera().needGif().withMaxCount(9 - multiImage.getFiles().size());
                Boxing.of(mulitImgConfig).
                        withIntent(that, BoxingActivity.class).
                        start(that, IMG_REQUEST_CODE);
            }
        });
        titleBar.setRightAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String title = tvTitle.getText().toString().trim();
//                String content = editContent.getText().toString().trim();

                new Thread(run).start();


            }
        });

    }

    Runnable run = new Runnable() {
        @Override
        public void run() {
            Save();
        }
    };

    Bitmap bit4 = null;

    public void Save() {
        if (list == null && name == null) {
            list = new ArrayList<String>();
            name = " ";
        }
        is_open = cbGongkai.isSelected() ? "1" : "-1";
        for (File file : multiImage.getFiles()) {
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            bitmaps.add(bitmap);
        }
        try {
            verify(tvTitle, new VerifyerSet.EmptyVerifyer("请输入模板名称"));
            verify(editContent, new VerifyerSet.EmptyVerifyer("请输入分享内容"));
        } catch (FormatException e) {
            e.printStackTrace();
            ToastUtils.shortToast(e.getLocalizedMessage());
            return;
        }

        if (multiImage.getFiles().size() > 0) {

            bit4 = createGridBitmap(bitmaps);
        } else {
            ToastUtils.shortToast("分享模板图片为空");
            return;
        }
        bitmaps_3.clear();
        for (File file : multiImage.getFiles()) {
             bitmaps_3.add(Tool.image2String(file));
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
         * @param remind_id_list->提醒指定团队成员id         list
         * @param is_open->模板是否公开                    -1不公开 1公开 (可选传)
         * @return code 200->成功 3001->template_name参数为空 3002->template_content参数为空 3005->template_cover_img参数为空  3003->img_list图片至少1张最多9张 3004->img_list包含不合法文件 3006->template_cover_img包含不合法文件 3007->数据插入失败
         */
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BaseQuestStart.addTemplate(AddTemplateActivity.this, tvTitle, editContent, Tool.image2String(Tool.saveBitmapFile(bit4)), bitmaps_3, list, is_open);
            }
        });
    }


    @Override
    public void nofityUpdate(int requestCode, BaseBean bean) {
        switch (requestCode) {
            case BaseQuestConfig.QUEST_ADD_TEMPLATE_CODE:
                //log 设置 tag为NetServer 可以查看请求情况
                if (bean.status == 200) {
                    ToastUtils.shortToast("保存成功");
                    finish();
                } else {
                    ToastUtils.shortToast("保存异常，请重新提交");
                }
                break;
        }

        super.nofityUpdate(requestCode, bean);

    }

    ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();
    ArrayList<String> bitmaps_3 = new ArrayList<String>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 123 && resultCode == RESULT_OK) {

            list = getSession().getBean("ids", new TypeToken<List<String>>() {
            });
            name = getSession().getString("names");

            itemRemind.setRightText(name);
        } else {
            ArrayList<BaseMedia> images = Boxing.getResult(data);
            if (images != null) {
                for (BaseMedia image : images) {
                    multiImage.addFile(new File(image.getPath()));
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick({R.id.item_gongkai, R.id.item_remind})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.item_gongkai:

                break;
            case R.id.item_remind:

                IntentUtil.startSlectPeopleActivity(AddTemplateActivity.this, 123);


                break;
            case R.id.titleBar:
                break;
        }
    }

    int Width = 0;



    public Bitmap createGridBitmap(List<Bitmap> bits) {
        int W = 500;
        Bitmap aimBit = Bitmap.createBitmap(W, W, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(aimBit);
        canvas.drawARGB(255, 255, 255, 255);//绘制底色
        int smallW = W / 3;
        Paint paint=new Paint();
        paint.setARGB(255,255,255,255);
        paint.setStrokeWidth(5);
        paint.setAntiAlias(true);
        for (int i = 0; i < 9; i++) {
            int row = i / 3;
            int nol = i % 3;
            if (i < bits.size()) {
                Bitmap scalBit=BitmapUtils.zoomBitmap(bits.get(i),smallW,smallW);
                canvas.drawBitmap(scalBit, nol * smallW,row * smallW, null);
            }
            canvas.drawLine(0,row*smallW,500,row*smallW,paint); //横线
            canvas.drawLine(nol*smallW,0,nol*smallW,500,paint);//竖线
        }
        return aimBit;
    }
}
