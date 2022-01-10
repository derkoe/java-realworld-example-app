create table users
(
    id uuid not null,
    bio      varchar(255),
    email    varchar(255),
    image    varchar(255),
    password varchar(255),
    username varchar(255),
    primary key (id)
);
create table follows
(
    user_id uuid not null,
    follower_id uuid not null,
    primary key (user_id, follower_id),
    foreign key (user_id) references users (id),
    foreign key (follower_id) references users (id)
);


create table articles
(
    id uuid not null,
    body        varchar(255),
    created_at  timestamp,
    description varchar(255),
    slug        varchar(255),
    title       varchar(255),
    updated_at  timestamp,
    author_id uuid,
    primary key (id),
    foreign key (author_id) references users (id)
);
create table tags
(
    id uuid not null,
    name varchar(255),
    primary key (id)
);
create table articles_tags
(
    article_id uuid not null,
    tags_id uuid not null,
    primary key (article_id, tags_id),
    foreign key (article_id) references articles (id),
    foreign key (tags_id) references tags (id)
);
create table comments
(
    id uuid not null,
    article_id uuid,
    body       varchar(255),
    created_at timestamp,
    updated_at timestamp,
    user_id uuid,
    primary key (id),
    foreign key (article_id) references articles(id),
    foreign key (user_id) references users(id)
);
create table articles_favorites
(
    id uuid not null,
    user_id uuid,
    article_id uuid,
    primary key (id),
    foreign key (user_id) references users(id),
    foreign key (article_id) references articles(id)
);
