DELETE FROM info_bus_device;
DELETE FROM info_device_type;
DELETE FROM info_collect_event;
DELETE FROM info_event_extend;
DELETE FROM info_event_level;
DELETE FROM info_event_target;
DELETE FROM info_event_type;
DELETE FROM info_collect_fault;
DELETE FROM info_fault_extend;
DELETE FROM info_fault_level;
DELETE FROM info_fault_target;
DELETE FROM info_fault_type;
DELETE FROM local_info_bus;
DELETE FROM info_update_list;
DELETE FROM info_bus_station;
DELETE FROM info_line_station;
DELETE FROM plan_bus_service;
DELETE FROM info_advertise_position;
DELETE FROM info_advertise_target;
DELETE FROM order_advertise_delivery;
DELETE FROM order_advertise_attach;
DELETE FROM order_delivery_area;
DELETE FROM order_delivery_target;
-- local_info_bus
INSERT INTO local_info_bus(local_id) values(1);
-- info_update_list
INSERT INTO info_update_list VALUES (1, '1', '', '', '', 1, '1990-01-01 01:01:01', 0, 1, '1990-01-01 01:01:01', 1, '1990-01-01 01:01:01');
INSERT INTO info_update_list VALUES (2, '3', '', '', '', 1, '1990-01-01 01:01:01', 0, 1, '1990-01-01 01:01:01', 1, '1990-01-01 01:01:01');