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
    private Boolean recordsDataStatus;

    //构造函数


    public void resSetBookUploadRecords(String recordsBookName, String recordsBookAuthor, String recordsBookLanguage, String recordsBookPublisher, String recordsBookEdition, String recordsBookCip,String recordsBookCover,Date date) {
        this.setRecordsBookName(recordsBookName);
        this.setRecordsBookAuthor(recordsBookAuthor);
        this.setRecordsBookCip(recordsBookCip);
        this.setRecordsBookLanguage(recordsBookLanguage);
        this.setRecordsBookPublisher(recordsBookPublisher);
        this.setRecordsBookEdition(recordsBookEdition);
        if(!recordsBookCover.equals(""))
             this.setRecordsBookCover(recordsBookCover);
        this.setRecordsCreatetime(date);
    }

    public BookUploadRecords(Long recordsId, String recordsBookName, String recordsBookAuthor, String recordsBookLanguage, String recordsBookPublisher, String recordsBookEdition, String recordsBookCover, String recordsBookCip) {
        this.recordsId = recordsId;
        this.recordsBookName = recordsBookName;
        this.recordsBookAuthor = recordsBookAuthor;
        this.recordsBookLanguage = recordsBookLanguage;
        this.recordsBookPublisher = recordsBookPublisher;
        this.recordsBookEdition = recordsBookEdition;
        this.recordsBookCover = recordsBookCover;
        this.recordsBookCip = recordsBookCip;
    }

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
