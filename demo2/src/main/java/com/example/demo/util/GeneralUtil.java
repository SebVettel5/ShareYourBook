package com.example.demo.util;

import com.example.demo.domain.Administrator;
import com.example.demo.domain.BookUploadRecords;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author ：chenjiajun
 * @description：常用工具类
 * @date ：2021/2/20 15:41
 */
public class GeneralUtil {
    
    /**
    * @Description: 比较操作对象和目标对象的授权等级，
    * @Param: [operator, target]
    * @return: boolean
    * @Author: chenjiajun
    * @Date: 2021/2/20
    */
    public boolean CheckAdminSecureLevel(Administrator operator,Administrator target){
        return operator.getAdminSecureLevel() > target.getAdminSecureLevel();
    }

    public String GetCode(){
        return "11111";
    }

    /**
    * @Description: 生成随机数方法
    * @Param: []
    * @return: java.lang.String
    * @Author: chenjiajun
    * @Date: 2021/3/17
    */
    public String GetCheckCode(){
        int ran = (int) ((Math.random() * 9 + 1) * Math.pow(10,  4));
        System.out.println(ran);
        return String.valueOf(ran);
    }

    /**
    * @Description: 工具，利用id给图片重新命名
    * @Param: [avart, id]
    * @return: java.lang.String
    * @Author: chenjiajun
    * @Date: 2021/4/25
    */
    public static String getImgSrc(MultipartFile avart,Long id){
        String cover = "";
        if (!avart.isEmpty()){
            try {
                BufferedOutputStream outputStream = new BufferedOutputStream(
                        new FileOutputStream( new File("E:\\localImg\\"+id.toString()+".jpg")));//图片保存路径，暂时用本地路径来进行演示
                outputStream.write(avart.getBytes());
                outputStream.flush();
                outputStream.close();
                cover =id.toString()+".jpg";
            } catch (IOException e) {
                e.printStackTrace();
//                model.addAttribute("optionMessage","内部错误！请稍后再试！");
//                return "org/uploadrecords";
            }
        }
        return cover;
    }

    /**
    * @Description: uploadRecords分页查询预封装函数
    * @Param: [list]
    * @return: com.github.pagehelper.PageInfo<com.example.demo.domain.BookUploadRecords>
    * @Author: chenjiajun
    * @Date: 2021/4/25
    */
    public static PageInfo<BookUploadRecords> DataPre(List<BookUploadRecords> list){
        PageInfo<BookUploadRecords> pageInfo = new PageInfo<>(list);
        //对查询到的对象进行处理，主要是时间转换和图片的地址拼接
        for (BookUploadRecords records : list) {
            //图片地址拼接
            String imgsrc = "/localImg/"+records.getRecordsBookCover();
            records.setRecordsBookCover(imgsrc);
            //时间转换
            SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date a = records.getRecordsCreatetime();
        }
        System.out.println(pageInfo);
        return pageInfo;
    }

}
