-- Create here example of data for start service
-- Two hotels in Ukraine
INSERT INTO hotels (permanent_id, id, building, city, countrycode, name, street, zipcode, latitude, longitude, website) VALUES (1, 'HO-UK-1', '25', 'Kiev', 'UK', 'Hilton Hotel KIev', 'Bandery', '38101', '45.33456', '54.667', 'http://hilton.com/kiev');
INSERT INTO hotels (permanent_id, id, building, city, countrycode, name, street, zipcode, latitude, longitude, website) VALUES (2, 'HO-UK-2', '2', 'Vinnica', 'UK', 'Hilton Hotel Roshen', 'Petlyura', '36100', '45.444', '34.55678', 'http://hilton.com/vinnica');
-- Rooms of hotels
INSERT INTO ROOMS (PERMANENT_ID, ID, HOTEL_ID, CAPACITY, FLOOR, TYPE, WINDOWS, DAILYCOST) VALUES (1, 'HO-UK-1-15', 1, 1, 1, 0, 1, 110);
INSERT INTO ROOMS (PERMANENT_ID, ID, HOTEL_ID, CAPACITY, FLOOR, TYPE, WINDOWS, DAILYCOST) VALUES (2, 'HO-UK-1-12', 1, 2, 1, 0, 1, 120);
INSERT INTO ROOMS (PERMANENT_ID, ID, HOTEL_ID, CAPACITY, FLOOR, TYPE, WINDOWS, DAILYCOST) VALUES (3, 'HO-UK-1-13', 1, 2, 1, 0, 1, 120);
INSERT INTO ROOMS (PERMANENT_ID, ID, HOTEL_ID, CAPACITY, FLOOR, TYPE, WINDOWS, DAILYCOST) VALUES (4, 'HO-UK-1-14', 1, 2, 1, 0, 1, 120);
INSERT INTO ROOMS (PERMANENT_ID, ID, HOTEL_ID, CAPACITY, FLOOR, TYPE, WINDOWS, DAILYCOST) VALUES (5, 'HO-UK-1-21', 1, 1, 2, 0, 1, 90);
INSERT INTO ROOMS (PERMANENT_ID, ID, HOTEL_ID, CAPACITY, FLOOR, TYPE, WINDOWS, DAILYCOST) VALUES (6, 'HO-UK-1-22', 1, 2, 2, 0, 1, 100);
INSERT INTO ROOMS (PERMANENT_ID, ID, HOTEL_ID, CAPACITY, FLOOR, TYPE, WINDOWS, DAILYCOST) VALUES (15, 'HO-UK-2-11', 2, 1, 1, 1, 2, 290);
INSERT INTO ROOMS (PERMANENT_ID, ID, HOTEL_ID, CAPACITY, FLOOR, TYPE, WINDOWS, DAILYCOST) VALUES (16, 'HO-UK-2-12', 2, 2, 1, 1, 0, 130);
-- Features in rooms
INSERT INTO room_feature (permanent_id, code, name, description, dailycost, totalcost) VALUES (1, 'TV', 'TV Set', 'TV Set with 30 cable channels', 2, 0);
INSERT INTO room_feature (permanent_id, code, name, description, dailycost, totalcost) VALUES (2, 'PH', 'Phone', 'Phone number for domestic calls', 1, 0);
INSERT INTO room_feature (permanent_id, code, name, description, dailycost, totalcost) VALUES (3, 'AC', 'Air Condition', 'Air condition for fresh air in the room', 0, 1);
INSERT INTO room_feature (permanent_id, code, name, description, dailycost, totalcost) VALUES (4, 'WF', 'WiFi', '10 mb/sec WiFi access to the Internet', 0, 1);
-- Join rooms and features
INSERT INTO rooms_features (feature_id, room_id) VALUES (1, 1);
INSERT INTO rooms_features (feature_id, room_id) VALUES (2, 1);
INSERT INTO rooms_features (feature_id, room_id) VALUES (4, 1);
INSERT INTO rooms_features (feature_id, room_id) VALUES (1, 2);
INSERT INTO rooms_features (feature_id, room_id) VALUES (2, 2);
INSERT INTO rooms_features (feature_id, room_id) VALUES (4, 2);
INSERT INTO rooms_features (feature_id, room_id) VALUES (1, 3);
INSERT INTO rooms_features (feature_id, room_id) VALUES (2, 3);
INSERT INTO rooms_features (feature_id, room_id) VALUES (4, 3);
INSERT INTO rooms_features (feature_id, room_id) VALUES (1, 4);
INSERT INTO rooms_features (feature_id, room_id) VALUES (2, 4);
INSERT INTO rooms_features (feature_id, room_id) VALUES (3, 4);
INSERT INTO rooms_features (feature_id, room_id) VALUES (1, 5);
INSERT INTO rooms_features (feature_id, room_id) VALUES (4, 5);
INSERT INTO rooms_features (feature_id, room_id) VALUES (1, 6);
INSERT INTO rooms_features (feature_id, room_id) VALUES (3, 6);
INSERT INTO rooms_features (feature_id, room_id) VALUES (1, 15);
INSERT INTO rooms_features (feature_id, room_id) VALUES (2, 15);
INSERT INTO rooms_features (feature_id, room_id) VALUES (3, 15);
INSERT INTO rooms_features (feature_id, room_id) VALUES (4, 15);
INSERT INTO rooms_features (feature_id, room_id) VALUES (1, 16);
INSERT INTO rooms_features (feature_id, room_id) VALUES (2, 16);
INSERT INTO rooms_features (feature_id, room_id) VALUES (3, 16);
INSERT INTO rooms_features (feature_id, room_id) VALUES (4, 16);
-- Hotel staff
INSERT INTO person_table (person_type, permanent_id, about, birthday, document_citizenship, document_expired, document_number, document_type, email, firstname, gender, person_id, language, lastname, middlename, phone, photo_contenttype, photo_image, tags, is_active, end_date, start_date, login, position_title) VALUES ('Employee', 1, 'Receptionist at reception table', '1999-05-27', 'RU', '2020-01-01', '1234', 'PASS', 'olga.sokol@hilton.com', 'Olga', 1, 'HO-STAFF-1234', 'EN', 'Sokolav', 'Petr', '802', 'png', NULL,'receptionist,booking,service', TRUE, NULL, '2015-08-24', 'olga_sokol', 'Receptionist');
INSERT INTO person_table (person_type, permanent_id, about, birthday, document_citizenship, document_expired, document_number, document_type, email, firstname, gender, person_id, language, lastname, middlename, phone, photo_contenttype, photo_image, tags, is_active, end_date, start_date, login, position_title) VALUES ('Employee', 4, 'Room service', '1990-02-28', 'RO', '2020-12-31', '532-654', 'PASS', 'mary.luchesku@hilton.com', 'Mary', 1, 'HO-STAFF-654', 'EN', 'Luchecku', '', '911', 'png', NULL,'maid,room-service,room-clean', TRUE, NULL, '2015-08-24', 'mary_luchl', 'Maid');
INSERT INTO person_table (person_type, permanent_id, about, birthday, document_citizenship, document_expired, document_number, document_type, email, firstname, gender, person_id, language, lastname, middlename, phone, photo_contenttype, photo_image, tags, is_active, end_date, start_date, login, position_title) VALUES ('Employee', 2, 'Senior hotel manager', '1979-12-23', 'US', '2018-01-01', '667778', 'PASS', 'john@hilton.com', 'John', 0, 'HO-STAFF-778', 'EN', 'Dow', '', '555-3546', 'png', NULL,'manager,staff,booking,service', TRUE, NULL, '2010-01-20', 'john_dow', 'Snr.Manager');
-- One guest
INSERT INTO person_table (person_type, permanent_id, about, birthday, document_citizenship, document_expired, document_number, document_type, email, firstname, gender, person_id, language, lastname, middlename, phone, photo_contenttype, photo_image, tags, is_active, end_date, start_date, login, position_title) VALUES ('Guest', 3, 'Lovely guest', '1972-03-08', 'US', '2025-01-01', '8686757-6565', 'PASS', 'helen@hilton.com', 'Helen', 1, 'HO-GUEST-HELEN-72-03-08', 'EN', 'Eagle', '', '555-1212', 'png', NULL,'guest,woman, vip,rich', FALSE, '2017-07-31', '2017-07-29', 'helen_eagle', 'Ms.');
-- Agreement
insert into habitation_part (permanent_id , id, checkin, checkout, hotelid, totalfee ) values ( 1, 'AGR-01-01/12', '2017-06-20 12:02:54', '2017-06-22 10:00:00', 'HO-UK-1', 230);
insert into room_occupied (permanent_id , hp_id ) values (1,1);
insert into ho_room  (room_id , ho_id ) values (3,1);
insert into ho_guests  (guest_id , ho_id ) values (3,1);
insert into ho_features   (feature_id , ho_id ) values (1,1);
insert into ho_features   (feature_id , ho_id ) values (2,1);
insert into ho_features   (feature_id , ho_id ) values (4,1);

insert into hp_check_in (employee_id , hp_id )  values (1,1);
insert into hp_check_out  (employee_id , hp_id )  values (2,1);

insert into reservation_part (permanent_id , id, start_period, end_period , reserv_state ) values (1,  'AGR-01-01/12', '2017-06-20', '2017-06-20', 'DONE');
insert into reservations_employees (employee_id , reservation_id ) values (1,1);

insert into agreement (permanent_id , id, creditcard_expired , creditcard_id , creditcard_number ,creditcard_owner, createdat, habitation_id ,reservation_id ) values (1,'AGR-01-01/12', '2020-01-01', 'VISA', '4111-1111-1111-1111', 'HELEN EAGLE', '2017-06-19 18:20:35', 1, 1);
insert into agreements_guests (guest_id , agreement_id ) values (3,1);

-- Provided Services
insert into provided_services (permanent_id , name, description , starttime , state, finishtime , duration , cost ,hp_id ) values (1,'Clean','Clean room, bring blanket', ' 2017-06-21 09:30:00',4, '2017-06-21 10:30:00', 60, 1, 1);
insert into services_done (employee_id , ps_id ) values (4,1);
-- Room activity
insert into room_schedule (permanent_id , id, createdat, startedat  , finishedat, hotelagreementid, roomid , type) values (1,'123-AFG-556', '2017-06-19 18:10:00', '2017-06-20 12:00:00', '2017-06-22 09:50:00', 'AGR-01-01/12', 'HO-UK-1-15', 3);

