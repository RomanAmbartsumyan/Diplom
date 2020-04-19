
--INSERT users
insert into user (id, name, password, email, photo, reg_time, is_moderator) values (1, 'Roman', '$2a$10$XR/zQU8iZE4wfabkaRKc8uK9oDAiibFrzlH/S0OfWJQP2z7/7y4d2', 'asd@mail.ru', 'img/default.c66f8640.jpg' , '2020-04-09 16:13:25', 1);
insert into user (id, name, password, email, photo, reg_time, is_moderator) values (2, 'Artem', '$2a$10$XR/zQU8iZE4wfabkaRKc8uK9oDAiibFrzlH/S0OfWJQP2z7/7y4d2', 'qwe@mail.ru', 'img/default.c66f8640.jpg' , '2020-04-09 16:13:25', 1);
insert into user (id, name, password, email, photo, reg_time, is_moderator) values (3, 'Alexander', '$2a$10$XR/zQU8iZE4wfabkaRKc8uK9oDAiibFrzlH/S0OfWJQP2z7/7y4d2', 'qwe@mail.ru', 'img/default.c66f8640.jpg' , '2020-04-09 16:13:25', 0);

--INSERT Post
insert into post (id, is_active, moderation_status, text, time, title, user_id, view_count, moderator_id) values (1, 1, 'ACCEPTED', 'odio elementum eu interdum eu tincidunt in leo maecenas pulvinar lobortis est phasellus sit amet erat nulla tempus vivamus in felis eu sapien cursus vestibulum proin eu mi nulla ac enim in tempor turpis', '2020-03-18 16:53:34', 'nulla ut erat id mauris', 1, 30, 1);
insert into post (id, is_active, moderation_status, text, time, title, user_id, view_count, moderator_id) values (2, 1, 'ACCEPTED', 'nulla neque libero convallis eget eleifend luctus ultricies eu nibh quisque id justo sit amet sapien dignissim vestibulum vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae nulla dapibus dolor vel est donec odio justo sollicitudin ut suscipit a feugiat et eros vestibulum', '2020-02-27 15:30:18', 'leo rhoncus sed vestibulum', 1, 60, 2);
insert into post (id, is_active, moderation_status, text, time, title, user_id, view_count, moderator_id) values (3, 1, 'NEW', 'at dolor quis odio consequat varius integer ac leo pellentesque ultrices mattis odio donec vitae nisi nam ultrices libero non mattis pulvinar nulla pede ullamcorper augue a suscipit nulla elit ac nulla sed vel enim sit amet nunc viverra dapibus nulla suscipit ligula in', '2019-09-22 18:07:02', 'posuere metus vitae ipsum aliquam', 1, 48, 1);
insert into post (id, is_active, moderation_status, text, time, title, user_id, view_count, moderator_id) values (4, 0, 'ACCEPTED', 'a nibh in quis justo maecenas rhoncus aliquam lacus morbi quis tortor id nulla ultrices aliquet maecenas leo odio condimentum id luctus nec molestie sed justo pellentesque viverra pede ac diam cras pellentesque volutpat dui maecenas tristique est et tempus semper est quam pharetra magna ac consequat metus sapien', '2019-08-04 04:45:47', 'in purus eu magna', 3, 54, 2);
insert into post (id, is_active, moderation_status, text, time, title, user_id, view_count, moderator_id) values (5, 1, 'DECLINED', 'lectus vestibulum quam sapien varius ut blandit non interdum in ante vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae duis faucibus accumsan odio curabitur convallis duis consequat dui nec nisi volutpat eleifend donec ut dolor morbi vel', '2019-06-14 03:44:18', 'platea dictumst aliquam augue', 2, 38, 3);

--INSERT PostComment
insert into post_comment (id, parent_id, text, time, user_id, post_id) values (1, null, 'sagittis nam congue risus semper porta volutpat quam pede lobortis', '2019-07-17 10:15:33', 3, 1);
