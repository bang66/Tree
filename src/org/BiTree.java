package org;

import java.util.HashMap;



class BiNode{
     BiNode lchild;
     BiNode rchild;
     int value;

    BiNode(int value){
        this.value=value;
    }

    public void display() {
        System.out.print(this.value + "\t");
    }
}

public class BiTree {
    private BiNode root=null;

    BiTree(int value){
        root=new BiNode(value);
        root.lchild=null;
        root.rchild=null;
    }

    public String insert(int value){
        String err=null;
        BiNode node=new BiNode(value);
        if (root==null){
            root=node;
            root.lchild=null;
            root.rchild=null;
        }else {
            BiNode current=root;
            BiNode parent=null;
            while (true){
                if (value<current.value){
                    parent=current;
                    current=current.lchild;
                    if (current==null){
                        parent.lchild=node;
                        break;
                    }
                }
                else if (value>current.value){
                    parent=current;
                    current=current.rchild;
                    if (current==null){
                        parent.rchild=node;
                        break;
                    }
                }
                else {
                    err="节点值重复";
                }
            }
        }
        return err;
    }


    public void preOrder(){
        System.out.println("前序遍历");
        preOrder(root);
        System.out.println();
    }

    private void preOrder(BiNode root) {
        if (root==null){
            return;
        }
        root.display();
        preOrder(root.lchild);
        preOrder(root.rchild);
    }


    public static void main(String[] args) {
        BiTree tree=new BiTree(5);
        tree.insert(2);
        tree.insert(1);
        tree.insert(6);
        tree.insert(4);
        tree.preOrder();
    }

}
