package com.example.demo.controller;

import com.example.demo.domain.BookUploadRecords;
import com.example.demo.exception.NotFindException;
import com.example.demo.service.Impl.BookServiceImpl;
import com.example.demo.service.Impl.RecordsServiceImpl;
import com.example.demo.util.GeneralUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpSession;
import java.awt.print.Book;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
* @Description:测试框架运行
* @Author: chenjiajun
* @Date: 2021/2/24
*/

@Controller
@MapperScan("com.example.demo.mapper.UserMapper")
//@RestController
public class controller {
        //----------以下为变量声明----------
    private GeneralUtil generalUtil = new GeneralUtil();
    @Autowired
    private RecordsServiceImpl recordsService;
        //------------框架测试--------
    /**
     * @Description: 测试框架启动
     * @Param: []
     * @return: java.lang.String
     * @Author: chenjiajun
     * @Date: 2021/2/19
     */
     @RequestMapping("/hello")
     public String hello(){
         return "hello";
     }

     @RequestMapping("getcode")
    public String GetCode(){
         return generalUtil.GetCode();
     }

     /**
     * @Description: 测试错误springboot自动拦截异常跳转页面
     * @Param: []
     * @return: java.lang.String
     * @Author: chenjiajun
     * @Date: 2021/3/8
     */
     @RequestMapping("/test404")
    public String TestError()
     {
         int a = 10/0;
//         String blog = null;
//         if(blog == null)throw new NotFindException("dee");
         return "homepage";
     }

     @RequestMapping("/test")
    public  String Test(){
         return "test";
     }

//     @RequestMapping("/getCheckcode")
//    public  void GetCheckCode(RedirectAttributes redirectAttributes){
//         redirectAttributes.addFlashAttribute("code",generalUtil.GetCode());
//     }

    /**
    * @Description: 登出映射
    * @Param: [session]
    * @return: java.lang.String
    * @Author: chenjiajun
    * @Date: 2021/4/1
    */
    @RequestMapping("/layout")
    public String layout(HttpSession session){
        Enumeration em = session.getAttributeNames();
        while(em.hasMoreElements()){
            session.removeAttribute(em.nextElement().toString());
        }
        return "redirect:/homepage";
    }

    /**
    * @Description: 上传图书申请的服务测试
    * @Param: [bookName, publisher, author, edition, cip, language, avart]
    * @return: com.example.demo.domain.BookUploadRecords
    * @Author: chenjiajun
    * @Date: 2021/4/13
    */
    @PostMapping("/uploadTest")
    @ResponseBody
    public BookUploadRecords uploadTest(@RequestParam(value = "bookName") String bookName,
                                 @RequestParam(value = "publisher") String publisher,
                                 @RequestParam(value = "author") String author,
                                 @RequestParam(value = "edition") String edition,
                                 @RequestParam(value = "cip") String cip,
                                 @RequestParam(value = "language") String language,
                                 @RequestParam(value = "avart") MultipartFile avart) throws FileNotFoundException {
        //参数
        Long orgid = 100000003L;
        String orgname = "时代云图";
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        dateFormat.format(date);
        BookUploadRecords bupr = new BookUploadRecords(date,orgid,orgname,bookName,author,language,publisher,edition,cip,"审核中",date);
//        System.out.println(bupr.getRecordsCreatetime());
        //先插入不带图的记录
        //再按照获取的id给图片重新命名再修改记录
        bupr = recordsService.uploadRecords(bupr);

        //获取图片文件
        if (!avart.isEmpty()){
            try {
                BufferedOutputStream outputStream = new BufferedOutputStream(
                        new FileOutputStream( new File("E:\\localImg\\"+bupr.getRecordsId().toString()+".jpg")));//图片保存路径，暂时用本地路径来进行演示
                outputStream.write(avart.getBytes());
                outputStream.flush();
                outputStream.close();
                bupr.setRecordsBookCover(bupr.getRecordsId().toString()+".jpg");
                recordsService.updateUploadRecords(bupr);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bupr;
    }

    @GetMapping("/getUploadTest")
//    @ResponseBody
    public String getUploadTest(Model model) throws IOException{
        //获取所有的上传记录
        List<BookUploadRecords> list = recordsService.getAllUploadRecords();
        //d对所有记录的对象中的图片的路径进行重新匹配，将得到的图片转换成二进制数组传回
        //将返回给前端的数据用map封装起来
//        Map<BookUploadRecords,String> map = new HashMap<>();
//        List<Map> result = new ArrayList<>();
//        List<String> imgByte = new ArrayList<>();

        for (BookUploadRecords records : list) {
            //拼接图片保存的路径以获取到本地的存储路径
            String imgsrc = "/localImg/"+records.getRecordsBookCover();
//            FileInputStream fileInputStream = new FileInputStream(imgsrc);
//            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            byte[] b = new byte[1024];
//            int len = -1;
//            while((len = fileInputStream.read(b)) != -1) {
//                bos.write(b, 0, len);
//            }
//            byte[] fileByte = bos.toByteArray();
//            BASE64Encoder encoder = new BASE64Encoder();
//            String data = encoder.encode(fileByte);
//            map.put(records,data);
            records.setRecordsBookCover(imgsrc);
        }
//        result.add(map);
//        model.addAttribute("result",list);
          model.addAttribute("records_list",list);
        return "org/uploadrecords";
    }
}
