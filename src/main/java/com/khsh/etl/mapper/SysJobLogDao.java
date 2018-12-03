package com.khsh.etl.mapper;

import com.ejet.comm.exception.CoBusinessException;
import com.khsh.etl.model.SysJobLogModel;
import com.khsh.etl.vo.SysJobLogVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysJobLogDao {


	public void insertSingle(SysJobLogModel obj) throws CoBusinessException;

	public int insertAutoKey(SysJobLogModel obj) throws CoBusinessException;

	public void update(SysJobLogModel obj) throws CoBusinessException;

	public void delete(SysJobLogModel obj) throws CoBusinessException;

	public abstract SysJobLogModel findByPK(SysJobLogModel obj) throws CoBusinessException;

	public abstract List<SysJobLogModel>  queryByCond(SysJobLogModel obj) throws CoBusinessException;

	public abstract List<SysJobLogVO> queryByPage(SysJobLogVO obj) throws CoBusinessException;

	public abstract Integer  findMaxId(SysJobLogModel obj) throws CoBusinessException;

    public void deleteBatch(SysJobLogVO obj) throws CoBusinessException;


}
