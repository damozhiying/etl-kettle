package com.khsh.etl.mapper;

import com.ejet.comm.exception.CoBusinessException;
import com.khsh.etl.model.EtlKettleRepositoryModel;
import com.khsh.etl.vo.EtlKettleRepositoryVO;
import com.khsh.etl.vo.bus.KettleJobParamVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EtlKettleRepositoryDao {


	public void insertSingle(EtlKettleRepositoryModel obj) throws CoBusinessException;

	public int insertAutoKey(EtlKettleRepositoryModel obj) throws CoBusinessException;

	public void update(EtlKettleRepositoryModel obj) throws CoBusinessException;

	public void delete(EtlKettleRepositoryModel obj) throws CoBusinessException;

	public abstract EtlKettleRepositoryModel findByPK(EtlKettleRepositoryModel obj) throws CoBusinessException;

	public abstract List<EtlKettleRepositoryModel>  queryByCond(EtlKettleRepositoryModel obj) throws CoBusinessException;

	public abstract List<EtlKettleRepositoryModel>  queryByPage(EtlKettleRepositoryModel obj) throws CoBusinessException;

	public abstract Integer  findMaxId(EtlKettleRepositoryModel obj) throws CoBusinessException;

    public abstract List<KettleJobParamVO> queryKettleJobParam(KettleJobParamVO vo) throws CoBusinessException;

    public abstract List<EtlKettleRepositoryVO> queryJobUsed(EtlKettleRepositoryVO vo) throws CoBusinessException;

    // public abstract void addJobsBatch(List<EtlKettleRepositoryVO> vo) throws CoBusinessException;

}
