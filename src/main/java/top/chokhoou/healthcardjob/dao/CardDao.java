package top.chokhoou.healthcardjob.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.chokhoou.healthcardjob.entity.Card;

/**
 * @author ChoKhoOu
 */
@Repository
public interface CardDao extends JpaRepository<Card, Long> {
    /**
     * 通过学号查找健康卡
     *
     * @param gh 学号
     * @return 健康卡信息
     */
    Card findByGh(String gh);
}
