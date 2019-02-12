package com.ferret.dao;


import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Repository;


@Repository
public interface SequenceMapper {
	@Select("select nextval(#{seqName})")
	@Options(statementType = StatementType.CALLABLE)
    String nextVal(String seqName);


}
