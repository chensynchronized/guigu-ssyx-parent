package com.atguigu.ssyx.acl.helper;

import com.atguigu.ssyx.model.acl.Permission;

import java.util.ArrayList;
import java.util.List;

public class PermissionHelper {

    /**
     * 使用递归方法建菜单
     * @param treeNodes
     * @return
     */
    public static List<Permission> bulid(List<Permission> treeNodes) {
        List<Permission> trees = new ArrayList<>();
        for (Permission treeNode : treeNodes) {
            if (treeNode.getPid() == 0) {
                treeNode.setLevel(1);
                trees.add(findChildren(treeNode,treeNodes));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     * @param treeNodes
     * @return
     */
    public static Permission findChildren(Permission treeNode,List<Permission> treeNodes) {
        if (treeNode.getChildren() == null){
            treeNode.setChildren(new ArrayList<Permission>());
        }
        if (treeNode.getLevel().longValue() == 4){
            return treeNode;
        }
        for (Permission it : treeNodes) {
            if(treeNode.getId().longValue() == it.getPid().longValue()) {
                int level = treeNode.getLevel() + 1;
                it.setLevel(level);

                treeNode.getChildren().add(findChildren(it,treeNodes));
            }
        }
        return treeNode;
    }
}
