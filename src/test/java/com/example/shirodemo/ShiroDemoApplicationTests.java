package com.example.shirodemo;

import com.example.shirodemo.dao.TCityRepository;
import com.example.shirodemo.entity.CityHohel;
import com.example.shirodemo.entity.TCity;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
class ShiroDemoApplicationTests {
    @Autowired
    private TCityRepository tCityRepository;

    @Test
    void contextLoads() {


    }

    @Test
    public void findByIdLessThanAndNameLike() throws Exception {
        List<TCity> shanghai = tCityRepository.findByIdLessThanAndNameLike(3, "%shanghai%");
        Assert.assertTrue(shanghai.size() > 0);
    }

    @Test
    public void findCityAndHotel() throws Exception {

        Page<Object[]> cityAndHotel = tCityRepository.findCityAndHotel("万达", PageRequest.of(0,10));


        Assert.assertTrue(cityAndHotel.getTotalElements() > 0);
    }

    @Test
    public void findCityAndHotelByHQL() throws Exception {

        List<Map<String,Object>> map = tCityRepository.findCityAndHotelByHQL("万达");


        Assert.assertTrue(map.size() > 0);
    }

    @Test
    public void findCityAndHotelAllSelf() throws Exception {
        Page<CityHohel> cityAndHotelAllSelf = tCityRepository.findCityAndHotelAllSelf("万达",  PageRequest.of(0, 10));

        Assert.assertTrue(cityAndHotelAllSelf.getTotalElements() > 0);
    }

}
