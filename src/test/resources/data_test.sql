--INSERT users
insert into user (id, name, password, email, photo, reg_time, is_moderator) values (1, 'Roman', '$2a$10$XR/zQU8iZE4wfabkaRKc8uK9oDAiibFrzlH/S0OfWJQP2z7/7y4d2', 'r9854334307@mail.ru', 'img/default.c66f8640.jpg' , '2020-04-09 16:13:25', 1);
insert into user (id, name, password, email, photo, reg_time, is_moderator) values (2, 'Artem', '$2a$10$XR/zQU8iZE4wfabkaRKc8uK9oDAiibFrzlH/S0OfWJQP2z7/7y4d2', 'asd@mail.ru', 'img/default.c66f8640.jpg' , '2020-04-09 16:13:25', 1);
insert into user (id, name, password, email, photo, reg_time, is_moderator) values (3, 'Denis', '$2a$10$XR/zQU8iZE4wfabkaRKc8uK9oDAiibFrzlH/S0OfWJQP2z7/7y4d2', 'qwe@mail.ru', 'img/default.c66f8640.jpg' , '2020-04-09 16:13:25', 0);

--INSERT Post
insert into post (id, is_active, moderation_status, text, time, title, user_id, view_count, moderator_id) values (1, 1, 'ACCEPTED', 'odio elementum eu interdum eu tincidunt in leo maecenas pulvinar lobortis est phasellus sit amet erat nulla tempus vivamus in felis eu sapien cursus vestibulum proin eu mi nulla ac enim in tempor turpis', '2020-03-18 16:53:34', 'nulla ut erat id mauris', 1, 30, 1);
insert into post (id, is_active, moderation_status, text, time, title, user_id, view_count, moderator_id) values (2, 1, 'DECLINED', 'nulla neque libero convallis eget eleifend luctus ultricies eu nibh quisque id justo sit amet sapien dignissim vestibulum vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia', '2020-02-27 15:30:18', 'leo rhoncus sed vestibulum', 1, 60, 1);
insert into post (id, is_active, moderation_status, text, time, title, user_id, view_count, moderator_id) values (3, 1, 'NEW', 'at dolor quis odio consequat varius integer ac leo pellentesque ultrices mattis odio donec vitae nisi nam ultrices libero non mattis pulvinar nulla pede ullamcorper augue a suscipit nulla elit ac nulla sed', '2019-09-22 18:07:02', 'posuere metus vitae ipsum aliquam', 2, 48, null);
insert into post (id, is_active, moderation_status, text, time, title, user_id, view_count, moderator_id) values (4, 1, 'ACCEPTED', 'a nibh in quis justo maecenas rhoncus aliquam lacus morbi quis tortor id nulla ultrices aliquet maecenas leo odio condimentum id luctus nec molestie sed justo pellentesque viverra pede ac diam cras', '2019-08-04 04:45:47', 'in purus eu magna', 3, 54, 1);
insert into post (id, is_active, moderation_status, text, time, title, user_id, view_count, moderator_id) values (5, 1, 'ACCEPTED', 'lectus vestibulum quam sapien varius ut blandit non interdum in ante vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae duis faucibus accumsan odio curabitur', '2019-06-14 03:44:18', 'platea dictumst aliquam augue', 3, 38, 1);

--INSERT PostComment
insert into post_comment (id, parent_id, text, time, user_id, post_id) values (1, null, 'sagittis nam congue risus semper porta volutpat quam pede lobortis', '2019-07-17 10:15:33', 1, 1);
insert into post_comment (id, parent_id, text, time, user_id, post_id) values (2, null, 'ac nulla sed vel enim sit amet nunc viverra dapibus nulla suscipit', '2019-10-27 18:42:31', 1, 2);
insert into post_comment (id, parent_id, text, time, user_id, post_id) values (3, null, 'tempus semper est quam pharetra magna', '2019-07-20 06:04:25', 2, 3);
insert into post_comment (id, parent_id, text, time, user_id, post_id) values (4, null, 'eu massa donec dapibus duis at velit', '2019-12-30 02:56:02', 3, 1);
insert into post_comment (id, parent_id, text, time, user_id, post_id) values (5, null, 'mi nulla ac enim in tempor turpis nec euismod', '2019-09-13 06:59:39', 1, 2);

--INSERT post_vote
insert into post_vote (id, time, user_id, value, post_id) values (1, '2019-07-17 21:32:39', 1, -1, 1);
insert into post_vote (id, time, user_id, value, post_id) values (2, '2020-03-03 02:45:19', 2, 1, 1);
insert into post_vote (id, time, user_id, value, post_id) values (3, '2020-01-15 18:07:58', 3, 1, 4);


--INSERT tag
insert into tag (id, name) values (1, 'JAVA');
insert into tag (id, name) values (2, 'HELLO');
insert into tag (id, name) values (3, 'WORLD');

--INSERT tag2post
insert into tag2post (id, post_id, tag_id) values (1, 1, 1);
insert into tag2post (id, post_id, tag_id) values (2, 2, 2);
insert into tag2post (id, post_id, tag_id) values (3, 3, 3);
insert into tag2post (id, post_id, tag_id) values (4, 4, 3);
insert into tag2post (id, post_id, tag_id) values (5, 5, 2);
insert into tag2post (id, post_id, tag_id) values (6, 4, 3);



