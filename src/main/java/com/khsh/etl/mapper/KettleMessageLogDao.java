package com.khsh.etl.mapper;

import com.ejet.comm.exception.CoBusinessException;
import com.khsh.etl.model.KettleMessageLogModel;
import com.khsh.etl.vo.KettleMessageLogVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface KettleMessageLogDao {


	public void insertSingle(KettleMessageLogModel obj) throws CoBusinessException;

	public int insertAutoKey(KettleMessageLogModel obj) throws CoBusinessException;

	public void update(KettleMessageLogModel obj) throws CoBusinessException;

	public void delete(KettleMessageLogModel obj) throws CoBusinessException;

	public abstract KettleMessageLogModel findByPK(KettleMessageLogModel obj) throws CoBusinessException;

	public abstract List<KettleMessageLogModel>  queryByCond(KettleMessageLogModel obj) throws CoBusinessException;

	public abstract List<KettleMessageLogVO> queryByPage(KettleMessageLogVO obj) throws CoBusinessException;

	public abstract Integer  findMaxId(KettleMessageLogModel obj) throws CoBusinessException;

    public abstract List<KettleMessageLogVO> queryJobDetailMessage(KettleMessageLogVO model) throws CoBusinessException;
}
