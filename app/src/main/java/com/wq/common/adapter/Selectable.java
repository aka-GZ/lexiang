package com.wq.common.adapter;

import java.util.List;

/**
 * 可选择接口
 * Created by WQ on 2017/4/27.
 */

public interface Selectable {
    /**
     * 选择模式 ,单选 or 多选
     */
    int RADIO = 0x2100;
    int MULTISELECT = 0x2101;

    /**
     * 获取当前选中的位置
     * @return
     */
    int getSelectPosition();

    /**
     * 获取选中的数量
     * @return
     */
    int getSelectCount();

    /**
     * 获取选中的条目
     * @return
     */
    Object getSelectItem();

    /**
     * 设置指定索引选中状态 ,
     * @param position
     */
    void setSelectPosition(int position);

    /**
     * 指定索引是否选中
     * @param position
     * @return
     */
    boolean isSelected(int position);

    /**
     * 指定条目是否选中
     * @param item
     * @param <T>
     * @return
     */
   <T extends SelectableEntity> boolean  isSelected(T item);

    /**
     * 选中/取消所有
     * @param isSelect
     */
    void selectAll(boolean isSelect);

    /**
     * 选择模式
     * @param mode
     */
    void selectMode(int mode);

    /**
     * 获取所有选中数据
     * @param <T>
     * @return
     */
    <T> List<T> getAllCheckData();

    /**
     * 参与选择的数据源接口,需返回唯一识别码供区分
     */
     interface SelectableEntity{
      int  getUniqueCode();
    }
}
