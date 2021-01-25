package top.chokhoou.healthcardjob.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import top.chokhoou.healthcardjob.common.converters.SpringBootJpaConverterListJson;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

/**
 * 健康卡
 * <p>
 * 不要问我为什么命名这么low(x)
 *
 * @author ChoKhoOu
 */
@Data
@Entity
@Accessors(chain = true)
public class Card {
    /**
     * id
     */
    @Id
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
    @Convert(converter = SpringBootJpaConverterListJson.class)
    @Column(columnDefinition = "TEXT")
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

    /**
     * 真实手机号
     */
    private String realPhone;

    /**
     * 真实邮箱
     */
    private String realEmail;

    /**
     * 是否需要帮他提交
     */
    private Integer needCommit;
}
