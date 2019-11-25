package com.example.shirodemo.dao;

import com.example.shirodemo.entity.CityHohel;
import com.example.shirodemo.entity.TCity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface TCityRepository extends JpaRepository<TCity, Integer>, JpaSpecificationExecutor<TCity> {
    /**
     * 查找出Id小于3,并且名称带有`shanghai`的记录.
     *
     * @param id   id
     * @param name 城市名称
     * @return 城市列表
     */
    List<TCity> findByIdLessThanAndNameLike(int id, String name);


    /**
     * 通过旅店名称分页查询旅店以及城市的信息
     *
     * @param name     旅店名称
     * @param pageable 分页信息
     * @return Page<Object[]>
     */
    @Query(value = "select t1.name as cityName,t2.name as hotelName\n" +
            "from t_city t1\n" +
            "  left join t_hotel t2 on t2.city = t1.id\n" +
            "where t2.name = :name",
            countQuery = "select count(*)" +
                    "from t_city t1 \n" +
                    "  left join t_hotel t2 on t2.city = t1.id\n" +
                    "where t2.name = :name"
            , nativeQuery = true)
    Page<Object[]> findCityAndHotel(@Param("name") String name, Pageable pageable);


    /**
     * HQL通过旅店名称查询旅店以及城市的所有信息
     *
     * @return
     */
    @Query(value = "select new map(t1,t2) from  TCity t1 left  join THotel t2 on t1.id=t2.city where t2.name =:name")
    List<Map<String, Object>> findCityAndHotelByHQL(@Param("name") String name);

    /**
     * 关联查询
     *
     * @return
     */
    @Query(value = "select new com.example.shirodemo.entity.CityHohel(t1.name AS cityName,t2.name AS hotelName) " +
            "from  TCity t1 left  join THotel t2 on t1.id=t2.city where t2.name =:name ",
            countQuery = "select count(t1) " +
                    "from  TCity t1 left  join THotel t2 on t1.id=t2.city where t2.name =:name",nativeQuery = false)
    Page<CityHohel> findCityAndHotelAllSelf(@Param("name") String name, Pageable pageable);

}