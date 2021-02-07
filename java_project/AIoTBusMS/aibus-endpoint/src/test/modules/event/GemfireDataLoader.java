package com.wntime.test.modules.event;

import com.wntime.Application;
import com.wntime.modules.device.form.InfoDeviceAddForm;
import com.wntime.modules.device.repo.DeviceStatusRepository;
import com.wntime.modules.device.service.InfoRestDeviceService;
import com.wntime.modules.event.service.EventBaseService;
import com.wntime.modules.event.vo.InfoEventTypeVo;
import com.wntime.modules.feed.entity.FeedFollow;
import com.wntime.modules.feed.region.IcabInfo;
import com.wntime.modules.feed.region.RestaurantInfo;
import com.wntime.modules.feed.service.BaseInfoService;
import com.wntime.modules.feed.service.FeedService;
import com.wntime.modules.statistics.entity.RuleEventStatistics;
import com.wntime.modules.statistics.service.StatisticsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class GemfireDataLoader {

    @Autowired
    private BaseInfoService baseInfoService;
    @Autowired
    private InfoRestDeviceService infoRestDeviceService;
    @Autowired
    private StatisticsService statisticsService;
    @Autowired
    private EventBaseService eventBaseService;
    @Autowired
    private FeedService feedService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Resource
    private DeviceStatusRepository deviceStatusRepository;
    @Value("${server.webUrl}")
    String webUrl;


        //查询数据 area_info  device_type event_statistics_rule event_type icab_info
    @Test
    public void loadAreaInfo(){
        jdbcTemplate.query("SELECT area_id,area_name,parent_area_id from info_area WHERE is_deleted=0 and is_enabled=1", new RowMapper<Object>() {
            @Override
            public Object mapRow(ResultSet resultSet, int i) throws SQLException {

                baseInfoService.updateAreaInfo(
                        resultSet.getLong("area_id"),
                        resultSet.getString("area_name"),
                        resultSet.getLong("parent_area_id")
                );
                return null;
            }
        });
    }
    @Test
    public void loadDeviceType(){
            jdbcTemplate.query("SELECT device_type_id,device_type_name,display_code FROM info_device_type WHERE is_deleted=0 AND is_enabled =1", new RowMapper<Object>() {
                @Override
                public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                    infoRestDeviceService.updateDeviceType(
                            resultSet.getLong("device_type_id"),
                            resultSet.getString("device_type_name"),
                            resultSet.getString("display_code"));
                    return null;
                }
            });
        }
    @Test
    public void loadIcabInfo(){
            jdbcTemplate.query("SELECT * from info_icab WHERE is_deleted=0 AND is_enabled =1", new RowMapper<Object>() {
                @Override
                public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                    IcabInfo icabInfo=new IcabInfo();
                    icabInfo.setId(resultSet.getLong("icab_id"));
                    icabInfo.setName(resultSet.getString("icab_name"));
                    icabInfo.setParentIcabId(resultSet.getLong("parent_icab_id"));
                    icabInfo.setAreaId(resultSet.getLong("area_id"));
                    icabInfo.setLatitude(resultSet.getFloat("icab_latitude"));
                    icabInfo.setLongitude(resultSet.getFloat("icab_longitude"));
                    baseInfoService.updateIcabInfo(icabInfo);
                    return null;
                }
            });
        }
    @Test
    public void loadEventType(){
            jdbcTemplate.query("SELECT * from info_event_type WHERE is_deleted=0 AND is_enabled =1", new RowMapper<Object>() {
                @Override
                public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                    InfoEventTypeVo vo=new InfoEventTypeVo();
                    vo.setEventTypeId(resultSet.getLong("event_type_id"));
                    vo.setEventTypeName(resultSet.getString("event_type_name"));
                    vo.setEventTypeGroup(resultSet.getInt("event_type_group"));
                    vo.setDisplayCode(resultSet.getString("display_code"));
                    vo.setParentTypeId(resultSet.getLong("parent_type_id"));
                    vo.setEventTypeLevel(resultSet.getInt("event_type_level"));
                    eventBaseService.updateEventType(vo);
                    return null;
                }
            });
        }
    @Test
    public void loadRuleEventStatistics(){
            jdbcTemplate.query("SELECT r.*,i.parent_type_id from rule_event_statistics r INNER JOIN info_event_type i on r.event_type_id=i.event_type_id and r.is_deleted=0 AND r.is_enabled =1 and i.is_deleted=0 AND i.is_enabled =1", new RowMapper<Object>() {
                 @Override
                 public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                     java.sql.Date time = resultSet.getDate("statistics_start");
                     if(time!=null){
                         RuleEventStatistics ruleEventStatistics=new RuleEventStatistics();
                         ruleEventStatistics.setRuleStatisticsId(resultSet.getLong("rule_statistics_id"));
                         ruleEventStatistics.setEventTypeId(resultSet.getLong("event_type_id"));
                         ruleEventStatistics.setRuleStatisticsType(resultSet.getInt("rule_statistics_type"));

                         ruleEventStatistics.setStatisticsStart(new Date(resultSet.getDate("statistics_start").getTime()));
                         ruleEventStatistics.setStatisticsCycle(resultSet.getLong("statistics_cycle"));
                         ruleEventStatistics.setWarnLine(resultSet.getInt("warn_line"));
                         ruleEventStatistics.setEventScore(resultSet.getFloat("event_score"));
                         ruleEventStatistics.setIsEnabled(1);
                         ruleEventStatistics.setIsDeleted(0);

                         statisticsService.updateStatisticsRule(ruleEventStatistics,resultSet.getLong("parent_type_id"));
                     }


                     return null;
                 }
             });
        }
    @Test
    public void loadFeedFollow(){
            jdbcTemplate.query("SELECT f.feed_follow_id,f.rule_statistics_id,f.area_actor,f.area_fans,r.rule_statistics_type,r.event_type_id from feed_follow f INNER JOIN rule_event_statistics r ON f.rule_statistics_id=r.rule_statistics_id and f.is_deleted=0 and f.is_enabled =1 and r.is_deleted=0 and r.is_enabled =1", new RowMapper<Object>() {
                @Override
                public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                    FeedFollow feedFollow=new FeedFollow();
                    feedFollow.setFeedFollowId(resultSet.getLong("feed_follow_id"));
                    feedFollow.setRuleStatisticsId(resultSet.getLong("rule_statistics_id"));
                    feedFollow.setAreaActor(resultSet.getLong("area_actor"));
                    feedFollow.setAreaFans(resultSet.getLong("area_fans"));

                    int ruleStatisticsType=resultSet.getInt("rule_statistics_type");
                    long eventTypeId=resultSet.getLong("event_type_id");

                    feedService.updateFeedFollow(feedFollow,ruleStatisticsType,eventTypeId);
                    return null;
                }
            });
        }
    @Test
    public void loadDeviceRestaurant(){
            jdbcTemplate.query("SELECT restaurant_id,rest_name,area_id,icab_id,composite_rate,health_rate,is_entered from info_restaurant where is_deleted=0 AND is_enabled=1 AND is_entered=1", new RowMapper<Object>() {
                @Override
                public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                    RestaurantInfo restaurantInfo = new RestaurantInfo();
                    restaurantInfo.setId(resultSet.getLong("restaurant_id"));
                    restaurantInfo.setName(resultSet.getString("rest_name"));
                    restaurantInfo.setAreaId(resultSet.getLong("area_id"));
                    restaurantInfo.setIcabId(resultSet.getLong("icab_id"));
                    restaurantInfo.setCompositeRate(resultSet.getInt("composite_rate"));
                    restaurantInfo.setHealthRate(resultSet.getInt("health_rate"));
                    restaurantInfo.setIsEntered(resultSet.getInt("is_entered"));
                    baseInfoService.updateRestaurant(restaurantInfo);
                    return null;
                }
            });
        }
    @Test
    public void loadDevice(){
        jdbcTemplate.query("SELECT d.rest_device_id,d.restaurant_id,d.device_name,r.rest_name,d.device_photo,c.collect_content from info_rest_device d  INNER JOIN info_restaurant r on d.restaurant_id=r.restaurant_id and d.is_deleted=0 AND d.is_enabled=1 LEFT JOIN info_collect_content C ON C.collect_content_id = d.collect_content_id", new RowMapper<Object>() {
            @Override
            public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                InfoDeviceAddForm infoDeviceAddForm=new InfoDeviceAddForm();
                infoDeviceAddForm.setDeviceId(resultSet.getLong("rest_device_id"));
                infoDeviceAddForm.setDeviceName(resultSet.getString("device_name"));
                infoDeviceAddForm.setRestaurantId(resultSet.getLong("restaurant_id"));
                infoDeviceAddForm.setRestaurantName(resultSet.getString("rest_name"));
                infoDeviceAddForm.setCollectContent(resultSet.getString("collect_content"));
                infoDeviceAddForm.setDevicePhoto(webUrl + "/" +resultSet.getString("device_photo"));
                infoRestDeviceService.newlyDevice(infoDeviceAddForm);
                return null;
            }
        });
    }
    /*@Test
    public void updateStatus(){
        List<Long> list = Arrays.asList(
                1227440605492150274l,
                1227440605458595842l,
                1227440605416652802l);

        for (int i=0;i<list.size();i++){
            Optional<DeviceStatus> optional = deviceStatusRepository.findById(list.get(i));
            optional.ifPresent(deviceStatus -> {
                deviceStatus.setStatus(0);
                deviceStatus.setDetectionTimes(1);
                deviceStatusRepository.save(deviceStatus);
            });
        }


    }*/
}
