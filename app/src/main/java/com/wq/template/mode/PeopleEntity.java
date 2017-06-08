package com.wq.template.mode;

import com.sunrun.sunrunframwork.view.sidebar.SortModel;

import java.io.Serializable;

/**
 * Created by WQ on 2017/1/4.
 */

public class PeopleEntity extends SortModel implements Serializable{

    /**
     * fuid : 31
     * remark : 前世今生
     * nickname : 158****4647
     * mobile : 15827144647
     * name : 前世今生
     */

    public String fuid;
    public String remark;
    public String nickname;
    public String mobile;
    public String avatar;
    public String getIcon(){
        return avatar;
    }
    @Override
    public String toString() {
        return name;
    }

    public PeopleEntity(String nickname) {
       this.name= this.nickname = nickname;
    }

    public PeopleEntity() {
    }
}
