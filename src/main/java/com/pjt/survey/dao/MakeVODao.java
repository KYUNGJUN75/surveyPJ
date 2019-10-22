package com.pjt.survey.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pjt.survey.vo.ComVrfcBaseDtlVo;
import com.pjt.survey.vo.ComVrfcBaseVo;
import com.pjt.survey.vo.SqlMapVO;


// DAO Implementation
@Repository
public class MakeVODao {
        
	@Autowired
    private SqlSessionTemplate sqlSession;
    
    // end must be point . 
    private static final String NS = "com.sqlmap.";

    public List<SqlMapVO> selectMakeVO(SqlMapVO SqlMapVO) {
    	    	
        return sqlSession.selectList(NS+"selectMakeVO", SqlMapVO);
    }
    
    public List<SqlMapVO> selectMakeVO_IN(SqlMapVO SqlMapVO) {
    	
    	return sqlSession.selectList(NS+"selectMakeVO_IN", SqlMapVO);
    }
    
    public int mergeInsertComVrfcBase(ComVrfcBaseVo ComVrfcBaseVo) {
    	
        return sqlSession.insert(NS+"mergeInsertComVrfcBase", ComVrfcBaseVo);
    }
    
    public int deleteComVrfcBaseDtl(ComVrfcBaseDtlVo ComVrfcBaseDtlVo) {
    	
        return sqlSession.delete(NS+"deleteComVrfcBaseDtl", ComVrfcBaseDtlVo);
    }
    
    public int mergeInsertComVrfcBaseDtl(ComVrfcBaseDtlVo ComVrfcBaseDtlVo) {
    	
        return sqlSession.insert(NS+"mergeInsertComVrfcBaseDtl", ComVrfcBaseDtlVo);
    }
    
    public List<SqlMapVO> selectBusiTableMap(HashMap<String, Object> paramMap) {
    	
        return sqlSession.selectList(NS+"selectBusiTableMap", paramMap);
    }
    
	public List<SqlMapVO> selectBusiInTableMap(HashMap<String, Object> paramMap) {
    	
        return sqlSession.selectList(NS+"selectBusiInTableMap", paramMap);
    }
	
	public List<SqlMapVO> runSQL(HashMap<String, Object> paramMap) {
    	
        return sqlSession.selectList(NS+"runSQL", paramMap);
    }
}
	

