create table if not exists public.categories
(
    id   bigserial
        primary key,
    name varchar(255) not null
);

alter table public.categories
    owner to postgres;

create table if not exists public.forums
(
    id          bigserial
        primary key,
    description varchar(255) not null,
    name        varchar(255) not null
);

alter table public.forums
    owner to postgres;

create table if not exists public.messages
(
    id      bigserial
        primary key,
    content varchar(255) not null,
    sent_at timestamp(6) not null
);

alter table public.messages
    owner to postgres;

create table if not exists public.roles
(
    id   bigserial
        primary key,
    name varchar(255) not null
);

alter table public.roles
    owner to postgres;

create table if not exists public.tags
(
    id   bigserial
        primary key,
    name varchar(255) not null
);

alter table public.tags
    owner to postgres;

create table if not exists public.users
(
    id      bigserial
        primary key,
    login   varchar(255) not null
        constraint uk_ow0gan20590jrb00upg3va2fn
            unique,
    pass    varchar(255) not null,
    role_id bigint
        constraint uk_krvotbtiqhudlkamvlpaqus0t
            unique
        constraint fkp56c1712k691lhsyewcssf40f
            references public.roles
);

alter table public.users
    owner to postgres;

create table if not exists public.posts
(
    id          bigserial
        primary key,
    content     varchar(5000) not null,
    created_at  timestamp(6)  not null,
    title       varchar(255)  not null,
    category_id bigint
        constraint fkijnwr3brs8vaosl80jg9rp7uc
            references public.categories,
    forum_id    bigint
        constraint fk9bleycktuep8yrcvuuveugtqf
            references public.forums,
    user_id     bigint        not null
        constraint fk5lidm6cqbc7u4xhqpxm898qme
            references public.users
);

alter table public.posts
    owner to postgres;

create table if not exists public.comments
(
    id         bigserial
        primary key,
    content    varchar(1000) not null,
    created_at timestamp(6)  not null,
    post_id    bigint        not null
        constraint fkh4c7lvsc298whoyd4w9ta25cr
            references public.posts,
    user_id    bigint        not null
        constraint fk8omq0tc18jd43bu5tjh6jvraq
            references public.users
);

alter table public.comments
    owner to postgres;

create table if not exists public.post_tags
(
    post_id bigint not null
        constraint fkkifam22p4s1nm3bkmp1igcn5w
            references public.posts,
    tag_id  bigint not null
        constraint fkm6cfovkyqvu5rlm6ahdx3eavj
            references public.tags,
    primary key (post_id, tag_id)
);

alter table public.post_tags
    owner to postgres;

create table if not exists public.user_roles
(
    role_id bigint not null
        constraint fkh8ciramu9cc9q3qcqiv4ue8a6
            references public.roles,
    user_id bigint not null
        constraint fkhfh9dx7w3ubf1co1vdev94g3f
            references public.users,
    primary key (role_id, user_id)
);

alter table public.user_roles
    owner to postgres;

