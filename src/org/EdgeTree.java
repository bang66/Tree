package org;



class Node{
    int left,right;    //左右区间的值节点
    boolean cover;        //表示是否被覆盖
    int count;            //表示此节点表示的线段区间出现的次数（被覆盖的次数），默认为0
    int val;            //左右区间和
    Node leftChild;
    Node rightChild;

    public Node(int left, int right){
        this.left = left;
        this.right = right;
        val=0;
        leftChild=null;
        rightChild=null;
        cover=false;
        count = 0;
    }


}
public class EdgeTree {
    
   private Node root;
    /*
     * 外部接口
     * 建立一棵线段树
     * */
    public void build(int left,int right){
        root = new Node(left, right);
        build(root);
    }


     /**
     * 内部接口
     * 建立一棵线段树
     */
    private void build(Node root) {
        int left=root.left;
        int right=root.right;
        if (right-left>1){
            int mid=(right+left)/2;
            Node leftNode=new Node(left,mid);
            Node rightNode=new Node(mid,right);
            root.leftChild=leftNode;
            root.rightChild=rightNode;
            build(leftNode);
            build(rightNode);
        }else {
            return;
        }
    }


    /*
     * 插入一条线段[c,d]的外部接口
     * c为左端点
     * d为右端点
     * root 为此线段树的根节点
     * */

    public void insert(int c, int d) {
        insert(c,d,root);
    }

    /*
     * 插入一条线段[c,d]的内部接口
     * c为左端点
     * d为右端点
     * root 为此线段树的根节点
     * */
    private void insert(int c, int d, Node node) {
        if (node==null||c<node.left||d>node.right){
            System.out.println("参数不合理");
        }

        else if (node.left==c && node.right==d){
            node.count++;
            node.cover=true;
        }

        else {
            int mid=(c+d)/2;
            if (d<=mid){
                insert(c, d, node.leftChild);
            }
            else if (c>=mid){
                insert(c,d,node.rightChild);
            }
            else{
                insert(c,mid,node.leftChild);
                insert(mid,d,node.rightChild);
            }
        }
    }


    //查询
    public int search(int left,int right){
        return search(root,left,right);
    }

    private int search(Node node, int left, int right) {
        if (left>right || node==null){
            return 0;
        }
        else if (node.left==left && node.right==right){
            return node.val;
        }
        else {
            int mid=(left+right)/2;
            int val=0;
            if (right<=mid){
                val=search(root.leftChild,left,right);
            }
            else if (left>mid){
                val=search(root.rightChild,left,right);
            }
            else {
                val=search(node.leftChild,left,right)+search(node.rightChild,left,right);
            }
            return val;
        }


    }





    /*
     * 前序遍历
     * 外部接口
     * */
    public void preOrder(){
        preOrder(root);
    }

    /*
     * 前序遍历
     * 内部接口
     * */
    private void preOrder(Node root){
//        叶子节点
        if(root.right-root.left==1) {
            System.out.println("["+root.left+","+root.right+"]:"+root.count);
            return ;
        }
        else if(root.right-root.left>1){
            System.out.println("["+root.left+","+root.right+"]:"+root.count);
            preOrder(root.leftChild);
            preOrder(root.rightChild);
        }
    }


    public static void main(String[] args) {
        EdgeTree edgeTree=new EdgeTree();
        edgeTree.build(1,10);
//        edgeTree.insert(3,5);

        System.out.println("前序遍历线段树:");
        edgeTree.preOrder();
        System.out.println("-------------");
        System.out.println(edgeTree.search(1,10));
    }

}

