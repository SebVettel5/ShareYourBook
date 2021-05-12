package com.example.demo.controller;

import com.example.demo.ViewObject.CommentVO;
import com.example.demo.domain.*;
import com.example.demo.service.Impl.RecordsServiceImpl;
import com.example.demo.util.GeneralUtil;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xpath.internal.operations.Or;
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

/**
 * @author ：chenjiajun
 * @description：书籍和与书籍相关记录的控制器
 * @date ：2021/3/24 20:31
 */
@Controller
public class RecordsController {

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

    @RequestMapping("/org/uploadbookpage")
    public String UploadBook(Model model){
        model.addAttribute("flashRecord",new BookUploadRecords());
        return "uploadbook";
    }

    /**
     * @Description: 上传图书申请，同时也是重新上传图书的申请
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
                                        @RequestParam(value = "avart") MultipartFile avart,
                                        @RequestParam(value = "id") Long flashRecordsId) throws FileNotFoundException {
        //获取当前上传书籍的机构
        Organization org = (Organization) session.getAttribute("org");
        //验证数据操作权限
        BookUploadRecords bookUploadRecords = recordsService.getBookUploadsById(org.getOrgId(),flashRecordsId);
        if (flashRecordsId !=null && bookUploadRecords == null){
            model.addAttribute("optionMessage","没有相关操作权限！请重新确认后进行操作！");
            return "uploadBook";
        }
        //返回上传操作反馈信息
        model.addAttribute("optionMessage",recordsService.insertUploadRecords(org,bookName,author,language,publisher,edition,cip,avart,bookUploadRecords));
        model.addAttribute("flashRecord",new BookUploadRecords());
        return "uploadBook";
    }


    /***
    * @Description: 按照orgid来请求指定审核状态的记录返回给innnerhtml,并进行分页查询
    * @Param: [model, httpSession, pageNum]
    * @return: java.lang.String
    * @Author: chenjiajun
    * @Date: 2021/4/17
    */
    @RequestMapping("/org/uploadrecords")
    public String orgGetData(Model model, HttpSession httpSession, @RequestParam int pageNum,@RequestParam String listType) throws ParseException {
        //获取机构用户对象
        Organization org = (Organization) httpSession.getAttribute("org");
        //进行分页查询，将分页信息和查询到的数据分别放到model中,pageSize为可调整的单页数据量
        System.out.println(listType+" "+pageNum);
        model.addAttribute("listType",listType);
//        model.addAttribute("result",recordsService.getOrgUploadRecordsByStatus(org.getOrgId(),listType,pageNum,4,true));
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
        model.addAttribute("listType",listType);
        model.addAttribute("result",recordsService.getAllUploadRecordPages(org.getOrgId(),pageNum,4));
        return "org/uploadrecords";
    }

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

    @RequestMapping("/datatest")
    @ResponseBody
    public PageInfo<BookUploadRecords> dataTest(int pageNum) throws ParseException {
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
        return recordsService.getBookUploadsForMod(model,recordsId);
    }

    /**
    * @Description: 更新一条上传申请记录
    * @Param: [session, model, recordsId, bookName, publisherName, author, edition, cip, language, avart]
    * @return: java.lang.String
    * @Author: chenjiajun
    * @Date: 2021/4/25
    */
    @PostMapping("/org/modificationPost")
    public String orgModificationPost(Model model,
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

    /**
    * @Description: 获取当前的记录，加载到上传页面
    * @Param: [model, records]
    * @return: java.lang.String
    * @Author: chenjiajun
    * @Date: 2021/4/29
    */
    @RequestMapping("/org/reUploadRecord")
    public String reUploadRecord(HttpSession session,Model model,@RequestParam Long recordsId){
        Organization org = (Organization) session.getAttribute("org");
        BookUploadRecords records = recordsService.getBookUploadsById(org.getOrgId(),recordsId);
        if(records != null){
            //进行图片地址进行解析
            records.setRecordsBookCover(GeneralUtil.imagUrl(records.getRecordsBookCover()));
            model.addAttribute("flashRecord",records);
        }  else{
            model.addAttribute("optionMessage","所选数据异常，请重试！");
        }
        return "uploadbook";
    }
}
