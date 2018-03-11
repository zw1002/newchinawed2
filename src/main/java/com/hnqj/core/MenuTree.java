package com.hnqj.core;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/9.
 */

    public class MenuTree {
        private MenuTree parentMenu;
        private List<MenuTree> childrenMenu = new ArrayList<MenuTree>();
        private String fid;
        private String title;
        private String displayOrder;
        private String icon;
        private String url;
        private String selected;

        public void addChildrenMenu(MenuTree childMenu) {
            this.childrenMenu.add(childMenu);
        }

        public MenuTree getParentMenu() {
            return this.parentMenu;
        }

        public void setParentMenu(MenuTree parentMenu) {
            this.parentMenu = parentMenu;
        }

        public List<MenuTree> getChildrenMenu() {
            return this.childrenMenu;
        }

        public void setChildrenMenu(List<MenuTree> childrenMenu) {
            this.childrenMenu = childrenMenu;
        }

        public String getFid() {
            return this.fid;
        }

        public void setFid(String fid) {
            this.fid = fid;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDisplayOrder() {
            return this.displayOrder;
        }

        public void setDisplayOrder(String displayOrder) {
            this.displayOrder = displayOrder;
        }

        public String getIcon() {
            return this.icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getUrl() {
            return this.url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        /**
         * 查找一个子菜单，包括无限的子级菜单
         *
         * @param fid
         * @return
         */
        public MenuTree searchChildMenu(String fid) {
            MenuTree result = null;
            for (MenuTree childMenu : this.childrenMenu) {
                if (StringUtils.equalsIgnoreCase(childMenu.getFid(), fid)) {
                    result = childMenu;
                    break;
                } else {
                    result = childMenu.searchChildMenu(fid);
                    if (result != null)
                        break;
                }
            }

            return result;
        }

        /**
         * 拷贝自己，除了子菜单和父级菜单
         *
         * @return
         */
        public MenuTree copySelfResidesChildren() {
            MenuTree menu = new MenuTree();
            menu.setTitle(this.title);
            menu.setFid(this.fid);
            menu.setUrl(this.url);
            menu.setIcon(this.icon);
            menu.setDisplayOrder(this.displayOrder);
            return menu;
        }

        public void sort() {
            Sort.sort(this.childrenMenu, "displayOrder");
            for (MenuTree menu : childrenMenu) {
                menu.sort();
            }
        }

        @Override
        public String toString() {
            StringBuffer sb = new StringBuffer();

            sb.append("title:").append(title).append("\n");
            sb.append("fid:").append(fid).append("\n");
            sb.append("displayorder").append(displayOrder).append("\n");

            for (MenuTree menu : childrenMenu) {
                sb.append(menu.toString());
            }

            return sb.toString();
        }

        public String getSelected() {
            return selected;
        }

        public void setSelected(String selected) {
            this.selected = selected;
        }
    }

