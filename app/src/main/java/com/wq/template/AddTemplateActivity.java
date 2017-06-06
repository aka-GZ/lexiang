package com.wq.template;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bilibili.boxing.Boxing;
import com.bilibili.boxing.model.config.BoxingConfig;
import com.bilibili.boxing.model.entity.BaseMedia;
import com.bilibili.boxing_impl.ui.BoxingActivity;
import com.sunrun.sunrunframwork.uiutils.PictureShow;
import com.sunrun.sunrunframwork.uiutils.UIUtils;
import com.sunrun.sunrunframwork.view.ItemView;
import com.sunrun.sunrunframwork.view.title.BaseTitleLayoutView;
import com.sunrun.sunrunframwork.weight.MultiImageUploadView;
import com.sunrun.sunrunframwork.weight.switchbtn.SlideSwitch;
import com.wq.base.LBaseActivity;
import com.wq.common.widget.TitleBar;
import com.wq.project01.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_template);


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

    }


    ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ArrayList<BaseMedia> images = Boxing.getResult(data);
        if (images != null) {
            for (BaseMedia image : images) {
                multiImage.addFile(new File(image.getPath()));

                try {
                    FileInputStream fis = new FileInputStream(image.getPath());
                    Bitmap bitmap  = BitmapFactory.decodeStream(fis);
                    bitmaps.add(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            Bitmap bit1 = add3Bitmap(bitmaps.get(0),bitmaps.get(1),bitmaps.get(2));
            Bitmap bit2 = add3Bitmap(bitmaps.get(3),bitmaps.get(4),bitmaps.get(5));
            Bitmap bit3 = add3Bitmap(bitmaps.get(6),bitmaps.get(7),bitmaps.get(8));
            Bitmap bit4 = addBitmap(bit1 ,bit2 ,bit3);


        }



        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick({R.id.item_gongkai, R.id.item_remind})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.item_gongkai:
                break;
            case R.id.item_remind:

//                Intent intent = new Intent(this,RemindActivity.class);
//                startActivity();

                break;
            case R.id.titleBar:
                break;
        }
    }

    /**
     * 横向拼接
     * <功能详细描述>
     * @param first
     * @param second
     * @return
     */
    private Bitmap add3Bitmap(Bitmap first, Bitmap second, Bitmap three) {
        int width = first.getWidth()/3;
        int height = first.getWidth()/3;
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(first, 0, 0, null);
        canvas.drawBitmap(second, first.getWidth()/3, 0, null);
        canvas.drawBitmap(three, (first.getWidth()+second.getWidth())/3, 0, null);
        return result;
    }


    /**
     * 纵向拼接
     * <功能详细描述>
     * @param first
     * @param second
     * @return
     */
    private Bitmap addBitmap(Bitmap first, Bitmap second, Bitmap three) {
        int width = Math.max(first.getWidth(),second.getWidth());
        int height = (first.getWidth() + second.getWidth() + three.getWidth())/3;;
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(first, 0, 0, null);
        canvas.drawBitmap(second, first.getWidth()/3, 0, null);
        canvas.drawBitmap(three, (first.getWidth()+second.getWidth())/3, 0, null);
        return result;
    }
}
