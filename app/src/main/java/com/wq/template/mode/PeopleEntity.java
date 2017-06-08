package com.wq.template.mode;

import com.sunrun.sunrunframwork.view.sidebar.SortModel;

import java.io.Serializable;

/**
 * Created by WQ on 2017/1/4.
 */

public class PeopleEntity extends SortModel implements Serializable{


    /**
     * phone_no : 15916035572
     * user_name : 张三
     * uid : 43
     */

    public String phone_no;
    public String user_name;
    public String uid;

    @Override
    public String toString() {
        return user_name;
    }

    public PeopleEntity(String nickname) {
       this.name= this.user_name = nickname;
    }

    public PeopleEntity() {
    }
}
