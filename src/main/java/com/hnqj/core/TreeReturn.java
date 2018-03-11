package com.hnqj.core;

/**
 * Created by Administrator on 2016/10/24.
 */

/**
 * 返回Ztree格式类
 */
public class TreeReturn {
    public String id;
    public String pId;
    public String name;
    public boolean open;
    public boolean isParent;
   // private List<TreeReturn> showChildren = new ArrayList<TreeReturn>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean getIsParent() {
        return isParent;
    }

    public void setIsParent(boolean parent) {
        isParent = parent;
    }

//    public List<TreeReturn> getShowChildren() {
//        return showChildren;
//    }
//
//    public void setShowChildren(List<TreeReturn> showChildren) {
//        this.showChildren = showChildren;
//    }
}
