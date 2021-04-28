package com.example.demo.controller;

import com.example.demo.ViewObject.CommentVO;
import com.example.demo.domain.BookUploadRecords;
import com.example.demo.domain.BorrowForms;
import com.example.demo.domain.Collection;
import com.example.demo.domain.Organization;
import com.example.demo.service.Impl.RecordsServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author ：chenjiajun
 * @description：书籍和与书籍相关记录的控制器
 * @date ：2021/3/24 20:31
 */
@Controller
public class BookAndRecordsController {

    @Autowired
    private RecordsServiceImpl recordsService;

    /**
    * @Description: 获取所有的书籍收藏记录，以json的格式返回
    * @Param: []
    * @return: java.util.List<com.example.demo.domain.Collection>
    * @Author: chenjiajun
    * @Date: 2021/3/24
    */
    @RequestMapping("/collection/getAll")
    @ResponseBody
    public CommentVO<Collection> getAllCollection(){
        return recordsService.getAllCollections();
    }

    /**
    * @Description: 获取所有书籍借阅订单
    * @Param: [status]
    * @return: com.example.demo.ViewObject.CommentVO<com.example.demo.domain.BorrowForms>
    * @Author: chenjiajun
    * @Date: 2021/3/31
    */
    @RequestMapping("/BorrowForms/getAll")
    @ResponseBody
    public CommentVO<BorrowForms> getAllForms(String status){
        return recordsService.getAllBorrowForms();
    }

    /***
    * @Description: 按照id获取用户所有的收藏并分页
    * @Param: [request]
    * @return: com.example.demo.ViewObject.CommentVO<com.example.demo.domain.Collection>
    * @Author: chenjiajun
    * @Date: 2021/4/10
    */
    @RequestMapping("/collection/getUserCollections")
    @ResponseBody
    public CommentVO<Collection> getAllCollectionByUid( @RequestParam("page")Integer page,
                                                        @RequestParam("limit")Integer limit,
                                                        HttpServletRequest request
                                                      ){
        return recordsService.getUserCollection(page,limit,request);
    }

    /**
     * @Description: 上传图书申请的服务测试
     * @Param: [bookName, publisher, author, edition, cip, language, avart]
     * @return: com.example.demo.domain.BookUploadRecords
     * @Author: chenjiajun
     * @Date: 2021/4/13
     */
    @PostMapping("/uploadBook")
    public String uploadTest(HttpSession session,Model model,
                                        @RequestParam(value = "bookName") String bookName,
                                        @RequestParam(value = "publisher") String publisher,
                                        @RequestParam(value = "author") String author,
                                        @RequestParam(value = "edition") String edition,
                                        @RequestParam(value = "cip") String cip,
                                        @RequestParam(value = "language") String language,
                                        @RequestParam(value = "avart") MultipartFile avart) throws FileNotFoundException {
        //获取当前上传书籍的机构
        Organization org = (Organization) session.getAttribute("org");

        //参数
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        dateFormat.format(date);
        BookUploadRecords bupr = new BookUploadRecords(date,org.getOrgId(),org.getOrgName(),bookName,author,language,publisher,edition,cip,"审核中",date);
        bupr.setRecordsDataStatus(true);
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
                model.addAttribute("optionMessage","内部错误！请稍后再试！");
                return "uploadBook";
            }
        }
        model.addAttribute("optionMessage","上传成功！");
        return "uploadBook";
    }


    /***
    * @Description: 按照orgid来请求指定审核状态的记录返回给innnerhtml,并进行分页查询
    * @Param: [model, httpSession, pageNum]
    * @return: java.lang.String
    * @Author: chenjiajun
    * @Date: 2021/4/17
    */
    @RequestMapping("/org/getData")
    public String orgGetData(Model model, HttpSession httpSession, @RequestParam int pageNum,@RequestParam String listType) throws ParseException {
        //获取机构用户对象
        Organization org = (Organization) httpSession.getAttribute("org");
        //进行分页查询，将分页信息和查询到的数据分别放到model中,pageSize为可调整的单页数据量

        model.addAttribute("result",recordsService.getOrgUploadRecordsByStatus(org.getOrgId(),listType,pageNum,4,true));
        switch (listType){
            case "checkingList":model.addAttribute("result",recordsService.getOrgUploadRecordsByStatus(org.getOrgId(),"审核中",pageNum,4,true));
                                break;
            case "passedList":model.addAttribute("result",recordsService.getOrgUploadRecordsByStatus(org.getOrgId(),"审核通过",pageNum,4,true));
                             break;
            case "refusedList":model.addAttribute("result",recordsService.getOrgUploadRecordsByStatus(org.getOrgId(),"未通过",pageNum,4,true));
                            break;
            case "withdrawList":model.addAttribute("result",recordsService.getOrgUploadRecordsByStatus(org.getOrgId(),"已撤销",pageNum,4,true));
                break;
//            default:;
        }
        return "org/uploadrecords";
    }


    /**
    * @Description: 分页获取全部未删除的申请记录
    * @Param: [model, session, pageNum]
    * @return: java.lang.String
    * @Author: chenjiajun
    * @Date: 2021/4/27
    */
    @RequestMapping("/org/getAllBookUploadRecords")
    public String orgGetAllRecords(Model model,HttpSession session,@RequestParam int pageNum,@RequestParam String listType){
        Organization org = (Organization) session.getAttribute("org");
        model.addAttribute("result",recordsService.getAllUploadRecordPages(org.getOrgId(),pageNum,4));
        System.out.println(1);
        return "org/allrecords";
    }




//    /***
//    * @Description: BookUploadRecords单条数据设置不可查看
//    * @Param: [model, httpSession, arr]
//    * @return: java.lang.String
//    * @Author: chenjiajun
//    * @Date: 2021/4/19
//    */
//    @RequestMapping("/org/posttest")
//    @ResponseBody
//    public String orgWithdrawRecord(Long recordId) throws ParseException {
//        //影响一条数据则为修改成功
//        if (recordsService.deletUploadRecordsById(recordId, Boolean.FALSE) == 1){
//            //返回撤销成功信息
//            return "成功撤销记录！";
//        }
//        else{
//           return "撤销失败，请刷新后重试!";
//        }
//    }

    /***
    * @Description: 对选中的图书申请记录进行假删除（设置不可访问），并返回处理结
    * @Param: [model, httpSession, arr]
    * @return: java.lang.String
    * @Author: chenjiajun
    * @Date: 2021/4/20
    */
    @RequestMapping("/org/changeUploadRecordsStatus")
    @ResponseBody
    public String orgChangeRecordsStatus(HttpSession httpSession,@RequestBody String[] arr){
        //获取机构用户对象
        Organization org = (Organization) httpSession.getAttribute("org");
        //传入操作人昵称和待处理的操作数据
        return recordsService.changeUploadRecordsById(org.getOrgName(),arr);
    }

//    @RequestMapping("/org/checkinglist/withdraw")
//    @ResponseBody
//    public String orgWithdrawRecords(Model model,HttpSession httpSession,@RequestBody String[] arr){
//        //获取机构用户对象
//        Organization org = (Organization) httpSession.getAttribute("org");
//        return recordsService.withdrawUploadRecordsById(org.getOrgId(),arr);
//    }


    @RequestMapping("/datatest")
    @ResponseBody
    public PageInfo<BookUploadRecords> dataTest(@RequestParam int pageNum) throws ParseException {
      return   recordsService.getOrgUploadRecordsByStatus((long) 100000003,"审核中",pageNum,4,true);
    }

    /**
    * @Description: 修改记录信息
    * @Param: [model, recordsId]
    * @return: java.lang.String
    * @Author: chenjiajun
    * @Date: 2021/4/25
    */
    @RequestMapping("/org/modification")
    public String orgModification(Model model,@RequestParam Long recordsId){
        return recordsService.getBookUploadsByid(model,recordsId);
    }

    /**
    * @Description: 更新一条上传申请记录
    * @Param: [session, model, recordsId, bookName, publisherName, author, edition, cip, language, avart]
    * @return: java.lang.String
    * @Author: chenjiajun
    * @Date: 2021/4/25
    */
    @PostMapping("/org/modificationPost")
    public String orgModificationPost(HttpSession session,Model model,
                                      @RequestParam(value = "recordsId")String recordsId,
                                      @RequestParam(value = "bookName")String bookName,
                                      @RequestParam(value = "publisherName")String publisherName,
                                      @RequestParam(value = "author")String author,
                                      @RequestParam(value = "edition")String edition,
                                      @RequestParam(value = "cip")String cip,
                                      @RequestParam(value = "language")String language,
                                      @RequestParam(value = "avart")MultipartFile avart){
        //处理图片
        Long recId = new Long(recordsId);
        String cover = "";
        if (!avart.isEmpty()){
            try {
                BufferedOutputStream outputStream = new BufferedOutputStream(
                        new FileOutputStream( new File("E:\\localImg\\"+recId.toString()+".jpg")));//图片保存路径，暂时用本地路径来进行演示
                outputStream.write(avart.getBytes());
                outputStream.flush();
                outputStream.close();
                cover =recId.toString()+".jpg";
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("optionMessage","内部错误！请稍后再试！");
//                return "org/uploadrecords";
            }
        }
        //获取更新的结果
        recordsService.updateRecords(model,recId,bookName,author,language,publisherName,edition,cip,cover);
        return "/org/modification";
    }

    @RequestMapping("/org/putaway")
    public String orgPutaway(){
        return "org/putaway";
    }
}
