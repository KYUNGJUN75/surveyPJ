package com.pjt.survey.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pjt.survey.vo.comLabelVo;

/** 
 * @Description : 
 * @author 송상구
 * @since 2018. 4. 11. 오전 10:54:12
 * @version 1.0 
 * @see 
 * 
 * <pre> 
 * == 개정이력(Modification Information) == 
 * 
 * 	  수정일 			수정자 				수정내용 
 * -----------	-------------	--------------------------- 
 * 2018. 4. 11.		송상구				최초생성 
 * 
 * </pre> 
 */
@Repository
public class SearchLabelDao {
	
	@Autowired
    private SqlSessionTemplate sqlSession;
    
    // end must be point . 
    private static final String NS = "com.sqlmap.";
    
    public List<comLabelVo> selectLabel( comLabelVo paramVo ) {
    	System.out.println("selectQuery --> "+paramVo.getLblNm());
    	return sqlSession.selectList(NS+"selectLabelId", paramVo);
    }
    
}
