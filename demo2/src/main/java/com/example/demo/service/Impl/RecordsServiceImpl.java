package com.example.demo.service.Impl;

import com.example.demo.ViewObject.CommentVO;
import com.example.demo.domain.*;
import com.example.demo.mapper.BookUploadRecordsMapper;
import com.example.demo.mapper.BorrowFormsMapper;
import com.example.demo.mapper.CollectionMapper;
import com.example.demo.service.RecordsService;
import com.example.demo.util.GeneralUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author ：chenjiajun
 * @description：collection服务实现类
 * @date ：2021/3/24 20:36
 */
@Service
public class RecordsServiceImpl implements RecordsService {
    //全局变量定义
    //两个set，设置验证符合要求的状态
    private Set<String> dataStatusTypeSet = new HashSet<String>() {{
        add("false");
        add("true");
    }};
    private Set<String>  recordsStatusTypeSet= new HashSet<String>(){{
        add("审核中");
        add("已撤销");
        add("未通过");
        add("审核通过");
    }};


    @Autowired
    private CollectionMapper collectionMapper;
    @Autowired
    private BorrowFormsMapper borrowFormsMapper;
    @Autowired
    private BookUploadRecordsMapper bookUploadRecordsMapper;

    /**
    * @Description: 返回所有书籍收藏记录
    * @Param: []
    * @return: java.util.List<com.example.demo.domain.Collection>
    * @Author: chenjiajun
    * @Date: 2021/3/25
    */
    @Override
    public CommentVO<Collection> getAllCollections() {
        List<Collection> l =collectionMapper.selectAll();
        return new CommentVO<Collection>(0,"",(long)l.size(),l);
    }

//    @Override
//    public CommentVO<Collection> getCollectionsByUserId(HttpServletRequest request) {
//        HttpSession s = request.getSession();
//        User u = (User) s.getAttribute("user");
////        System.out.println(u);
//        List<Collection> l = collectionMapper.selectByUserId(u.getUserId());
//        return new CommentVO<Collection>(0,"",(long)l.size(),l);
//    }

    /**
    * @Description: 为个人用户图书收藏记录提供分页查询
    * @Param: [page, limit]
    * @return: com.example.demo.ViewObject.CommentVO<com.example.demo.domain.Collection>
    * @Author: chenjiajun
    * @Date: 2021/4/1
    */
    @Override
    public CommentVO<Collection> getUserCollection(Integer page, Integer limit,HttpServletRequest request) {
        HttpSession session = request.getSession();
        User u = (User) session.getAttribute("user");
        PageHelper.startPage(page,limit);
        List<Collection> l = collectionMapper.selectByUserId(u.getUserId());
        return new CommentVO<Collection>(0,"",(long)l.size(),l);
    }


    /**
    * @Description: 获取所有的书籍借阅订单记录
    * @Param: []
    * @return: com.example.demo.ViewObject.CommentVO<com.example.demo.domain.BorrowForms>
    * @Author: chenjiajun
    * @Date: 2021/3/31
    */
    @Override
    public CommentVO<BorrowForms> getAllBorrowForms() {
        List<BorrowForms> l =borrowFormsMapper.selectAll();
        return  new CommentVO<BorrowForms>(0,"",(long)l.size(),l);
    }

    @Override
    public BookUploadRecords uploadRecords(BookUploadRecords bookUploadRecords) {
        //先将记录插入表中
        bookUploadRecordsMapper.insertRecords(bookUploadRecords);
//        return bookUploadRecordsMapper.selectOne(bookUploadRecords);
        //再重新获取带id的记录对象
//        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        Date d = bookUploadRecords.getRecordsUpdateDatetime();
//        dateFormat.format(d);
//        System.out.println(bookUploadRecords.getRecordsId());
        return bookUploadRecords;
    }


    /**
    * @Description: 插入一条新记录
    * @Param: [org, bookName, author, language, publisher, edition, cip, avart]
    * @return: java.lang.String
    * @Author: chenjiajun
    * @Date: 2021/4/29
    */
    @Override
    public String insertUploadRecords(Organization org, String bookName, String author, String language,
                                      String publisher, String edition, String cip, MultipartFile avart,
                                      BookUploadRecords bookUploadRecords) {
        //参数
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        dateFormat.format(date);
        //先插入一个条不含图的记录
        BookUploadRecords bupr = new BookUploadRecords(date,org.getOrgId(),org.getOrgName(),bookName,author,language,publisher,edition,cip,"审核中",date);
        bupr.setRecordsDataStatus(true);

        //再按照获取的id给图片重新命名再修改记录
        bupr = uploadRecords(bupr);

        //图片处理逻辑
        //如果是重新申请提交记录且没有改变图片的话，对图片进行覆盖操作
        if (bookUploadRecords != null && avart.isEmpty()){
            bupr.setRecordsBookCover(bookUploadRecords.getRecordsBookCover());
        }
        //对于是第一次上传的，直接解析图片
        //将图片文件存储在指定文件，并进行重命名，以记录id为名
        else  if (!avart.isEmpty()){
            try {
                BufferedOutputStream outputStream = new BufferedOutputStream(
                        new FileOutputStream( new File("E:\\localImg\\"+bupr.getRecordsId().toString()+".jpg")));//图片保存路径，暂时用本地路径来进行演示
                outputStream.write(avart.getBytes());
                outputStream.flush();
                outputStream.close();
                bupr.setRecordsBookCover(bupr.getRecordsId().toString()+".jpg");
            } catch (IOException e) {
                e.printStackTrace();
                //返回数据处理结果
                return "内部错误！请稍后再试！";
            }
        }
        //最后进行记录更新
        if(bookUploadRecordsMapper.updateByPrimaryKey(bupr) == 0)
            return  "内部错误！请稍后再试！";
        else {
            return"上传成功！";
        }
    }


    /**
    * @Description: 按条件分页获取所有记录
    * @Param: [orgid, status, pageNum, pageSize, dataStatus]
    * @return: com.github.pagehelper.PageInfo<com.example.demo.domain.BookUploadRecords>
    * @Author: chenjiajun
    * @Date: 2021/4/29
    */
    @Override
    public PageInfo<BookUploadRecords> getOrgUploadRecordsByStatus(Long orgid, String status,
                                                                   int pageNum, int pageSize,Boolean dataStatus) throws ParseException {
        //进行分页查询
        PageHelper.startPage(pageNum,pageSize);
        //按条件查询到所有数据
        List<BookUploadRecords> list = bookUploadRecordsMapper.selectOrgUploadRecordsByStatus(orgid,status,dataStatus);
        return GeneralUtil.DataPre(list);
    }

    /**
    * @Description: 分页获取所有记录
    * @Param: [orgId, pageNum, pageSize]
    * @return: com.github.pagehelper.PageInfo<com.example.demo.domain.BookUploadRecords>
    * @Author: chenjiajun
    * @Date: 2021/4/29
    */
    public PageInfo<BookUploadRecords> getAllUploadRecordPages(Long orgId, int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<BookUploadRecords> list = bookUploadRecordsMapper.selectAllByStatus(orgId, true);
        return GeneralUtil.DataPre(list);
    }
    

    /**
    * @Description: 按照orgid 和 reocrdsId获取对象
    * @Param: [orgId, recordsId]
    * @return: com.example.demo.domain.BookUploadRecords
    * @Author: chenjiajun
    * @Date: 2021/4/29
    */
    @Override
    public BookUploadRecords getBookUploadsById(Long orgId, Long recordsId) {
        BookUploadRecords bookUploadRecords = bookUploadRecordsMapper.findOne(orgId,recordsId);
        //进行图片路径处理
//        if (bookUploadRecords != null)bookUploadRecords.setRecordsBookCover(GeneralUtil.imagUrl(bookUploadRecords.getRecordsBookCover()));
        return bookUploadRecords;
    }

    /**
    * @Description: 将指定多条数据进行改变数据状态的操作，传入操作人姓名，同时更新操作时间
    * @Param: [optionName, arr]
    * @return: java.lang.String
    * @Author: chenjiajun
    * @Date: 2021/4/28
    */
    public String changeUploadRecordsById(String optionName, String[] arr){
//        for (String s:arr
//             ) {
//            System.out.println(s);
//        }
        String statusType = arr[0];
        System.out.println(arr[0]);
        //当前操作时间
        Date optionTime = new Date();
        //验证状态设定合法性，获取设定的状态
        if (!dataStatusTypeSet.contains(statusType) && !recordsStatusTypeSet.contains(statusType)){
            System.out.println(arr[0]);
            return  "操作无效，请刷新页面后重试！";
        }
        else{
            Boolean status = Boolean.valueOf(arr[0]);
            int len = arr.length;
            //从数组第二个位置开始进行改变状态的操作,count用来计算剩余没有成功的记录条数
            int count = 0;
            for (int i = 1;i < len;i++){
                //撤销操作，标记状态为撤销
                if (recordsStatusTypeSet.contains(statusType))
                    count += bookUploadRecordsMapper.withdrawById(new Long(arr[i]),arr[0],optionTime,optionName+"操作");
                //假删除
                else if (dataStatusTypeSet.contains(statusType))
                    count += bookUploadRecordsMapper.deleteById(new Long(arr[i]),status,optionTime,optionName+"操作");
            }
            //返回所有的操作结果
            return "选中"+(len-1)+"条数据，完成"+count+"条申请撤销";
        }
    }


    /**
    * @Description: 根据记录id获取到该条记录，并返回到修改界面
    * @Param: [model, recordsId]
    * @return: java.lang.String
    * @Author: chenjiajun
    * @Date: 2021/4/29
    */
    @Override
    public String getBookUploadsForMod(Model model,Long recordsId) {
        BookUploadRecords bookUploadRecords = bookUploadRecordsMapper.selectOneById(recordsId);
        if (bookUploadRecords != null){
            String s = "/localImg/"+bookUploadRecords.getRecordsBookCover();
            bookUploadRecords.setRecordsBookCover(s);
            model.addAttribute("record",bookUploadRecords);
            return "/org/modification";
        }
        else{
            //不存在记录则返回到列表
            model.addAttribute("erroeMessage","所操作记录不存在，请确认后重试！");
            return "redirect: /org/checkinglist?pageNum=1 ";
        }
    }

    /**
    * @Description: 按照记录的id进行更新
    * @Param: [recId, bookName, author, language, publisherName, edition, cip, cover]
    * @return: java.lang.String
    * @Author: chenjiajun
    * @Date: 2021/4/25
    */
    @Override
    public String updateRecords(Model model,Long recId, String bookName, String author, String language, String publisherName, String edition, String cip, String cover) {
        //获取待更新的记录，然后更新相关字段
        BookUploadRecords bookUploadRecords = bookUploadRecordsMapper.selectOneById(recId);

        if (bookUploadRecords == null)return "当前记录不存在，更新失败！请稍后重试。";
        if (cover.equals(""))cover = bookUploadRecords.getRecordsBookCover();
        System.out.println(cover);
        //更新数据
        bookUploadRecords.resSetBookUploadRecords(bookName,author,language,publisherName,edition,cip,cover,new Date());
        int res = bookUploadRecordsMapper.updateByPrimaryKey(bookUploadRecords);

        String message = "";
        if(res == 1)
            message =  "修改成功!";
        else
            message =  "修改失败,请稍后重试！";

        model.addAttribute("optionMessage",message);
        cover = "/localImg/"+bookUploadRecords.getRecordsBookCover();
        model.addAttribute("record",new BookUploadRecords(recId,bookName,author,language,publisherName,edition,cover,cip));
        return message;
    }


}
