package com.pjt.survey.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pjt.survey.vo.VrfcBaseDVo;

/** 
 * @Description : 
 * @author 장경준
 * @since 2018. 6. 11.
 * @version 1.0 
 * @see 
 * 
 * <pre> 
 * == 개정이력(Modification Information) == 
 * 
 * 	  수정일 			수정자 				수정내용 
 * -----------	-------------	--------------------------- 
 * 2018. 6. 11.		장경준				최초생성 
 * 
 * </pre> 
 */
@Repository
public class ExcelLoadDao {
	
	@Autowired
    private SqlSessionTemplate sqlSession;
    
    // end must be point . 
    private static final String NS = "com.sqlmap.";
    
    public List<HashMap> selectComVrfcBaseDCol( HashMap map ) {
    	return sqlSession.selectList(NS+"selectComVrfcBaseDCol", map);
    }
    
    public List<VrfcBaseDVo> selectVrfcBaseD( HashMap map ) {
    	return sqlSession.selectList(NS+"selectComVrfcBaseDList", map);
    }
    
    public void mergeInsertComVrfcBaseDMap( HashMap map ) {
    	sqlSession.insert(NS+"mergeInsertComVrfcBaseDMap", map);
    }
    
    public void updateDelYnComVrfcBaseD( HashMap map ) {
    	sqlSession.update(NS+"updateDelYnComVrfcBaseD", map);
    }
    
}
