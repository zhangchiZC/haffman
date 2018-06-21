package compressFile;
/**
 * Created by ZC on 2017/12/20.
 */


import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import static GUI.View.findPath;


/**
 * 统计指定文件中每个字节数 功能：定义数组，将文件中的字节类型及个数写入数组
 * 创建优先队列和哈夫曼树
 *
 */
public class StatisticBytes {


    /**
     * 第一步：
     * 统计文件中字节的方法
     *
     * @param path
     *      所统计的文件路径
     * @return 字节个数数组
     */
    private int[] statistic(String path) {
        /******储存字节数据的数组--索引值代表字节类型-存储值代表权值******/
        int[] array_Bytes = new int[256];
        try {
            InputStream data = new FileInputStream(path);
            BufferedInputStream buffered = new BufferedInputStream(data);
            /******** 文件中字符个数 ********/
            int number = data.available();
            System.out.println("字节个数》》》"+number);
            for (int i = 0; i < number; i++) {
                int b = data.read();
                array_Bytes[b] ++; //统计每种字节个数
            }

            data.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return array_Bytes;
    }
    /**
     * 第二步：
     * 根据统计的字节数组创建优先队列
     * @param array 统计文件字节的数组
     * @return    优先队列
     */
    private PriorityQueue<NodeData> createQueue(int[] array){
        //定义优先队列，根据数据的权值排序从小到大
        PriorityQueue<NodeData> queue =
                new PriorityQueue<NodeData>(array.length,new Comparator<NodeData>(){
                    public int compare(NodeData o1, NodeData o2) {
                        return o1.weight - o2.weight;
                    }
                });

        for(int i=0; i<array.length; i++){
            if(0 != array[i]){
                NodeData node = new NodeData();
                node.data = i;//设置该节点的数据
                node.weight = array[i];//设置该节点的权值
                queue.add(node);
            }
        }
        return queue;
    }
    /**
     * 第三步：
     * 根据优先队列创建哈夫曼树
     * @param queue  优先队列
     * @return     哈夫曼树根节点
     */
    private NodeData createTree(PriorityQueue<NodeData> queue){
        while(queue.size() > 1){  //根节点加入队列 直到队列中只剩一个节点为止 即（queue.size() == 1）

            NodeData left = queue.poll();//取得左节点
            NodeData right = queue.poll();//取得右节点

            NodeData root = new NodeData();//创建新节点
            root.weight = left.weight + right.weight;

            root.setLeft(left);
            root.setRight(right);
            left.setParent(root);
            right.setParent(root);



            left.point = 0;
            right.point = 1;



            queue.add(root);
        }
        NodeData firstNode = queue.poll();
        return firstNode;
    }
    /**
     * 第四步：
     * 寻找叶子节点并获得哈夫曼编码
     * @param root  根节点
     */
    private void achievehfmCode(NodeData root){

        if(null == root.getLeft() && null == root.getRight()){
            array_Str[root.data] = this.achieveLeafCode(root);
            /**
             *
             * 得到将文件转换为哈夫曼编码后的文件长度
             * 文件长度 = 一种编码的长度 * 该编码出现的次数
             */
            WriteFile.size_File += (array_Str[root.data].length())*(root.weight);
        }
        if(null != root.getLeft()){
            achievehfmCode(root.getLeft());
        }
        if(null != root.getRight()){
            achievehfmCode(root.getRight());
        }
    }
    /**
     * 根据某叶节点获得该叶子节点的哈夫曼编码
     * @param leafNode  叶节点对象
     */
    private String achieveLeafCode(NodeData leafNode){
        String str = "";
        /*****************存储节点 01 编码的队列****************/
        List<Integer> list_hfmCode = new ArrayList<Integer>();
        list_hfmCode.add(leafNode.point);
        NodeData parent = leafNode.getParent();

        while(null != parent){
            list_hfmCode.add(parent.point);
            parent = parent.getParent();
        }

        int size = list_hfmCode.size();
        for(int i=size-2; i>=0; i--){
            str += list_hfmCode.get(i);
        }
        System.out.println(leafNode.weight+"的哈夫曼编码为>>>"+str);
        return str;
    }
    /**
     * 第五步：
     * 根据获得的哈夫曼表创建 码表
     * Integer 为字节byte [0~256)
     * String 为哈夫曼编码
     * @return 码表
     */
    public Map<Integer,String> createMap(){
        //int[] hfm_Codes = this.statistic("F:\\JAVA\\压缩前.txt");//获得文件字节信息
        int[] hfm_Codes = this.statistic(findPath);
        PriorityQueue<NodeData> queue = this.createQueue(hfm_Codes);//获得优先队列
    /*
     * 获得哈夫曼树的根节点，
     * this.createTree(queue)方法调用之后(含有poll())，queue.size()=0
     */
        NodeData root = this.createTree(queue);
        this.achievehfmCode(root);//获得哈夫曼编码并将其存入数组中

        Map<Integer,String> map = new HashMap<Integer,String>();
        for(int i=0; i<256; i++){
            if(null != array_Str[i]){
                map.put(i, array_Str[i]);
            }
        }
        return map;
    }
    /**
     * 存储字节哈夫曼编码的数组
     * 下标：字节数据
     * 元素：哈夫曼编码
     */
    public String[] array_Str = new String[256];
}