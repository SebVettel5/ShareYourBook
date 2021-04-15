package com.example.demo.domain;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author ：chenjiajun
 * @description：图书上传申请记录
 * @date ：2021/4/12 22:08
 */
@Data
@Table(name = "bookuploadrecords")
public class BookUploadRecords {
    @Id
    private Long recordsId;
    private Date recordsCreatetime;
    private Long recordsUploadOrgId;
    private String recordsUploadOrgName;
    private Long recordsOptAdminId;
    private String recordsOptAdminNickname;
    private String recordsBookName;
    private String recordsBookAuthor;
    private String recordsBookLanguage;
    private String recordsBookPublisher;
    private String recordsBookEdition;
    private String recordsBookCover;
    private String recordsBookCip;
    private String recordsStatus;
    private Date recordsUpdateDatetime;
    private Long recordsBookId;
    //将数据库查询出来的时间戳转换成字符串返回给前端
    private String createtime;

    //构造函数

    public BookUploadRecords(Date recordsCreatetime, Long recordsUploadOrgId, String recordsUploadOrgName, String recordsBookName, String recordsBookAuthor, String recordsBookLanguage, String recordsBookPublisher, String recordsBookEdition, String recordsBookCip, String recordsStatus, Date recordsUpdateDatetime) {
        this.recordsCreatetime = recordsCreatetime;
        this.recordsUploadOrgId = recordsUploadOrgId;
        this.recordsUploadOrgName = recordsUploadOrgName;
        this.recordsBookName = recordsBookName;
        this.recordsBookAuthor = recordsBookAuthor;
        this.recordsBookLanguage = recordsBookLanguage;
        this.recordsBookPublisher = recordsBookPublisher;
        this.recordsBookEdition = recordsBookEdition;
        this.recordsBookCip = recordsBookCip;
        this.recordsStatus = recordsStatus;
        this.recordsUpdateDatetime = recordsUpdateDatetime;
    }


}
