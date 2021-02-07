package com.wntime.service.service;

import com.wntime.Application;
import com.wntime.customer.service.ItineraryReceiptService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes={Application.class})
public class ItineraryReceiptServiceTest {

    @Autowired
    private ItineraryReceiptService itineraryReceiptService;

    @Test
    public void produceItinerary(){
        itineraryReceiptService.produceItinerary("20210205","2",null);
    }

    @Test
    public void statReceiptData() throws InterruptedException {
        long begin = System.currentTimeMillis();
        itineraryReceiptService.statReceiptData(1356878846074290177L);
        System.out.println("cost : " + (System.currentTimeMillis() - begin));
        Thread.sleep(1000 * 10);
    }
}
