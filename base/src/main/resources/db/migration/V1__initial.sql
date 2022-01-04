create table articles_favorites (
                                    id binary(16) not null,
                                    user_id binary(16),
                                    article_id binary(16),
                                    primary key (id)
);
create table articles (
                          id binary(16) not null,
                          body varchar(255),
                          created_at timestamp,
                          description varchar(255),
                          slug varchar(255),
                          title varchar(255),
                          updated_at timestamp,
                          author_id binary(16),
                          primary key (id)
);
create table articles_tags (
                               article_id binary(16) not null,
                               tags_id binary(16) not null,
                               primary key (article_id, tags_id)
);
create table comments (
                          id binary(16) not null,
                          articleId binary(16),
                          body varchar(255),
                          created_at timestamp,
                          updated_at timestamp,
                          userId binary(16),
                          primary key (id)
);
create table tags (
                      id binary(16) not null,
                      name varchar(255),
                      primary key (id)
);
create table users (
                       id binary(16) not null,
                       bio varchar(255),
                       email varchar(255),
                       image varchar(255),
                       password varchar(255),
                       username varchar(255),
                       primary key (id)
);
create table follows (
                         user_id binary(16) not null,
                         follower_id binary(16) not null
);

alter table articles_favorites
    add constraint FKkwoosavyapalh4oepsyvojw91 foreign key (article_id) references articles;
alter table articles
    add constraint FKe02fs2ut6qqoabfhj325wcjul foreign key (author_id) references users;
alter table articles_tags
    add constraint FKpq6t50xjngelojo3ugfu5wtet foreign key (tags_id) references tags;
alter table articles_tags
    add constraint FK7pyxtdtmv4dw8ujmp4d7jj1ok foreign key (article_id) references articles;
alter table follows add constraint FKqnkw0cwwh6572nyhvdjqlr163 foreign key (follower_id) references users;
alter table follows add constraint FKn4am7c82j2uo8pkw4x7qibf12 foreign key (user_id) references users;
