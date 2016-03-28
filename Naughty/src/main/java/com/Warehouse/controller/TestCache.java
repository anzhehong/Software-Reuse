package com.Warehouse.controller;

import com.Warehouse.service.MainService;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * Created by fowafolo
 * Date: 16/1/4
 * Time: 上午3:49
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/WEB-INF/applicationContext.xml"})
public class TestCache {
    @Autowired
    MainService mainService;

//    @After
    public void cleanUp() {
        Logger logger = Logger.getRootLogger();
        logger.setLevel(Level.ERROR);


    }
//    @Before
    public void AddToShowCacheWorking() {
    }

//    @Before
    public void setup() {
    }

    @Test
    public void testCache() {
    }

}
