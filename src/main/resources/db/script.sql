create table admins
(
    Id             int auto_increment
        primary key,
    Nickname       varchar(50)                        not null,
    Email          varchar(50)                        not null,
    PasswordDigest varchar(70)                        null,
    PasswordSalt   varchar(70)                        null,
    RememberDigest varchar(70)                        null,
    CreatedTime    datetime default CURRENT_TIMESTAMP not null,
    UpdatedTime    datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    Description    varchar(100)                       null,
    Status         int                                not null,
    Address        varchar(100)                       null,
    Birthday       datetime                           not null,
    Gender         int                                not null
);

create table users
(
    Id                 int auto_increment
        primary key,
    Nickname           varchar(50)                        not null,
    Email              varchar(50)                        not null,
    PasswordDigest     varchar(70)                        null,
    PasswordSalt       varchar(70)                        null,
    RememberDigest     varchar(70)                        null,
    Avatar             varchar(2048)                      null,
    ColorPreference    varchar(50)                        null,
    LayoutPreference   varchar(50)                        null,
    RevealedPreference varchar(50)                        null,
    CreatedTime        datetime default CURRENT_TIMESTAMP not null,
    UpdatedTime        datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    Gender             int                                not null,
    Status             int                                not null,
    Description        varchar(100)                       null,
    Address            varchar(100)                       null,
    Birthday           datetime                           not null
);

create table teams
(
    Id          int auto_increment
        primary key,
    Name        varchar(50)                        not null,
    AdminId     int                                not null,
    CreatedTime datetime default CURRENT_TIMESTAMP not null,
    UpdatedTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    constraint FK_Teams_Users_AdminId
        foreign key (AdminId) references users (Id)
            on delete cascade
);

create table cooperations
(
    UserId      int                                not null,
    TeamId      int                                not null,
    AccessCount int                                not null,
    CreatedTime datetime default CURRENT_TIMESTAMP not null,
    UpdatedTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    primary key (UserId, TeamId),
    constraint FK_Cooperations_Teams_TeamId
        foreign key (TeamId) references teams (Id)
            on delete cascade,
    constraint FK_Cooperations_Users_UserId
        foreign key (UserId) references users (Id)
            on delete cascade
);

create index IX_Cooperations_TeamId
    on cooperations (TeamId);

create table projects
(
    Id          int auto_increment
        primary key,
    Name        varchar(50)                        not null,
    Publicity   tinyint(1)                         not null,
    Icon        varchar(2048)                      null,
    TeamId      int                                not null,
    AdminId     int                                not null,
    CreatedTime datetime default CURRENT_TIMESTAMP not null,
    UpdatedTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    constraint FK_Projects_Teams_TeamId
        foreign key (TeamId) references teams (Id)
            on delete cascade
);

create table bulletinboards
(
    Id          int auto_increment
        primary key,
    description varchar(100)                       null,
    CreatedTime datetime default CURRENT_TIMESTAMP not null,
    UpdatedTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    ProjectId   int                                not null,
    constraint FK_BulletinBoards_Projects_ProjectId
        foreign key (ProjectId) references projects (Id)
            on delete cascade
);

create index IX_BulletinBoards_ProjectId
    on bulletinboards (ProjectId);

create table bulletins
(
    Id          int auto_increment
        primary key,
    Title       varchar(50)                        not null,
    Content     varchar(100)                       null,
    CreatedTime datetime default CURRENT_TIMESTAMP not null,
    UpdatedTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    BoardId     int                                not null,
    constraint FK_Bulletins_BulletinBoards_BoardId
        foreign key (BoardId) references bulletinboards (Id)
            on delete cascade
);

create table bulletinfeeds
(
    BulletinId  int                                not null,
    UserId      int                                not null,
    CreatedTime datetime default CURRENT_TIMESTAMP not null,
    UpdatedTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    primary key (UserId, BulletinId),
    constraint FK_BulletinFeeds_Bulletins_BulletinId
        foreign key (BulletinId) references bulletins (Id)
            on delete cascade,
    constraint FK_BulletinFeeds_Users_UserId
        foreign key (UserId) references users (Id)
            on delete cascade
);

create index IX_BulletinFeeds_BulletinId
    on bulletinfeeds (BulletinId);

create index IX_Bulletins_BoardId
    on bulletins (BoardId);

create table develops
(
    UserId      int                                not null,
    ProjectId   int                                not null,
    CreatedTime datetime default CURRENT_TIMESTAMP not null,
    UpdatedTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    primary key (UserId, ProjectId),
    constraint FK_Develops_Projects_ProjectId
        foreign key (ProjectId) references projects (Id)
            on delete cascade,
    constraint FK_Develops_Users_UserId
        foreign key (UserId) references users (Id)
            on delete cascade
);

create index IX_Develops_ProjectId
    on develops (ProjectId);

create table projectmemocollections
(
    Id          int auto_increment
        primary key,
    Description varchar(100)                       null,
    ProjectId   int                                not null,
    CreatedTime datetime default CURRENT_TIMESTAMP not null,
    UpdatedTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    constraint IX_ProjectMemoCollections_ProjectId
        unique (ProjectId),
    constraint FK_ProjectMemoCollections_Projects_ProjectId
        foreign key (ProjectId) references projects (Id)
            on delete cascade
);

create table projectmemos
(
    Id           int auto_increment
        primary key,
    Title        varchar(50)                        not null,
    Text         varchar(100)                       null,
    CollectionId int                                not null,
    UserId       int                                not null,
    CreatedTime  datetime default CURRENT_TIMESTAMP not null,
    UpdatedTime  datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    constraint FK_ProjectMemos_ProjectMemoCollections_CollectionId
        foreign key (CollectionId) references projectmemocollections (Id)
            on delete cascade,
    constraint FK_ProjectMemos_Users_UserId
        foreign key (UserId) references users (Id)
            on delete cascade
);

create index IX_ProjectMemos_CollectionId
    on projectmemos (CollectionId);

create index IX_ProjectMemos_UserId
    on projectmemos (UserId);

create index IX_Projects_TeamId
    on projects (TeamId);

create table taskboards
(
    Id          int auto_increment
        primary key,
    Description varchar(100)                       null,
    ProjectId   int                                not null,
    CreatedTime datetime default CURRENT_TIMESTAMP not null,
    UpdatedTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    constraint FK_TaskBoards_Projects_ProjectId
        foreign key (ProjectId) references projects (Id)
            on delete cascade
);

create index IX_TaskBoards_ProjectId
    on taskboards (ProjectId);

create table tasks
(
    Id          int auto_increment
        primary key,
    Name        varchar(50)                        not null,
    Priority    int                                not null,
    Description varchar(100)                       null,
    StartTime   datetime                           not null,
    EndTime     datetime                           not null,
    CreatedTime datetime default CURRENT_TIMESTAMP not null,
    UpdatedTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    Finished    tinyint(1)                         not null,
    BoardId     int                                not null,
    LeaderId    int                                not null,
    constraint FK_Tasks_TaskBoards_BoardId
        foreign key (BoardId) references taskboards (Id)
            on delete cascade,
    constraint FK_Tasks_Users_LeaderId
        foreign key (LeaderId) references users (Id)
            on delete cascade
);

create table subtasks
(
    Title       varchar(50)                          not null,
    TaskId      int                                  not null,
    Description varchar(100)                         null,
    Finished    tinyint(1) default 0                 not null,
    CreatedTime datetime   default CURRENT_TIMESTAMP not null,
    UpdatedTime datetime   default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    primary key (TaskId, Title),
    constraint FK_Subtasks_Tasks_TaskId
        foreign key (TaskId) references tasks (Id)
            on delete cascade
);

create table assigns
(
    TaskId      int                                not null,
    Title       varchar(50)                        not null,
    UserId      int                                not null,
    CreatedTime datetime default CURRENT_TIMESTAMP not null,
    UpdatedTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    primary key (TaskId, Title, UserId),
    constraint FK_Assigns_Subtasks_TaskId_Title
        foreign key (TaskId, Title) references subtasks (TaskId, Title)
            on delete cascade,
    constraint FK_Assigns_Users_UserId
        foreign key (UserId) references users (Id)
            on delete cascade
);

create index IX_Assigns_UserId
    on assigns (UserId);

create table taskfeeds
(
    TaskId      int                                not null,
    UserId      int                                not null,
    CreatedTime datetime default CURRENT_TIMESTAMP not null,
    UpdatedTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    primary key (UserId, TaskId),
    constraint FK_TaskFeeds_Tasks_TaskId
        foreign key (TaskId) references tasks (Id)
            on delete cascade,
    constraint FK_TaskFeeds_Users_UserId
        foreign key (UserId) references users (Id)
            on delete cascade
);

create index IX_TaskFeeds_TaskId
    on taskfeeds (TaskId);

create index IX_Tasks_BoardId
    on tasks (BoardId);

create index IX_Tasks_LeaderId
    on tasks (LeaderId);

create table tasktags
(
    TaskId      int                                not null,
    tag         varchar(20)                        not null,
    CreatedTime datetime default CURRENT_TIMESTAMP not null,
    UpdatedTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    primary key (TaskId, tag),
    constraint FK_TaskTags_Tasks_TaskId
        foreign key (TaskId) references tasks (Id)
            on delete cascade
);

create table teammemocollections
(
    Id          int auto_increment
        primary key,
    Description varchar(100)                       null,
    TeamId      int                                not null,
    CreatedTime datetime default CURRENT_TIMESTAMP not null,
    UpdatedTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    constraint FK_TeamMemoCollections_Teams_TeamId
        foreign key (TeamId) references teams (Id)
            on delete cascade
);

create index IX_TeamMemoCollections_TeamId
    on teammemocollections (TeamId);

create table teammemos
(
    Id           int auto_increment
        primary key,
    Title        varchar(50)                        not null,
    Text         varchar(100)                       null,
    CreatedTime  datetime default CURRENT_TIMESTAMP not null,
    UserId       int                                not null,
    UpdatedTime  datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    CollectionId int                                not null,
    constraint FK_TeamMemos_TeamMemoCollections_CollectionId
        foreign key (CollectionId) references teammemocollections (Id)
            on delete cascade,
    constraint FK_TeamMemos_Users_UserId
        foreign key (UserId) references users (Id)
            on delete cascade
);

create index IX_TeamMemos_CollectionId
    on teammemos (CollectionId);

create index IX_TeamMemos_UserId
    on teammemos (UserId);

create index IX_Teams_AdminId
    on teams (AdminId);


