package com.example.springwebmvc.repository;

import com.example.springwebmvc.model.Supplier;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SupplierRepository {

    @Select("SELECT * FROM suppliers")
    List<Supplier> findAll();

    @Select("""
        SELECT *
        FROM suppliers
        WHERE id = #{id}
    """)
    Supplier findById(@Param("id") Integer id);

    @Insert("""
        INSERT INTO suppliers (company, since, status)
        VALUES (#{s.company}, #{s.since}, #{s.status})
    """)
    void insert(@Param("s") Supplier supplier);

    @Update("""
        UPDATE suppliers
        SET company = #{s.company},
            since   = #{s.since},
            status  = #{s.status}
        WHERE id = #{s.id}
    """)
    void update(@Param("s") Supplier supplier);

    @Update("""
        UPDATE suppliers
        SET status = #{status}
        WHERE id = #{id}
    """)
    void updateStatus(@Param("id") Integer id, @Param("status") Boolean status);


    @Delete("DELETE FROM suppliers WHERE id = #{id}")
    void deleteById(@Param("id") Integer id);
}
