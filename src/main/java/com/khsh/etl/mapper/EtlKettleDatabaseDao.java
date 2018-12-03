package com.khsh.etl.mapper;

import com.ejet.comm.exception.CoBusinessException;
import com.khsh.etl.model.EtlKettleDatabaseModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EtlKettleDatabaseDao {


	public void insertSingle(EtlKettleDatabaseModel obj) throws CoBusinessException;

	public int insertAutoKey(EtlKettleDatabaseModel obj) throws CoBusinessException;

	public void update(EtlKettleDatabaseModel obj) throws CoBusinessException;

	public void delete(EtlKettleDatabaseModel obj) throws CoBusinessException;

	public abstract EtlKettleDatabaseModel findByPK(EtlKettleDatabaseModel obj) throws CoBusinessException;

	public abstract List<EtlKettleDatabaseModel>  queryByCond(EtlKettleDatabaseModel obj) throws CoBusinessException;

	public abstract List<EtlKettleDatabaseModel>  queryByPage(EtlKettleDatabaseModel obj) throws CoBusinessException;

	public abstract Integer  findMaxId(EtlKettleDatabaseModel obj) throws CoBusinessException;


}
