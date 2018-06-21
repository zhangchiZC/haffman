package compressFile; /**
 * Created by ZC on 2017/12/20.
 */


import GUI.View;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static GUI.View.findPath;

/**
 * 将码表和文件写入新的文件中
 * @author Andrew
 *
 */
public class WriteFile {

    // 实例化创建码表类对象
    private StatisticBytes statistic = new StatisticBytes();
    private Map<Integer, String> map = statistic.createMap();// 获得码表
    // 字节接收变量，接收哈夫曼编码连接够8位时转换的字节
    private int exmpCode;
    public static int size_File;

    public static void main(String[] args) {
        WriteFile writeFile = new WriteFile();
       // writeFile.init();
    }

    public void init(String path) {

        //String filePath = "F:\\JAVA\\压缩后.zc";
        this.writeFile(path);
    }

    /**
     * 第一步： 向文件中写入码表
     *
     * @param dataOut
     *      基本数据流
     */
    private void writeCodeTable(DataOutputStream dataOut) {
        Set<Integer> set = map.keySet();
        Iterator<Integer> p = set.iterator();

        try {
            //向文件中写入码表的长度
            dataOut.writeInt(map.size());
            while (p.hasNext()) {
                Integer key = p.next();
                String hfmCode = map.get(key);

                dataOut.writeInt(key);//写入字节
                //写入哈夫曼编码的长度
                dataOut.writeByte(hfmCode.length());
                for(int i=0; i<hfmCode.length(); i++){
                    dataOut.writeChar(hfmCode.charAt(i));//写入哈夫曼编码
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 第二步： 向压缩文件中写入编码
     *
     * @param path
     */
    public void writeFile(String path) {

        try {
            // 输入流
            //FileInputStream in = new FileInputStream("F:\\JAVA\\压缩前.txt");
            FileInputStream in = new FileInputStream(findPath);
            BufferedInputStream bIn = new BufferedInputStream(in);
            // 输出流
            FileOutputStream out = new FileOutputStream(path);
            DataOutputStream dataOut = new DataOutputStream(out);
            BufferedOutputStream bOut = new BufferedOutputStream(out);
            // 向文件中写入码表
            this.writeCodeTable(dataOut);
            /********************写入补零个数*********************/
            if(0 != size_File % 8){
                dataOut.writeByte(8 - (size_File % 8));
            }

            String transString = "";//中转字符串,存储字符串直到size大于8
            String waiteString = "";//转化字符串,

            int size_File = in.available();
            for(int i=0; i<size_File; i++){
                int j = bIn.read();
                System.out.println("]]]]]]]]]]]>>");
                waiteString = this.changeStringToByte(transString + statistic.array_Str[j]);
                if(waiteString != ""){
                    bOut.write(exmpCode);
                    transString = waiteString;
                }else{
                    transString += statistic.array_Str[j];
                }
            }
            if("" != transString){
                int num = 8-transString.length()%8;
                for(int i=0; i<num; i++){
                    transString += 0;
                }
            }
            transString = this.changeStringToByte(transString);
            bOut.write(exmpCode);

            bIn.close();
            bOut.flush();
            bOut.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 附属第二步：
     * 将字符串转化为byte
     *
     * @param str
     *      要转化的字符串
     * @return 如果str的长度大于8返回一个截取前8位后的str
     *     否则返回""
     */
    private String changeStringToByte(String str) {
        if (8 <= str.length()) {
            exmpCode = ((str.charAt(0) - 48) * 128
                    + (str.charAt(1) - 48) * 64
                    + (str.charAt(2) - 48) * 32
                    + (str.charAt(3) - 48) * 16
                    + (str.charAt(4) - 48) * 8
                    + (str.charAt(5) - 48) * 4
                    + (str.charAt(6) - 48) * 2
                    + (str.charAt(7) - 48));
            str = str.substring(8);
            return str;
        }
        return "";
    }
}