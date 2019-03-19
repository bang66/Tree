package org;





public class Rb_Tree {

    private Rb_Node mRoot;

    class Rb_Node {
        //true-red       false-black
        boolean color;
        int key;
        Rb_Node lchild;
        Rb_Node rchild;
        Rb_Node parent;

        public Rb_Node(int key,boolean color,Rb_Node lchild,Rb_Node rchild,Rb_Node parent){
            this.color=color;
            this.key=key;
            this.lchild=lchild;
            this.rchild=rchild;
            this.parent=parent;

        }

    }



    public Rb_Tree (){
        mRoot =null;
    }

    public Rb_Node getParent(Rb_Node node){
        if (node!=null){
            return node.parent;
        }else {
            return null;
        }
    }

    public boolean getColor(Rb_Node node){
        if (node!=null){
            return node.color;
        }else {
            return false;
        }
    }

    public boolean isRed(Rb_Node node){
        if (node!=null && node.color==true){
            return true;
        }else {
            return false;
        }
    }
    public boolean isBlack(Rb_Node node){
        return !isRed(node);
    }

    public void setRed(Rb_Node node){
        if (node!=null){
            node.color=true;
        }
    }

    public void setBlack(Rb_Node node) {
        if (node!= null){
            node.color=false;
        }
    }

    public void setParent(Rb_Node node,Rb_Node parent){
        if (node!=null){
            node.parent=parent;
        }
    }

    public void setColor(Rb_Node node,boolean color){
        if (node!=null){
            node.color=color;
        }
    }




    /*
      * 对红黑树的节点(x)进行左旋转
      *
      * 左旋示意图(对节点x进行左旋)：
      *      px                             px
      *     /                              /
      *    x                              y
      *   / \       --(左旋)-.           / \                #
      *  lx  y                         x  ry
      *     / \                       / \
      *    ly ry                     lx ly
      *
      *
      */

    public void leftRotate(Rb_Node x){
        // 设置x的右孩子为y
        Rb_Node y=x.rchild;

        //让y的左孩子变成x的右孩子
        x.rchild=y.lchild;

        //若y的左孩子不空,则让y的左孩子的父节点为x
        if (y.lchild!=null){
            y.lchild.parent=x;
        }

        //将x的父节点设为y的父节点
        y.parent=x.parent;

        //将x的父节点设为y
        if(x.parent==null){
            this.mRoot=y;
        }
        //如果父节点不空
        else {

            //如果x是它父节点的左孩子,则y为其父节点的左孩子
            if (x.parent.lchild==x){
                x.parent.lchild=y;
            }else {
                x.parent.rchild=y;
            }

        }

        //把x作为y的左孩子
        y.lchild=x;

        //把x的父节点作为y
        x.parent=y;

    }




    /**
      * 对红黑树的节点(y)进行右旋转
      *
      * 右旋示意图(对节点y进行右旋)：
      *            py                               py
      *           /                                /
      *          y                                x
      *         / \      --(右旋)-.              / \                     #
      *        x  ry                           lx   y
      *       / \                                  / \                   #
      *      lx rx                                rx ry
      *
      */

    public void  rightRotate(Rb_Node y){
        // 设置x是当前节点的左孩子。
        Rb_Node x=y.lchild;

        // 将 “x的右孩子” 设为 “y的左孩子”；
        y.lchild=x.rchild;

        // 如果"x的右孩子"不为空的话，将 “y” 设为 “x的右孩子的父亲”
        if (x.rchild!=null){
            x.rchild.parent=y;
        }

        // 将 “y的父亲” 设为 “x的父亲”
        x.parent=y.parent;

        if (y.parent==null){
            this.mRoot=x;
        }
        else {
            if (y==y.parent.rchild){
                y.parent.rchild=x;
            }else {
                y.parent.lchild=x;
            }
        }

        // 将 “y” 设为 “x的右孩子”
        x.rchild=y;

        // 将 “y的父节点” 设为 “x”
        y.parent=x;

    }


    private void insert(Rb_Node node){
        int cmp;
        Rb_Node y=null;
        Rb_Node x=this.mRoot;

        // 1. 将红黑树当作一颗二叉查找树，将节点添加到二叉查找树中。
        while (x!=null){
            y=x;
            if (node.key<x.key){
                x=x.lchild;
            }else {
                x=x.rchild;
            }
        }

        node.parent=y;
        if (y!=null){
            if (node.key<y.key){
                y.lchild=node;
            }
            else {
                y.rchild=node;
            }
        }  else {
            this.mRoot=node;
        }


        // 2. 设置节点的颜色为红色
        node.color=true;


        // 3. 将它重新修正为一颗二叉查找树
        insertFixUp(node);
    }



    private void insertFixUp(Rb_Node node) {
        Rb_Node parent,gparent;

        // 若“父节点存在，并且父节点的颜色是红色”
        parent=getParent(node);
        while ((parent!=null)&&isRed(parent)){
            gparent=getParent(parent);


            //若“父节点”是“祖父节点的左孩子”
            if (parent==gparent.lchild){
                Rb_Node uncle=gparent.rchild;
                // Case 1条件：叔叔节点是红色
                // 处理思路：a.将父节点和叔节点设为黑色;
                //  b.将祖父节点设为红色;
                // c.将祖父节点设为当前节点，并继续对新当前节点进行操作
                if ((uncle!=null)&&isRed(uncle)){
                    setBlack(uncle);
                    setBlack(parent);
                    setRed(gparent);
                    node=gparent;
                    continue;
                }


                // Case 2条件：叔叔是黑色，且当前节点是右孩子
                // 处理思路：a.将父节点左旋，并将父节点作为当前节点;
                // b.然后再 :a.将祖父节点右旋;b.交换父节点和祖父节点的颜色

                if (parent.rchild==node){
                    Rb_Node tmp;
                    leftRotate(parent);
                    tmp=parent;
                    parent=node;
                    node=tmp;
                }
                setBlack(parent);
                setRed(gparent);
                leftRotate(gparent);

            }
            //若“父节点”是“祖父节点的右孩子”
            else {
                // Case 1条件：叔叔节点是红色
                Rb_Node uncle=gparent.rchild;
                if ((uncle!=null) && isRed(uncle)) {
                    setBlack(uncle);
                    setBlack(parent);
                    setRed(gparent);
                    node = gparent;
                    continue;
                }

                // Case 2条件：叔叔是黑色，且当前节点是左孩子
                // 处理思路：a.将父节点右旋，并将父节点作为当前节点;
                // b.然后再: a.将祖父节点左旋;b.交换父节点和祖父节点的颜色
                if (parent.lchild==node){
                    rightRotate(parent);
                    Rb_Node tmp;
                    tmp=parent;
                    parent=node;
                    node=tmp;
                }
                leftRotate(gparent);
                setRed(gparent);
                setBlack(parent);
            }
        }

        // 将根节点设为黑色
        setBlack(this.mRoot);

    }

    public void insert(int key){
        Rb_Node node=new Rb_Node(key,false,null,null,null);
        if (node!=null){
            insert(node);
        }
    }



    private void print(Rb_Node tree, int key, int direction) {

        if(tree != null) {
            // tree是根节点
            if(direction==0) {
                System.out.printf("%2d(B) is root\n", tree.key);
            }
            else                // tree是分支节点
            System.out.printf("%2d(%s) is %2d's %6s child\n", tree.key, isRed(tree)?"R":"B", key, direction==1?"right" : "left");

            print(tree.lchild, tree.key, -1);
            print(tree.rchild,tree.key,  1);
            }
        }

        public void print() {
            if (mRoot != null)
                print(mRoot, mRoot.key, 0);
        }

}
