DROP FROM  captcha_code, global_setting, post, post_comment, post_vote, tag, tag2post, user;

--INSERT users
insert into user (id, name, password, email, photo, reg_time, is_moderator) values (1, 'Roman', '$2a$10$XR/zQU8iZE4wfabkaRKc8uK9oDAiibFrzlH/S0OfWJQP2z7/7y4d2', 'asd@mail.ru', 'img/default.c66f8640.jpg' , '2020-04-09 16:13:25', 1);
insert into user (id, name, password, email, photo, reg_time, is_moderator) values (2, 'Artem', '$2a$10$XR/zQU8iZE4wfabkaRKc8uK9oDAiibFrzlH/S0OfWJQP2z7/7y4d2', 'qwe@mail.ru', 'img/default.c66f8640.jpg' , '2020-04-09 16:13:25', 1);
insert into user (id, name, password, email, photo, reg_time, is_moderator) values (3, 'Alexander', '$2a$10$XR/zQU8iZE4wfabkaRKc8uK9oDAiibFrzlH/S0OfWJQP2z7/7y4d2', 'qwe@mail.ru', 'img/default.c66f8640.jpg' , '2020-04-09 16:13:25', 0);

--INSERT Post
insert into post (id, is_active, moderation_status, text, time, title, user_id, view_count, moderator_id) values (1, 1, 'ACCEPTED', 'odio elementum eu interdum eu tincidunt in leo maecenas pulvinar lobortis est phasellus sit amet erat nulla', '2020-03-18 16:53:34', 'nulla ut erat id mauris', 1, 30, 1);
insert into post (id, is_active, moderation_status, text, time, title, user_id, view_count, moderator_id) values (2, 1, 'ACCEPTED', 'nulla neque libero convallis eget eleifend luctus ultricies eu nibh quisque id justo sit amet sapien dignissim vestibulum vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae nulla dapibus dolor vel est donec odio justo', 1, 85, 1);
insert into post (id, is_active, moderation_status, text, time, title, user_id, view_count, moderator_id) values (3, 1, 'ACCEPTED', 'nulla neque libero convallis eget eleifend luctus ultricies eu nibh quisque id justo sit amet sapien dignissim vestibulum vestibulum ante ipsum primis in faucibus', 1, 66, 2);
insert into post (id, is_active, moderation_status, text, time, title, user_id, view_count, moderator_id) values (4, 0, 'NEW', 'odio elementum eu interdum eu tincidunt in leo maecenas pulvinar lobortis est phasellus sit amet erat nulla curae nulla dapibus ', 1, 78, 1);
insert into post (id, is_active, moderation_status, text, time, title, user_id, view_count, moderator_id) values (5, 1, 'ACCEPTED', 'interdum eu tincidunt in leo in leo maecenas pulvinar lobortis est nibh quisque id justo sit amet sapien dignissim vestibulum vestibulum ante ipsum primis in faucibus', '2020-03-18 16:53:34', 'nulla ut erat id mauris', 1, 22, 2);

--INSERT post_vote
insert into post_vote (id, time, user_id, value, post_id) values (1, '2019-07-17 21:32:39', 1, -1, 1);
insert into post_vote (id, time, user_id, value, post_id) values (2, '2020-03-03 02:45:19', 2, 1, 1);
insert into post_vote (id, time, user_id, value, post_id) values (3, '2020-01-15 18:07:58', 3, 1, 2);
insert into post_vote (id, time, user_id, value, post_id) values (4, '2019-11-19 08:48:19', 1, -1, 3);
insert into post_vote (id, time, user_id, value, post_id) values (5, '2019-04-25 21:46:27', 2, 1, 4);
insert into post_vote (id, time, user_id, value, post_id) values (6, '2019-11-17 08:37:52', 1, -1, 4);

--INSERT tag
insert into tag (id, name) values (1, 'java');
insert into tag (id, name) values (2, 'hello');
insert into tag (id, name) values (3, 'world');

--INSERT tag2post
insert into tag2post (id, post_id, tag_id) values (1, 1, 1);
insert into tag2post (id, post_id, tag_id) values (2, 2, 2);
insert into tag2post (id, post_id, tag_id) values (3, 3, 3);
insert into tag2post (id, post_id, tag_id) values (4, 4, 4);
insert into tag2post (id, post_id, tag_id) values (5, 2, 4);
insert into tag2post (id, post_id, tag_id) values (6, 5, 3);

--INSERT PostComment
insert into post_comment (id, parent_id, text, time, user_id, post_id) values (1, null, 'sagittis nam congue risus semper porta volutpat quam pede lobortis', '2019-07-17 10:15:33', 1, 2);
insert into post_comment (id, parent_id, text, time, user_id, post_id) values (2, null, 'ac nulla sed vel enim sit amet nunc viverra dapibus nulla suscipit', '2019-10-27 18:42:31', 2, 2);
insert into post_comment (id, parent_id, text, time, user_id, post_id) values (3, null, 'tempus semper est quam pharetra magna', '2019-07-20 06:04:25', 3, 1);
insert into post_comment (id, parent_id, text, time, user_id, post_id) values (4, null, 'eu massa donec dapibus duis at velit', '2019-12-30 02:56:02', 3, 5);
insert into post_comment (id, parent_id, text, time, user_id, post_id) values (5, null, 'mi nulla ac enim in tempor turpis nec euismod', '2019-09-13 06:59:39', 1, 4);
insert into post_comment (id, parent_id, text, time, user_id, post_id) values (6, null, 'quis turpis eget elit sodales scelerisque mauris sit amet eros suspendisse', '2020-03-29 17:58:17', 2, 4);