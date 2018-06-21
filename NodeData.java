package compressFile; /**
 * Created by ZC on 2017/12/20.
 */


/**
 * 节点数据
 * 功能：定义数据，及其哈夫曼编码
 * @author Andrew
 *
 */
public class NodeData {
    public NodeData(){

    }
    public int data;//节点数据
    public int weight;//该节点的权值
    public int point;//该节点所在的左右位置 0-左 1-右
    private NodeData parent;
    private NodeData left;
    private NodeData right;

    public int getData(){
        return data;
    }
    public NodeData getParent() {
        return parent;
    }
    public void setParent(NodeData parent) {
        this.parent = parent;
    }
    public NodeData getLeft() {
        return left;
    }
    public void setLeft(NodeData left) {
        this.left = left;
    }
    public NodeData getRight() {
        return right;
    }
    public void setRight(NodeData right) {
        this.right = right;
    }
}