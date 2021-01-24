package top.chokhoou.healthcardjob.entity.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author ChoKhoOu
 */
@Data
@Accessors(chain = true)
public class CardDTO {
    /**
     * id
     */
    private Long id;

    /**
     * 申请人id
     */
    private Long sqrid;

    /**
     * 申请人mid
     */
    private Long sqbmid;

    /**
     *
     */
    private String fdygh;

    /**
     *
     */
    private String rysf;

    /**
     * 标题
     */
    private String bt;

    /**
     * 申请人名字
     */
    private String sqrmc;

    /**
     * 学号
     */
    private String gh;

    /**
     *
     */
    private String xb;

    /**
     * 学院名称
     */
    private String sqbmcc;

    /**
     * 年级
     */
    private String nj;

    /**
     * 专业
     */
    private String zymc;

    /**
     * 班级名称
     */
    private String bjmc;

    /**
     * 辅导员名称
     */
    private String fdymc;

    /**
     * 宿舍号 床号
     */
    private String ssh;

    /**
     * 电话
     */
    private String lxdh;

    /**
     * 填表日期
     */
    private String tbrq;

    /**
     * 提交时间
     */
    private String tjsj;

    /**
     * 地址
     */
    private String xjzdz;

    /**
     * 地址
     */
    private String jqqx;

    /**
     *
     */
    private String sfqwhb;

    /**
     *
     */
    private String sfjchbjry;

    /**
     *
     */
    private String sfjwhy;

    /**
     *
     */
    private String sfjwhygjdq;

    /**
     *
     */
    private String xrywz;

    /**
     * 地址
     */
    private String jtdz;

    /**
     *
     */
    private Integer grjkzk;

    /**
     * 体温
     */
    private String jrtw;

    /**
     *
     */
    private String qsjkzk;

    /**
     *
     */
    private String jkqk;

    /**
     *
     */
    private List<String> cn;

    /**
     *
     */
    private String bz;

    /**
     *
     */
    private String __type;

    /**
     *
     */
    private String _ext;
}
