import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.UTF_8;
import static jdk.nashorn.internal.objects.Global.print;
import static jdk.nashorn.internal.objects.Global.undefined;

public class bishe {
    public static void main(String[] args) throws IOException {
        String data = new String("110011");
        String gx = new String("11001");

        String get_CRC = GetCrc(data,gx);
        boolean check = CRC_check(get_CRC,gx);
        System.out.println(check);


        String chuan = StringToBinary("湿度:36");
        System.out.println("--------------------------");
        int [][] result = StringToInt(chuan);
        System.out.println(Arrays.toString(result[0]));
        int [][] result2 = reverse(result);
        System.out.println(Arrays.toString(result2[0]));
        int [][] result3 = reverse(result2);
        System.out.println(Arrays.toString(result3[0]));







    }
    //CRC编码
    public static String GetCrc(String data_humidity,String gx_humidity)
    {
        //System.out.println("请输入二进制数据: ");
        //Scanner sc = new Scanner(System.in);
        String dataStr = data_humidity;
        //System.out.println("请输入多项式系数: ");
        String gxStr = gx_humidity;
        //sc.close();
        //获取二进制帧的位数
        int dataStrLen = dataStr.length();
        //获取多项式位数
        int gxStrLen = gxStr.length();
        //将二进制的字符串变为整型
        int data=Integer.valueOf(dataStr,2);
        //将多项式的字符串变为整型
        int gx = Integer.valueOf(gxStr,2);
        //算出原始数据补零后的总位数
        int sum = dataStrLen+gxStrLen-1;
        //计算2的sum-1次幂
        BigInteger bi = new BigInteger("2");
        //将2的sum-1次幂转换为int型
        int flag = bi.pow(sum-1).intValue();
        //原始帧低位补零
        data = data<<(gxStrLen-1);
        //多项式低位补零,使其与补零后的帧位数一致，以便异或
        gx = gx<<(dataStrLen-1);
        //循环dataStrLen次
        for(int i=0; i<(dataStrLen);i++){
        //判断补零后的帧最高位为1还是零
        if((data&flag)!=0) {
            data = data^gx;
            gx = gx>>1;
        }else {
            gx = gx>>1;
        }
        //flag最高位的1右移
        flag = flag>>1;
    }
        String crc = Integer.toBinaryString(data);
        //解决Java输出二进制时略去高位零的问题
        while(crc.length()<(gxStrLen-1)) {
        crc = "0"+crc;
    }
        System.out.println("冗余码:"+crc);
        return data_humidity+crc;
    }
    //CRC校验
    public static boolean CRC_check(String data_CRC,String gx_humidity)
    {
        //System.out.println("请输入二进制数据: ");
        //Scanner sc = new Scanner(System.in);
        String dataStr = data_CRC;
        //System.out.println("请输入多项式系数: ");
        String gxStr = gx_humidity;
        //sc.close();
        //获取二进制帧的位数
        int dataStrLen = dataStr.length();
        //获取多项式位数
        int gxStrLen = gxStr.length();
        //将二进制的字符串变为整型
        int data=Integer.valueOf(dataStr,2);
        //将多项式的字符串变为整型
        int gx = Integer.valueOf(gxStr,2);
        //算出原始数据补零后的总位数
        int sum = dataStrLen+gxStrLen-1;
        //计算2的sum-1次幂
        BigInteger bi = new BigInteger("2");
        //将2的sum-1次幂转换为int型
        int flag = bi.pow(sum-1).intValue();
        //原始帧低位补零
        data = data<<(gxStrLen-1);
        //多项式低位补零,使其与补零后的帧位数一致，以便异或
        gx = gx<<(dataStrLen-1);
        //循环dataStrLen次
        for(int i=0; i<(dataStrLen);i++){
            //判断补零后的帧最高位为1还是零
            if((data&flag)!=0) {
                data = data^gx;
                gx = gx>>1;
            }else {
                gx = gx>>1;
            }
            //flag最高位的1右移
            flag = flag>>1;
        }
        String crc = Integer.toBinaryString(data);
        //解决Java输出二进制时略去高位零的问题
        while(crc.length()<(gxStrLen-1)) {
            crc = "0"+crc;
        }
        System.out.println("冗余码:"+crc);
        int check = Integer.parseInt(crc);
        if(check==0)
            return true;
        else
            return false;
    }


    //字符串utf-8编码成二进制字符串
    public static String StringToBinary(String start)
    {
        String ZERO="00000000";
        String x = start;
        String result = new String();
        byte[]   bs   =   x.getBytes();
        for   (int i = 0;i<bs.length;i++)   {
            String   s   =   Integer.toBinaryString(bs[i]);
            if   (s.length()   >
                    8)   {
                s   =   s.substring(s.length()   -   8);
            }   else   if   (s.length()
                    <   8)   {
                s   =   ZERO.substring(s.length())   +   s;
            }
            System.out.println(s);
            result = result + s;
        }
        return result;

    }

    //二进制字符串转为byte数组
    public static byte[] bitTObyte(String come)
    {
        byte[] result = new byte[come.length()/8];
        byte middle = 0;
        for(int x = 0;x<come.length()/8;x++)
        {
            String bString = come.substring(x*8,x*8+8);
            for(int i = bString.length()-1,j=0;i>=0;i--,j++)
            {
                middle+=(Byte.parseByte(bString.charAt(i)+"")*Math.pow(2,j));
            }
            result[x] = middle;
            middle = 0;
        }
        return result;
    }
    //二进制字符串转为二维数组
    public static int[][] StringToInt(String chuan)
    {
        int [][] result = new int[chuan.length()/8][8];
        int number = 0;
        for(int i = 0;i<chuan.length()/8;i++){
            for(int j = 0;j<8;j++){
                result[i][j] = Integer.parseInt(chuan.substring(number,number+1));
                number++;
            }
        }
        return result;
    }

    public static int[][] reverse(int[][] temp) {
        int[][] result = new int[temp[0].length][temp.length];
        for(int i = 0;i<temp.length;i++){
            for(int j = 0;j<temp[i].length;j++){
                result[j][i] = temp[i][j];
            }
        }
        return result;
    }
}
