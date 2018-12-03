package com.khsh.etl.mapper;

import com.ejet.comm.exception.CoBusinessException;
import com.khsh.etl.model.SysJobParamModel;
import com.khsh.etl.vo.SysJobParamKettleVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysJobParamDao {

	public void insertSingle(SysJobParamModel obj) throws CoBusinessException;

	public int insertAutoKey(SysJobParamModel obj) throws CoBusinessException;

	public void update(SysJobParamModel obj) throws CoBusinessException;

	public void delete(SysJobParamModel obj) throws CoBusinessException;

	public abstract SysJobParamModel  findByPK(SysJobParamModel obj) throws CoBusinessException;
	public abstract List<SysJobParamModel>  queryByCond(SysJobParamModel obj) throws CoBusinessException;

	public abstract List<SysJobParamModel>  queryByPage(SysJobParamModel obj) throws CoBusinessException;

	public abstract Integer  findMaxId(SysJobParamModel obj) throws CoBusinessException;


    public abstract SysJobParamKettleVO findKettleByPK(SysJobParamKettleVO obj) throws CoBusinessException;
    public abstract List<SysJobParamKettleVO> queryKettleByPage(SysJobParamKettleVO obj) throws CoBusinessException;


}
