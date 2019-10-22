package com.pjt.survey.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pjt.survey.dao.SearchLabelDao;
import com.pjt.survey.vo.comLabelVo;

/** 
 * @Description : 
 * @author 송상구
 * @since 2018. 4. 11. 오전 10:57:47
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
@Service
public class SearchLabelSvc {
	
	@Autowired
	private SearchLabelDao searchLabelDao;
	
	public List<comLabelVo> selectLabelInfo( comLabelVo paramVo ) {
		return searchLabelDao.selectLabel(paramVo);
	}
	
	
}
