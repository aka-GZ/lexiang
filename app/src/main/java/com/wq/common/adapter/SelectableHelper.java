package com.wq.common.adapter;

import android.util.SparseBooleanArray;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择帮助类,选择接口的实现
 * Created by WQ on 2017/4/27.
 */

public class SelectableHelper<T extends Selectable.SelectableEntity> implements Selectable {
    protected int selectMode = RADIO;
    protected SparseBooleanArray selectList = null;
    protected int selectIndex = -1;
    protected int selectedNum = 0;
    protected List<T> mData;

    public SelectableHelper(List<T> mData) {
        this.mData = mData;
        selectMode(RADIO);
    }
    public SelectableHelper() {
    }
    public SelectableHelper(List<T> mData, int selectMode) {
        this.mData = mData;
        selectMode(selectMode);
    }

    public void setData(List<T> mData) {
        this.mData = mData;
    }

    public int getSelectPosition() {
        return selectIndex;
    }

    public int getSelectCount() {
        return selectedNum;
    }

    public T getSelectItem() {
        if (isVaildIndex(selectIndex)) {
            return mData.get(selectIndex);
        }
        return null;
    }


    public void setSelectPosition(int position) {
        if (selectMode == RADIO) {
            selectIndex = position;
            selectedNum = 1;
        } else {
            boolean isSelected = !isSelected(position);
            selectedNum = isSelected ? selectedNum + 1 : selectedNum - 1;
            selectList.put(itemId(position), isSelected);
        }
    }


    public boolean isSelected(int position) {
        return selectMode == RADIO ? selectIndex == position : selectList
                .get(itemId(position));
    }

    @Override
    public <T extends SelectableEntity> boolean isSelected(T item) {
        return selectList.get(item.getUniqueCode());
    }

    public boolean isSelected(Object item) {
        return selectList
                .get(item.hashCode());
    }

    public void selectAll(boolean isSelect) {
        // TODO Auto-generated method stub
        if (selectMode == MULTISELECT) {
            for (int i = 0; i < mData.size(); i++)
                selectList.put(itemId(i), isSelect);
            selectedNum = isSelect ? getCount() : 0;
        } else if (!isSelect) {
            selectIndex = -1;
            selectedNum = 0;
        }
    }

    public List<T> getAllCheckData() {
        List<T> tmpData = new ArrayList<>();
        if (mData != null) {
            for (int i = 0; i < mData.size(); i++) {
                if (isSelected(i)) {
                    tmpData.add(mData.get(i));
                }
            }
        }
        return tmpData;
    }

    /**
     * 选择模式
     */

    public void selectMode(int mode) {
        if (mode == RADIO || mode == MULTISELECT) {
            this.selectMode = mode;
            selectedNum = 0;
            if (selectMode == MULTISELECT)
                selectList = new SparseBooleanArray(16);
        }

    }

    /**
     * 获得数据源hashCode
     *
     * @param position
     * @return
     */
    private int itemId(int position) {
        if (isVaildIndex(position) && mData.get(position) != null) {
            return mData.get(position).getUniqueCode();
        }
        return 0;
    }

    private boolean isVaildIndex(int index) {
        return !(mData == null || index < 0 || index >= mData.size());
    }

    private int getCount() {
        if (isVaildIndex(0)) return mData.size();
        return 0;
    }
}
